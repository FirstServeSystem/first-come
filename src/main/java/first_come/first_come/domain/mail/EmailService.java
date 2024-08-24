package first_come.first_come.domain.mail;

import first_come.first_come.domain.user.User;

public interface EmailService {

    void sendEmail(User user);
}
