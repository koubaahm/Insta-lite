package univ_rouen.fr.Insta_lite.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import univ_rouen.fr.Insta_lite.services.JwtTokenService;
import univ_rouen.fr.Insta_lite.services.JwtTokenServiceImpl;


import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;

    public FormLoginAuthenticationSuccessHandler(JwtTokenServiceImpl jwtTokenServiceImpl) {
        this.jwtTokenService = jwtTokenServiceImpl;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        String jwtToken = jwtTokenService.generateToken(authentication);

        System.out.println("Bearer " + jwtToken);

        // ajout de token au header
        response.setHeader("Authorization", "Bearer " + jwtToken);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Bearer " + jwtToken);
        response.getWriter().flush();
    }



}
