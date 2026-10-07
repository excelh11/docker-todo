package com.devwiki.todo.service.user;

import com.devwiki.todo.dto.user.UserMapper;
import com.devwiki.todo.dto.user.UserPutRequestDto;
import com.devwiki.todo.dto.user.UserRequestDto;
import com.devwiki.todo.dto.user.UserResponseDto;
import com.devwiki.todo.exception.ResourceNotFoundException;
import com.devwiki.todo.model.user.User;
import com.devwiki.todo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 모든 사용자 조회
     */
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toResponseDto).toList();
    }

    /**
     * 사용자 ID로 조회
     */
    public UserResponseDto getUserResponseById(Long userId) {
        return UserMapper.toResponseDto(findUserById(userId));
    }

    /**
     * 새로운 사용자 생성 (비밀번호는 BCrypt로 암호화해서 저장)
     */
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        User user = userRepository.save(new User(userRequestDto));
        return UserMapper.toResponseDto(user);
    }

    /**
     * 이메일과 비밀번호로 사용자 조회 (로그인)
     *
     * @return 일치하면 사용자 정보, 아니면 빈 Optional
     */
    public Optional<UserResponseDto> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(UserMapper::toResponseDto);
    }

    /**
     * 이름과 비밀번호 수정
     */
    @Transactional
    public UserResponseDto updateUser(Long userId, UserPutRequestDto userPutRequestDto) {
        User user = findUserById(userId);
        String encoded = userPutRequestDto.getPassword() != null
                ? passwordEncoder.encode(userPutRequestDto.getPassword())
                : null;
        user.update(userPutRequestDto.getName(), encoded);
        return UserMapper.toResponseDto(user);
    }

    /**
     * 사용자 삭제 (DB에서 할 일, 기록까지 함께 삭제됨)
     */
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.delete(findUserById(userId));
    }

    /**
     * 다른 서비스에서 사용자 엔티티가 필요할 때 사용
     */
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }
}
