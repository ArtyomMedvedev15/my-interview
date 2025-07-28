package os.project.myinterview.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import os.project.myinterview.model.InterviewSession;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {

    List<InterviewSession> findByCandidateName(String name);
}
