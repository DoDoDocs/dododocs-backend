
package dododocs.dododocs.analyze.presentation;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/download")
public class AnalyzeController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String githubApiUrl = "https://api.github.com/repos/{owner}/{repo}/contents/{path}";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/github-zip")
    public ResponseEntity<Resource> downloadGithubFolderAsZip() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
            // GitHub API에서 특정 폴더의 파일 목록을 가져옴
            String owner = "msung99";
            String repo = "Gatsby-Starter-Haon";
            String path = ""; // 예: src/main/resources
            String url = githubApiUrl.replace("{owner}", owner)
                    .replace("{repo}", repo)
                    .replace("{path}", path);

            // API 호출
            Object response = restTemplate.getForObject(url, Object.class);
            System.out.println("API 응답 데이터: " + response);  // 응답 데이터 로그 추가

            // 응답이 배열인지 확인하고 처리
            if (response instanceof List) {
                List<Map<String, Object>> fileList = objectMapper.convertValue(response, new TypeReference<List<Map<String, Object>>>() {});

                // 파일 목록이 비어있는지 확인
                if (fileList.isEmpty()) {
                    System.out.println("파일 목록이 비어 있습니다.");
                } else {
                    System.out.println("파일 목록이 성공적으로 로드되었습니다.");
                }

                for (Map<String, Object> fileData : fileList) {
                    String downloadUrl = (String) fileData.get("download_url");
                    String fileName = (String) fileData.get("name");

                    // downloadUrl이 null인지 확인
                    if (downloadUrl != null) {
                        System.out.println("Adding file to ZIP: " + fileName + " from " + downloadUrl); // 디버깅 로그
                        try (InputStream inputStream = new URL(downloadUrl).openStream()) {
                            zipOut.putNextEntry(new ZipEntry(fileName));
                            inputStream.transferTo(zipOut);
                            zipOut.closeEntry();
                        } catch (Exception e) {
                            System.err.println("파일을 추가하는 중 오류 발생: " + fileName);
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("download_url is null for file: " + fileName);
                    }
                }
            } else {
                System.out.println("응답이 파일 목록이 아닙니다.");
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
