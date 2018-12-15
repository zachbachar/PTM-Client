package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PipeGameModel  {

	public StringProperty gameData;


	public PipeGameModel() {
		gameData = new SimpleStringProperty();	
	}

	public void rotatePipe(int x, int y) {
		StringBuilder sb = new StringBuilder(gameData.get());
		int size = sb.indexOf(System.lineSeparator()) + 1;
		int index = (y * size) + x;

		switch (sb.charAt(index)) {
		case '-':
			sb.setCharAt(index, '|');
			break;

		case '|':
			sb.setCharAt(index, '-');
			break;
		
		case 'L':
			sb.setCharAt(index, 'F');
			break;

		case 'F':
			sb.setCharAt(index, '7');
			break;

		case '7':
			sb.setCharAt(index, 'J');
			break;

		case 'J':
			sb.setCharAt(index, 'L');
			break;

		default:
			break;
		}

		gameData.set(sb.toString());

	}
	public void loadGame(String fileName) {

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferReader;
		try {
			bufferReader = new BufferedReader(new FileReader(fileName));

			String line;
			while ((line = bufferReader.readLine()) != null) {
				stringBuilder.append(line).append(System.lineSeparator());
			}
			this.gameData.set(stringBuilder.toString());
			bufferReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveGame(File file) {
		try {
			PrintWriter saveFile = new PrintWriter(file);

			saveFile.print(this.gameData.get());

			saveFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



}
