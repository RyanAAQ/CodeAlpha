import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    private static JTextArea chatArea;
    private static JTextField messageField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() { createWindow(); }
        });
    }

    private static void createWindow() {
        JFrame window = new JFrame("CodeAlpha FAQ Chatbot");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(620, 480);
        window.setMinimumSize(new Dimension(450, 350));
        window.setLayout(new BorderLayout(8, 8));

        JLabel heading = new JLabel("Java Help Chat", SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        window.add(heading, BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        chatArea.setText("Bot: Hi, I'm a simple Java chatbot. Ask me about Java or the CodeAlpha tasks.\n\n");
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        window.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(6, 6));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        messageField = new JTextField();
        messageField.setToolTipText("Type a question and press Enter");
        JButton sendButton = new JButton("Send");
        bottomPanel.add(messageField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);
        window.add(bottomPanel, BorderLayout.SOUTH);

        JPanel topics = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addTopicButton(topics, "Java");
        addTopicButton(topics, "Internship");
        addTopicButton(topics, "Projects");
        addTopicButton(topics, "Help");
        window.add(topics, BorderLayout.PAGE_END);
        window.remove(topics);
        JPanel lowerArea = new JPanel(new BorderLayout());
        lowerArea.add(topics, BorderLayout.NORTH);
        lowerArea.add(bottomPanel, BorderLayout.SOUTH);
        window.add(lowerArea, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) { sendMessage(); }
        });
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) { sendMessage(); }
        });

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        messageField.requestFocusInWindow();
    }

    private static void addTopicButton(JPanel panel, String topic) {
        JButton button = new JButton(topic);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                messageField.setText(topic);
                sendMessage();
            }
        });
        panel.add(button);
    }

    private static void sendMessage() {
        String message = messageField.getText().trim();
        if (message.length() == 0) {
            chatArea.append("Bot: Type a question first, then press Send.\n\n");
            return;
        }
        chatArea.append("You: " + message + "\n");
        chatArea.append("Bot: " + getAnswer(message) + "\n\n");
        messageField.setText("");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
        messageField.requestFocusInWindow();
    }

    private static String getAnswer(String message) {
        String text = message.toLowerCase();
        text = text.replace('?', ' ').replace('!', ' ').replace('.', ' ').replace(',', ' ');
        text = " " + text.trim() + " ";

        if (has(text, "hello", "hi", "hey")) {
            return "Hey! You can ask me about Java or the CodeAlpha tasks.";
        } else if (has(text, "java", " jdk ", " jvm ")) {
            return "Java is a programming language. You can use it to build apps, websites, and other software.";
        } else if (has(text, "programming", "code", "coding")) {
            return "Programming is giving a computer instructions to follow. In Java, those instructions go inside classes and methods.";
        } else if (has(text, "internship", "codealpha", "code alpha")) {
            return "The task sheet says to complete two or three projects from the Java list.";
        } else if (has(text, "project", "task", "tasks")) {
            return "There are four choices: Student Grade Tracker, Stock Trading Platform, AI Chatbot, and Hotel Reservation System.";
        } else if (has(text, "grade", "student")) {
            return "That task is about saving student grades and showing the average, highest, and lowest scores.";
        } else if (has(text, "stock", "trading")) {
            return "That one simulates buying and selling shares and keeping track of a user's portfolio.";
        } else if (has(text, "hotel", "reservation", "booking")) {
            return "That project lets someone check rooms, make or cancel a booking, and try a pretend payment.";
        } else if (has(text, "help", "what can you do")) {
            return "Try asking me what Java is, what the internship asks for, or what the project choices are.";
        } else if (has(text, "thank", "thanks")) {
            return "No problem.";
        } else if (has(text, "bye", "goodbye")) {
            return "Bye! Close the window when you're done.";
        }
        return "I don't know that one yet. Try asking about Java or the project tasks.";
    }

    private static boolean has(String text, String... words) {
        for (int i = 0; i < words.length; i++) {
            if (text.contains(" " + words[i] + " ")) return true;
        }
        return false;
    }
}
