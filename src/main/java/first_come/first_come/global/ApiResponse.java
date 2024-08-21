package first_come.first_come.global;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    int statusCode;
    String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;

    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiResponse(String message) {
        this.message = message;
    }

    public static <T> ApiResponse<T> ok(int statusCode, String message, T data) {
        return new ApiResponse<>(statusCode, message, data);
    }

    public static <T> ApiResponse<T> ok(int statusCode, String message) {
        return new ApiResponse<>(statusCode, message);
    }

    public static <T> ApiResponse<T> error(int statusCode, String message) {
        return new ApiResponse<>(statusCode, message);
    }

    public static <T> ApiResponse<T> error(int statusCode, String message,T data) {
        return new ApiResponse<>(statusCode, message,data);
    }

}
