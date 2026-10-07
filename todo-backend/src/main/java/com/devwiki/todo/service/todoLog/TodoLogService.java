package com.devwiki.todo.service.todoLog;

import com.devwiki.todo.dto.todoLog.TodoLogDto;
import com.devwiki.todo.dto.todoLog.TodoLogMapper;
import com.devwiki.todo.dto.todoLog.TodoLogSimpleDto;
import com.devwiki.todo.exception.ResourceNotFoundException;
import com.devwiki.todo.model.todo.Todo;
import com.devwiki.todo.model.todoLog.TodoLog;
import com.devwiki.todo.repository.todoLog.TodoLogRepository;
import com.devwiki.todo.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoLogService {

    private final TodoLogRepository todoLogRepository;
    private final TodoService todoService;

    /**
     * 모든 진행 기록 조회
     */
    public List<TodoLogSimpleDto> getAllTodoLogs() {
        return todoLogRepository.findAll().stream().map(TodoLogMapper::toSimpleDto).toList();
    }

    /**
     * 사용자의 모든 진행 기록 조회 (최신 순)
     */
    public List<TodoLogSimpleDto> getAllTodoLogsByUserId(Long userId) {
        return todoLogRepository.findAllByTodo_User_UserIdOrderByLogDateDescTodoLogIdDesc(userId)
                .stream().map(TodoLogMapper::toSimpleDto).toList();
    }

    /**
     * 진행 기록 ID로 조회
     */
    public TodoLogSimpleDto getTodoLogById(Long todoLogId) {
        return TodoLogMapper.toSimpleDto(findTodoLogById(todoLogId));
    }

    /**
     * 특정 할 일의 모든 진행 기록 조회
     */
    public List<TodoLogSimpleDto> getAllTodoLogsByTodoId(Long todoId) {
        return todoLogRepository.findAllByTodo_TodoIdOrderByLogDateDescTodoLogIdDesc(todoId)
                .stream().map(TodoLogMapper::toSimpleDto).toList();
    }

    /**
     * 진행 기록 추가
     * '완료함(done)'으로 기록하면 해당 할 일도 완료 처리
     */
    @Transactional
    public TodoLogSimpleDto addTodoLog(TodoLogDto todoLogDto) {
        if (todoLogDto.getTodo() == null || todoLogDto.getTodo().getTodoId() == null) {
            throw new IllegalArgumentException("todo.todoId 는 필수입니다.");
        }
        Todo todo = todoService.findTodoById(todoLogDto.getTodo().getTodoId());

        TodoLog todoLog = TodoLog.builder()
                .todo(todo)
                .logDate(todoLogDto.getLogDate() != null ? todoLogDto.getLogDate() : LocalDate.now())
                .note(todoLogDto.getNote())
                .done(todoLogDto.isDone())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        todoLogRepository.save(todoLog);

        if (todoLog.isDone()) {
            todoService.completeTodo(todo.getTodoId());
        }
        return TodoLogMapper.toSimpleDto(todoLog);
    }

    /**
     * 진행 기록 수정
     */
    @Transactional
    public TodoLogSimpleDto updateTodoLog(Long todoLogId, TodoLogDto todoLogDto) {
        TodoLog todoLog = findTodoLogById(todoLogId);
        LocalDate logDate = todoLogDto.getLogDate() != null ? todoLogDto.getLogDate() : todoLog.getLogDate();
        todoLog.updateEntity(logDate, todoLogDto.getNote(), todoLogDto.isDone());
        if (todoLog.isDone()) {
            todoService.completeTodo(todoLog.getTodo().getTodoId());
        }
        return TodoLogMapper.toSimpleDto(todoLog);
    }

    /**
     * 진행 기록 삭제
     */
    @Transactional
    public void deleteTodoLog(Long todoLogId) {
        todoLogRepository.delete(findTodoLogById(todoLogId));
    }

    private TodoLog findTodoLogById(Long todoLogId) {
        return todoLogRepository.findById(todoLogId)
                .orElseThrow(() -> new ResourceNotFoundException("TodoLog", "id", todoLogId));
    }
}
