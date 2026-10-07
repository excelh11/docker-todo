package com.devwiki.todo.model.category;

import com.devwiki.todo.dto.category.CategoryDetailDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 할 일 카테고리 (예: 업무, 공부, 운동, 집안일)
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Column(name = "category_desc")
    private String categoryDesc;

    /** 카드에 표시할 색상 (예: #4F6BED) */
    @Column(name = "color", nullable = false)
    private String color;

    /** Material Design Icons 이름 (예: mdi-briefcase-outline) */
    @Column(name = "icon", nullable = false)
    private String icon;

    /** 이 카테고리로 할 일을 만들 때 기본 마감 기한(일) */
    @Column(name = "default_due_days", nullable = false)
    private int defaultDueDays;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Category(CategoryDetailDto dto) {
        this.categoryName = dto.getCategoryName();
        this.categoryDesc = dto.getCategoryDesc();
        this.color = dto.getColor();
        this.icon = dto.getIcon();
        this.defaultDueDays = dto.getDefaultDueDays();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateEntity(CategoryDetailDto dto) {
        this.categoryName = dto.getCategoryName();
        this.categoryDesc = dto.getCategoryDesc();
        this.color = dto.getColor();
        this.icon = dto.getIcon();
        this.defaultDueDays = dto.getDefaultDueDays();
        this.updatedAt = LocalDateTime.now();
    }
}
