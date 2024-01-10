package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.CustomerFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFavoriteRepository extends JpaRepository<CustomerFavorites, Integer> {

    // Busca todos los favoritos de un cliente
    List<CustomerFavorites> findByCustomerCustomerId(int id);

    // Busca si un producto existe en la lista de favoritos de un cliente
    @Query(value = "SELECT * FROM customer_favorites " +
            "WHERE product_id = :productId AND customer_id = :customerId", nativeQuery = true)
    Optional<CustomerFavorites> findExistingFavorite(@Param("productId") int productId,
                                                     @Param("customerId") int customerId);

    // Agrega un producto de la lista de favoritos de un cliente
    @Modifying
    @Query(value = "INSERT INTO customer_favorites (product_id, customer_id)" +
            " VALUES (:productId, :customerId)", nativeQuery = true)
    void addFavorite(@Param("productId") int productId, @Param("customerId") int customerId);

    // Elimina un producto de la lista de favoritos de un cliente
    @Modifying
    @Query(value = "DELETE FROM customer_favorites " +
            "WHERE product_id = :productId AND customer_id = :customerId", nativeQuery = true)
    void deleteFavorite(@Param("productId") int productId, @Param("customerId") int customerId);

}
