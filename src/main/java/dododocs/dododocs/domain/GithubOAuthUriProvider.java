package dododocs.dododocs.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GithubOAuthUriProvider {
    private String authorizationUri;
    private String clientId;
    private String redirectUri;
    private String responseType;

    public String generateUri() {
        return authorizationUri + "?"
                + "client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + responseType;
    }
}
