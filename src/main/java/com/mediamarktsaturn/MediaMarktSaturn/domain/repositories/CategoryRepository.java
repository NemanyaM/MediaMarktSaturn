package com.mediamarktsaturn.MediaMarktSaturn.domain.repositories;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "select count(*) from category where name = ?1", nativeQuery = true)
    int findByName(String name);
}
