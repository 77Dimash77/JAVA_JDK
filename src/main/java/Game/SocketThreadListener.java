package Game;

import java.net.Socket;

public interface SocketThreadListener {
    void onSocketStart(Socket s);
    void onSocketStop();
    void onSocketReady(Socket socket);
    void onReceiveString(Socket s, String msg);
    void onSocketException(Throwable e);
}