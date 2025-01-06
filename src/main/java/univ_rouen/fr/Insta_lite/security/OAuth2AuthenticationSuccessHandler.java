package univ_rouen.fr.Insta_lite.security;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import univ_rouen.fr.Insta_lite.models.AppUser;
import univ_rouen.fr.Insta_lite.models.User;
import univ_rouen.fr.Insta_lite.repository.UserRepository;
import univ_rouen.fr.Insta_lite.enumeration.AppRole;
import univ_rouen.fr.Insta_lite.services.EmailService;
import univ_rouen.fr.Insta_lite.services.JwtTokenServiceImpl;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final JwtTokenServiceImpl jwtTokenService;

    public OAuth2AuthenticationSuccessHandler(UserRepository userRepository, EmailService emailService, JwtTokenServiceImpl jwtTokenService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Récupération de l'utilisateur OAuth2
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");


        Optional<AppUser> existingUser = userRepository.findByEmail(email);

        AppUser appUser = existingUser.orElseGet(() -> {
            AppUser newUser = new AppUser();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setRole(AppRole.USER);
            newUser.setPassword(generateRandomPassword());
            newUser.setActive(true);

            userRepository.save(newUser);
            try {
                emailService.sendPasswordResetEmail(newUser.getEmail());
            } catch (MessagingException e) {
                throw new RuntimeException(e);

            }
            return newUser;
        });


        User user= new User(appUser);

        Authentication authenticationUser = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());


        String jwtToken = jwtTokenService.generateToken(authenticationUser);


        System.out.println("JWT généré: " + jwtToken);


        response.setHeader("Authorization", "Bearer " + jwtToken);


        System.out.println("En-tête Authorization : " + response.getHeader("Authorization"));


        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("JWT Token: " + jwtToken);
        response.getWriter().flush();
    }




    public String generateRandomPassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
    }
}

