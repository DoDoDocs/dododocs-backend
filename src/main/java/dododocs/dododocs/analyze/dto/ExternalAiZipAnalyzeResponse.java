package dododocs.dododocs.analyze.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ExternalAiZipAnalyzeResponse {

    @JsonProperty("readm3_s3_key")
    private String readM3S3Key;

    @JsonProperty("docs_s3_key")
    private String docsS3Key;

    private ExternalAiZipAnalyzeResponse() {
    }

    public ExternalAiZipAnalyzeResponse(final String readM3S3Key, final String docsS3Key) {
        this.readM3S3Key = readM3S3Key;
        this.docsS3Key = docsS3Key;
    }
}
