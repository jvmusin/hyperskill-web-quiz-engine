package engine;

import engine.controller.QuizNotFoundException;
import engine.db.*;
import engine.dto.CompletionDto;
import engine.dto.FeedbackDto;
import engine.dto.QuizDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private static final int PAGE_SIZE = 10;

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final CompletionRepository completionRepository;

    private Quiz findQuiz(int id) {
        return quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException(id));
    }

    @Override
    public QuizDto findById(int id) {
        return quizToDto(findQuiz(id));
    }

    @Override
    public Page<QuizDto> findAll(int page) {
        return quizRepository.findAll(PageRequest.of(page, PAGE_SIZE)).map(this::quizToDto);
    }

    @Override
    public QuizDto save(QuizDto quiz, String authorEmail) {
        return quizToDto(quizRepository.save(dtoToQuiz(quiz, authorEmail)));
    }

    @Override
    @Transactional
    public void delete(int id, String authorizedEmail) {
        Quiz quiz = findQuiz(id);
        if (!quiz.getAuthor().getEmail().equals(authorizedEmail))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only quiz author can delete it");
        quizRepository.delete(quiz);
    }

    @Override
    public Page<CompletionDto> findCompletions(String authorEmail, int page) {
        User author = userRepository.findByEmail(authorEmail).orElseThrow();
        PageRequest pr = PageRequest.of(page, PAGE_SIZE, Sort.by("completedAt").descending());
        return completionRepository.findAllByAuthor(author, pr).map(this::completionToDto);
    }

    @Override
    @Transactional
    public FeedbackDto solve(int id, Set<Integer> answer, String authorEmail) {
        boolean success = findById(id).getAnswer().equals(answer);
        if (success) {
            Quiz quiz = Quiz.builder().id(id).build();
            User author = userRepository.findByEmail(authorEmail).orElseThrow();
            completionRepository.save(new Completion(null, author, quiz, LocalDateTime.now()));
        }
        return success ? FeedbackDto.SUCCESS : FeedbackDto.FAIL;
    }

    private CompletionDto completionToDto(Completion completion) {
        return new CompletionDto(completion.getQuiz().getId(), completion.getCompletedAt());
    }

    private QuizDto quizToDto(Quiz quiz) {
        return QuizDto.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .text(quiz.getText())
                .options(quiz.getOptions())
                .answer(quiz.getAnswer())
                .build();
    }

    private Quiz dtoToQuiz(QuizDto quiz, String authorEmail) {
        return Quiz.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .text(quiz.getText())
                .options(quiz.getOptions())
                .answer(quiz.getAnswer())
                .author(userRepository.findByEmail(authorEmail).orElseThrow())
                .build();
    }
}
