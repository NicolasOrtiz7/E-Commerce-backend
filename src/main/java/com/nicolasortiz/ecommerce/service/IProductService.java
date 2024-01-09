package com.nicolasortiz.ecommerce.service;

import com.nicolasortiz.ecommerce.model.entity.Product;

import java.util.Optional;

public interface IProductService {
    /*

    Métodos Productos

    - Buscar todos con paginación
    - Buscar por id

    - Crear
    - Actualizar
    - Eliminar

     */

    //buscarTodos

    Optional<Product> findById(int id);

    Product save(Product product);

    Product update(int id, Product product);

    Product deleteById(int id);

}
