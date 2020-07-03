package engine;

import engine.dto.CompletionDto;
import engine.dto.FeedbackDto;
import engine.dto.QuizDto;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface QuizService {
    QuizDto findById(int id);

    Page<QuizDto> findAll(int page);

    QuizDto save(QuizDto quiz, String authorEmail);

    void delete(int id, String authorizedEmail);

    Page<CompletionDto> findCompletions(String authorEmail, int page);

    FeedbackDto solve(int id, Set<Integer> answer, String authorEmail);
}
