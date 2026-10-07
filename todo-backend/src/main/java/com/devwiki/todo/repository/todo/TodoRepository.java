package com.devwiki.todo.repository.todo;

import com.devwiki.todo.model.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    /** 미완료 먼저, 그 안에서 마감일이 빠른 순 */
    List<Todo> findAllByUser_UserIdOrderByCompletedAscDueDateAscTodoIdAsc(Long userId);
}
