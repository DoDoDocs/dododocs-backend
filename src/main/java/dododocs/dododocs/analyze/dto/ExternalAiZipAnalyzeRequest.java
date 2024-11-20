package dododocs.dododocs.analyze.dto;

import lombok.Getter;

@Getter
public class ExternalAiZipAnalyzeRequest {
    // s3 key 값, 레포 주소 필요
    private String s3Key;
    private String repositoryUrl;

    private ExternalAiZipAnalyzeRequest() {
    }

    public ExternalAiZipAnalyzeRequest(final String s3Key, final String repositoryUrl) {
        this.s3Key = s3Key;
        this.repositoryUrl = repositoryUrl;
    }
}
