package com.devwiki.todo.dto.user;

import com.devwiki.todo.model.user.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        return dto;
    }
}
