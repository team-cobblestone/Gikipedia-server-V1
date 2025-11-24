package team.cobblestone.gikipedia.server.v1.domain.user.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.user.dto.UserRequest;
import team.cobblestone.gikipedia.server.v1.domain.user.dto.UserResponse;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.User;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.UserRole;
import team.cobblestone.gikipedia.server.v1.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse registerUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다: " + request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + request.getEmail());
        }

        User user = User.builder().username(request.getUsername()).email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : UserRole.VIEWER).build();

        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser);
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));
        return UserResponse.from(user);
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다: " + username));
        return UserResponse.from(user);
    }

    public Page<UserResponse> searchUsers(String keyword, UserRole role, Pageable pageable) {
        return userRepository.searchUsers(keyword, role, pageable).map(UserResponse::from);
    }

    public List<UserResponse> getTopContributors(int limit) {
        return userRepository.findTopContributors(limit).stream().map(UserResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public UserResponse updateUserRole(Long id, UserRole role) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        user.updateRole(role);
        return UserResponse.from(user);
    }

    @Transactional
    public void changePassword(Long id, String currentPassword, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다"));

        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다");
        }

        user.updatePassword(passwordEncoder.encode(newPassword));
    }

}
