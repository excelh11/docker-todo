package com.devwiki.todo.dto.category;

import com.devwiki.todo.model.category.Category;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static CategoryDetailDto toDetailDto(Category category) {
        CategoryDetailDto dto = new CategoryDetailDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setCategoryDesc(category.getCategoryDesc());
        dto.setColor(category.getColor());
        dto.setIcon(category.getIcon());
        dto.setDefaultDueDays(category.getDefaultDueDays());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }

    public static CategorySimpleDto toSimpleDto(Category category) {
        CategorySimpleDto dto = new CategorySimpleDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setColor(category.getColor());
        dto.setIcon(category.getIcon());
        return dto;
    }
}
