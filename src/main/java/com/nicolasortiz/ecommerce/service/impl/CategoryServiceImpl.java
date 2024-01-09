package com.nicolasortiz.ecommerce.service.impl;

import com.nicolasortiz.ecommerce.exception.MyExistingObjectException;
import com.nicolasortiz.ecommerce.exception.MyNotFoundException;
import com.nicolasortiz.ecommerce.model.entity.ProductCategory;
import com.nicolasortiz.ecommerce.repository.ICategoryRepository;
import com.nicolasortiz.ecommerce.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public List<ProductCategory> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<ProductCategory> findById(int id) {
        return Optional.ofNullable(categoryRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Categoría no encontrada")));
    }

    @Override
    public ProductCategory save(ProductCategory category) {
        Optional<ProductCategory> categoryFound =
                Optional.ofNullable(categoryRepository.findByName(category.getName()));

        if (categoryFound.isPresent()){
            throw new MyExistingObjectException("No se puede crear 2 categorías con el mismo nombre");
        }
        return categoryRepository.save(category);
    }

    @Override
    public ProductCategory update(int id, ProductCategory category) {
        Optional<ProductCategory> categoryFound = findById(id);
        if (categoryFound.isEmpty()){
            throw new MyNotFoundException("No existe la categoría, creala antes de editarla");
        }

        category.setCategoryId(id);
        return save(category);
    }

    @Override
    public ProductCategory delete(int id) {
        Optional<ProductCategory> category = findById(id);
        categoryRepository.deleteById(id);
        return category.get();
    }
}
