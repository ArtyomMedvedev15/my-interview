package os.project.myinterview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import os.project.myinterview.config.AbstractJpaConfigTest;
import os.project.myinterview.model.Topic;

class TopicRepositoryTest extends AbstractJpaConfigTest {

    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void shouldFindTopicByName() {
        // GIVEN
        Topic topic = getTopic("JVM");
        entityManager.persist(topic);

        // WHEN
        Optional<Topic> result = topicRepository.findByName("JVM");

        // THEN
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("JVM");
    }

    @Test
    void shouldReturnEmptyIfTopicNotFound() {
        // GIVEN | WHEN
        Optional<Topic> result = topicRepository.findByName("NonExistentTopic");

        // THEN
        assertThat(result).isEmpty();
    }
}