package dododocs.dododocs.domain;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long accessTokenValidityInSeconds;

    public JwtTokenProvider(SecretKey secretKey, long accessTokenValidityInSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
    }
}
