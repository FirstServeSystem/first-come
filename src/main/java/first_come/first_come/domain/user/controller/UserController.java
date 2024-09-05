package first_come.first_come.domain.user.controller;

import first_come.first_come.domain.user.dto.EmailRequestDto;
import first_come.first_come.domain.user.dto.SignupRequestDto;
import first_come.first_come.domain.user.dto.SignupResponseDto;
import first_come.first_come.domain.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> userSignup(@RequestBody SignupRequestDto requestDto) {
        log.info("회원가입 : {}", requestDto.getName());
        SignupResponseDto responseDto = userService.userSignup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailRequestDto requestDto) {
        log.info("이메일 인증 절차");
        boolean isVerified = userService.verifyEmail(requestDto);
        if (isVerified) {
            return ResponseEntity.status(HttpStatus.OK).body("이메일 인증 성공하였습니다");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 이메일 인증번호입니다.");
        }
    }

}
