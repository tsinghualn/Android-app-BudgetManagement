package com.example.badgerbudget.data.model;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Properties;



public class Server {
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
                System.out.println("Waiting for a client ...");
                Socket clientSocket = server.accept();
                System.out.println("Client accepted");
                // takes input from the client socket
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
                String line = "";
                String message = "";
                while ((line = in.readLine()) != null)
                {
                    message+=line;
                    accessMySQL(message);
                    out.println("data retrieved from database: $125");
                }
            }
            catch(IOException i)
            {
                i.printStackTrace();
            }
        }

        private Connection conn = null;
        public void accessMySQL(String query) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();

                String url = "jdbc:mysql://localhost:8080/phpmyadmin/BudgetInfo?" +"allowPublicKeyRetrieval=true&useSSL=false";
                Properties info = new Properties();
                info.put("user", "root");
                info.put("password", "badgerbudget");
                System.out.println("info pushed succesfully");
                conn = DriverManager.getConnection(url, info);
                System.out.println("getConnection successful");
                Statement stmt = conn.createStatement();
                ResultSet resultSet = stmt.executeQuery(query);
                //writeResultSet(resultSet);
                System.out.println("After Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        public static void main(String[] args) {
            Server server = new Server(6868);
        }
    }

