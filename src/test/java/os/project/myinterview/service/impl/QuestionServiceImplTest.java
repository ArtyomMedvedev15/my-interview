package os.project.myinterview.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import os.project.myinterview.model.Question;
import os.project.myinterview.model.Topic;
import os.project.myinterview.model.enums.Difficulty;
import os.project.myinterview.repository.QuestionRepository;
import os.project.myinterview.repository.TopicRepository;
import os.project.myinterview.service.dto.QuestionDto;
import os.project.myinterview.service.dto.TopicDto;
import os.project.myinterview.service.exception.QuestionNotFoundException;
import os.project.myinterview.service.exception.TopicNotFoundException;
import os.project.myinterview.service.mapper.QuestionMapper;

@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private QuestionMapper questionMapper;

    @InjectMocks
    private QuestionServiceImpl questionService;

    private Topic topic;
    private Question question;
    private QuestionDto dto;

    @BeforeEach
    void setUp() {
        topic = new Topic();
        topic.setId(1L);
        topic.setName("Java");

        question = new Question();
        question.setId(1L);
        question.setText("What is JVM?");
        question.setDifficulty(Difficulty.EASY);
        question.setPractical(false);
        question.setTags("jvm,java");
        question.setExpectedAnswer("JVM is...");
        question.setTopic(topic);

        dto = new QuestionDto();
        dto.setText("What is JVM?");
        dto.setDifficulty(Difficulty.EASY);
        dto.setPractical(false);
        dto.setTags("jvm,java");
        dto.setExpectedAnswer("JVM is...");
        dto.setTopic(new TopicDto(1L, "Java", null));
    }

    @Test
    void createQuestionShouldCreateAndReturnDto() {
        // GIVEN
        when(questionMapper.toEntity(dto)).thenReturn(question);
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.toDto(question)).thenReturn(dto);

        // WHEN
        QuestionDto result = questionService.createQuestion(dto);

        // THEN
        assertEquals(dto.getText(), result.getText());
        verify(questionRepository).save(question);
    }

    @Test
    void createQuestionShouldThrowIfTopicNotFound() {
        // GIVEN
        when(questionMapper.toEntity(dto)).thenReturn(question);
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(TopicNotFoundException.class, () -> questionService.createQuestion(dto));
    }

    @Test
    void updateQuestionShouldUpdateAndReturnDto() {
        // GIVEN
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(questionRepository.save(any())).thenReturn(question);
        when(questionMapper.toDto(any())).thenReturn(dto);

        // WHEN
        QuestionDto result = questionService.updateQuestion(1L, dto);

        // THEN
        assertEquals(dto.getExpectedAnswer(), result.getExpectedAnswer());
    }

    @Test
    void updateQuestionShouldThrowIfNotFound() {
        // GIVEN
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(QuestionNotFoundException.class, () -> questionService.updateQuestion(1L, dto));
    }

    @Test
    void deleteQuestionShouldDeleteSuccessfully() {
        // GIVEN
        when(questionRepository.existsById(1L)).thenReturn(true);

        // WHEN
        questionService.deleteQuestion(1L);

        // THEN
        verify(questionRepository).deleteById(1L);
    }

    @Test
    void deleteQuestionShouldThrowIfNotFound() {
        // GIVEN
        when(questionRepository.existsById(1L)).thenReturn(false);

        // WHEN | THEN
        assertThrows(QuestionNotFoundException.class, () -> questionService.deleteQuestion(1L));
    }

    @Test
    void getByIdShouldReturnDto() {
        // GIVEN
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(questionMapper.toDto(question)).thenReturn(dto);

        // WHEN
        QuestionDto result = questionService.getById(1L);

        // THEN
        assertEquals(dto.getText(), result.getText());
    }

    @Test
    void getByIdShouldThrowIfNotFound() {
        // GIVEN
        when(questionRepository.findById(1L)).thenReturn(Optional.empty());

        // WHEN | THEN
        assertThrows(QuestionNotFoundException.class, () -> questionService.getById(1L));
    }

    @Test
    void getAllShouldReturnList() {
        // GIVEN
        when(questionRepository.findAll()).thenReturn(List.of(question));
        when(questionMapper.toDto(question)).thenReturn(dto);

        // WHEN
        List<QuestionDto> result = questionService.getAll();

        // THEN
        assertEquals(1, result.size());
    }

    @Test
    void findByTopicIdShouldReturnList() {
        // GIVEN
        when(questionRepository.findByTopic_Id(1L)).thenReturn(List.of(question));
        when(questionMapper.toDto(question)).thenReturn(dto);

        // WHEN
        List<QuestionDto> result = questionService.findByTopicId(1L);

        // THEN
        assertEquals(1, result.size());
    }

    @Test
    void findByTopicAndDifficultyShouldReturnList() {
        // GIVEN
        when(questionRepository.findAllByTopic_IdAndDifficulty(1L, Difficulty.EASY)).thenReturn(List.of(question));
        when(questionMapper.toDto(question)).thenReturn(dto);

        // WHEN
        List<QuestionDto> result = questionService.findByTopicAndDifficulty(1L, Difficulty.EASY);

        // THEN
        assertEquals(1, result.size());
    }

    @Test
    void findRandomByTopicAndDifficultyShouldReturnLimitedList() {
        // GIVEN
        when(questionRepository.findRandomByTopicAndDifficulty(1L, "EASY", 2)).thenReturn(List.of(question, question));
        when(questionMapper.toDto(any())).thenReturn(dto);

        // WHEN
        List<QuestionDto> result = questionService.findRandomByTopicAndDifficulty(1L, Difficulty.EASY, 2);

        // THEN
        assertEquals(2, result.size());
    }
}
