package univ_rouen.fr.Insta_lite.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String toEmail) throws MessagingException {
        String resetLink = generatePasswordResetLink(toEmail);

        String subject = "Réinitialisation de mot de passe";
        String content = "<html><body>" +
                "<h3>Réinitialisation de mot de passe</h3>" +
                "<p>Bonjour,</p>" +
                "<p>Vous avez demandé à réinitialiser votre mot de passe. Veuillez cliquer sur le lien ci-dessous pour définir un nouveau mot de passe :</p>" +
                "<p><a href=\"" + resetLink + "\">Réinitialiser mon mot de passe</a></p>" +
                "<p>Si vous n'avez pas demandé cette réinitialisation, ignorez cet email.</p>" +
                "</body></html>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("amedkoubaa@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    private String generatePasswordResetLink(String email) {

        return "http://localhost:8089/api/users/reset-password?email=" + email;
    }
}
