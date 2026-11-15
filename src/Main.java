import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static ArrayList<Message> messages = new ArrayList<Message>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initMessages();

        int choice;
        do {
            printMenu();
            choice = readInt("Your choice: ");
            switch (choice) {
                case 1:
                    addMessage();
                    break;
                case 2:
                    deleteMessage();
                    break;
                case 3:
                    printAllMessages();
                    break;
                case 4:
                    countMessagesWithWords();
                    break;
                case 5:
                    printDigitalMessages();
                    break;
                case 6:
                    printPreviews();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 0);
    }

    /** Adds 2 objects of every type to the list. */
    private static void initMessages() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.OCTOBER, 20, 9, 30);
        Date oldDate = calendar.getTime();

        messages.add(new BoardMessage("Dana", "Important update about the exam schedule", oldDate, "All students",
                PriorityType.URGENT, "Computer Science Board"));
        messages.add(new BoardMessage("Yossi", "Room for rent near the campus", "All students", "Students Board"));

        ArrayList<File> files = new ArrayList<File>();
        files.add(new File("HW1", "pdf"));
        files.add(new File("Diagram", "drawio"));
        messages.add(new EmailMessage("Noa", "Attached is our solution for homework 1", oldDate, "Lecturer",
                "Project Submission", files));
        messages.add(new EmailMessage("Amir", "Can we move the meeting to Sunday?", "Noa", "Meeting"));

        messages.add(new ChatMessage("Lior", "Who is coming to the lecture today?", oldDate, "Java Group",
                "WhatsApp", "Java Course", 3));
        messages.add(new ChatMessage("Maya", "The exercise was published", "Java Group", "Telegram", "Java Course"));
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Messaging System =====");
        System.out.println("1. Add a message");
        System.out.println("2. Delete a message");
        System.out.println("3. Print all messages");
        System.out.println("4. Count messages that contain words");
        System.out.println("5. Print digital messages only");
        System.out.println("6. Print a preview of all messages");
        System.out.println("0. Exit");
    }

    private static void addMessage() {
        System.out.println("Message type: 1. Board  2. Email  3. Chat");
        int type = readInt("Type: ");
        if (type < 1 || type > 3) {
            System.out.println("Invalid message type.");
            return;
        }

        try {
            String sender = readLine("Sender: ");
            String content = readLine("Content: ");
            String recipient = readLine("Recipient: ");
            Message message;

            if (type == 1) {
                String boardName = readLine("Board name: ");
                BoardMessage board = new BoardMessage(sender, content, recipient, boardName);
                System.out.println("Priority: 1. REGULAR  2. URGENT  3. LOW");
                int priority = readInt("Priority: ");
                if (priority == 2) {
                    board.setPriority(PriorityType.URGENT);
                } else if (priority == 3) {
                    board.setPriority(PriorityType.LOW);
                }
                message = board;
            } else if (type == 2) {
                String subject = readLine("Subject: ");
                EmailMessage email = new EmailMessage(sender, content, recipient, subject);
                int count = readInt("Number of attachments: ");
                for (int i = 1; i <= count; i++) {
                    String fileName = readLine("File " + i + " name: ");
                    String fileType = readLine("File " + i + " type: ");
                    email.addAttachment(new File(fileName, fileType));
                }
                message = email;
            } else {
                String platform = readLine("Platform: ");
                String groupName = readLine("Group name: ");
                message = new ChatMessage(sender, content, recipient, platform, groupName);
            }

            messages.add(message);
            System.out.println("The message was added.");
        } catch (ChatLengthException e) {
            System.out.println("Chat error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteMessage() {
        if (messages.isEmpty()) {
            System.out.println("There are no messages.");
            return;
        }
        printPreviews();
        int number = readInt("Number of the message to delete: ");
        if (number < 1 || number > messages.size()) {
            System.out.println("There is no message with this number.");
            return;
        }
        Message removed = messages.remove(number - 1);
        System.out.println("Deleted: " + removed.generatePreview());
    }

    private static void printAllMessages() {
        if (messages.isEmpty()) {
            System.out.println("There are no messages.");
            return;
        }
        for (int i = 0; i < messages.size(); i++) {
            System.out.println((i + 1) + ". " + messages.get(i));
        }
    }

    private static void countMessagesWithWords() {
        String line = readLine("Words to search (separated by spaces): ");
        ArrayList<String> words = new ArrayList<String>();
        for (String word : line.trim().split("\\s+")) {
            if (!word.isEmpty()) {
                words.add(word);
            }
        }

        int count = 0;
        for (Message message : messages) {
            if (message.find(words)) {
                count++;
            }
        }
        System.out.println("Messages that contain at least one of the words: " + count);
    }

    private static void printDigitalMessages() {
        int count = 0;
        for (Message message : messages) {
            if (message instanceof IDigital) {
                System.out.println(message);
                System.out.println("   " + ((IDigital) message).printCommunicationMethod());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("There are no digital messages.");
        }
    }

    private static void printPreviews() {
        if (messages.isEmpty()) {
            System.out.println("There are no messages.");
            return;
        }
        for (int i = 0; i < messages.size(); i++) {
            System.out.println((i + 1) + ". " + messages.get(i).generatePreview());
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int readInt(String prompt) {
        while (true) {
            String line = readLine(prompt);
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }
}
