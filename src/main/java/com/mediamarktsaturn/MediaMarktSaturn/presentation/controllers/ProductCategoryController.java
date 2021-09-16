package com.mediamarktsaturn.MediaMarktSaturn.presentation.controllers;

import com.mediamarktsaturn.MediaMarktSaturn.commons.LocalCacheObject;
import com.mediamarktsaturn.MediaMarktSaturn.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/product-category/paths", produces = APPLICATION_JSON_VALUE)
public class ProductCategoryController {

    private static final long USER_OBJECT_CACHE_TTL = 1000 * 12;
    private LocalCacheObject<List<Object>> cache;

    @Autowired
    private ProductService service;

    @GetMapping()
    public Optional<List<Object>> getAllPaths() {
        if (Objects.isNull(this.cache)) {
            this.cache = new LocalCacheObject<>(USER_OBJECT_CACHE_TTL);
        }
        return Optional.of(this.cache.getAndSet(() -> service.findProductFullPaths()).get());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<Object>> findById(@PathVariable Long productId) {
        try {
            List<Object> product = service.findProductFullPathByProductId(productId);
            return ResponseEntity.ok().body(product);
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
