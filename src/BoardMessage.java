import java.util.Date;

/**
 * A message published on a notice board.
 */
public class BoardMessage extends Message {
    private static final int PREVIEW_LENGTH = 15;

    private PriorityType priority;
    private String boardName; // extra field of a board message

    /** Full constructor. */
    public BoardMessage(String sender, String content, Date sendDate, String recipient, PriorityType priority,
            String boardName) {
        super(sender, content, sendDate, recipient);
        setPriority(priority);
        setBoardName(boardName);
    }

    /** Constructor without date and priority: current date, REGULAR priority. */
    public BoardMessage(String sender, String content, String recipient, String boardName) {
        super(sender, content, recipient);
        setPriority(PriorityType.REGULAR);
        setBoardName(boardName);
    }

    public PriorityType getPriority() {
        return priority;
    }

    public void setPriority(PriorityType priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        this.priority = priority;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        if (boardName == null || boardName.trim().isEmpty()) {
            throw new IllegalArgumentException("Board name cannot be empty");
        }
        this.boardName = boardName.trim();
    }

    /** Extra method: is this an urgent message? */
    public boolean isUrgent() {
        return priority == PriorityType.URGENT;
    }

    @Override
    public String generatePreview() {
        String shortContent = content;
        if (content.length() > PREVIEW_LENGTH) {
            shortContent = content.substring(0, PREVIEW_LENGTH) + "...";
        }
        return "[Board] " + sender + ": " + shortContent;
    }

    @Override
    public String toString() {
        return "BoardMessage [" + super.toString() + ", priority=" + priority + ", boardName=" + boardName + "]";
    }
}
