package com.devwiki.todo.dto.todoLog;

import com.devwiki.todo.dto.todo.TodoDto;
import lombok.Data;

import java.time.LocalDate;

/**
 * 진행 기록 추가/수정 요청
 * 예: { "todo": {"todoId": 3}, "logDate": "2026-10-07", "note": "초안 작성", "done": false }
 */
@Data
public class TodoLogDto {

    private Long todoLogId;
    private TodoDto todo;
    private LocalDate logDate;
    private String note;
    private boolean done;
}
