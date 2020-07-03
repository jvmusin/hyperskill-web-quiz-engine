package engine.db;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompletionRepository extends JpaRepository<Completion, Integer> {
    Page<Completion> findAllByAuthor(User author, Pageable pageable);
}
