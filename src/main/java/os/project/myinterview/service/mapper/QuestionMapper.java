package os.project.myinterview.service.mapper;

import org.mapstruct.Mapper;
import os.project.myinterview.model.Question;
import os.project.myinterview.service.dto.QuestionDto;

@Mapper(uses = TopicMapper.class)
public interface QuestionMapper {

    QuestionDto toDto(Question question);

    Question toEntity(QuestionDto question);
}
