package com.devwiki.todo.dto.todoLog;

import com.devwiki.todo.dto.todo.TodoSimpleDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TodoLogSimpleDto {

    private Long todoLogId;
    private TodoSimpleDto todo;
    private LocalDate logDate;
    private String note;
    private boolean done;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
