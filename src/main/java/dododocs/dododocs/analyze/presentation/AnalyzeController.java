
package dododocs.dododocs.analyze.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dododocs.dododocs.analyze.application.AnalyzeService;
import dododocs.dododocs.analyze.dto.*;
import dododocs.dododocs.auth.dto.Accessor;
import dododocs.dododocs.auth.presentation.authentication.Authentication;
import org.hibernate.sql.Update;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api")
public class AnalyzeController {
    private final AnalyzeService analyzeService;

    public AnalyzeController(final AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    // 1. 등록한 레포 리스트 뭐 되어있는지 조회 (유저당 최대 3개 레포 등록 가능)
    // 2. 레포 등록시 DTO 수정 (korean, test 코드 넣을꺼임?)
    // 3. 리드미 문자열 수정한거 DB 에 반영하기
    // => Test API 에 리드미 수정한거 반영하기

    @PostMapping("/upload/s3")
    public void uploadGithubToS3(@Authentication final Accessor accessor,
                                 @RequestBody final UploadGitRepoContentToS3Request uploadToS3Request) {
        // s3 key 값, 레포 주소 필요
        analyzeService.uploadGithubRepoToS3(uploadToS3Request, accessor.getId());
    }

    /* @PutMapping("/docs/update/{registeredRepoId}")
    public ResponseEntity<Void> updateDocsContents(@Authentication final Accessor accessor,
                                                     @PathVariable final Long registeredRepoId,
                                                     @RequestBody final UpdateDocsRequest updateDocsRequest) throws Exception {
        analyzeService.updateDocsContents(updateDocsRequest.getFileName());
        return ResponseEntity.noContent().build();
    } */


    @GetMapping("/repo/contents")
    public RepositoryContentDto getRepoContents(@Authentication final Accessor accessor,
                                                @RequestBody final FindGitRepoContentRequest findGitRepoContentRequest) throws IOException {
        return analyzeService.getRepositoryContents(accessor.getId(), findGitRepoContentRequest.getRepositoryName(), findGitRepoContentRequest.getBranchName());
    }

    // 업로딩 되고있는(= 아직 AI 로 부터 챗봇, docs, readme 중 단 하나라도 제공받지 못하는 레포가 있는 경우) 레포지토리 있으면 -> ID 값 리턴
    // else => null 리턴
}

