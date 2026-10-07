package com.devwiki.todo.dto.todo;

import com.devwiki.todo.dto.category.CategoryMapper;
import com.devwiki.todo.dto.user.UserMapper;
import com.devwiki.todo.model.todo.Todo;

public final class TodoMapper {

    private TodoMapper() {
    }

    public static TodoSimpleDto toSimpleDto(Todo todo) {
        TodoSimpleDto dto = new TodoSimpleDto();
        dto.setTodoId(todo.getTodoId());
        dto.setUser(UserMapper.toResponseDto(todo.getUser()));
        if (todo.getCategory() != null) {
            dto.setCategory(CategoryMapper.toSimpleDto(todo.getCategory()));
        }
        dto.setTitle(todo.getTitle());
        dto.setPriority(todo.getPriority());
        dto.setDueDate(todo.getDueDate());
        dto.setCompleted(todo.isCompleted());
        dto.setOverdue(todo.isOverdue());
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setUpdatedAt(todo.getUpdatedAt());
        return dto;
    }
}
