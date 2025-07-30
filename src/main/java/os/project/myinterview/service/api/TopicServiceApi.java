package os.project.myinterview.service.api;

import java.util.List;
import java.util.Optional;
import os.project.myinterview.service.dto.TopicDto;

public interface TopicServiceApi {

    TopicDto createTopic(String name);

    List<TopicDto> getAllTopics();

    Optional<TopicDto> findByName(String name);

    TopicDto getById(Long id);

    void deleteTopic(Long id);
}
