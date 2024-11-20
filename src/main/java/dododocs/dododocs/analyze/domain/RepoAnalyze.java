package dododocs.dododocs.analyze.domain;

import dododocs.dododocs.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Table(name = "repo_analyze")
@Getter
@Entity
public class RepoAnalyze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repository_name")
    private String repositoryName;

    @Column(name = "readme_key")
    private String readMeKey;

    @Column(name = "docs_key")
    private String docsKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    protected RepoAnalyze() {
    }

    public RepoAnalyze(final String repositoryName, final String readMeKey, final String docsKey, final Member member) {
        this.repositoryName = repositoryName;
        this.readMeKey = readMeKey;
        this.docsKey = docsKey;
        this.member = member;
    }
}
