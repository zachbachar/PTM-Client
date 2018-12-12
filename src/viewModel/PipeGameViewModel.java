package viewModel;

import java.util.Observable;
import java.util.Observer;

import model.PipeGameModel;

@SuppressWarnings("deprecation")
public class PipeGameViewModel extends Observable implements Observer {

	PipeGameModel pgm;
	
	public PipeGameViewModel(PipeGameModel _pgm) {
		pgm = _pgm;
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}
