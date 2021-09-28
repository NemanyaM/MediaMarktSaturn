package com.mediamarktsaturn.MediaMarktSaturn.domain.repositories;

import com.mediamarktsaturn.MediaMarktSaturn.domain.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select count(*) from PRODUCT where name = ?name", nativeQuery = true)
    int findByName(String name);

    @Query(value = "WITH RECURSIVE product_path (id, parent_id, name, onlineStatus, longDescription, shortDescription, path) AS (\n" +
            "    SELECT p.id, c.parent_id, p.name, p.online_status, p.long_description, p.short_description, c.name AS path\n" +
            "    FROM product p\n" +
            "        JOIN product_category pc ON p.id = pc.product_id\n" +
            "        JOIN category c ON pc.category_id = c.id\n" +
            "    WHERE p.id = ?id\n" +
            "    UNION ALL\n" +
            "    select anc.id, c.parent_id, anc.name, onlineStatus, longDescription, shortDescription, CONCAT(c.name, '/', anc.path)\n" +
            "    from category c\n" +
            "         JOIN product_path anc on anc.parent_id = c.id\n" +
            ")\n" +
            "SELECT id ,name, path\n" +
            "FROM product_path\n" +
            "WHERE parent_id IS null;", nativeQuery = true)
    List<Object> findProductFullPathByProductId(Long id);

    @Query(value = "WITH RECURSIVE product_path (id, parent_id, name, onlineStatus, longDescription, shortDescription, path) AS (\n" +
            "    SELECT p.id, c.parent_id, p.name, p.online_status, p.long_description, p.short_description, c.name AS path\n" +
            "    FROM product p\n" +
            "        JOIN product_category pc ON p.id = pc.product_id\n" +
            "        JOIN category c ON pc.category_id = c.id\n" +
            "    UNION ALL\n" +
            "    select anc.id, c.parent_id, anc.name, onlineStatus, longDescription, shortDescription, CONCAT(c.name, '/', anc.path)\n" +
            "    from category c\n" +
            "         JOIN product_path anc on anc.parent_id = c.id\n" +
            ")\n" +
            "SELECT id ,name, path\n" +
            "FROM product_path\n" +
            "WHERE parent_id IS null;", nativeQuery = true)
    List<Object> findProductFullPaths();
}
