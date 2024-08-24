package first_come.first_come.domain.user.service;

import first_come.first_come.domain.mail.EmailServiceImpl;
import first_come.first_come.domain.user.User;
import first_come.first_come.domain.user.UserRepository;
import first_come.first_come.domain.user.dto.EmailRequestDto;
import first_come.first_come.domain.user.dto.SignupRequestDto;
import first_come.first_come.domain.user.dto.SignupResponseDto;
import first_come.first_come.global.redis.RedisUtil;
import first_come.first_come.security.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EncryptionUtils encryptionUtils;
    private final EmailServiceImpl emailService;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public SignupResponseDto userSignup(SignupRequestDto requestDto) {

        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String email = encryptionUtils.encrypt(requestDto.getEmail());
        String password = passwordEncoder.encode(requestDto.getPassword());
        String name = encryptionUtils.encrypt(requestDto.getName());
        String phone = encryptionUtils.encrypt(requestDto.getPhone());
        String address = encryptionUtils.encrypt(requestDto.getAddress());

        User user = User.of(email, password,name, phone, address);
        User saveUser = userRepository.save(user);

        emailService.sendEmail(saveUser);

        return SignupResponseDto.from(saveUser);

    }

    @Override
    @Transactional
    public boolean verifyEmail(EmailRequestDto requestDto) {

        String email = requestDto.getEmail();
        String code = requestDto.getCode();

        String redisCode = redisUtil.getData(email);

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
