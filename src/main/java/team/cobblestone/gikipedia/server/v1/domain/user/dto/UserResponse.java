package team.cobblestone.gikipedia.server.v1.domain.user.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.User;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.UserRole;

@Getter
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private String email;

    private UserRole role;

    private LocalDateTime createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail())
                .role(user.getRole()).createdAt(user.getCreatedAt()).build();
    }

}
