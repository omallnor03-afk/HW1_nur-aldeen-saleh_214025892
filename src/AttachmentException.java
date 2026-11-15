/**
 * Thrown when an attachment action on an email message fails.
 */
public class AttachmentException extends Exception {
    public AttachmentException(String message) {
        super(message);
    }
}
