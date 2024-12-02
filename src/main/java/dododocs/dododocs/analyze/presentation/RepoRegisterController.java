package dododocs.dododocs.analyze.presentation;

import dododocs.dododocs.analyze.application.RepoRegisterService;
import dododocs.dododocs.analyze.dto.FindRepoRegisterResponses;
import dododocs.dododocs.auth.dto.Accessor;
import dododocs.dododocs.auth.presentation.authentication.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RepoRegisterController {
    private final RepoRegisterService repoRegisterService;

    @GetMapping
    public FindRepoRegisterResponses findRegisteredRepos(@Authentication final Accessor accessor) {
        return repoRegisterService.findRegisteredRepos(accessor.getId());
    }
}
