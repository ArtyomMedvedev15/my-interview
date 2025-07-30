package os.project.myinterview.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.project.myinterview.model.enums.Difficulty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private Long id;

    @NotBlank(message = "Question text must not be blank")
    @Size(max = 1000, message = "Question text must be at most 1000 characters")
    private String text;

    private TopicDto topic;

    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    private boolean practical;

    @Size(max = 255, message = "Tags must be at most 255 characters")
    private String tags;

    @NotBlank(message = "Expected answer must not be blank")
    @Size(max = 2000, message = "Expected answer must be at most 2000 characters")
    private String expectedAnswer;
}
