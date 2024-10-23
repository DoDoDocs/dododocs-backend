package dododocs.dododocs.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubOAuthUriProvider {
    private final String authorizationUri;
    private final String clientId;
    private final String redirectUri;
    private final String responseType;

    public GithubOAuthUriProvider(final String authorizationUri, final String clientId, final String redirectUri, final String responseType) {
        this.authorizationUri = authorizationUri;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
    }

    public String generateUri() {
        return authorizationUri + "?"
                + "client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + responseType;
    }
}
