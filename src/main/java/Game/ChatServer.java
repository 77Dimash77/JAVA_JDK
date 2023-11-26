package Game;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {
    private boolean isServerWorking;
    private final ChatServerListener listener;


    ChatServer(ChatServerListener listener) {
        isServerWorking = false;
        this.listener = listener;
    }

    public void start() {
        if (isServerWorking) {
            listener.onMessageReceived("Server is already working");
            return;
        }
        listener.onMessageReceived("Server started");
        isServerWorking = true;
    }

    public void stop() {
        if (!isServerWorking) {
            listener.onMessageReceived("Server is stopped");
            return;
        }
        listener.onMessageReceived("Server stopped");
        isServerWorking = false;
    }

    @Override
    public void onServerStart() {
        listener.onMessageReceived("Server started");
        isServerWorking = true;
    }

    @Override
    public void onServerStop() {
        listener.onMessageReceived("Server stopped");
        isServerWorking = false;
    }

    @Override
    public void onServerSocketCreated(ServerSocket s) {
        listener.onMessageReceived("Server socket created");
    }

    @Override
    public void onServerSoTimeout(ServerSocket s) {
        // Логика, когда установлен таймаут серверного сокета
    }

    @Override
    public void onSocketAccepted(ServerSocket s, Socket client) {
        listener.onMessageReceived("Client connected");
    }

    @Override
    public void onServerException(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public synchronized void onSocketStart(Socket s) {
        listener.onMessageReceived("Client connected");
    }

    @Override
    public synchronized void onSocketStop() {
        listener.onMessageReceived("Client dropped");
    }

    @Override
    public synchronized void onSocketReady(Socket socket) {
        listener.onMessageReceived("Client is ready");
    }

    @Override
    public synchronized void onReceiveString(Socket s, String msg) {
        listener.onMessageReceived(msg);
    }

    @Override
    public void onSocketException(Throwable e) {
        e.printStackTrace();
    }
}
