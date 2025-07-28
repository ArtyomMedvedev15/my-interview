package os.project.myinterview.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import os.project.myinterview.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    List<Question> findByTopic_Id(Long topicId);

    @Query(value = """
                SELECT * FROM question 
                WHERE topic_id = :topicId AND difficulty = :difficulty 
                ORDER BY random() 
                LIMIT :count
            """, nativeQuery = true)
    List<Question> findRandomByTopicAndDifficulty(@Param("topicId") Long topicId,
            @Param("difficulty") String difficulty,
            @Param("count") int count);
}