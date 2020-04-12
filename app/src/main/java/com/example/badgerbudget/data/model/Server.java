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
            while (true) {
                Socket clientSocket = server.accept();
                // takes input from the client socket
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())),true);
                String line = "";
                String message = "";

                while ((line = in.readLine()) != null) {
                    message+=line;
                    System.out.println(message);
                    Connector conn = new Connector();

                    //Creating a user handling
                    //Creates a database and tables for the new user after they click
                    //the confirm button.
                    if (message.contains("createuser;")) {
                        String op = message.substring(11);
                        System.out.println(op);
                        String[] userInfo = op.split(" ");
                        String result = conn.insertUser(userInfo[0], userInfo[1], userInfo[2], userInfo[3], userInfo[4], userInfo[5], userInfo[6]);
                        out.println(result);
                        String db = conn.createUserDB(userInfo[0]);
                        //After the user is created by setting their information up, all database tables
                        //Are initiated for that user in that user's new database
                        conn.createUserTable();
                        conn.createUserTransactionTable();
                        conn.createUserCategoryTable();

                    }

                    //Login an existing user handling
                    //Validates un and pw and afterwards, switches to the appropriate database.
                    if (message.contains("login;")) {
                        String userInfo = message.substring(6);
                        String[] unpw = userInfo.split(" ");
                        String username = unpw[0];
                        System.out.println("username: " + username);
                        String password = unpw[1];
                        System.out.println("password: " + password);
                        String validate = conn.passwordCheck(username, password);
                        //After the user is validated, switch to that users database
                        String switchDb = conn.createUserDB(username);
                        out.println(validate);
                    }
                    if (message.contains("gettransactions;")) {
                        String[] parsed = message.split(";");
                        String username = parsed[1];
                        conn.createUserDB(username);
                        String transactions = conn.getTransactionList(username);
                        out.println(transactions);
                    }
                }


                //Will need to distinguish what query is being sent and call the right conn method here
            }
        }
        catch(IOException i){
            i.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(6868);
    }
}