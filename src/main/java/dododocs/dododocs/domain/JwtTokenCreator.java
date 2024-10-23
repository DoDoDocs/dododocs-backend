package dododocs.dododocs.domain;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenCreator {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenCreator(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String createToken(final long memberId) {
        // final String accessToken = jwtTokenProvider.
        return null;
    }
}
