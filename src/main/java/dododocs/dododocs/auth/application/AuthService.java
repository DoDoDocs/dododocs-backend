package dododocs.dododocs.auth.application;

import dododocs.dododocs.auth.infrastructure.GithubOAuthClient;
import dododocs.dododocs.auth.infrastructure.GithubOAuthMember;
import dododocs.dododocs.domain.GithubOAuthUriProvider;
import dododocs.dododocs.domain.JwtTokenCreator;
import dododocs.dododocs.domain.repository.MemberRepository;
import dododocs.dododocs.member.domain.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AuthService {
    private final GithubOAuthUriProvider oAuthUriProvider;
    private final JwtTokenCreator jwtTokenCreator;
    private final GithubOAuthClient githubOAuthClient;
    private final MemberRepository memberRepository;

    public AuthService(final GithubOAuthUriProvider oAuthUriProvider,
                       final JwtTokenCreator jwtTokenCreator,
                        final GithubOAuthClient githubOAuthClient,
                       final MemberRepository memberRepository) {
        this.oAuthUriProvider = oAuthUriProvider;
        this.jwtTokenCreator = jwtTokenCreator;
        this.githubOAuthClient = githubOAuthClient;
        this.memberRepository = memberRepository;
    }

    public String generateUri() {
        return oAuthUriProvider.generateUri();
    }

    public String generateTokenWithCode(final String code) {
        final GithubOAuthMember githubOAuthMember = githubOAuthClient.getOAuthMember(code);
        final Member foundMember = findOrCreateMember(githubOAuthMember);
        final String accessToken = jwtTokenCreator.createToken(foundMember.getId());
        return accessToken;
    }

    public Member findOrCreateMember(final GithubOAuthMember githubOAuthmember) {
        final String email = githubOAuthmember.getEmail();

        if(memberRepository.existsByEmail(email)) {
            memberRepository.save(generateMember(githubOAuthmember));
        }
        final Member foundMember = memberRepository.findByEmail(email);
        return foundMember;
    }

    private Member generateMember(final GithubOAuthMember githubOAuthMember) {
        return new Member(githubOAuthMember.getEmail(), githubOAuthMember.getName());
    }
}
