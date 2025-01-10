package univ_rouen.fr.Insta_lite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import univ_rouen.fr.Insta_lite.services.JwtTokenServiceImpl;
import univ_rouen.fr.Insta_lite.services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenServiceImpl jwtTokenServiceImpl;
    public final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenServiceImpl jwtTokenServiceImpl, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenServiceImpl = jwtTokenServiceImpl;
        this.userService = userService;
    }

    @Operation(summary = "récupérer les informations de l'utilisateur authentifié")
    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("username", userDetails.getUsername());
            userInfo.put("authorities", userDetails.getAuthorities());

            Long userId = userService.getIdByEmail(userDetails.getUsername());
            userInfo.put("userId", userId);

            return ResponseEntity.ok(userInfo);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur non authentifié");
    }

    @Operation(summary = "connexion user")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Parameter(description = "email", required = true) @RequestParam String username,
            @Parameter(description = "password", required = true) @RequestParam String password) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Générer le token JWT
            String token = jwtTokenServiceImpl.generateToken(authentication);

            System.out.println("Bearer " + token);

            return ResponseEntity.ok("Bearer " + token);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("mail ou mot de passe invalide");
        }
    }
}
