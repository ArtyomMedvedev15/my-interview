package os.project.myinterview.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import os.project.myinterview.config.AbstractJpaConfigTest;
import os.project.myinterview.model.InterviewSession;
import os.project.myinterview.model.Question;
import os.project.myinterview.model.QuestionAttempt;
import os.project.myinterview.model.Topic;
import os.project.myinterview.model.enums.AnswerResult;

class QuestionAttemptRepositoryTest extends AbstractJpaConfigTest {

    @Autowired
    private QuestionAttemptRepository attemptRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private InterviewSessionRepository sessionRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Test
    void shouldFindAttemptsByInterviewSessionId() {
        // GIVEN
        Topic topic = topicRepository.save(getTopic("SQL"));

        Question question = getQuestion("What is DI?", topic, false, "Dependency Injection");
        questionRepository.save(question);

        InterviewSession session1 = getInterviewSession("Alice", LocalDateTime.now());
        session1 = sessionRepository.save(session1);

        InterviewSession session2 = getInterviewSession("Bob", LocalDateTime.now());
        session2 = sessionRepository.save(session2);

        QuestionAttempt a1 = getQuestionAttempt(question, session1, AnswerResult.SUCCESS);
        attemptRepository.save(a1);

        QuestionAttempt a2 = getQuestionAttempt(question, session2, AnswerResult.FAIL);
        attemptRepository.save(a2);

        // WHEN
        List<QuestionAttempt> result = attemptRepository.findByInterviewSession_Id(session1.getId());

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getInterviewSession().getCandidateName()).isEqualTo("Alice");
    }

    @Test
    void shouldFindAttemptsByResult() {
        // GIVEN
        Topic topic = topicRepository.save(getTopic("JVM"));

        Question question = getQuestion("What is GC?", topic, false, "Garbage Collector");
        question = questionRepository.save(question);

        InterviewSession session = sessionRepository.save(getInterviewSession("Bobby", LocalDateTime.now()));

        QuestionAttempt a1 = getQuestionAttempt(question, session, AnswerResult.FAIL);
        attemptRepository.save(a1);

        QuestionAttempt a2 = getQuestionAttempt(question, session, AnswerResult.FAIL);
        attemptRepository.save(a2);

        QuestionAttempt a3 = getQuestionAttempt(question, session, AnswerResult.SUCCESS);
        attemptRepository.save(a3);

        // WHEN
        List<QuestionAttempt> result = attemptRepository.findByResult(AnswerResult.FAIL);

        // THEN
        assertThat(result).hasSize(2);
        assertThat(result).allMatch(a -> a.getResult() == AnswerResult.FAIL);
    }

    @Test
    void shouldFindAttemptsByQuestionTopicId() {
        // GIVEN
        Topic topic1 = topicRepository.save(getTopic("Collections"));
        Topic topic2 = topicRepository.save(getTopic("Streams"));

        Question question1 = getQuestion("List vs Set", topic1, false, "Garbage Collector");
        questionRepository.save(question1);

        Question question2 = getQuestion("map vs flatMap", topic2, false, "Garbage Collector");
        questionRepository.save(question2);

        InterviewSession session = sessionRepository.save(getInterviewSession("Tom", LocalDateTime.now()));

        QuestionAttempt a1 = getQuestionAttempt(question1, session, AnswerResult.PARTIAL);
        attemptRepository.save(a1);

        QuestionAttempt a2 = getQuestionAttempt(question2, session, AnswerResult.SUCCESS);
        attemptRepository.save(a2);

        // WHEN
        List<QuestionAttempt> result = attemptRepository.findByQuestion_Topic_Id(topic1.getId());

        // THEN
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getQuestion().getTopic().getName()).isEqualTo("Collections");
    }
}