package com.mediamarktsaturn.MediaMarktSaturn.presentation.controllers;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Category;
import com.mediamarktsaturn.MediaMarktSaturn.domain.services.CategoryService;
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
@RequestMapping(value = "/categories", produces = APPLICATION_JSON_VALUE)
public class CategoryController {

    @Autowired
    private CategoryService service;


    @GetMapping()
    public ResponseEntity<List<Category>> getCategories() {
        try {
            List<Category> getAll = service.getAllCategories();
            return ResponseEntity.ok().body(getAll);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        try {
            Category category = service.getCategoryById(id);
            return ResponseEntity.ok().body(category);
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping()
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        try {
            Category created = service.createCategory(category);
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
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        try {
            Category updated = service.updateCategory(id, category);
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
    public ResponseEntity<Category> patchCategory(@PathVariable Long id, @Valid @RequestBody Map<Object, Object> fields) {
        try {
            Category patched = service.patchCategory(id, fields);
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
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        try {
            Category category = service.deleteCategory(id);
            return ResponseEntity.ok().body(category);
        } catch (NoResultException e) {
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
