package model;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class PipeGameModel extends Observable  {

	String gameData;
	
	

	public PipeGameModel() {
		
	}

	

	public String getGameData() {
		return gameData;
	}



	public void setGameData(String gameData) {
		this.gameData = gameData;
	}





	public void rotatePipe(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
