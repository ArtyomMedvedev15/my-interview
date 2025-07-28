package os.project.myinterview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import os.project.myinterview.config.AbstractJpaConfigTest;
import os.project.myinterview.model.Question;
import os.project.myinterview.model.Topic;
import os.project.myinterview.model.enums.Difficulty;


class QuestionRepositoryTest extends AbstractJpaConfigTest {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindRandomQuestionsByTopicAndDifficulty() {
        // GIVEN
        Topic topic = getTopic("JVM");
        entityManager.persist(topic);

        Question question1 = getQuestion("Q1", topic, true, "Answer1");

        Question question2 = getQuestion("Q2", topic, false, "Answer2");

        entityManager.persist(question1);
        entityManager.persist(question2);

        // WHEN
        List<Question> result = questionRepository.findRandomByTopicAndDifficulty(topic.getId(), "EASY", 1);

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDifficulty()).isEqualTo(Difficulty.EASY);
    }

    @Test
    void shouldReturnQuestionsByTopicId() {
        // GIVEN
        Topic topic1 = getTopic("Spring");
        topic1 = topicRepository.save(topic1);

        Topic topic2 = getTopic("JVM");
        topic2 = topicRepository.save(topic2);

        Question question1 = getQuestion("Q1", topic1, true, "Answer1");
        Question question2 = getQuestion("Q2", topic1, true, "Answer2");
        Question question3 = getQuestion("Q3", topic2, true, "Answer3");

        entityManager.persist(question1);
        entityManager.persist(question2);
        entityManager.persist(question3);

        // WHEN
        List<Question> result = questionRepository.findByTopic_Id(topic1.getId());

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result).extracting("text").containsExactlyInAnyOrder("Q1", "Q2");
    }
}
