package dododocs.dododocs.chatbot.dto;

import lombok.Getter;

@Getter
public class QuestToChatbotResponse {
    private String answer;

    private QuestToChatbotResponse() {
    }

    public QuestToChatbotResponse(final String answer) {
        this.answer = answer;
    }
}
