/**
 * Thrown when the content of a chat message is longer than allowed.
 */
public class ChatLengthException extends RuntimeException {
    public ChatLengthException(String message) {
        super(message);
    }
}
