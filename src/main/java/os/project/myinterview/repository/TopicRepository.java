package os.project.myinterview.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import os.project.myinterview.model.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByName(String name);
}