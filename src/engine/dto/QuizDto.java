package engine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Integer id;
    private @NotBlank String title;
    private @NotBlank String text;
    private @NotNull @Size(min = 2) List<String> options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Builder.Default
    private Set<Integer> answer = new HashSet<>();
}
