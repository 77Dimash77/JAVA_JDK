import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;

public class ChatClientWindow extends JFrame {
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField ipField;
    private JTextField portField;
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton connectButton;
    private JButton sendButton;
    private JList<String> userList;
    private PrintWriter logWriter;

    public ChatClientWindow() {
        // Инициализация компонентов
        loginField = new JTextField();
        passwordField = new JPasswordField();
        ipField = new JTextField();
        portField = new JTextField();
        chatArea = new JTextArea();
        messageField = new JTextField();
        connectButton = new JButton("Connect");
        sendButton = new JButton("Send");

        // Components for user list
        String[] userNames = {"User1", "User2", "User3", "User4"}; // Выдуманные имена пользователей
        userList = new JList<>(userNames);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane userListScrollPane = new JScrollPane(userList);

        // Создание панелей
        JPanel serverPanel = new JPanel(new GridLayout(4, 2));
        serverPanel.add(new JLabel("Login:"));
        serverPanel.add(loginField);
        serverPanel.add(new JLabel("Password:"));
        serverPanel.add(passwordField);
        serverPanel.add(new JLabel("IP Address:"));
        serverPanel.add(ipField);
        serverPanel.add(new JLabel("Port:"));
        serverPanel.add(portField);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(new JLabel("Chat Messages"), BorderLayout.NORTH);
        messagePanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Message"), BorderLayout.NORTH);
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Основная панель
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(serverPanel, BorderLayout.NORTH);
        mainPanel.add(messagePanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        mainPanel.add(userListScrollPane, BorderLayout.EAST);

        // Добавление основной панели к окну
        add(mainPanel);

        // Добавление слушателя к кнопке "Send"
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        // Добавление слушателя клавиш к полю ввода сообщения
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // Настройка окна
        setTitle("Chat Client");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Центрирование окна по центру экрана
        setVisible(true);

        // Инициализация логгера для записи истории чата в файл
        try {
            logWriter = new PrintWriter(new FileWriter("chat_log.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Загрузка истории из файла при открытии окна
        loadChatHistory();
    }

    private void sendMessage() {
        // Получение текста из поля ввода сообщения
        String message = messageField.getText();

        // Очистка поля ввода сообщения
        messageField.setText("");

        // Логика отправки сообщения (можете здесь добавить свою логику отправки на сервер)

        // Пример: добавление сообщения в область чата
        String formattedMessage = "You: " + message + "\n";
        chatArea.append(formattedMessage);

        // Запись в файл
        logWriter.println(formattedMessage);
        logWriter.flush(); // Для немедленной записи в файл
    }

    private void loadChatHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("chat_log.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                chatArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientWindow::new);
    }
}
