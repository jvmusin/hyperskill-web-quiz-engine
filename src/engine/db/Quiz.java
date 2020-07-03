package engine.db;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String text;
    @ElementCollection
    private List<String> options;
    @ElementCollection
    @Builder.Default
    private Set<Integer> answer = new HashSet<>();

    @ManyToOne
    private User author;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    private List<Completion> completions;
}
