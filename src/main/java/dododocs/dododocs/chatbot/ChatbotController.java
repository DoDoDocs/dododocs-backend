package dododocs.dododocs.chatbot;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping("/question/save")
    public ResponseEntity<Void> questionToChatbotAndSaveLogs() {
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<Void> findChatbotTalkLogs() {
        return ResponseEntity.noContent().build();
    }
}
