package com.example.badgerbudget.data.model;

import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.Properties;
public class Server
{
    //initialize socket and input stream
    private ServerSocket server = null;
    private BufferedReader in =  null;
    private PrintWriter out;
    // constructor with port

    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
            //while (true) {
            Socket clientSocket = server.accept();
            // takes input from the client socket
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),true);
            String line = "";
            String message = "";

            while ((line = in.readLine()) != null) {
                message+=line;
                out.println(message);
            }
            Connector conn = new Connector(message);
            System.out.println("connection established");
            //Will need to distinguish what query is being sent and call the right conn method here
            //}
        }
        catch(IOException i){
            i.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(6868);
    }
}

