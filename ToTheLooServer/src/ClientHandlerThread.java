import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandlerThread implements Runnable {

    Socket clientSocket;
    PrintWriter out = null;
    BufferedReader in = null;
    Time time;

    public ClientHandlerThread(Socket socket, Time time) {
        clientSocket = socket;
        this.time = time;
    }

    @Override
    public void run() {
        System.out.println("[THREAD] Connection established.");
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String input;
        try {
            input = in.readLine();
            System.out.println("[THREAD] Received: " + input);
            dispatch(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(String input) {
        String[] data = input.split(";");
        if(data[0].equals("TimeUpdateMessage")) {
            handleTimeUpdateMessage(data);
        }
        else if(data[0].equals("TimeRequestMessage")) {
            handleTimeRequestMessage();
        }
    }

    private void handleTimeUpdateMessage(String[] data) {
        int hour = Integer.parseInt(data[1]);
        int minute = Integer.parseInt(data[2]);
        int second = Integer.parseInt(data[3]);
        System.out.println("[THREAD] Updated time: " + hour + ":" + minute + ":" + second);
        time.setTime(hour, minute, second);
    }

    private void handleTimeRequestMessage() {
        String responseMessage = "TimeResponseMessage;";
        responseMessage += time.getHour() + ";";
        responseMessage += time.getMinute() + ";";
        responseMessage += time.getSecond() + ";";
        out.println(responseMessage);
    }

}
