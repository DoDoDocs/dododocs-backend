package dododocs.dododocs.chatbot.dto;

import dododocs.dododocs.chatbot.domain.ChatLog;
import lombok.Getter;

import java.util.List;

@Getter
public class FindChatLogResponeses {

    private FindChatLogResponeses() {
    }

    public FindChatLogResponeses(final List<ChatLog> chatLogs) {

    }
}
