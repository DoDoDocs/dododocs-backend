package dododocs.dododocs.chatbot;

import dododocs.dododocs.analyze.domain.RepoAnalyze;
import dododocs.dododocs.analyze.dto.UploadGitRepoContentToS3Request;
import dododocs.dododocs.chatbot.domain.ChatLog;
import dododocs.dododocs.chatbot.dto.FindChatLogResponses;
import dododocs.dododocs.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChatbotControllerTest extends ControllerTestConfig {

    @DisplayName("챗봇 대화 내역을 불러온다.")
    @Test
    void findChatbotHistory() throws Exception {
        // given
        given(jwtTokenCreator.extractMemberId(anyString())).willReturn(1L);
        given(chatbotService.findChatbotHistory(anyLong(), any()))
                .willReturn(new FindChatLogResponses(List.of(
                        new ChatLog("질문1", "대답1", new RepoAnalyze(1L, "repo-name")),
                        new ChatLog("질문1", "대답1", new RepoAnalyze(1L, "repo-name")),
                        new ChatLog("질문1", "대답1", new RepoAnalyze(1L, "repo-name"))
                )));

        // when, then
        mockMvc.perform(get("/api/chatbot/logs/{registeredRepoId}", 1L)
                    .header("Authorization", "Bearer aaaaaa.bbbbbb.cccccc")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("chatbot/find/logs/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(
                                headerWithName("Authorization").description("엑세스 토큰")
                        )
                ))
                .andExpect(status().isOk());
    }
}
