package os.project.myinterview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import os.project.myinterview.model.enums.Difficulty;

@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private Topic topic;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private boolean practical;

    private String tags;

    private String expectedAnswer;
}
