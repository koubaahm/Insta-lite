package univ_rouen.fr.Insta_lite.security;

import io.jsonwebtoken.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import univ_rouen.fr.Insta_lite.services.JwtTokenService;



@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    // Ajouter AuthenticationManager dans le constructeur
    public JwtAuthenticationFilter(JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }


    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        String jwtToken = extractJwtFromRequest(request);

        if (jwtToken != null) {
            try {
                // Valider si le token n'est pas expiré
                if (jwtTokenService.isTokenExpired(jwtToken)) {
                    // Extraire l'Authentication directement à partir du token
                    Authentication authentication = jwtTokenService.getAuthentication(jwtToken);

                    // Valider le token avec l'authentification récupérée
                    if (jwtTokenService.validateToken(jwtToken, authentication)) {
                        // Si le token est valide, ajouter l'authentification au SecurityContext
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // En cas d'erreur, vous pouvez logger l'exception
                System.err.println("Erreur lors de la validation du JWT : " + e.getMessage());
            }
        }

        // Continuer la chaîne des filtres
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}


