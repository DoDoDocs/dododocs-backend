package dododocs.dododocs.chatbot.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Table(name = "chat_log")
@Entity
public class ChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "sequence")
    private Long sequence;

    protected ChatLog() {
    }

    public ChatLog(final String question, final String answer) {
        this.question = question;
        this.answer = answer;
        this.sequence = 0L;
    }
}
