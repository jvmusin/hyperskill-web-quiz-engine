package engine.dto;

import lombok.Data;

@Data
public class FeedbackDto {
    public static final FeedbackDto SUCCESS = new FeedbackDto(true, "Congratulations, you're right!");
    public static final FeedbackDto FAIL = new FeedbackDto(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;
}
