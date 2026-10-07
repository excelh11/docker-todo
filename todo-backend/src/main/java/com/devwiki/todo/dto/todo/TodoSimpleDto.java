package com.devwiki.todo.dto.todo;

import com.devwiki.todo.dto.category.CategorySimpleDto;
import com.devwiki.todo.dto.user.UserResponseDto;
import com.devwiki.todo.model.todo.Priority;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TodoSimpleDto {

    private Long todoId;
    private UserResponseDto user;
    private CategorySimpleDto category;
    private String title;
    private Priority priority;
    private LocalDate dueDate;
    private boolean completed;
    /** 마감일이 지났는데 미완료인지 (화면 경고 표시용) */
    private boolean overdue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
