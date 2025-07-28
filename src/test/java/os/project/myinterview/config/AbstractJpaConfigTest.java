package os.project.myinterview.config;

import java.time.LocalDateTime;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import os.project.myinterview.model.InterviewSession;
import os.project.myinterview.model.Question;
import os.project.myinterview.model.QuestionAttempt;
import os.project.myinterview.model.Topic;
import os.project.myinterview.model.enums.AnswerResult;
import os.project.myinterview.model.enums.Difficulty;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties")
public abstract class AbstractJpaConfigTest {

    protected static Topic getTopic(String nameTopic) {
        Topic topic = new Topic();
        topic.setName(nameTopic);
        return topic;
    }

    protected static Question getQuestion(String Q2, Topic topic, boolean practical, String Answer2) {
        Question q2 = new Question();
        q2.setText(Q2);
        q2.setTopic(topic);
        q2.setDifficulty(Difficulty.EASY);
        q2.setPractical(practical);
        q2.setTags(null);
        q2.setExpectedAnswer(Answer2);
        return q2;
    }

    protected static InterviewSession getInterviewSession(String name, LocalDateTime dateTime) {
        InterviewSession s1 = new InterviewSession();
        s1.setCandidateName(name);
        s1.setDateTime(dateTime);
        return s1;
    }

    protected static QuestionAttempt getQuestionAttempt(Question question, InterviewSession session1, AnswerResult success) {
        QuestionAttempt a1 = new QuestionAttempt();
        a1.setQuestion(question);
        a1.setInterviewSession(session1);
        a1.setResult(success);
        return a1;
    }
}
