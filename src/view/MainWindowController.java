package view;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import viewModel.PipeGameViewModel;

@SuppressWarnings("deprecation")
public class MainWindowController implements Initializable, Observer {

	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	
	PipeGameViewModel vm;
	
	String[][] pipeData = {
			{ "s", "7", "-", "F" },
			{ "-", "L", "-", "7" },
			{ "J", "|", "-", "g" }};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pipeGameDisplayer.setGameData(pipeData);
		
		
	}


	public void setViewModel(PipeGameViewModel _vm) {
		vm = _vm;
		
		pipeGameDisplayer.addEventHandler(MouseEvent.MOUSE_CLICKED,
				(MouseEvent click) -> {
					double w = pipeGameDisplayer.getWidth() / pipeData[0].length;
					double h = pipeGameDisplayer.getHeight() / pipeData.length;
					int x = (int) (click.getX() / w);
					int y = (int) (click.getY() / h);
					vm.rotatePipe(x, y);
				}
		);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
