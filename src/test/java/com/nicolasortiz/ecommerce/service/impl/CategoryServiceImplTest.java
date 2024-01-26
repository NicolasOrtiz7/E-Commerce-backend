package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyExistingObjectException;
import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import com.nicolasortiz.ecommerce.repository.ICategoryRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private ICategoryRepository categoryRepository;

    // ----------------------------------------------

    private final int id = 1;
    private List<ProductCategory> mockList = new ArrayList<>();
    private ProductCategory cat1;
    private ProductCategory cat2;
    private ProductCategory cat3;
    private ProductCategory catError;


    // ----------------------------------------------

    @BeforeEach
    void setUp() {
        cat1 = ProductCategory.builder().categoryId(1).name("Remeras").image("remeras.png").build();
        cat2 = ProductCategory.builder().categoryId(2).name("Pantalones").image("pantalones.png").build();
        cat3 = ProductCategory.builder().categoryId(3).name("Camisas").image("camisas.png").build();

        catError = ProductCategory.builder().categoryId(4).name("").image("a.png").build();
        mockList = Arrays.asList(cat1, cat2, cat3);
    }

    @Test
    void findAll_Present() {
        when(categoryRepository.findAll())
                .thenReturn(mockList);

        List<ProductCategory> catList = categoryService.findAll();

        assertSame(mockList, catList);
        assertSame(3, catList.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findAll_Empty() {
        when(categoryRepository.findAll())
                .thenReturn(Collections.emptyList());

        List<ProductCategory> catList = categoryService.findAll();

        assertSame(0, catList.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findById_Present() {
        when(categoryRepository.findById(id))
                .thenReturn(Optional.ofNullable(cat1));

        ProductCategory cat = categoryService.findById(id);

        assertSame(cat1, cat);
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void findById_Empty() {
        when(categoryRepository.findById(id))
                .thenThrow(MyNotFoundException.class);

        assertThrows(MyNotFoundException.class, () ->
                categoryService.findById(id)
        );
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    void save_UniqueName() {
        when(categoryRepository.findByName("Remeras"))
                .thenReturn(Optional.empty());

        categoryService.save(cat1);

        verify(categoryRepository, times(1)).findByName(cat1.getName());
        verify(categoryRepository, times(1)).save(cat1);
    }

    @Test
    void save_ExistingName() {
        when(categoryRepository.findByName("Remeras"))
                .thenReturn(Optional.ofNullable(cat1));

        assertThrows(MyExistingObjectException.class, () ->
                categoryService.save(cat1)
        );

        verify(categoryRepository, times(1)).findByName(cat1.getName());
        verify(categoryRepository, times(0)).save(cat1);
    }

    @Test
    void save_NullName() {
        when(categoryRepository.save(catError))
                .thenThrow(ConstraintViolationException.class);

        assertThrows(ConstraintViolationException.class, () ->
                categoryService.save(catError)
        );

        verify(categoryRepository, times(0)).save(cat1);
    }

    @Test
    void update_Success() {
        // Update category "cat1"
        ProductCategory newCat = ProductCategory.builder()
                .name("Remeras updated")
                .image("remeras.png").build();

        when(categoryRepository.findById(id))
                .thenReturn(Optional.ofNullable(newCat));

        categoryService.update(cat1.getCategoryId(), newCat);

        ProductCategory catUpdated = categoryRepository.findById(id).get();

        assertSame(newCat.getName(), catUpdated.getName());
        verify(categoryRepository, times(1)).save(newCat);
    }

    @Test
    void update_ExistingName() {
        // Update cat2
        cat2 = ProductCategory.builder().categoryId(2)
                .name("Remeras") // Usa un nombre que ya existe
                .image("pantalones.png").build();

        when(categoryRepository.findById(id))
                .thenThrow(MyExistingObjectException.class);

        assertThrows(MyExistingObjectException.class, ()->
                categoryService.update(id, cat2)
        );

        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void update_CatNotFound() {
        when(categoryRepository.findById(id))
                .thenThrow(MyNotFoundException.class);

        assertThrows(MyNotFoundException.class, ()->
                categoryService.update(id, any())
        );

        verify(categoryRepository, times(0)).save(any());
    }

    @Test
    void delete_Success() {
        when(categoryRepository.findById(id))
                .thenReturn(Optional.ofNullable(cat1));

        categoryService.delete(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    void delete_NotFound() {
        when(categoryRepository.findById(id))
                .thenThrow(MyNotFoundException.class);

        assertThrows(MyNotFoundException.class, () ->
                categoryService.delete(id)
        );

        verify(categoryRepository, times(1)).findById(id);
        verify(categoryRepository, times(0)).deleteById(id);
    }

}