package os.project.myinterview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import os.project.myinterview.model.enums.AnswerResult;

@Entity
@Data
public class QuestionAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    @ManyToOne
    private InterviewSession interviewSession;

    @Enumerated(EnumType.STRING)
    private AnswerResult result;

    private String candidateAnswer;

    private String notes;
}
