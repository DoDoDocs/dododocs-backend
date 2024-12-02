package dododocs.dododocs.analyze.dto;

import dododocs.dododocs.analyze.domain.RepoAnalyze;
import lombok.Getter;

@Getter
public class RegisteredRepoResponse {
    private String repositoryName;

    private RegisteredRepoResponse() {
    }

    public RegisteredRepoResponse(final RepoAnalyze repoAnalyze) {
        this.repositoryName = repoAnalyze.getRepositoryName();
    }
}
