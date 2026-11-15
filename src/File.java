/**
 * A file attached to an email message.
 */
public class File {
    private String fileName;
    private String fileType;

    public File(String fileName, String fileType) {
        setFileName(fileName);
        setFileType(fileType);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        this.fileName = fileName.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        if (fileType == null || fileType.trim().isEmpty()) {
            throw new IllegalArgumentException("File type cannot be empty");
        }
        this.fileType = fileType.trim();
    }

    /** Two files match when both their name and type are the same (case-insensitive). */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof File)) {
            return false;
        }
        File other = (File) obj;
        return fileName.equalsIgnoreCase(other.fileName) && fileType.equalsIgnoreCase(other.fileType);
    }

    @Override
    public int hashCode() {
        return (fileName.toLowerCase() + "." + fileType.toLowerCase()).hashCode();
    }

    @Override
    public String toString() {
        return "File [name=" + fileName + ", type=" + fileType + "]";
    }
}
