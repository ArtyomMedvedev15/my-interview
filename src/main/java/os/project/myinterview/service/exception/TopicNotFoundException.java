package os.project.myinterview.service.exception;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException() {
        super();
    }

    public TopicNotFoundException(String message) {
        super(message);
    }
}
