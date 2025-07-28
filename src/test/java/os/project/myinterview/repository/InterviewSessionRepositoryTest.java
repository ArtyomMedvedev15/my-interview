package os.project.myinterview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import os.project.myinterview.config.AbstractJpaConfigTest;
import os.project.myinterview.model.InterviewSession;

class InterviewSessionRepositoryTest extends AbstractJpaConfigTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private InterviewSessionRepository interviewSessionRepository;

    @Test
    void shouldFindSessionsByCandidateName() {
        // GIVEN
        InterviewSession s1 = getInterviewSession("Анна", LocalDateTime.now().minusDays(1));
        entityManager.persist(s1);

        InterviewSession s2 = getInterviewSession("Анна", LocalDateTime.now());
        entityManager.persist(s2);

        InterviewSession s3 = getInterviewSession("Иван", LocalDateTime.now().minusDays(2));
        entityManager.persist(s3);

        // WHEN
        List<InterviewSession> result = interviewSessionRepository.findByCandidateName("Анна");

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(session -> session.getCandidateName().equals("Анна"));
    }
}