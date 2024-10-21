package dododocs.dododocs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

// 레포 통째로 받아오기
@Service
public class GitHubService {
    private final String GITHUB_API_URL = "https://api.github.com/repos";
    private final RestTemplate restTemplate;

    public GitHubService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getFileContent(String owner, String repo, String path, String token) throws JsonProcessingException {
        final String url = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL)
                .pathSegment(owner, repo, "contents", path)
                .queryParam("ref", "main")  // 브랜치가 main일 경우 명시적으로 지정
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String responseBody = response.getBody();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        // Base64 인코딩된 파일 내용 추출
        String encodedContent = jsonNode.get("content").asText().replaceAll("\n", "").replaceAll("\r", "");
        byte[] decodedBytes = Base64.getDecoder().decode(encodedContent);
        String fileContent = new String(decodedBytes, StandardCharsets.UTF_8);

        System.out.println(fileContent);

        return response.getBody();
    }

}
