package com.devwiki.todo.model.user;

import com.devwiki.todo.dto.user.UserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User(UserRequestDto createDto) {
        this.name = createDto.getName();
        this.email = createDto.getEmail();
        this.password = createDto.getPassword();
        this.gender = createDto.getGender();
        this.birthDate = createDto.getBirthDate();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 이름과 (암호화된) 비밀번호 수정
     */
    public void update(String name, String encodedPassword) {
        this.name = name;
        if (encodedPassword != null) {
            this.password = encodedPassword;
        }
        this.updatedAt = LocalDateTime.now();
    }
}
