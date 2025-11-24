package team.cobblestone.gikipedia.server.v1.domain.user.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import team.cobblestone.gikipedia.server.v1.domain.user.dto.UserRequest;
import team.cobblestone.gikipedia.server.v1.domain.user.dto.UserResponse;
import team.cobblestone.gikipedia.server.v1.domain.user.entity.UserRole;
import team.cobblestone.gikipedia.server.v1.domain.user.service.UserService;
import team.cobblestone.gikipedia.server.v1.global.common.response.CommonApiResponse;

@Tag(name = "User", description = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 등록", description = "새로운 사용자를 등록합니다")
    @PostMapping("/register")
    public ResponseEntity<CommonApiResponse<UserResponse>> registerUser(@Validated @RequestBody UserRequest request) {
        UserResponse response = userService.registerUser(request);
        return ResponseEntity.ok(CommonApiResponse.created("사용자가 등록되었습니다", response));
    }

    @Operation(summary = "사용자 조회", description = "ID로 사용자를 조회합니다")
    @GetMapping("/{id}")
    public ResponseEntity<CommonApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse response = userService.getUser(id);
        return ResponseEntity.ok(CommonApiResponse.success("사용자 조회 성공", response));
    }

    @Operation(summary = "사용자 이름으로 조회", description = "사용자 이름으로 사용자를 조회합니다")
    @GetMapping("/username/{username}")
    public ResponseEntity<CommonApiResponse<UserResponse>> getUserByUsername(@PathVariable String username) {
        UserResponse response = userService.getUserByUsername(username);
        return ResponseEntity.ok(CommonApiResponse.success("사용자 조회 성공", response));
    }

    @Operation(summary = "사용자 검색", description = "키워드와 역할로 사용자를 검색합니다")
    @GetMapping("/search")
    public ResponseEntity<CommonApiResponse<Page<UserResponse>>> searchUsers(
            @RequestParam(required = false) String keyword, @RequestParam(required = false) UserRole role,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<UserResponse> response = userService.searchUsers(keyword, role, pageable);
        return ResponseEntity.ok(CommonApiResponse.success("사용자 검색 성공", response));
    }

    @Operation(summary = "상위 기여자", description = "기여도가 높은 사용자 목록을 조회합니다")
    @GetMapping("/top-contributors")
    public ResponseEntity<CommonApiResponse<List<UserResponse>>> getTopContributors(
            @RequestParam(defaultValue = "10") int limit) {
        List<UserResponse> response = userService.getTopContributors(limit);
        return ResponseEntity.ok(CommonApiResponse.success("상위 기여자 조회 성공", response));
    }

    @Operation(summary = "역할 변경", description = "사용자의 역할을 변경합니다")
    @PatchMapping("/{id}/role")
    public ResponseEntity<CommonApiResponse<UserResponse>> updateUserRole(@PathVariable Long id,
            @RequestParam UserRole role) {
        UserResponse response = userService.updateUserRole(id, role);
        return ResponseEntity.ok(CommonApiResponse.success("역할이 변경되었습니다", response));
    }

    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경합니다")
    @PatchMapping("/{id}/password")
    public ResponseEntity<CommonApiResponse<Void>> changePassword(@PathVariable Long id,
            @RequestParam String currentPassword, @RequestParam String newPassword) {
        userService.changePassword(id, currentPassword, newPassword);
        return ResponseEntity.ok(CommonApiResponse.success("비밀번호가 변경되었습니다"));
    }

}
