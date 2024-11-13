package ru.andreycherenkov.taskmasterserver.impl.component;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.andreycherenkov.taskmasterserver.api.dto.Token;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;
    private final long validityInMilliseconds = 3_600_000;

    public String generateToken(Token token) {
        try {
            Date now = new Date();
            Date expirationTime = new Date(now.getTime() + validityInMilliseconds);

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
            var claimsSet = new JWTClaimsSet.Builder()
                    .claim("user_id", token.getUserId())
                    .subject(token.getUsername())
                    .issueTime(now)
                    .expirationTime(expirationTime)
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claimsSet);
            signedJWT.sign(new MACSigner(secretKey));

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("Error while generating JWT", e);
        }
    }

    public boolean validateToken(String token, String username) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(secretKey);

            if (!signedJWT.verify(verifier)) {
                return false;
            }

            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expirationTime != null && expirationTime.after(new Date())
                    && signedJWT.getJWTClaimsSet().getSubject().equals(username);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}

