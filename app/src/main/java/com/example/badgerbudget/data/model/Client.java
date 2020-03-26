package com.example.badgerbudget.data.model;
import java.net.*;
import java.io.*;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String hostname;
    private int port;

    Client(int port, String hostname) {
        try {
            this.port = port;
            this.hostname = hostname;
            new Thread(new ClientThread()).start();
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(UnknownHostException e) {
            System.out.println("Issue with host.");
        }
        catch(IOException e) {
            System.out.println("Could not create socket.");
        }
    }

    public String sendMessage(String message) {
        this.out.println(message);
        String response = "";
        String temp;
        try {
            while((temp = in.readLine()) != null) {
                response+=temp;
            }
        } catch(IOException e) {
            System.out.println("Could not receive response.");
        }
        return response;
    }
    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(hostname);

                socket = new Socket(serverAddr, port);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}

