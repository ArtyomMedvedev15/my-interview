package os.project.myinterview.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import os.project.myinterview.model.Question;
import os.project.myinterview.model.Topic;
import os.project.myinterview.model.enums.Difficulty;
import os.project.myinterview.repository.QuestionRepository;
import os.project.myinterview.repository.TopicRepository;
import os.project.myinterview.service.api.QuestionServiceApi;
import os.project.myinterview.service.dto.QuestionDto;
import os.project.myinterview.service.exception.QuestionNotFoundException;
import os.project.myinterview.service.exception.TopicNotFoundException;
import os.project.myinterview.service.mapper.QuestionMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionServiceApi {

    private final QuestionRepository questionRepository;
    private final TopicRepository topicRepository;
    private final QuestionMapper questionMapper;

    @Override
    public QuestionDto createQuestion(QuestionDto dto) {
        Question question = questionMapper.toEntity(dto);
        question.setTopic(resolveTopicFromDto(dto));

        Question saved = questionRepository.save(question);
        log.info("Created question with id {}", saved.getId());
        return questionMapper.toDto(saved);
    }

    @Override
    public QuestionDto updateQuestion(Long id, QuestionDto dto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        question.setText(dto.getText());
        question.setDifficulty(dto.getDifficulty());
        question.setExpectedAnswer(dto.getExpectedAnswer());
        question.setTags(dto.getTags());
        question.setPractical(dto.isPractical());
        question.setTopic(resolveTopicFromDto(dto));

        Question updated = questionRepository.save(question);
        log.info("Updated question with id {}", updated.getId());
        return questionMapper.toDto(updated);
    }

    @Override
    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new QuestionNotFoundException("Question not found");
        }
        questionRepository.deleteById(id);
        log.info("Deleted question with id {}", id);
    }

    @Override
    public QuestionDto getById(Long id) {
        return questionRepository.findById(id)
                .map(questionMapper::toDto)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
    }

    @Override
    public List<QuestionDto> getAll() {
        return questionRepository.findAll()
                .stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @Override
    public List<QuestionDto> findByTopicId(Long topicId) {
        log.info("Get questions by topic id {}", topicId);
        return questionRepository.findByTopic_Id(topicId)
                .stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @Override
    public List<QuestionDto> findByTopicAndDifficulty(Long topicId, Difficulty difficulty) {
        log.info("Get questions by topic id {} and difficulty {}", topicId, difficulty);
        return questionRepository.findAllByTopic_IdAndDifficulty(topicId, difficulty)
                .stream()
                .map(questionMapper::toDto)
                .toList();
    }

    @Override
    public List<QuestionDto> findRandomByTopicAndDifficulty(Long topicId, Difficulty difficulty, int limit) {
        String difficultyStr = difficulty.name();
        log.info("Get random questions by topic id {} and difficulty {} with limit {}", topicId, difficultyStr, limit);
        return questionRepository.findRandomByTopicAndDifficulty(topicId, difficultyStr, limit)
                .stream()
                .map(questionMapper::toDto)
                .toList();
    }

    private Topic resolveTopicFromDto(QuestionDto dto) {
        if (dto.getTopic() != null && dto.getTopic().getId() != null) {
            return topicRepository.findById(dto.getTopic().getId())
                    .orElseThrow(() -> new TopicNotFoundException("Topic not found"));
        }
        return null;
    }
}
