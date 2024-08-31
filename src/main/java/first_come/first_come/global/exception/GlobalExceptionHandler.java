package first_come.first_come.global.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception ex) {
        RestApiException restApiException = new RestApiException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(restApiException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RestApiException> exception(CustomException ex) {
        // ErrorCode에서 상태 코드를 받아 사용
        HttpStatus status = HttpStatus.resolve(ex.getErrorCode().getStatus());
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // 상태 코드가 잘못되었을 경우 기본값 설정
        }

        RestApiException restApiException = new RestApiException(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(restApiException, status);
    }
}