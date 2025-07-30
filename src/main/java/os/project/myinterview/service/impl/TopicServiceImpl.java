package os.project.myinterview.service.impl;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import os.project.myinterview.model.Topic;
import os.project.myinterview.repository.TopicRepository;
import os.project.myinterview.service.api.TopicServiceApi;
import os.project.myinterview.service.dto.TopicDto;
import os.project.myinterview.service.exception.TopicAlreadyExistsException;
import os.project.myinterview.service.exception.TopicNotFoundException;
import os.project.myinterview.service.mapper.TopicMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class TopicServiceImpl implements TopicServiceApi {

    private final TopicMapper topicMapper;
    private final TopicRepository topicRepository;

    @Override
    public TopicDto createTopic(String name) {
        validateTopic(name);

        Topic topic = new Topic();
        topic.setName(name);

        Topic savedTopic = topicRepository.save(topic);

        log.info("Create topic with name {} and id {}", savedTopic.getName(), savedTopic.getId());
        return topicMapper.toDto(savedTopic);
    }

    private void validateTopic(String name) {
        if (topicRepository.findByName(name).isPresent()) {
            String errorMessage = String.format("Topic with name %s already exists.", name);
            throw new TopicAlreadyExistsException(errorMessage);
        }
    }

    @Override
    public List<TopicDto> getAllTopics() {
        List<TopicDto> topicDtoList = topicRepository.findAll().stream().map(topicMapper::toDto).toList();
        log.info("Get all topics with size {}", topicDtoList.size());
        return topicDtoList;
    }

    @Override
    public Optional<TopicDto> findByName(String name) {
        Optional<TopicDto> topicDto = topicRepository.findByName(name).map(topicMapper::toDto);
        log.info("Get topic by name {}", name);
        return topicDto;
    }

    @Override
    public TopicDto getById(Long id) {
        TopicDto topicDtoById = topicRepository.findById(id)
                .map(topicMapper::toDto)
                .orElseThrow(() -> new TopicNotFoundException(String.format("Topic with id %s not found", id)));
        log.info("Get topic by id {}", topicDtoById);
        return topicDtoById;
    }

    @Override
    public void deleteTopic(Long id) {
        topicRepository.deleteById(id);
        log.info("Remove topic with id {}", id);
    }
}
