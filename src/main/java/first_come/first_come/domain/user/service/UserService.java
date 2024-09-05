package first_come.first_come.domain.user.service;

import first_come.first_come.domain.user.dto.EmailRequestDto;
import first_come.first_come.domain.user.dto.SignupRequestDto;
import first_come.first_come.domain.user.dto.SignupResponseDto;

public interface UserService {

    SignupResponseDto userSignup(SignupRequestDto requestDto);

    boolean verifyEmail(EmailRequestDto requestDto);
}
