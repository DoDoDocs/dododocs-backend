package dododocs.dododocs.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dododocs.dododocs.auth.application.AuthService;
import dododocs.dododocs.auth.infrastructure.GithubOAuthClient;
import dododocs.dododocs.auth.presentation.AuthController;
import dododocs.dododocs.auth.presentation.authentication.AuthenticationBearerExtractor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WebMvcTest({
        AuthController.class,
})
@Import(TestConfig.class)
@ActiveProfiles("test")
public abstract class ControllerTestConfig {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthService authService;

    @Mock
    protected GithubOAuthClient githubOAuthClient;

    @MockBean
    protected AuthenticationBearerExtractor authenticationBearerExtractor;
}
