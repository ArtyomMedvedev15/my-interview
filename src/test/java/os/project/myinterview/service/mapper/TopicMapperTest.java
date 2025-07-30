package os.project.myinterview.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import os.project.myinterview.model.Topic;
import os.project.myinterview.service.dto.TopicDto;

@ExtendWith(MockitoExtension.class)
class TopicMapperTest {

    private static final String TOPIC_NAME = "Name";
    private static final long TOPIC_ID = 1L;

    @InjectMocks
    private TopicMapperImpl topicMapper;

    @Test
    void mapToTopicEntityTest() {
        // GIVEN
        TopicDto topicDto = new TopicDto();
        topicDto.setName(TOPIC_NAME);
        topicDto.setId(1L);

        // WHEN
        Topic actualEntity = topicMapper.toEntity(topicDto);

        // THEN
        assertEquals(topicDto.getId(), actualEntity.getId());
        assertEquals(topicDto.getName(), actualEntity.getName());
    }

    @Test
    void mapToTopicDtoTest() {
        // GIVEN
        Topic topicEntity = new Topic();
        topicEntity.setName(TOPIC_NAME);
        topicEntity.setId(TOPIC_ID);

        // WHEN
        TopicDto actualDto = topicMapper.toDto(topicEntity);

        // THEN
        assertEquals(topicEntity.getId(), actualDto.getId());
        assertEquals(topicEntity.getName(), actualDto.getName());
    }
}