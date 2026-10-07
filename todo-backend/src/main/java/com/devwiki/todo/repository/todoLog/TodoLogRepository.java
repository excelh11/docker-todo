package com.devwiki.todo.repository.todoLog;

import com.devwiki.todo.model.todoLog.TodoLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoLogRepository extends JpaRepository<TodoLog, Long> {

    List<TodoLog> findAllByTodo_TodoIdOrderByLogDateDescTodoLogIdDesc(Long todoId);

    List<TodoLog> findAllByTodo_User_UserIdOrderByLogDateDescTodoLogIdDesc(Long userId);
}
