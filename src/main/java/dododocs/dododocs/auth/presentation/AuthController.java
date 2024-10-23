package dododocs.dododocs.auth.presentation;

import dododocs.dododocs.auth.application.AuthService;
import dododocs.dododocs.auth.dto.OAuthUriResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/link")
    public ResponseEntity<OAuthUriResponse> generateUri() {
        return ResponseEntity.ok(new OAuthUriResponse(authService.generateUri()));
    }
}
