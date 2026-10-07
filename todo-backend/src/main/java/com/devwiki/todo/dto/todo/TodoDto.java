package com.devwiki.todo.dto.todo;

import com.devwiki.todo.dto.category.CategorySimpleDto;
import com.devwiki.todo.dto.user.UserDto;
import com.devwiki.todo.model.todo.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

/**
 * 할 일 추가/수정 요청
 * 예: { "user": {"userId": 1}, "category": {"categoryId": 2}, "title": "보고서 쓰기", "priority": "HIGH", "dueDate": "2026-10-10" }
 */
@Data
public class TodoDto {

    private Long todoId;
    private UserDto user;
    private CategorySimpleDto category;

    @NotBlank
    private String title;

    private Priority priority;
    private LocalDate dueDate;
    private boolean completed;
}
