package com.devwiki.todo.dto.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDetailDto {

    private Long categoryId;

    @NotBlank
    private String categoryName;

    private String categoryDesc;

    @NotBlank
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "색상은 #RRGGBB 형식이어야 합니다.")
    private String color;

    @NotBlank
    private String icon;

    @Min(0)
    private int defaultDueDays;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
