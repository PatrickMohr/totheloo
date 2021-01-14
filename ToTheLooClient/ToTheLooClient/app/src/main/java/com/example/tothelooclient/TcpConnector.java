package com.example.tothelooclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpConnector implements Runnable {

    private String messageType;
    private String message;


    public TcpConnector(String messageType, String message){
        this.messageType = messageType;
        this.message = message;
    }


    @Override
    public void run() {
        Socket socket = null;
        String host = "192.168.178.34"; //your computer IP address
        int port = 42069;

        PrintWriter out = null;
        BufferedReader in = null;
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            Log.e("NetworkError","Don't know about host: " + host);
        } catch (IOException e) {
            Log.e("NetworkError","Couldn't get I/O for host: " + host);
        }
        String serverInput;
        try {

            out.println(message);
            out.flush();

            if(messageType.equals("TimeRequestMessage")){
                serverInput = in.readLine();
                Log.d("SERVER","Server: " + serverInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
