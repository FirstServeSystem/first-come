package first_come.first_come.domain.user.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private String email;
    private String password;
    private String name;
    private String phone;
    private String address;
}
