package dododocs.dododocs.analyze.application;

import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Service
public class AnalyzeService {

    private final AmazonS3Client amazonS3Client;

    // GitHub 레포지토리를 ZIP 파일로 가져와 S3에 업로드
    public void uploadGithubRepoToS3(String owner, String repo, String branch, String bucketName, String s3Key) throws IOException {
        // GitHub 레포지토리를 ZIP 파일로 다운로드
        String downloadUrl = String.format("https://github.com/%s/%s/archive/refs/heads/%s.zip", owner, repo, branch);

        // ZIP 파일을 임시 디렉토리에 저장
        File tempFile = File.createTempFile(repo + "-" + branch, ".zip");
        downloadFileFromUrl(downloadUrl, tempFile);

        // S3에 업로드
        amazonS3Client.putObject(bucketName, s3Key, tempFile);

        // 업로드 후 임시 파일 삭제
        tempFile.delete();
    }

    // URL에서 파일 다운로드
    private void downloadFileFromUrl(String downloadUrl, File destinationFile) throws IOException {
        URL url = new URL(downloadUrl);
        URLConnection connection = url.openConnection();
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}