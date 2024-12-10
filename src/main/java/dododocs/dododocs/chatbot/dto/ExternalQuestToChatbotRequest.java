package dododocs.dododocs.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class ExternalQuestToChatbotRequest {

    @JsonProperty("repo_url")
    private String repoUrl;

    @JsonProperty("query")
    private String query;

    @JsonProperty("chat_history")
    private List<ChatLog> chatHistory;

    private boolean stream;

    @Getter
    public class ChatLog {
        private String question;
        private String answer;

        private ChatLog() {
        }

        public ChatLog(final String question, final String answer) {
            this.question = question;
            this.answer = answer;
        }
    }

    private ExternalQuestToChatbotRequest() {
    }

    public ExternalQuestToChatbotRequest(final String repoUrl, final String query, final List<ChatLog> chatHistory, final boolean stream) {
        this.repoUrl = repoUrl;
        this.query = query;
        this.chatHistory = chatHistory;
        this.stream = stream;
    }
}
