package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.exception.NoStockException;
import com.nicolasortiz.ecommerce.model.dto.invoice.InvoiceDto;
import com.nicolasortiz.ecommerce.model.dto.order.OrderRequestDto;
import com.nicolasortiz.ecommerce.model.dto.order.OrderResponseDto;
import com.nicolasortiz.ecommerce.model.dto.order.request.OrderItemsReq;
import com.nicolasortiz.ecommerce.model.entity.*;
import com.nicolasortiz.ecommerce.model.mapper.OrderMapper;
import com.nicolasortiz.ecommerce.repository.ICustomerRepository;
import com.nicolasortiz.ecommerce.repository.IOrderRepository;
import com.nicolasortiz.ecommerce.repository.IProductRepository;
import com.nicolasortiz.ecommerce.service.IOrderService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IProductRepository productRepository;
    private final ICustomerRepository customerRepository;

    @Override
    public Page<OrderResponseDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).map(OrderMapper.INSTANCE::toDto);
    }

    @Override
    public OrderResponseDto findById(int id) {
        return OrderMapper.INSTANCE.toDto(orderRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("No se encontró la orden")));
    }

    @Override
    public List<OrderResponseDto> findByCustomerId(int id) {
        return orderRepository.findByCustomerCustomerId(id)
                .stream()
                .map(OrderMapper.INSTANCE::toDto)
                .toList();
    }

    @Override
    public void saveOrder(OrderRequestDto orderRequest) {
        // Mapear OrderDto a Order entity
        Order orderEntity = OrderMapper.INSTANCE.toEntity(orderRequest);

        // Verificar el Customer
        verifyCustomer(orderEntity.getCustomer());

        // Verificar que todos los productos existen y tienen stock
        List<Product> productList = verifyProducts(orderRequest.getOrderItems());

        // Establece el Product en la entidad de OrderItems
        orderEntity.setOrderItems(setProductToOrderItem(orderRequest.getOrderItems()));

        // Calcular el total de la orden
        calculateOrderTotal(orderEntity, productList);

        // Asignar la fecha actual y el order_id a la tabla de order_details
        orderEntity.setDatetime(LocalDateTime.now());
        orderEntity.getOrderDetails().setOrder(orderEntity);

        try {
            generateInvoice(orderEntity);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la factura, intente nuevamente.");
        }
        orderRepository.save(orderEntity);
    }

    @Override
    public void generateInvoice(Order order) throws FileNotFoundException, JRException {

        // Lista de productos de la orden
        List<InvoiceDto> products = new ArrayList<>();

        // Iterar la lista de productos de la orden
        order.getOrderItems()
                .forEach(item -> {
                            // Busca el producto en la base de datos para obtener el nombre
                            Product product = productRepository
                                    .findById(item.getProduct().getProductId())
                                    .orElseThrow(() -> new MyNotFoundException("Producto no encontrado"));

                            // Agrega el producto a la lista
                            products.add(InvoiceDto.builder()
                                    .productId(item.getProduct().getProductId())
                                    .name(product.getName())
                                    .quantity(item.getQuantity())
                                    .price(product.getPrice())
                                    .subtotal(item.getQuantity() * product.getPrice())
                                    .total(order.getOrderDetails().getTotal())
                                    .build());
                        }
                );

        // Crea una fuente de datos para el reporte a partir de la List<InvoicesDto>
        JRBeanCollectionDataSource collection = new JRBeanCollectionDataSource(products);

        // Compila el reporte
        JasperReport compileReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/templates/FacturaCompra.jrxml"));

        // Buscar cliente para obtener sus datos y asignarlos en la factura
        Customer customer = customerRepository.findById(order.getCustomer().getCustomerId())
                .orElseThrow(() -> new MyNotFoundException("Cliente no encontrado"));

        // Parámetros extras para la factura
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", order.getOrderDetails().getTotal());
        map.put("customerName", customer.getName());
        map.put("customerEmail", customer.getEmail());
        map.put("customerPhone", customer.getPhone());
        map.put("city", order.getOrderDetails().getCity());
        map.put("address", order.getOrderDetails().getAddress());

        // Llena el reporte con datos y crea un objeto JasperPrint
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, collection);

        // Exporta el PDF y le asigna el nombre
        JasperExportManager.exportReportToPdfFile(report,
                customer.getEmail() + "-" + LocalDate.now() + "-" + Math.round(Math.random() * 1000) + ".pdf");
    }

    // --------------------------------------------------------

    // Verificar que el cliente está registrado y su email coincide
    private void verifyCustomer(Customer customer) {
        // Buscar Customer en base de datos por ID
        Customer customerDB = customerRepository.findById(customer.getCustomerId())
                .orElseThrow(() -> new MyNotFoundException("El cliente no está registrado"));

        // Verificar que el email de la base de datos es el mismo que el que hace la petición
        if (!customerDB.getEmail().equals(customer.getEmail())) {
            throw new RuntimeException("El cliente no coincide");
        }
    }

    // Verificar si los productos existen y tienen stock
    private List<Product> verifyProducts(List<OrderItemsReq> productIds) {
        List<Product> productList = new ArrayList<>();
        Map<Integer, String> outOfStock = new HashMap<>();

        productIds.forEach(prod -> {
            // Verifica que la cantidad sea mayor que 0
            if (prod.getQuantity() < 1) {
                throw new NoStockException("La cantidad mínima de compra es 1"); // Crear exception personalizada para este error
            }
            // Verificar si existe el Producto
            Product product = productRepository.findById(prod.getProductId())
                    .orElseThrow(() -> new MyNotFoundException("No existe el producto: " + prod.getProductId()));

            // Verificar si el producto tiene Stock
            if (prod.getQuantity() > product.getProductStock().getQuantity()) {
                outOfStock.put(prod.getProductId(), product.getName());
            }

            // Actualizar Stock
            int updatedStock = product.getProductStock().getQuantity() - prod.getQuantity();
            product.getProductStock().setQuantity(updatedStock);

            productList.add(product);

        });

//        if (productList.isEmpty()){ // crear una clase de exception nueva para este error
//            throw new NoStockException("Stock no disponible");
//        }
        if (!outOfStock.isEmpty()) {
            throw new NoStockException("No existe stock de los siguientes productos: " + outOfStock);
        }
        return productList;
    }

    // Asigna el Product en la entidad de OrderItems (porque este solo recibía el productId y no el Product Entity)
    private List<OrderItems> setProductToOrderItem(List<OrderItemsReq> orderReq) {
        return orderReq.stream()
                .map(prod -> OrderItems.builder()
                        .quantity(prod.getQuantity())
                        .product(Product.builder()
                                .productId(prod.getProductId())
                                .build())
                        .build()).toList();
    }

    // Calcula el precio total de la Order
    private void calculateOrderTotal(Order orderEntity, List<Product> productList) {
        long total = 0;

        // Recorrer la lista de productos
        for (int i = 0; i < orderEntity.getOrderItems().size(); i++) {
            OrderItems item = orderEntity.getOrderItems().get(i);
            item.setOrder(orderEntity); // Asignar order_id a la tabla de order_items

            total += item.getQuantity() * productList.get(i).getPrice(); // Calcular total
        }
        orderEntity.getOrderDetails().setTotal(total);
    }
}
