package com.devwiki.todo.service.category;

import com.devwiki.todo.dto.category.CategoryDetailDto;
import com.devwiki.todo.dto.category.CategoryMapper;
import com.devwiki.todo.exception.ResourceNotFoundException;
import com.devwiki.todo.model.category.Category;
import com.devwiki.todo.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 모든 카테고리 조회 (최근 등록 순)
     */
    public List<CategoryDetailDto> getAllCategories() {
        return categoryRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(CategoryMapper::toDetailDto)
                .toList();
    }

    /**
     * 특정 카테고리 조회
     */
    public CategoryDetailDto getCategoryById(Long categoryId) {
        return CategoryMapper.toDetailDto(findCategoryById(categoryId));
    }

    /**
     * 카테고리 추가
     */
    @Transactional
    public CategoryDetailDto addCategory(CategoryDetailDto categoryDetailDto) {
        Category category = categoryRepository.save(new Category(categoryDetailDto));
        return CategoryMapper.toDetailDto(category);
    }

    /**
     * 카테고리 수정
     */
    @Transactional
    public CategoryDetailDto updateCategory(Long categoryId, CategoryDetailDto categoryDetailDto) {
        Category category = findCategoryById(categoryId);
        category.updateEntity(categoryDetailDto);
        return CategoryMapper.toDetailDto(category);
    }

    /**
     * 카테고리 삭제 (해당 카테고리의 할 일은 남고 카테고리만 비워짐)
     */
    @Transactional
    public void deleteCategory(Long categoryId) {
        categoryRepository.delete(findCategoryById(categoryId));
    }

    /**
     * 다른 서비스에서 카테고리 엔티티가 필요할 때 사용
     */
    public Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
    }
}
