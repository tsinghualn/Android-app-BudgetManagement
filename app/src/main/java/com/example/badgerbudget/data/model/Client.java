import java.net.*;
import java.io.*;

public class Client {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String hostname;
	private int port;
	private static volatile String returnValue;
	
	Client(int port, String hostname) {
			this.port = port;
			this.hostname = hostname;
	        //new Thread(new ClientThread()).start();
	}
		
	public String sendMessage(String message) {
		Thread thread = new Thread(new ClientThread());
		thread.start();
		try {
			thread.join();
		} catch(Exception e) {
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
    class ClientThread implements Runnable {
    	 
        @Override
        public void run() {
 
            try {
                InetAddress serverAddr = InetAddress.getByName(hostname);
                socket = new Socket(serverAddr, port);
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())),
                        true);
    	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(serverAddr);
                out.println("Hello");
                try {
                    String response = "";
                    response = in.readLine();
                    System.out.println(response);
                    returnValue = response;
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
		InetAddress ip = InetAddress.getLocalHost();
		String hostname = ip.getHostName();
		Client client = new Client(6868, hostname);
		System.out.println(client.sendMessage("hello"));
		
	}
}

