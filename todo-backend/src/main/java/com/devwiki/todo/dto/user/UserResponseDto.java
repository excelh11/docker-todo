package com.devwiki.todo.dto.user;

import lombok.Data;

/**
 * 화면으로 내려주는 사용자 정보 (비밀번호 제외)
 */
@Data
public class UserResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String gender;
}
