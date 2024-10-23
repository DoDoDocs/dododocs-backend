package dododocs.dododocs.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubOAuthUriProvider {
    private final String authorizationUri;
    private final String clientId;
    private final String redirectUri;
    private final String responseType = "code";

    public GithubOAuthUriProvider(@Value("oauth.github.authorize_uri") final String authorizationUri,
                              @Value("oauth.github.client_id") final String clientId,
                              @Value("oauth.github.redirect_uri") final String redirectUri) {
        this.authorizationUri = authorizationUri;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
    }

    public String generateUri() {
        return authorizationUri + "?"
                + "client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + responseType;
    }
}
