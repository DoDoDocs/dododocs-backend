package dododocs.dododocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/github")
@RestController
public class GithubController {
    private final GitHubService gitHubService;

    public GithubController(final GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/file")
    public ResponseEntity<Void> findGitRepoInfo() throws JsonProcessingException {
        gitHubService.getFileContent("msung99", "Gatsby-Starter-Haon", "gatsby-config.js", "TOKEN");
        // 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
        return ResponseEntity.noContent().build();
    }
}
