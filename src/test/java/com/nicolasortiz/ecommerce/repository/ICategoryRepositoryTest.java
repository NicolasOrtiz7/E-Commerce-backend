package com.nicolasortiz.ecommerce.repository;

import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ICategoryRepositoryTest {

    @Autowired
    private ICategoryRepository categoryRepository;

    private ProductCategory category;

    @BeforeEach
    void setup(){
        category = ProductCategory.builder()
                .name("shirts")
                .image("new_shirts.png")
                .build();
    }

    @Test
    void findByName_Present(){
        categoryRepository.save(category);

        Optional<ProductCategory> cat = categoryRepository.findByName("shirts");

        assertTrue(cat.isPresent());
        assertSame(cat.get().getName(), category.getName());
        assertSame(cat.get().getImage(), category.getImage());
    }

    @Test
    void findByName_Empty(){
        categoryRepository.save(category);

        Optional<ProductCategory> cat = categoryRepository.findByName("NotFound");

        assertTrue(cat.isEmpty());
    }

}