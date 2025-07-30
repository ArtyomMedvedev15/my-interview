package os.project.myinterview.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import os.project.myinterview.model.Question;
import os.project.myinterview.model.enums.Difficulty;
import os.project.myinterview.service.dto.QuestionDto;

@ExtendWith(MockitoExtension.class)
class QuestionMapperTest {

    private static final long QUESTION_ID = 1L;
    private static final String QUESTION_TEXT = "Text";
    private static final String QUESTION_TAGS = "Tags";
    private static final boolean QUESTION_PRACTICE = true;
    private static final Difficulty QUESTION_DIFF = Difficulty.EASY;
    private static final String QUESTION_ANSWER = "Answer";

    private final QuestionMapperImpl mapper = new QuestionMapperImpl();

    @Test
    void mapToDtoTest() {
        // GIVEN
        Question question = new Question();
        question.setId(QUESTION_ID);
        question.setText(QUESTION_TEXT);
        question.setTags(QUESTION_TAGS);
        question.setPractical(QUESTION_PRACTICE);
        question.setDifficulty(QUESTION_DIFF);
        question.setExpectedAnswer(QUESTION_ANSWER);

        // WHEN
        QuestionDto actualDto = mapper.toDto(question);

        // THEN
        assertEquals(actualDto.getId(), question.getId());
        assertEquals(actualDto.getText(), question.getText());
        assertEquals(actualDto.getTags(), question.getTags());
        assertEquals(actualDto.isPractical(), question.isPractical());
        assertEquals(actualDto.getDifficulty(), question.getDifficulty());
        assertEquals(actualDto.getExpectedAnswer(), question.getExpectedAnswer());
    }

    @Test
    void mapToEntityTest() {
        // GIVEN
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(QUESTION_ID);
        questionDto.setText(QUESTION_TEXT);
        questionDto.setTags(QUESTION_TAGS);
        questionDto.setPractical(QUESTION_PRACTICE);
        questionDto.setDifficulty(QUESTION_DIFF);
        questionDto.setExpectedAnswer(QUESTION_ANSWER);

        // WHEN
        Question actualEntity = mapper.toEntity(questionDto);

        // THEN
        assertEquals(actualEntity.getId(), questionDto.getId());
        assertEquals(actualEntity.getText(), questionDto.getText());
        assertEquals(actualEntity.getTags(), questionDto.getTags());
        assertEquals(actualEntity.isPractical(), questionDto.isPractical());
        assertEquals(actualEntity.getDifficulty(), questionDto.getDifficulty());
        assertEquals(actualEntity.getExpectedAnswer(), questionDto.getExpectedAnswer());
    }
}
