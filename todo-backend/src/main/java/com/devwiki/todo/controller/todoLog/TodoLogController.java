package com.devwiki.todo.controller.todoLog;

import com.devwiki.todo.dto.todoLog.TodoLogDto;
import com.devwiki.todo.dto.todoLog.TodoLogSimpleDto;
import com.devwiki.todo.service.todoLog.TodoLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo-logs")
@RequiredArgsConstructor
public class TodoLogController {

    private static final int RECENT_LOG_COUNT = 4;

    private final TodoLogService todoLogService;

    /**
     * 모든 진행 기록 조회
     */
    @GetMapping("")
    public ResponseEntity<List<TodoLogSimpleDto>> getAllTodoLogs() {
        return ResponseEntity.ok(todoLogService.getAllTodoLogs());
    }

    /**
     * 특정 사용자의 모든 진행 기록 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoLogSimpleDto>> getAllTodoLogsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(todoLogService.getAllTodoLogsByUserId(userId));
    }

    /**
     * 특정 사용자의 최근 진행 기록 조회 (최근 4건)
     */
    @GetMapping("/recent/user/{userId}")
    public ResponseEntity<List<TodoLogSimpleDto>> getRecentTodoLogsByUserId(@PathVariable Long userId) {
        List<TodoLogSimpleDto> logs = todoLogService.getAllTodoLogsByUserId(userId)
                .stream().limit(RECENT_LOG_COUNT).toList();
        return ResponseEntity.ok(logs);
    }

    /**
     * 진행 기록 ID로 조회
     */
    @GetMapping("/{todoLogId}")
    public ResponseEntity<TodoLogSimpleDto> getTodoLogById(@PathVariable Long todoLogId) {
        return ResponseEntity.ok(todoLogService.getTodoLogById(todoLogId));
    }

    /**
     * 특정 할 일의 모든 진행 기록 조회
     */
    @GetMapping("/todos/{todoId}")
    public ResponseEntity<List<TodoLogSimpleDto>> getAllTodoLogsByTodoId(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoLogService.getAllTodoLogsByTodoId(todoId));
    }

    /**
     * 진행 기록 추가
     */
    @PostMapping("")
    public ResponseEntity<TodoLogSimpleDto> addTodoLog(@RequestBody TodoLogDto todoLogDto) {
        return new ResponseEntity<>(todoLogService.addTodoLog(todoLogDto), HttpStatus.CREATED);
    }

    /**
     * 진행 기록 수정
     */
    @PutMapping("/{todoLogId}")
    public ResponseEntity<TodoLogSimpleDto> updateTodoLog(@PathVariable Long todoLogId,
                                                          @RequestBody TodoLogDto todoLogDto) {
        return ResponseEntity.ok(todoLogService.updateTodoLog(todoLogId, todoLogDto));
    }

    /**
     * 진행 기록 삭제
     */
    @DeleteMapping("/{todoLogId}")
    public ResponseEntity<Void> deleteTodoLog(@PathVariable Long todoLogId) {
        todoLogService.deleteTodoLog(todoLogId);
        return ResponseEntity.noContent().build();
    }
}
