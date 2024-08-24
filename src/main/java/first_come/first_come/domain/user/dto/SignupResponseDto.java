package first_come.first_come.domain.user.dto;

import first_come.first_come.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignupResponseDto {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;

    public static SignupResponseDto from(User saveUser) {
        return SignupResponseDto.builder()
                .id(saveUser.getId())
                .email(saveUser.getEmail())
                .name(saveUser.getName())
                .phone(saveUser.getPhone())
                .address(saveUser.getAddress())
                .build();
    }

}
