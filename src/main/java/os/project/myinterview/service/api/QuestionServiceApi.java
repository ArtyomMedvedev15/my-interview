package os.project.myinterview.service.api;

import java.util.List;
import os.project.myinterview.model.enums.Difficulty;
import os.project.myinterview.service.dto.QuestionDto;

public interface QuestionServiceApi {

    QuestionDto createQuestion(QuestionDto dto);

    QuestionDto updateQuestion(Long id, QuestionDto dto);

    void deleteQuestion(Long id);

    QuestionDto getById(Long id);

    List<QuestionDto> getAll();

    List<QuestionDto> findByTopicId(Long topicId);

    List<QuestionDto> findByTopicAndDifficulty(Long topicId, Difficulty difficulty);

    List<QuestionDto> findRandomByTopicAndDifficulty(Long topicId, Difficulty difficulty, int limit);
}
