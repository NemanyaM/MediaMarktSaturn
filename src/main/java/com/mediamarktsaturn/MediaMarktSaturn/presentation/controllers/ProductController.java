package com.mediamarktsaturn.MediaMarktSaturn.presentation.controllers;

import com.mediamarktsaturn.MediaMarktSaturn.commons.LocalCacheObject;
import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Product;
import com.mediamarktsaturn.MediaMarktSaturn.domain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.persistence.NoResultException;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        try {
            List<Product> getAll = service.getAllProducts();
            return ResponseEntity.ok().body(getAll);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        try {
            Product product = service.getProductById(id);
            return ResponseEntity.ok().body(product);
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        try {
            Product created = service.createProduct(product);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(created.getId())
                    .toUri();
            return ResponseEntity.created(location).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        try {
            Product updated = service.updateProduct(id, product);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(updated.getId())
                    .toUri();
            return ResponseEntity.ok().body(updated);
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable Long id, @Valid @RequestBody Map<Object, Object> fields) {
        try {
            Product patched = service.patchProduct(id, fields);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(patched.getId())
                    .toUri();
            return ResponseEntity.created(location).body(patched);
        } catch (NoResultException e) {
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        try {
            Product product = service.deleteProduct(id);
            return ResponseEntity.ok().body(product);
        } catch (NoResultException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
