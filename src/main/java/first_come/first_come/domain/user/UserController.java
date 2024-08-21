package first_come.first_come.domain.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    @PostMapping("/signup")
    public String joinUser(@RequestBody User user) {
        return "회원가입 컨트롤러 완";
    }
}
