package Game;

import javax.swing.*;

public class LogWriter implements ChatServerListener {
    private JTextArea log;

    public LogWriter(JTextArea log) {
        this.log = log;
    }

    @Override
    public void onMessageReceived(String msg) {
        putMessageToLog(msg);
    }

    private void putMessageToLog(String msg) {
        log.append(msg + "\n");
    }
}