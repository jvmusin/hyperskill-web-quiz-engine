package engine.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompletionDto {
    private final int id;
    private final LocalDateTime completedAt;
}
