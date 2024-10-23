package dododocs.dododocs.auth.application;

import dododocs.dododocs.domain.GithubOAuthUriProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {
    private final GithubOAuthUriProvider oAuthUriProvider;

    public AuthService(final GithubOAuthUriProvider oAuthUriProvider) {
        this.oAuthUriProvider = oAuthUriProvider;
    }

    public String generateUri() {
        return oAuthUriProvider.generateUri();
    }
}
