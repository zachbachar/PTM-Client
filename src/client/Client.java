package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	
	int port = 6400;
	String ip = "localhost";
	
	
	
	public int getPort() {
		return port;
	}



	public void setPort(int port) {
		this.port = port;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public String sendToServer(String message) {

		try {
			Socket theServer = new Socket(ip, port);
			System.out.println("Connected to server");
			BufferedReader serverInput = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());
			
			String[] parsedMessage = message.split(System.lineSeparator());
			for (String line : parsedMessage) {
				outToServer.println(line);
				outToServer.flush();
			}
			
			outToServer.println("done");
			outToServer.flush();

			StringBuilder sb = new StringBuilder();
			String line;
			while (!(line = serverInput.readLine()).equals("done")) {
				sb.append(line);
				sb.append(System.lineSeparator());
			}
			
			// Close everything
			serverInput.close();
			outToServer.close();
			theServer.close();
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;

	}
}
