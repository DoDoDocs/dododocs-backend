package dododocs.dododocs.analyze;

import dododocs.dododocs.config.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnalyzeControllerTest extends ControllerTestConfig {

    @DisplayName("AI 레포지토리 분석 결과를 요청하고 상태코드 200을 리턴한다.")
    @Test
    void AI_레포지토리_분석_결과를_요청하고_상태코드_200을_리턴한다() throws Exception {
        // given
        given(authService.extractMemberId(anyString())).willReturn(1L);
        given(authService.generateUri()).willReturn("https://어쩌구저쩌구");

        // when, then
        mockMvc.perform(get("/api/upload/s3"))
                .andDo(print())
                .andDo(document("analyze/upload/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(fieldWithPath("repositoryName").type(JsonFieldType.STRING).description("분석 할 레포지토리 이름 (ex. Gatsby-Starter-Haon)")),
                        requestFields(fieldWithPath("branchName").type(JsonFieldType.STRING).description("브랜치 명 (ex. main)"))
                ))
                .andExpect(status().isOk());
    }
}
