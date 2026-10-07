package com.devwiki.todo.repository.category;

import com.devwiki.todo.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByOrderByCreatedAtDesc();
}
