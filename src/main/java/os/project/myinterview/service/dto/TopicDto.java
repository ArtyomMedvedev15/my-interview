package os.project.myinterview.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import os.project.myinterview.model.Question;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {

    private Long id;
    @NotBlank(message = "Название темы не может быть пустым")
    @Size(max = 255, message = "Название темы не должно превышать 255 символов")
    private String name;
    private List<Question> questions;
}
