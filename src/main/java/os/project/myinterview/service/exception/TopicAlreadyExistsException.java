package os.project.myinterview.service.exception;

public class TopicAlreadyExistsException extends RuntimeException {

    public TopicAlreadyExistsException() {
        super();
    }

    public TopicAlreadyExistsException(String message) {
        super(message);
    }
}
