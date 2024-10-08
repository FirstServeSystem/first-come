package first_come.first_come.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


@Component
@RequiredArgsConstructor
public class EncryptionUtils {

    /** 암호화, 복호화 기능 구현 **/

    private static final String ALGORITHM = "AES";
    private static final byte[] KEY = "1234567890123456".getBytes(); // 128-bit key

    /** AES를 사용한 개인정보 암호화
     * @param value 암호화할 개인정보
     * @return 암호화된 개인정보
     **/
    public String encrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** AES를 사용한 개인정보 복호화
     * @param value 복호화할 개인정보
     * @return 복호화된 개인정보
     **/
    public String decrypt(String value) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(KEY, ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(value));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}