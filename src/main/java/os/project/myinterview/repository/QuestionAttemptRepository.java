package os.project.myinterview.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import os.project.myinterview.model.QuestionAttempt;
import os.project.myinterview.model.enums.AnswerResult;

public interface QuestionAttemptRepository extends JpaRepository<QuestionAttempt, Long> {

    List<QuestionAttempt> findByInterviewSession_Id(Long sessionId);

    List<QuestionAttempt> findByResult(AnswerResult result);

    List<QuestionAttempt> findByQuestion_Topic_Id(Long topicId);
}

