package dododocs.dododocs.chatbot.application;

import dododocs.dododocs.auth.dto.Accessor;
import dododocs.dododocs.auth.presentation.authentication.Authentication;
import dododocs.dododocs.chatbot.dto.QuestToChatbotRequest;
import dododocs.dododocs.chatbot.dto.QuestToChatbotResponse;
import dododocs.dododocs.chatbot.infrastructure.ExternalChatbotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Service
public class ChatbotService {
    private ExternalChatbotClient externalChatbotClient;
    
    public QuestToChatbotResponse questionToChatbotAndSaveLogs(final QuestToChatbotRequest questToChatbotRequest) {
        return externalChatbotClient.questToChatbot(questToChatbotRequest);
    }
}
