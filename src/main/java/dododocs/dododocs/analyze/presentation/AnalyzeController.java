package dododocs.dododocs.analyze.presentation;

import dododocs.dododocs.analyze.application.AnalyzeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/analyze")
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    public AnalyzeController(final AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping
    public ResponseEntity<Void> analyzeGithubRepo() {
        return ResponseEntity.ok().build();
    }
}
