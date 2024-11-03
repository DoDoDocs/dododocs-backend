
package dododocs.dododocs.analyze.presentation;

import dododocs.dododocs.analyze.application.AnalyzeService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/download")
public class AnalyzeController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String githubApiUrl = "https://api.github.com/repos/{owner}/{repo}/contents/{path}";

    @GetMapping("/github-zip")
    public ResponseEntity<Resource> downloadGithubFolderAsZip() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // zip 파일에 대한 ZipOutputStream 생성
        try (ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
            // GitHub API에서 특정 폴더의 파일 목록을 가져옴
            String owner = "msung99";
            String repo = "Gatsby-Starter-Haon";
            String path = "gatsby-config.js"; // ex) src/main/resources
            String url = githubApiUrl.replace("{owner}", owner)
                    .replace("{repo}", repo)
                    .replace("{path}", path);

            // API 호출
            var response = restTemplate.getForObject(url, Object[].class);

            // 각 파일에 대해 다운로드 및 압축 추가
            for (Object file : response) {
                var fileData = (Map<String, Object>) file;
                String downloadUrl = (String) fileData.get("download_url");
                String fileName = (String) fileData.get("name");

                try (InputStream inputStream = new URL(downloadUrl).openStream()) {
                    zipOut.putNextEntry(new ZipEntry(fileName));
                    inputStream.transferTo(zipOut);
                    zipOut.closeEntry();
                }
            }
        }

        // ByteArrayResource로 변환하여 HTTP 응답으로 반환
        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"github_folder.zip\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
