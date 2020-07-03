package engine.controller;

import engine.QuizService;
import engine.dto.AnswerDto;
import engine.dto.CompletionDto;
import engine.dto.FeedbackDto;
import engine.dto.QuizDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @GetMapping("/{id}")
    public QuizDto getQuizById(@PathVariable int id) {
        return quizService.findById(id);
    }

    @GetMapping
    public Page<QuizDto> getQuizzes(@RequestParam int page) {
        return quizService.findAll(page);
    }

    @PostMapping("/{id}/solve")
    public FeedbackDto solveQuiz(@PathVariable int id, @RequestBody AnswerDto answer, Principal principal) {
        return quizService.solve(id, answer.getAnswer(), principal.getName());
    }

    @PostMapping
    public QuizDto createQuiz(@Valid @RequestBody QuizDto quiz, Principal principal) {
        return quizService.save(quiz, principal.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id, Principal principal) {
        quizService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed")
    public Page<CompletionDto> getCompletions(@RequestParam int page, Principal principal) {
        return quizService.findCompletions(principal.getName(), page);
    }
}
