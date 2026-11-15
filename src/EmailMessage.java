import java.util.ArrayList;
import java.util.Date;

/**
 * An email message. Can carry attached files.
 */
public class EmailMessage extends Message implements IDigital {
    private String subject;
    private ArrayList<File> attachments;

    /** Full constructor. */
    public EmailMessage(String sender, String content, Date sendDate, String recipient, String subject,
            ArrayList<File> attachments) {
        super(sender, content, sendDate, recipient);
        setSubject(subject);
        setAttachments(attachments);
    }

    /** Constructor without date and attachments: current date, no attachments. */
    public EmailMessage(String sender, String content, String recipient, String subject) {
        super(sender, content, recipient);
        setSubject(subject);
        setAttachments(new ArrayList<File>());
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject cannot be empty");
        }
        this.subject = subject.trim();
    }

    /** Returns a copy, so the list changes only through addAttachment / removeAttachment. */
    public ArrayList<File> getAttachments() {
        return new ArrayList<File>(attachments);
    }

    private void setAttachments(ArrayList<File> attachments) {
        if (attachments == null) {
            throw new IllegalArgumentException("Attachments list cannot be null");
        }
        this.attachments = new ArrayList<File>();
        for (File file : attachments) {
            addAttachment(file);
        }
    }

    public void addAttachment(File file) {
        if (file == null) {
            throw new IllegalArgumentException("Attachment cannot be null");
        }
        attachments.add(file);
    }

    /** Removes every attachment that matches the given file. */
    public void removeAttachment(File file) throws AttachmentException {
        boolean removed = false;
        for (int i = attachments.size() - 1; i >= 0; i--) {
            if (attachments.get(i).equals(file)) {
                attachments.remove(i);
                removed = true;
            }
        }
        if (!removed) {
            throw new AttachmentException("Attachment not found in the message: " + file);
        }
    }

    @Override
    public String printCommunicationMethod() {
        return "Sent via Email Server";
    }

    @Override
    public String generatePreview() {
        return "[Email] Subject: " + subject + " | From: " + sender;
    }

    @Override
    public String toString() {
        return "EmailMessage [" + super.toString() + ", subject=" + subject + ", attachments=" + attachments + "]";
    }
}
