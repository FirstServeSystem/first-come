package first_come.first_come.domain.mail;

import first_come.first_come.domain.user.User;
import first_come.first_come.global.redis.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    @Override
    @Async
    public void sendEmail(User user) {

        String verificationCode = generateVerificationCode();
        sendAuthEmail(user.getEmail(), verificationCode);

        // 유효 시간(5분) { email : verificationCode } 저장
        redisUtil.setDataExpire(user.getEmail(), verificationCode, 60 * 5L);
    }


    private void sendAuthEmail(String email, String verificationCode) {

        String subject = "이메일 인증 번호 전송";
        String text = "회원 가입을 위한 인증번호는 " + verificationCode + "입니다. <br/>";

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);  //포함된 텍스트가 HTML이라는 의미로 true.

            mailSender.send(mimeMessage);

        }  catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private String generateVerificationCode() {
        // 여기서 랜덤한 인증번호를 생성
        // 예: 6자리 숫자
        return String.valueOf((int)((Math.random() * 900000) + 100000));
    }
}