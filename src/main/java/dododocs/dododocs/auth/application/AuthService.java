package dododocs.dododocs.auth.application;

import dododocs.dododocs.domain.GithubOAuthUriProvider;
import dododocs.dododocs.domain.JwtTokenCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {
    private final GithubOAuthUriProvider oAuthUriProvider;
    private final JwtTokenCreator jwtTokenCreator;

    public AuthService(final GithubOAuthUriProvider oAuthUriProvider,
                       final JwtTokenCreator jwtTokenCreator) {
        this.oAuthUriProvider = oAuthUriProvider;
        this.jwtTokenCreator = jwtTokenCreator;
    }

    public String generateUri() {
        return oAuthUriProvider.generateUri();
    }

    public void a() {

    }

    private
}
