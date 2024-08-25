package first_come.first_come.domain.user.service;

import first_come.first_come.domain.mail.EmailService;
import first_come.first_come.domain.user.UserRepository;
import first_come.first_come.domain.user.dto.EmailRequestDto;
import first_come.first_come.domain.user.dto.SignupRequestDto;
import first_come.first_come.domain.user.dto.SignupResponseDto;
import first_come.first_come.domain.user.entity.User;
import first_come.first_come.global.redis.RedisUtil;
import first_come.first_come.security.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionUtils encryptionUtils;
    private final EmailService emailService;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public SignupResponseDto userSignup(SignupRequestDto requestDto) {

        String findEmail = encryptionUtils.encrypt(requestDto.getEmail());

        if (userRepository.findByEmail(findEmail).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        emailService.sendEmail(requestDto.getEmail());

//        String email = encryptionUtils.encrypt(requestDto.getEmail());
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String name = encryptionUtils.encrypt(requestDto.getName());
        String phone = encryptionUtils.encrypt(requestDto.getPhone());
        String address = encryptionUtils.encrypt(requestDto.getAddress());

        User user = User.of(email, password, name, phone, address);
        userRepository.save(user);

        return SignupResponseDto.from(user);

    }

    @Override
    @Transactional
    public boolean verifyEmail(EmailRequestDto requestDto) {

        String value = requestDto.getEmail();
        String redisCode = redisUtil.getData(value); // 레디스에서 인증코드 받아오기

//        String email = encryptionUtils.encrypt(value);
        String email = requestDto.getEmail();
        String code = requestDto.getCode();

        if (redisCode != null && redisCode.equals(code)) {
            User user = userRepository.findByEmail(email).orElseThrow(
                    () -> new IllegalArgumentException("없는 회원입니다."));

            user.setVerified(true);
            userRepository.save(user);
            redisUtil.deleteData(email);
            return true;
        }

        return false;
    }
}
