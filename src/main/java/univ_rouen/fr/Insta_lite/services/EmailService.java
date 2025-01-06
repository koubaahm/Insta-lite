package univ_rouen.fr.Insta_lite.services;

import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;



@Service
public interface EmailService  {

    void sendPasswordResetEmail(String toEmail) throws MessagingException;


}
