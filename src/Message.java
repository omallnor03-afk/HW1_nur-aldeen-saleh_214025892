import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Base class of every message in the system.
 */
public abstract class Message {
    protected String sender;
    protected String content;
    protected Date sendDate;
    protected String recipient; // extra field shared by all message types

    /** Full constructor. */
    public Message(String sender, String content, Date sendDate, String recipient) {
        setSender(sender);
        setContent(content);
        setSendDate(sendDate);
        setRecipient(recipient);
    }

    /** Constructor without a date: the send date is the current date of the computer. */
    public Message(String sender, String content, String recipient) {
        this(sender, content, new Date(), recipient);
    }

    public String getSender() {
        return sender;
    }

    // The sender is set once, when the message is created.
    private void setSender(String sender) {
        if (sender == null || sender.trim().isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be empty");
        }
        this.sender = sender.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
        this.content = content.trim();
    }

    public Date getSendDate() {
        return new Date(sendDate.getTime());
    }

    // The send date is set once, when the message is created.
    private void setSendDate(Date sendDate) {
        if (sendDate == null) {
            throw new IllegalArgumentException("Send date cannot be null");
        }
        this.sendDate = new Date(sendDate.getTime());
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty");
        }
        this.recipient = recipient.trim();
    }

    /** Checks whether at least one of the given words appears in the content of the message. */
    public boolean find(ArrayList<String> words) {
        if (words == null) {
            return false;
        }
        String lowerContent = content.toLowerCase();
        for (String word : words) {
            if (word != null && !word.trim().isEmpty() && lowerContent.contains(word.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /** Extra method: the number of words in the content of the message. */
    public int countWords() {
        return content.split("\\s+").length;
    }

    /** Returns a short version of the message, according to its type. */
    public abstract String generatePreview();

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "sender=" + sender + ", recipient=" + recipient + ", sendDate=" + format.format(sendDate)
                + ", content=" + content;
    }
}
