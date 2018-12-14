package view;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import viewModel.PipeGameViewModel;

@SuppressWarnings("deprecation")
public class MainWindowController implements Initializable, Observer {

	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	
	PipeGameViewModel vm;
	
	String[][] pipeData = {
			{ "s", "-", "-", "F" },
			{ "L", "|", "-", "L" },
			{ "L", "|", "-", "g" }};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pipeGameDisplayer.setGameData(pipeData);
	}
	
	public void setViewModel(PipeGameViewModel _vm) {
		vm = _vm;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
