package ru.andreycherenkov.taskmasterserver.impl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.andreycherenkov.taskmasterserver.impl.component.JwtUtil;
import ru.andreycherenkov.taskmasterserver.impl.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private @Value("${jwt.secret-key}") String secretKey;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public JwtAuthenticationConfigurer jwtAuthenticationConfigurer(
//            @Value("${jwt.access-token-key}") String accessTokenKey,
//            @Value("${jwt.refresh-token-key}") String refreshTokenKey
//    ) throws ParseException, JOSEException {
//        return JwtAuthenticationConfigurer.builder()
//                .accessTokenStringSerializer(new AccessTokenJwsStringSerializer(
//                        new MACSigner(OctetSequenceKey.parse(accessTokenKey))
//                ))
//                .refreshTokenStringSerializer(new RefreshTokenJweStringSerializer(
//                        new DirectEncrypter(OctetSequenceKey.parse(refreshTokenKey))
//                ))
//                .build();
//    }

    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(secretKey, userDetailsService(), new JwtUtil());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) //todo fix it
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/users/register", "/api/v1/users/login").permitAll();
                    auth.anyRequest().authenticated();
                })
                .build();

//        return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> request.anyRequest().permitAll()).build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
