package dododocs.dododocs.member.dto;

import dododocs.dododocs.analyze.domain.RepoAnalyze;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FindRegisterRepoResponse {
    private String repositoryName;
    private String branchName;
    private LocalDateTime createdAt;

    private FindRegisterRepoResponse() {
    }

    public FindRegisterRepoResponse(final RepoAnalyze repoAnalyze) {
        this.repositoryName = repoAnalyze.getRepositoryName();
        this.createdAt = repoAnalyze.getCreatedAt();
        this.branchName = repoAnalyze.getBranchName();
    }
}
