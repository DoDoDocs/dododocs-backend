package dododocs.dododocs.chatbot.presentation;

import dododocs.dododocs.auth.dto.Accessor;
import dododocs.dododocs.auth.presentation.authentication.Authentication;
import dododocs.dododocs.chatbot.application.ChatbotService;
import dododocs.dododocs.chatbot.dto.ExternalQuestToChatbotRequest;
import dododocs.dododocs.chatbot.dto.QuestToChatbotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("/api/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping("/question/save/{registeredRepoId}")
    public ResponseEntity<Void> questionToChatbotAndSaveLogs(@Authentication final Accessor accessor,
                                                             @PathVariable final Long registeredRepoId,
                                                             @RequestBody final QuestToChatbotRequest questToChatbotRequest) {
        chatbotService.questionToChatbotAndSaveLogs(registeredRepoId, questToChatbotRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logs/{repoId}")
    public ResponseEntity<Void> findChatbotTalkLogs(@Authentication final Accessor accessor, @PathVariable final long repoId) {
        return ResponseEntity.noContent().build();
    }
}
