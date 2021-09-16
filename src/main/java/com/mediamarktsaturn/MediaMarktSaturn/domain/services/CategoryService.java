package com.mediamarktsaturn.MediaMarktSaturn.domain.services;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Category;
import com.mediamarktsaturn.MediaMarktSaturn.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.persistence.NoResultException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        Iterable<Category> items = repository.findAll();
        items.forEach(list::add);

        return list;
    }

    public Category getCategoryById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoResultException("Not found."));
    }

    public Category createCategory(Category category) {
        int duplicates = repository.findByName(category.getName());
        if (duplicates != 0) {
            throw new IllegalArgumentException("Record with the same name already exists.");
        }
        repository.save(category);

        return category;
    }

    public Category updateCategory(Long id, Category category) {
        Category old = repository.findById(id).orElseThrow(() -> new NoResultException("Not found."));
        int duplicates = repository.findByName(category.getName());
        if (duplicates != 0) {
            throw new IllegalArgumentException("Record with the same name already exists.");
        }
        Category updated = old.updateWith(category);
        repository.save(updated);

        return updated;
    }

    public Category patchCategory(Long id, Map<Object, Object> fields) {
        Optional<Category> oldCategory = repository.findById(id);
        if (oldCategory.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Category.class, key.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, oldCategory.get(), value);
            });
            return repository.save(oldCategory.get());
        }
        throw new NoResultException("Not found.");
    }

    public Category deleteCategory(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new NoResultException("Not found."));
        repository.delete(category);
        return category;
    }
}
