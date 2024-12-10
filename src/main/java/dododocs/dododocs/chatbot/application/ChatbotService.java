package dododocs.dododocs.chatbot.application;

import dododocs.dododocs.analyze.domain.RepoAnalyze;
import dododocs.dododocs.analyze.domain.repository.RepoAnalyzeRepository;
import dododocs.dododocs.analyze.exception.NoExistRepoAnalyzeException;
import dododocs.dododocs.chatbot.dto.ExternalQuestToChatbotRequest;
import dododocs.dododocs.chatbot.dto.ExternalQuestToChatbotResponse;
import dododocs.dododocs.chatbot.infrastructure.ExternalChatbotClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatbotService {
    private ExternalChatbotClient externalChatbotClient;
    private RepoAnalyzeRepository repoAnalyzeRepository;

    public ExternalQuestToChatbotResponse questionToChatbotAndSaveLogs(final long registeredRepoId, final String question) {
        final RepoAnalyze repoAnalyze = repoAnalyzeRepository.findById(registeredRepoId)
                .orElseThrow(() -> new NoExistRepoAnalyzeException("레포지토리 정보가 존재하지 않습니다."));

        final ExternalQuestToChatbotRequest questToChatbotRequest = new ExternalQuestToChatbotRequest(
                repoAnalyze.getRepoUrl(),
                question,
                List.of(),
                false
        );
        return externalChatbotClient.questToChatbot(questToChatbotRequest);
    }
}
