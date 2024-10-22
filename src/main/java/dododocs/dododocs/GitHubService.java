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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<String> getFolderContents(String owner, String repo, String path, String branch, String token) throws Exception {
        String url = UriComponentsBuilder.fromHttpUrl(GITHUB_API_URL)
                .pathSegment(owner, repo, "contents", path)
                .queryParam("ref", branch)  // 브랜치를 ref 파라미터로 지정
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBody = response.getBody();

        // JSON 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        List<String> folderContents = new ArrayList<>();

        // JSON 응답이 배열인지 확인
        if (jsonNode.isArray()) {
            // 각 파일/폴더 정보 확인
            for (JsonNode node : jsonNode) {
                // null 체크 추가
                JsonNode typeNode = node.get("type");
                JsonNode nameNode = node.get("name");
                JsonNode pathNode = node.get("path");

                if (typeNode != null && nameNode != null && pathNode != null) {
                    String type = typeNode.asText();
                    String name = nameNode.asText();
                    String filePath = pathNode.asText();

                    if ("file".equals(type)) {
                        // 파일인 경우
                        folderContents.add("File: " + name + " (" + filePath + ")");
                    } else if ("dir".equals(type)) {
                        // 폴더인 경우
                        folderContents.add("Directory: " + name + " (" + filePath + ")");
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Expected JSON array but received: " + jsonNode.toString());
        }

        return folderContents;
    }
}
