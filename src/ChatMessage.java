import java.util.Date;

/**
 * A short message sent in a chat group.
 */
public class ChatMessage extends Message implements IDigital {
    public static final int MAX_LENGTH = 100;
    private static final int PREVIEW_LENGTH = 10;

    private String platform;
    private String groupName;
    private int likes;

    /** Full constructor. */
    public ChatMessage(String sender, String content, Date sendDate, String recipient, String platform,
            String groupName, int likes) {
        super(sender, content, sendDate, recipient);
        setPlatform(platform);
        setGroupName(groupName);
        setLikes(likes);
    }

    /** Constructor without date and likes: current date, 0 likes. */
    public ChatMessage(String sender, String content, String recipient, String platform, String groupName) {
        super(sender, content, recipient);
        setPlatform(platform);
        setGroupName(groupName);
        setLikes(0);
    }

    /** A chat message is short: at most MAX_LENGTH characters. */
    @Override
    public void setContent(String content) {
        if (content != null && content.trim().length() > MAX_LENGTH) {
            throw new ChatLengthException("Chat message is too long: " + content.trim().length()
                    + " characters (maximum " + MAX_LENGTH + ")");
        }
        super.setContent(content);
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        if (platform == null || platform.trim().isEmpty()) {
            throw new IllegalArgumentException("Platform cannot be empty");
        }
        this.platform = platform.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        if (groupName == null || groupName.trim().isEmpty()) {
            throw new IllegalArgumentException("Group name cannot be empty");
        }
        this.groupName = groupName.trim();
    }

    public int getLikes() {
        return likes;
    }

    // Likes change only through addLike.
    private void setLikes(int likes) {
        if (likes < 0) {
            throw new IllegalArgumentException("Likes cannot be negative");
        }
        this.likes = likes;
    }

    public void addLike() {
        likes++;
    }

    @Override
    public String printCommunicationMethod() {
        return "Sent via " + platform + " Chat Application";
    }

    @Override
    public String generatePreview() {
        String shortContent = content;
        if (content.length() > PREVIEW_LENGTH) {
            shortContent = content.substring(0, PREVIEW_LENGTH) + "...";
        }
        return "[Chat] " + groupName + " | " + sender + ": " + shortContent;
    }

    @Override
    public String toString() {
        return "ChatMessage [" + super.toString() + ", platform=" + platform + ", groupName=" + groupName
                + ", likes=" + likes + "]";
    }
}
