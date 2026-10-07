package com.devwiki.todo.controller.category;

import com.devwiki.todo.dto.category.CategoryDetailDto;
import com.devwiki.todo.service.category.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 모든 카테고리 조회
     */
    @GetMapping("")
    public ResponseEntity<List<CategoryDetailDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     * 카테고리 ID로 조회
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDto> getCategoryById(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    /**
     * 카테고리 추가
     */
    @PostMapping("")
    public ResponseEntity<CategoryDetailDto> addCategory(@Valid @RequestBody CategoryDetailDto categoryDetailDto) {
        return new ResponseEntity<>(categoryService.addCategory(categoryDetailDto), HttpStatus.CREATED);
    }

    /**
     * 카테고리 수정
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDetailDto> updateCategory(@PathVariable Long categoryId,
                                                            @Valid @RequestBody CategoryDetailDto categoryDetailDto) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryId, categoryDetailDto));
    }

    /**
     * 카테고리 삭제
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
