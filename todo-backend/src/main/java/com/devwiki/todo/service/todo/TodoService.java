package com.devwiki.todo.service.todo;

import com.devwiki.todo.dto.todo.TodoDto;
import com.devwiki.todo.dto.todo.TodoMapper;
import com.devwiki.todo.dto.todo.TodoSimpleDto;
import com.devwiki.todo.exception.ResourceNotFoundException;
import com.devwiki.todo.model.category.Category;
import com.devwiki.todo.model.todo.Priority;
import com.devwiki.todo.model.todo.Todo;
import com.devwiki.todo.model.user.User;
import com.devwiki.todo.repository.todo.TodoRepository;
import com.devwiki.todo.service.category.CategoryService;
import com.devwiki.todo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    /**
     * 특정 사용자의 모든 할 일 조회 (미완료 → 마감일 빠른 순)
     */
    public List<TodoSimpleDto> getTodosByUserId(Long userId) {
        userService.findUserById(userId);
        return todoRepository.findAllByUser_UserIdOrderByCompletedAscDueDateAscTodoIdAsc(userId)
                .stream()
                .map(TodoMapper::toSimpleDto)
                .toList();
    }

    /**
     * 특정 할 일 조회
     */
    public TodoSimpleDto getTodoById(Long todoId) {
        return TodoMapper.toSimpleDto(findTodoById(todoId));
    }

    /**
     * 할 일 추가
     * 마감일을 비워 두면 카테고리의 기본 마감 기한(일)으로 자동 설정
     */
    @Transactional
    public TodoSimpleDto addTodo(TodoDto todoDto) {
        if (todoDto.getUser() == null || todoDto.getUser().getUserId() == null) {
            throw new IllegalArgumentException("user.userId 는 필수입니다.");
        }
        User user = userService.findUserById(todoDto.getUser().getUserId());
        Category category = findCategoryOrNull(todoDto);

        LocalDate dueDate = todoDto.getDueDate();
        if (dueDate == null && category != null) {
            dueDate = LocalDate.now().plusDays(category.getDefaultDueDays());
        }

        Todo todo = Todo.builder()
                .user(user)
                .category(category)
                .title(todoDto.getTitle())
                .priority(priorityOrDefault(todoDto.getPriority()))
                .dueDate(dueDate)
                .completed(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return TodoMapper.toSimpleDto(todoRepository.save(todo));
    }

    /**
     * 할 일 수정 (카테고리, 제목, 중요도, 마감일, 완료 여부)
     */
    @Transactional
    public TodoSimpleDto updateTodo(Long todoId, TodoDto todoDto) {
        Todo todo = findTodoById(todoId);
        todo.updateEntity(findCategoryOrNull(todoDto), todoDto.getTitle(),
                priorityOrDefault(todoDto.getPriority()), todoDto.getDueDate());
        todo.changeCompleted(todoDto.isCompleted());
        return TodoMapper.toSimpleDto(todo);
    }

    /**
     * 완료 ↔ 미완료 전환 (체크박스)
     */
    @Transactional
    public TodoSimpleDto toggleTodo(Long todoId) {
        Todo todo = findTodoById(todoId);
        todo.changeCompleted(!todo.isCompleted());
        return TodoMapper.toSimpleDto(todo);
    }

    /**
     * 완료 처리 (진행 기록에서 '완료함'으로 남겼을 때 사용)
     */
    @Transactional
    public void completeTodo(Long todoId) {
        findTodoById(todoId).changeCompleted(true);
    }

    /**
     * 할 일 삭제 (DB에서 진행 기록도 함께 삭제됨)
     */
    @Transactional
    public void deleteTodo(Long todoId) {
        todoRepository.delete(findTodoById(todoId));
    }

    /**
     * 다른 서비스에서 할 일 엔티티가 필요할 때 사용
     */
    public Todo findTodoById(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo", "id", todoId));
    }

    private Category findCategoryOrNull(TodoDto todoDto) {
        if (todoDto.getCategory() == null || todoDto.getCategory().getCategoryId() == null) {
            return null;
        }
        return categoryService.findCategoryById(todoDto.getCategory().getCategoryId());
    }

    private Priority priorityOrDefault(Priority priority) {
        return priority != null ? priority : Priority.MEDIUM;
    }
}
