package team.cobblestone.gikipedia.server.v1.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.UserRole;

@Getter
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "사용자 이름은 필수입니다")
    @Size(min = 3, max = 50, message = "사용자 이름은 3-50자 사이여야 합니다")
    private String username;

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다")
    private String password;

    private UserRole role;

    public UserRequest(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
