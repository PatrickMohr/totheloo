import java.io.*;
import java.net.*;

public class Server {

    public static Time lastUpdate = new Time(0,0,0);

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        int port = 42069;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            System.exit(1);
        }
        Socket clientSocket = null;

        while (true) {

            System.out.println("[SERVER] Waiting...");
            System.out.println("[SERVER] " + lastUpdate);
            clientSocket = serverSocket.accept();

            Runnable handlerThread = new ClientHandlerThread(clientSocket, lastUpdate);
            Thread t = new Thread(handlerThread);
            t.start();

        }
    }
}
