package dododocs.dododocs.domain;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long accessTokenValidityInSeconds;

    public JwtTokenProvider(@Value("${security.jwt.token.secret_key}") final String secretKey,
                            @Value("${security.jwt.token.expire_length.access_token}") final long accessTokenValidityInSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }

    public String createAccessToken(final long memberId) {
        return createToken(String.valueOf(memberId), accessTokenValidityInSeconds);
    }

    public String createToken(final String payload, final long tokenValidityInSeconds) {
        final Date now = new Date();
        final Date expireDate = new Date(now.getTime() + tokenValidityInSeconds);

        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
