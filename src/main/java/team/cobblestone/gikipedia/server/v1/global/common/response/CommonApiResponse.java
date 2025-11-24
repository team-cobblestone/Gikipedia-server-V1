package team.cobblestone.gikipedia.server.v1.global.common.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonApiResponse<T> {

    @Schema(description = "상태 메시지", nullable = false, example = "OK")
    private HttpStatus status;

    @Schema(description = "상태 코드", nullable = false, example = "200")
    private int code;

    @Schema(description = "메시지", nullable = false, example = "완료되었습니다.")
    private String message;

    @Schema(description = "데이터", nullable = true)
    @JsonInclude(Include.NON_NULL)
    private T data;

    public static <T> CommonApiResponse<T> success(@Nonnull String message) {
        return new CommonApiResponse<>(HttpStatus.OK, HttpStatus.OK.value(), message, null);
    }

    public static <T> CommonApiResponse<T> success(@Nonnull String message, T data) {
        return new CommonApiResponse<>(HttpStatus.OK, HttpStatus.OK.value(), message, data);
    }

    public static <T> CommonApiResponse<T> created(@Nonnull String message) {
        return new CommonApiResponse<>(HttpStatus.CREATED, HttpStatus.CREATED.value(), message, null);
    }

    public static <T> CommonApiResponse<T> created(@Nonnull String message, T data) {
        return new CommonApiResponse<>(HttpStatus.CREATED, HttpStatus.CREATED.value(), message, data);
    }

    public static <T> CommonApiResponse<T> error(@Nonnull String message, @Nonnull HttpStatus status) {
        return new CommonApiResponse<>(status, status.value(), message, null);
    }

}