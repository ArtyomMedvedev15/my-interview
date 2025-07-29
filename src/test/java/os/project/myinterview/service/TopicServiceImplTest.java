package os.project.myinterview.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import os.project.myinterview.model.Topic;
import os.project.myinterview.repository.TopicRepository;
import os.project.myinterview.service.dto.TopicDto;
import os.project.myinterview.service.exception.TopicAlreadyExistsException;
import os.project.myinterview.service.exception.TopicNotFoundException;
import os.project.myinterview.service.mapper.TopicMapper;

@ExtendWith(MockitoExtension.class)
class TopicServiceImplTest {

    private static final String TOPIC_NAME = "test";

    @Mock
    private TopicRepository topicRepository;
    @Mock
    private TopicMapper topicMapper;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Test
    void createTopicTest() {
        // GIVEN
        Topic topicEntity = new Topic();
        topicEntity.setName(TOPIC_NAME);

        TopicDto topicDto = new TopicDto();
        topicDto.setName(TOPIC_NAME);

        when(topicMapper.toDto(topicEntity)).thenReturn(topicDto);
        when(topicRepository.findByName(any())).thenReturn(Optional.empty());
        when(topicRepository.save(any())).thenReturn(topicEntity);

        // WHEN
        TopicDto actualTopic = topicService.createTopic(TOPIC_NAME);

        // THEN
        assertEquals(TOPIC_NAME, actualTopic.getName());
        verify(topicRepository).findByName(TOPIC_NAME);
        verify(topicRepository).save(any());
        verify(topicMapper).toDto(topicEntity);
    }

    @Test
    void createTopicWithExceptionTest() {
        // GIVEN
        Topic topicEntity = getTopicEntity(1L, "Test");

        when(topicRepository.findByName(any())).thenReturn(Optional.of(topicEntity));

        // WHEN
        TopicAlreadyExistsException actualException = assertThrows(TopicAlreadyExistsException.class,
                () -> topicService.createTopic("Test"));

        // THEN
        assertEquals("Topic with name Test already exists.", actualException.getMessage());
        verify(topicRepository).findByName("Test");
        verify(topicRepository, times(0)).save(any());
        verifyNoInteractions(topicMapper);
    }

    @Test
    void getAllTopicsShouldReturnList() {
        // GIVEN
        Topic topic = getTopicEntity(1L, "Java");
        TopicDto dto = getTopicDto(1L, "Java");

        when(topicRepository.findAll()).thenReturn(List.of(topic));
        when(topicMapper.toDto(topic)).thenReturn(dto);

        // WHEN
        List<TopicDto> result = topicService.getAllTopics();

        // THEN
        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getName());
        verify(topicRepository).findAll();
    }

    @Test
    void findByNameShouldReturnTopic() {
        // GIVEN
        String name = "Spring";
        Topic topic = getTopicEntity(2L, name);

        TopicDto dto = getTopicDto(2L, name);

        when(topicRepository.findByName(name)).thenReturn(Optional.of(topic));
        when(topicMapper.toDto(topic)).thenReturn(dto);

        // WHEN
        Optional<TopicDto> result = topicService.findByName(name);

        // THEN
        assertTrue(result.isPresent());
        assertEquals(name, result.get().getName());
        verify(topicRepository).findByName(name);
    }

    @Test
    void getByIdShouldReturnTopic() {
        // GIVEN
        Long id = 3L;
        Topic topic = getTopicEntity(id, "Hibernate");

        TopicDto dto = getTopicDto(id, "Hibernate");

        when(topicRepository.findById(id)).thenReturn(Optional.of(topic));
        when(topicMapper.toDto(topic)).thenReturn(dto);

        // WHEN
        TopicDto result = topicService.getById(id);

        // THEN
        assertEquals("Hibernate", result.getName());
        verify(topicRepository).findById(id);
    }

    @Test
    void getByIdShouldThrowExceptionIfNotFound() {
        // GIVEN
        Long id = 999L;
        when(topicRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN | THEN
        TopicNotFoundException ex = assertThrows(TopicNotFoundException.class, () -> topicService.getById(id));
        assertTrue(ex.getMessage().contains("not found"));

        verify(topicRepository).findById(id);
    }

    @Test
    void deleteTopicShouldCallRepository() {
        // GIVEN
        Long id = 5L;

        // WHEN | THEN
        topicService.deleteTopic(id);

        verify(topicRepository).deleteById(id);
    }

    private static TopicDto getTopicDto(long id, String Java) {
        TopicDto dto = new TopicDto();
        dto.setId(id);
        dto.setName(Java);
        return dto;
    }

    private static Topic getTopicEntity(long id, String Java) {
        Topic topic = new Topic();
        topic.setId(id);
        topic.setName(Java);
        return topic;
    }
}
