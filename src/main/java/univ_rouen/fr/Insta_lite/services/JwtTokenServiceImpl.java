package univ_rouen.fr.Insta_lite.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import univ_rouen.fr.Insta_lite.models.AppUser;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY;

    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtTokenServiceImpl(CustomUserDetailsService userService) {
        this.userDetailsService = userService;
    }

    @Override
    public String generateToken(Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Base64.getDecoder()
                .decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(bytes, "HmacSHA256");
    }

    @Override
    public boolean validateToken(String token, Authentication authentication) {
        AppUser user = (AppUser) authentication.getPrincipal();
        String email = extractAllClaims(token).getSubject();
        return (email.equals(user.getEmail()) && !isTokenExpired(token));
    }

    @Override
    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);
        String email = claims.getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

