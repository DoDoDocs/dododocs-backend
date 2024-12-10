package dododocs.dododocs.chatbot.presentation;

import dododocs.dododocs.auth.dto.Accessor;
import dododocs.dododocs.auth.presentation.authentication.Authentication;
import dododocs.dododocs.chatbot.application.ChatbotService;
import dododocs.dododocs.chatbot.dto.ExternalQuestToChatbotRequest;
import dododocs.dododocs.chatbot.dto.FindChatLogResponeses;
import dododocs.dododocs.chatbot.dto.QuestToChatbotRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RequiredArgsConstructor
@RestController("/api/chatbot")
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping("/question/save/{registeredRepoId}")
    public ResponseEntity<Void> questionToChatbotAndSaveLogs(@Authentication final Accessor accessor,
                                                             @PathVariable final Long registeredRepoId,
                                                             @RequestBody final QuestToChatbotRequest questToChatbotRequest) {
        chatbotService.questionToChatbotAndSaveLogs(registeredRepoId, questToChatbotRequest.getQuestion());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logs/{registeredRepoId}")
    public ResponseEntity<FindChatLogResponeses> findChatbotHistory(@Authentication final Accessor accessor,
                                                                    @PathVariable final Long registeredRepoId,
                                                                    @PageableDefault(size = 30) final Pageable pageable) {
        return ResponseEntity.ok(chatbotService.findChatbotHistory(registeredRepoId, pageable));
    }
}
