package Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ServerControlWindow().setVisible(true);
        });
        new ServerControlWindow();
        new ChatClientWindow();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameWindow gameWindow = new GameWindow();
                gameWindow.setVisible(true);
            }
        });
    }
}