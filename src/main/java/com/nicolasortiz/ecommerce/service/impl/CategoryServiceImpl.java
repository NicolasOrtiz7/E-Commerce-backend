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
    public ProductCategory findById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new MyNotFoundException("Categoría no encontrada"));
    }

    @Override
    public void save(ProductCategory category) {
        Optional<ProductCategory> categoryFound = categoryRepository.findByName(category.getName());

        if (categoryFound.isPresent()){
            throw new MyExistingObjectException("No se puede crear 2 categorías con el mismo nombre");
        }
        categoryRepository.save(category);
    }

    @Override
    public void update(int id, ProductCategory category) {
        ProductCategory categoryFound = findById(id);
        if (categoryFound == null){
            throw new MyNotFoundException("No existe la categoría, creala antes de editarla");
        }

        category.setCategoryId(id);
        categoryRepository.save(category);
    }

    @Override
    public void delete(int id) {
        ProductCategory category = findById(id);
        categoryRepository.deleteById(id);
    }
}
