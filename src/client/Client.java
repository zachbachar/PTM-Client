package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {

		System.out.println("**** Client Side ****");
		try {
			Socket theServer = new Socket("localhost", 6400);
			System.out.println("Connected to server");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			PrintWriter outToServer = new PrintWriter(theServer.getOutputStream());

			String line;
			while (!(line = inFromUser.readLine()).equals("done")) {
				outToServer.println(line);
				outToServer.flush();
				System.out.println(inFromServer.readLine());
			}
			outToServer.println("done");
			outToServer.flush();

			// Close everything
			inFromServer.close();
			outToServer.close();
			inFromUser.close();
			theServer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
