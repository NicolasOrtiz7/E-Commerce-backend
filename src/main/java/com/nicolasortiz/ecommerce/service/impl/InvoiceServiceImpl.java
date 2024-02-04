package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.dto.invoice.InvoiceDto;
import com.nicolasortiz.ecommerce.model.entity.Customer;
import com.nicolasortiz.ecommerce.model.entity.Order;
import com.nicolasortiz.ecommerce.model.entity.Product;
import com.nicolasortiz.ecommerce.repository.ICustomerRepository;
import com.nicolasortiz.ecommerce.repository.IProductRepository;
import com.nicolasortiz.ecommerce.service.IEmailService;
import com.nicolasortiz.ecommerce.service.IInvoiceService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

    private final IProductRepository productRepository;
    private final ICustomerRepository customerRepository;
    private final IEmailService emailService;

    @Override
//    @Async
    public void generateInvoice(Order order) throws FileNotFoundException, JRException, MessagingException {

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

        // Exporta el PDF y le asigna el nombre y la ruta donde se guardará
        String pdfName = customer.getEmail() + "-" + LocalDate.now() + "-" + Math.round(Math.random() * 1000);
        String destinationFile = "src/main/resources/static/" + pdfName + ".pdf";
        JasperExportManager.exportReportToPdfFile(report, destinationFile);

        // Envía el mail con el PDF
        String pdfFilePath = destinationFile;
        File pdfFile = new File(pdfFilePath);

        // Descomentar en producción
//        emailService.sendEmailWithPdf(customer.getEmail(), "Asunto del Correo", "Cuerpo del Correo", pdfFile);

    }

}
