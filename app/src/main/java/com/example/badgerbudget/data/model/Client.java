package com.example.badgerbudget.data.model;
import android.os.AsyncTask;

import java.net.*;
import java.io.*;

public class Client extends AsyncTask<String,Void,String> {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String hostname;
	private int port;
	private static volatile String returnValue;
	private static volatile String message;

	public Client(){}
	public Client(int port, String hostname) {
		this.port = port;
		this.hostname = hostname;
		//new Thread(new ClientThread()).start();
	}

	public String sendMessage(String message) {
		this.message = message;
		Thread thread = new Thread(new ClientThread(port,hostname));
		thread.start();
		try {
			thread.join();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Interrupted Exception");
		}
		String response = "";
		String temp;
/*		try {
			while((temp = in.readLine()) != null) {
				response+=temp;
			}
		} catch(IOException e) {
			System.out.println("Could not receive response.");
		}
		return response;*/ return returnValue;
	}

	@Override
	protected String doInBackground(String[] objects) {
		this.port = port;
		this.hostname = hostname;
		return null;
	}


	class ClientThread implements Runnable  {
		int port;
		String hostname;
		public ClientThread(int port, String hostname){
			this.port = port;
			this.hostname = hostname;
		}
		public void run() {
			android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

			try {
				InetAddress serverAddr = InetAddress.getByName(this.hostname);
				socket = new Socket(serverAddr, this.port);
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())),
						true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out.println(message);
				try {
					String response = "";
					response = in.readLine();
					System.out.println(response);
					returnValue = response;
					socket.close();
				} catch(IOException e) {
					System.out.println("Could not receive response.");
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public static void main(String[] args) throws UnknownHostException {
		//InetAddress ip = InetAddress.getLocalHost();
		//String hostname = ip.getHostName();
		//Client client = new Client(6868, hostname);
		//System.out.println(client.sendMessage("login;andy_boho password"));

	}
}

