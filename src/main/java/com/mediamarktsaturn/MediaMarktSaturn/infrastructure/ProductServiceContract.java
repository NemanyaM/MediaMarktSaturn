package com.mediamarktsaturn.MediaMarktSaturn.infrastructure;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Category;
import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Product;

import java.util.List;
import java.util.Map;

public interface ProductServiceContract {

    List<Product> getAll();

    Category getById(Long id);

    Category create(Product product);

    Category update(Long id, Product product);

    Category patch(Long id, Map<Object, Object> fields);

    Category delete(Long id);

}

