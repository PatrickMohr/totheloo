package com.example.tothelooclient;
import java.io.*;
import java.net.*;
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        String host = "127.0.0.1";
        int port = 42069;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true); //In Datenbank schreiben
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for host: " + host);
            System.exit(1);
        }
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        String userInput, serverInput;
        while ((userInput = keyboard.readLine()) != null) {
            out.println(userInput);
            serverInput = in.readLine();
            System.out.println("Server: " + serverInput);
            if (serverInput.equals("BYE."))
                break;
        }
        out.close();
        in.close();
        keyboard.close();
        socket.close();
    }
}