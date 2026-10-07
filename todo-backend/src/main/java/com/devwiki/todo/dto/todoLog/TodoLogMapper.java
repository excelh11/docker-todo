package com.devwiki.todo.dto.todoLog;

import com.devwiki.todo.dto.todo.TodoMapper;
import com.devwiki.todo.model.todoLog.TodoLog;

public final class TodoLogMapper {

    private TodoLogMapper() {
    }

    public static TodoLogSimpleDto toSimpleDto(TodoLog todoLog) {
        TodoLogSimpleDto dto = new TodoLogSimpleDto();
        dto.setTodoLogId(todoLog.getTodoLogId());
        dto.setTodo(TodoMapper.toSimpleDto(todoLog.getTodo()));
        dto.setLogDate(todoLog.getLogDate());
        dto.setNote(todoLog.getNote());
        dto.setDone(todoLog.isDone());
        dto.setCreatedAt(todoLog.getCreatedAt());
        dto.setUpdatedAt(todoLog.getUpdatedAt());
        return dto;
    }
}
