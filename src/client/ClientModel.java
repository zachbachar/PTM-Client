package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientModel {

	public IntegerProperty port;
	public StringProperty ip;
	public StringProperty errorMessage;

	public ClientModel() {
		port = new SimpleIntegerProperty();
		ip = new SimpleStringProperty();
		errorMessage = new SimpleStringProperty();
		port.set(6400);
		ip.set("localhost");
	}
	
	public String sendToServer(String message) {

		try {
			Socket theServer = new Socket(ip.get(), port.get());
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
			errorMessage.set("Cannot Connect To Server");
			e.printStackTrace();
		}

		return null;

	}
}
