package com.devwiki.todo.controller.todo;

import com.devwiki.todo.dto.todo.TodoDto;
import com.devwiki.todo.dto.todo.TodoSimpleDto;
import com.devwiki.todo.service.todo.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 사용자의 모든 할 일 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoSimpleDto>> getTodosByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(todoService.getTodosByUserId(userId));
    }

    /**
     * 할 일 ID로 조회
     */
    @GetMapping("/{todoId}")
    public ResponseEntity<TodoSimpleDto> getTodoById(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.getTodoById(todoId));
    }

    /**
     * 할 일 추가
     */
    @PostMapping("")
    public ResponseEntity<TodoSimpleDto> addTodo(@Valid @RequestBody TodoDto todoDto) {
        return new ResponseEntity<>(todoService.addTodo(todoDto), HttpStatus.CREATED);
    }

    /**
     * 할 일 수정
     */
    @PutMapping("/{todoId}")
    public ResponseEntity<TodoSimpleDto> updateTodo(@PathVariable Long todoId, @Valid @RequestBody TodoDto todoDto) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, todoDto));
    }

    /**
     * 완료 ↔ 미완료 전환
     */
    @PatchMapping("/{todoId}/toggle")
    public ResponseEntity<TodoSimpleDto> toggleTodo(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.toggleTodo(todoId));
    }

    /**
     * 할 일 삭제
     */
    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.noContent().build();
    }
}
