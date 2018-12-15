package viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.PipeGameModel;

public class PipeGameViewModel  {

	PipeGameModel pgm;
	public StringProperty gameData;
	
	public PipeGameViewModel(PipeGameModel _pgm) {
		pgm = _pgm;
		gameData = new SimpleStringProperty();
		gameData.bindBidirectional(pgm.gameData);
	}
	
	
	public void rotatePipe(int x, int y) {
		pgm.rotatePipe(x, y);
	}

}
