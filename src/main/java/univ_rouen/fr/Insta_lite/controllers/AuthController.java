package univ_rouen.fr.Insta_lite.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import univ_rouen.fr.Insta_lite.services.JwtTokenServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenServiceImpl jwtTokenServiceImpl;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenServiceImpl jwtTokenServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServiceImpl = jwtTokenServiceImpl;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        System.out.println("Fetching user: " + authentication.getPrincipal());
        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {

        System.out.println("Fetching user: " + username);
        // Créer une authentification basée sur les informations d'identification de l'utilisateur
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Générer un JWT
            String token = jwtTokenServiceImpl.generateToken(authentication);

            System.out.println("Generated token: " + token);

            // Retourner le JWT dans la réponse
            return ResponseEntity.ok("Bearer " + token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
