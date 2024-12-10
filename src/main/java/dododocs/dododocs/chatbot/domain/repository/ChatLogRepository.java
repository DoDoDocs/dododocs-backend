package dododocs.dododocs.chatbot.domain.repository;

import dododocs.dododocs.analyze.domain.RepoAnalyze;
import dododocs.dododocs.chatbot.domain.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    List<ChatLog> findByRepoAnalyze(final RepoAnalyze repoAnalyze);
}
