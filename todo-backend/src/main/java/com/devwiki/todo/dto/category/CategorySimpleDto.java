package com.devwiki.todo.dto.category;

import lombok.Data;

@Data
public class CategorySimpleDto {

    private Long categoryId;
    private String categoryName;
    private String color;
    private String icon;
}
