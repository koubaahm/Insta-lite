package univ_rouen.fr.Insta_lite.services;

import org.springframework.security.core.Authentication;
import io.jsonwebtoken.Claims;

public interface JwtTokenService {
    String generateToken(Authentication authentication);
    boolean validateToken(String token, Authentication authentication);
    Authentication getAuthentication(String token);
    boolean isTokenExpired(String token);
    Claims extractAllClaims(String token);
}
