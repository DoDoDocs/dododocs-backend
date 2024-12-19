package dododocs.dododocs.chatbot.presentation;

import dododocs.dododocs.auth.dto.Accessor;
import dododocs.dododocs.auth.presentation.authentication.Authentication;
import dododocs.dododocs.chatbot.application.ChatbotService;
import dododocs.dododocs.chatbot.dto.ExternalQuestToChatbotResponse;
import dododocs.dododocs.chatbot.dto.FindChatLogResponses;
import dododocs.dododocs.chatbot.dto.QuestToChatbotRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    @Value("${ai.basic_url}")
    private String aiBasicUrl;

    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        // WebClient 초기화 시 aiBasicUrl을 사용
        this.webClient = WebClient.builder()
                .baseUrl(aiBasicUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.TEXT_EVENT_STREAM_VALUE) // SSE 헤더
                .build();
    }

    @PostMapping("/question/save/{registeredRepoId}")
    public Flux<ExternalQuestToChatbotResponse> questionToChatbotAndSaveLogs(@Authentication final Accessor accessor,
                                                                             @PathVariable final Long registeredRepoId,
                                                                             @RequestBody final QuestToChatbotRequest questToChatbotRequest) {
        return chatbotService.questionToChatbotAndSaveLogsByWebFlux(registeredRepoId, questToChatbotRequest.getQuestion()).flux();
    }


    @GetMapping("/logs/{registeredRepoId}")
    public ResponseEntity<FindChatLogResponses> findChatbotHistory(@Authentication final Accessor accessor,
                                                                   @PathVariable final Long registeredRepoId,
                                                                   @PageableDefault(size = 30) final Pageable pageable) {
        return ResponseEntity.ok(chatbotService.findChatbotHistory(registeredRepoId, pageable));
    }

    @GetMapping(value = "/stream-from-backend", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFromBackend() {
        System.out.println("AI 서버로 데이터 요청 시작...");
        System.out.println(aiBasicUrl);
        return webClient.get()
                .uri("/chat")
                .retrieve()
                .bodyToFlux(String.class) // SSE 데이터를 Flux로 수신
                .doOnNext(data -> System.out.println("AI 서버에서 수신한 데이터: " + data))
                .doOnError(error -> System.err.println("AI 서버 연결 에러: " + error.getMessage()))
                .onErrorResume(error -> Flux.just("AI 서버와 연결 중 문제가 발생했습니다."));
    }
}
