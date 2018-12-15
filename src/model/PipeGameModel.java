package model;

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



}
