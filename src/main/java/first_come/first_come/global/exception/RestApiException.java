package first_come.first_come.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestApiException {

    private int statusCode;
    private String msg;

    public RestApiException(ErrorCode errorCode, String message) {
        this.statusCode = errorCode.getStatus();
        this.msg = message;
    }
}