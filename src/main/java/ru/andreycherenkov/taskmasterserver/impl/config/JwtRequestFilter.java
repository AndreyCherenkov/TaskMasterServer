package ru.andreycherenkov.taskmasterserver.impl.config;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.andreycherenkov.taskmasterserver.impl.component.JwtUtil;

import java.io.IOException;
import java.text.ParseException;


//todo дописать на досуге
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final String secretKey;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username;
        String jwtToken;

        if (authorizationHeader != null) {
            try {
                jwtToken = authorizationHeader;
                SignedJWT signedJWT = (SignedJWT) JWTParser.parse(jwtToken);

                if (!isValidToken(signedJWT, secretKey)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("ХУЙ");
                    return;
                }

                username = jwtUtil.getUsername(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isValidToken(SignedJWT signedJWT, String secret) {
        try {
            JWSVerifier verifier = new MACVerifier(secret);
            if (!signedJWT.verify(verifier)) {
                return false;
            }

//            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
//
//            Date expirationTime = claims.getExpirationTime();
//            Date currentTime = new Date();
//
//            if (expirationTime == null || expirationTime.before(currentTime)) {
//                return false;
//            }

            return true; // Токен валиден
        } catch (Exception e) {
            e.printStackTrace();
            return false; // В случае ошибки токен считается недействительным
        }
    }

}
