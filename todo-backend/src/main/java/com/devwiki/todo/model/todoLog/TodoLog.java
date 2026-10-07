package com.devwiki.todo.model.todoLog;

import com.devwiki.todo.model.todo.Todo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 할 일 진행 기록 (오늘 무엇을 했는지 남기는 일지)
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "todo_logs")
@Getter
@Builder
public class TodoLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_log_id")
    private Long todoLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "note")
    private String note;

    /** 이 기록으로 할 일을 완료했는지 */
    @Column(name = "done", nullable = false)
    private boolean done;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateEntity(LocalDate logDate, String note, boolean done) {
        this.logDate = logDate;
        this.note = note;
        this.done = done;
        this.updatedAt = LocalDateTime.now();
    }
}
