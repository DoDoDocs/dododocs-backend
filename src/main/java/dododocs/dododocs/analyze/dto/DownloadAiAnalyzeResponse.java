package dododocs.dododocs.analyze.dto;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class DownloadAiAnalyzeResponse {
    private List<Map<String, String>> analyzeResults;

    private DownloadAiAnalyzeResponse() {
    }

    public DownloadAiAnalyzeResponse(final List<Map<String, String>> analyzeResults) {
        this.analyzeResults = analyzeResults;
    }
}
