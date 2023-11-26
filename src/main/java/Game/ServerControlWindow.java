package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.io.PrintStream;

public class ServerControlWindow extends JFrame implements ChatServerListener {
    private boolean isServerWorking = false;
    private JTextArea textArea;
    private ChatServer server;
    private JTextArea log;

    public ServerControlWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 200);
        setTitle("Server Control");
        server = new ChatServer(this);

        JButton startButton = new JButton("Start Server");
        JButton stopButton = new JButton("Stop Server");
        textArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(log);

        log = new JTextArea();
        add(scrollPane, BorderLayout.CENTER);
        LogWriter logWriter = new LogWriter(log);
        server = new ChatServer(logWriter);




        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.start();

            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stop();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Redirecting System.out to JTextArea
        PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(printStream);
        System.setErr(printStream);
    }

    private void appendToTextArea(String message) {
        textArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerControlWindow();
            }
        });
    }

    @Override
    public void onMessageReceived(String msg) {
        textArea.append(msg + "\n");
    }

    // Custom OutputStream to redirect System.out to JTextArea
    private static class CustomOutputStream extends OutputStream {
        private JTextArea textArea;

        public CustomOutputStream(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            textArea.append(String.valueOf((char) b));
        }
    }
}
