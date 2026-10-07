package com.devwiki.todo.dto.user;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 요청 본문에서 사용자를 가리킬 때 사용 (예: { "user": { "userId": 1 } })
 */
@Data
public class UserDto {

    private Long userId;
    private String name;
    private String email;
    private String gender;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
