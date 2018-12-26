package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PipeGameModel  {

	public StringProperty gameData;
	public StringProperty solution;
	public BooleanProperty isGoal;
	public enum from {start, goal, right, left, up, down};
	

	public PipeGameModel() {
		gameData = new SimpleStringProperty();
		solution = new SimpleStringProperty();
		isGoal = new SimpleBooleanProperty();
		isGoal.set(false);
	}
	
	public boolean isStartorGoal(int x, int y) {
		StringBuilder sb = new StringBuilder(gameData.get());
		int size = sb.indexOf(System.lineSeparator()) + 1;
		int index = (y * size) + x;
		if(sb.charAt(index) == 's' || sb.charAt(index) == 'g')
			return true;
		return false;
	}
	

	public void rotatePipe(int x, int y) {
		if(isStartorGoal(x, y))
			return;
		
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
		isGoal.set(isGoalState());

	}
	public void loadGame(String fileName) {
		isGoal.set(isGoalState());
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
	///////////////////////////IsGoalLogic////////////////////////////////////
	
	public boolean isGoalState() {
		int[] startPos = findChar('s');
		int[] goalPos = findChar('g');

		if((startPos[0] < 0)||(startPos[1] < 0)){
			startPos = findChar('S');
			if((startPos[0] < 0)||(startPos[1] < 0)){
				return false;
			}
		}
		if((goalPos[0] < 0)||(goalPos[1] < 0)){
			goalPos = findChar('G');
			if((goalPos[0] < 0)||(goalPos[1] < 0)){
				return false;
			}
		}
		return hasPath(startPos,from.start);
	}


	public int[] findChar(char ch) {
		ArrayList<char[]> board = new ArrayList<>();
		String[] rows = gameData.get().split(System.lineSeparator());

		for (String row : rows) {
			board.add(row.toCharArray());
		}

		int[] charPosition = {-1,-1};
		for (int i=0;i<board.size();i++) {
			for (int j=0;j<board.get(i).length;j++) {
				if(board.get(i)[j] == ch) {
					charPosition[0] = i;
					charPosition[1] = j;
					return charPosition;
				}
			}
		}
		return charPosition;
	}

	private boolean hasPath(int[] pos, from f) {
		ArrayList<char[]> board = new ArrayList<>();
		String[] rows = gameData.get().split(System.lineSeparator());

		for (String row : rows) {
			board.add(row.toCharArray());
		}
		
		if((pos[0]<0 || pos[1]<0) || (pos[0]>=board.size())) {
			return false;
		}
		else if(pos[1]>=board.get(pos[0]).length) {
			return false;
		}

		char shape = board.get(pos[0])[pos[1]];

		switch (shape) {
		case '-':
			if(f == from.left) {
				pos[1]++;
				return hasPath(pos, f);
			}
			else if(f == from.right) {
				pos[1]--;
				return hasPath(pos, f);
			}
			else {
				return false;
			}

		case '|':
			if(f == from.up) {
				pos[0]++;
				return hasPath(pos,f);
			}
			else if(f == from.down) {
				pos[0]--;
				return hasPath(pos,f);
			}
			else {
				return false;
			}

		case 'L':
			if (f == from.up) {
				pos[1]++;
				return hasPath( pos,from.left);
			}
			else if(f== from.right) {
				pos[0]--;
				return hasPath(pos,from.down);
			}
			else {
				return false;
			}

		case 'F':
			if(f==from.down) {
				pos[1]++;
				return hasPath(pos,from.left);
			}
			else if(f==from.right) {
				pos[0]++;
				return hasPath(pos,from.up);
			}
			else {
				return false;
			}

		case '7':
			if(f==from.left) {
				pos[0]++;
				return hasPath( pos, from.up);
			}
			else if(f==from.down) {
				pos[1]--;
				return hasPath( pos, from.right);
			}
			else {
				return false;
			}

		case 'J':
			if(f==from.up) {
				pos[1]--;
				return hasPath( pos, from.right);
			}
			else if(f==from.left) {
				pos[0]--;
				return hasPath( pos, from.down);
			}
			else {
				return false;
			}

		case 'S': 
		case 's':
			if(f == from.start) {
				int[] temp = {pos[0],pos[1]};
				pos[0]--;
				boolean a1 = hasPath( pos,from.down);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[0]++;
				boolean a2 = hasPath( pos,from.up);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]--;
				boolean a3 = hasPath( pos,from.right);
				pos[0] = temp[0];
				pos[1] = temp[1];
				pos[1]++;
				boolean a4 = hasPath( pos,from.left);
				pos[0] = temp[0];
				pos[1] = temp[1];
				return (a1||a2||a3||a4);
			}
			else {
				return false;
			}
		case 'G':
		case 'g':
			if(f==from.up) {
				if(board.get(pos[0]-1)[pos[1]] == 's') {
					return false;
				}
				else if(board.get(pos[0]-1)[pos[1]] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.down) {
				if(board.get(pos[0]+1)[pos[1]] == 's') {
					return false;
				}
				else if(board.get(pos[0]+1)[pos[1]] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.left) {
				if(board.get(pos[0])[pos[1]-1] == 's') {
					return false;
				}
				else if(board.get(pos[0])[pos[1]-1] == 'S') {
					return false;
				}
				return true;
			}
			else if(f==from.right) {
				if(board.get(pos[0])[pos[1]+1] == 's') {
					return false;
				}
				else if(board.get(pos[0])[pos[1]+1] == 'S') {
					return false;
				}
				return true;
			}
			return false;
		default:
			return false;
		}
	}


}
