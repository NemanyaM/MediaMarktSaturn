package com.mediamarktsaturn.MediaMarktSaturn.domain.services;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Product;
import com.mediamarktsaturn.MediaMarktSaturn.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.persistence.NoResultException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        Iterable<Product> items = repository.findAll();
        items.forEach(list::add);

        return list;
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NoResultException("Not found."));
    }

    public Product createProduct(Product product) {
        int duplicates = repository.findByName(product.getName());
        if (duplicates != 0) {
            throw new IllegalArgumentException("Record with the same name already exists.");
        }

        return repository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Product old = repository.findById(id).orElseThrow(() -> new NoResultException("Not found."));
        int duplicates = repository.findByName(product.getName());
        if (duplicates != 0) {
            throw new IllegalArgumentException("Record with the same name already exists.");
        }
        Product updated = old.updateWith(product);

        return repository.save(updated);
    }

    public Product patchProduct(Long id, Map<Object, Object> fields) {
        Optional<Product> oldProduct = repository.findById(id);
        if (oldProduct.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Product.class, key.toString());
                field.setAccessible(true);
                ReflectionUtils.setField(field, oldProduct.get(), value);
            });
            return repository.save(oldProduct.get());
        }
        throw new NoResultException("Not found.");
    }

    public Product deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NoResultException("Not found."));
        repository.delete(product);
        return product;
    }

    public List<Object> findProductFullPathByProductId(Long id) {
        return Optional.ofNullable(repository.findProductFullPathByProductId(id))
                .orElseThrow(() -> new NoResultException("Not found."));
    }

    public List<Object> findProductFullPaths() {
        return Optional.ofNullable(repository.findProductFullPaths())
                .orElseThrow(() -> new NoResultException("Not found."));
    }
}
