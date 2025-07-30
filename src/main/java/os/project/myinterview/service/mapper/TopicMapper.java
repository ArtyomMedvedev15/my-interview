package os.project.myinterview.service.mapper;

import org.mapstruct.Mapper;
import os.project.myinterview.model.Topic;
import os.project.myinterview.service.dto.TopicDto;

@Mapper(componentModel = "spring", uses = QuestionMapper.class)
public interface TopicMapper {


    TopicDto toDto(Topic entity);

    Topic toEntity(TopicDto dto);
}
