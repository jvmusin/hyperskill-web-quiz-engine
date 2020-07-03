package engine.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Completion {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private User author;
    @ManyToOne
    private Quiz quiz;
    private LocalDateTime completedAt;
}
