import javax.swing.*;

public class Main {
    public static void main(String[] args) {
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