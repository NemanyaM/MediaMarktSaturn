package com.mediamarktsaturn.MediaMarktSaturn.infrastructure;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Category;

import java.util.List;
import java.util.Map;

public interface CategoryServiceContract {

    List<Category> getAll();

    Category getById(Long id);

    Category create(Category category);

    Category update(Long id, Category category);

    Category patch(Long id, Map<Object, Object> fields);

    Category delete(Long id);
}
