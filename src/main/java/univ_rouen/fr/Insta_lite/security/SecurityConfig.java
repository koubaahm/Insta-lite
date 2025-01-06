package univ_rouen.fr.Insta_lite.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import univ_rouen.fr.Insta_lite.services.CustomUserDetailsService;

import univ_rouen.fr.Insta_lite.services.JwtTokenService;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final OAuth2AuthenticationSuccessHandler oauth2SuccessHandler;
    private final FormLoginAuthenticationSuccessHandler formLoginSuccessHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtTokenService jwtTokenService,
                          OAuth2AuthenticationSuccessHandler oauth2SuccessHandler,
                          FormLoginAuthenticationSuccessHandler formLoginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.oauth2SuccessHandler = oauth2SuccessHandler;
        this.formLoginSuccessHandler = formLoginSuccessHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
        return new JwtAuthenticationFilter(jwtTokenService, authenticationManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/oauth2/authorization/google", "/login/oauth2/code/google", "/api/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(oauth2SuccessHandler)
                )
                .formLogin(form -> form
                        .loginPage("/api/auth/login")
                        .permitAll()
                        .defaultSuccessUrl("/api/auth/user", true)
                        .successHandler(formLoginSuccessHandler)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/").invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .addFilterBefore(jwtAuthenticationFilter(jwtTokenService, authenticationManager(http)), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}














/*    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }*/


