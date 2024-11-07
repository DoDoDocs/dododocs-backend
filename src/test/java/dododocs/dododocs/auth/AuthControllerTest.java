package dododocs.dododocs.auth;


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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends ControllerTestConfig {

    @DisplayName("소셜 로그인을 위한 링크와 상태코드 200을 리턴한다.")
    @Test
    void generateSocialLoginLink() throws Exception {
        // given
        given(authService.generateUri()).willReturn("qweqwe");

        // when, then
        mockMvc.perform(get("/api/auth/link"))
                .andDo(print())
                .andDo(document("auth/generate/link",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(fieldWithPath("oauthUri").type(JsonFieldType.STRING).description("OAuth 소셜 로그인 링크"))
                ))
                .andExpect(status().isOk());
    }
}
