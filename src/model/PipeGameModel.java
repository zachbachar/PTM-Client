package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PipeGameModel  {

	public StringProperty gameData;
	public StringProperty solution;
	public BooleanProperty isGoal;
	public IntegerProperty moves;
	public IntegerProperty seconds;
	public enum from {start, goal, right, left, up, down};
	private Timer timer;
	

	public PipeGameModel() {
		gameData = new SimpleStringProperty();
		solution = new SimpleStringProperty();
		isGoal = new SimpleBooleanProperty();
		isGoal.set(false);
		moves = new SimpleIntegerProperty(0);
		seconds = new SimpleIntegerProperty(0);
		timer = new Timer();
		timer.scheduleAtFixedRate(secondsTimer(), 1000, 1000);
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
			boolean hasSec = false, hasMov = false;
			while ((line = bufferReader.readLine()) != null) {
				if(line.startsWith("seconds:")) {
					int sec = Integer.parseInt(line.split(":")[1]);
					seconds.set(sec);
					hasSec = true;
				}
				else if(line.startsWith("moves:")) {
					int mov = Integer.parseInt(line.split(":")[1]);
					moves.set(mov);
					hasMov = true;
				}else
					stringBuilder.append(line).append(System.lineSeparator());
			}
			if(!hasSec)
				seconds.set(0);
			if(!hasMov)
				moves.set(0);
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
			
			saveFile.println("seconds:" + seconds.get());
			saveFile.println("moves:" + moves.get());


			saveFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void stopTimer() {
		timer.cancel();
		timer.purge();
	}
	
	public void newTimer() {
		timer = new Timer();
		timer.scheduleAtFixedRate(secondsTimer(), 1000, 1000);
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

	private TimerTask secondsTimer() {
		return new TimerTask() {
			public void run(){
				Platform.runLater(()-> seconds.set(seconds.get() + 1));
			}
		};
	}
}
