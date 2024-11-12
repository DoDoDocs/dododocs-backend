package dododocs.dododocs.test;

import dododocs.dododocs.auth.domain.repository.MemberRepository;
import dododocs.dododocs.member.domain.Member;
import dododocs.dododocs.test.dto.CreateMemberRequest;
import dododocs.dododocs.test.dto.FindDbTestResponse;
import dododocs.dododocs.test.dto.FindTrueTestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiTestController {
    private final MemberRepository memberRepository;

    public ApiTestController(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/server")
    public ResponseEntity<FindTrueTestResponse> findTrueResponse() {
        return ResponseEntity.ok(new FindTrueTestResponse("ok!"));
    }

    @GetMapping("/db")
    public ResponseEntity<FindDbTestResponse> findDBResponse() {
        return ResponseEntity.ok(new FindDbTestResponse(1L, "Hello DKOS!"));
    }

    @PostMapping("/dbinsert")
    public ResponseEntity<Void> insertMember() {
        Member member = memberRepository.save(new Member("email"));
        System.out.println(member.getId());
        System.out.println(member.getEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dbfind")
    public ResponseEntity<Member> findMember() {
        return ResponseEntity.ok(memberRepository.findById(1L).orElse(null));
    }

    @GetMapping("/proxy")
    public ResponseEntity<Void> findProxy() {
        return ResponseEntity.ok().build();
    }
}
