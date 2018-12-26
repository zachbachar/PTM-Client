package viewModel;

import java.io.File;

import client.ClientModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.PipeGameModel;
import model.PipeGameThemeModel;

public class PipeGameViewModel  {

	PipeGameModel pgm;
	ClientModel client;
	PipeGameThemeModel theme;
	public StringProperty gameData;
	public StringProperty solution;
	public StringProperty ip;
	public IntegerProperty port;
	public StringProperty errorMessage;
	public IntegerProperty themeType;
	public BooleanProperty isGoal;
	public BooleanProperty redraw;
	
	public PipeGameViewModel(PipeGameModel _pgm, ClientModel _client) {
		pgm = _pgm;
		client = _client;
		gameData = new SimpleStringProperty();
		gameData.bindBidirectional(pgm.gameData);
		solution = new SimpleStringProperty();
		solution.bindBidirectional(pgm.solution);
		ip = new SimpleStringProperty();
		ip.bindBidirectional(client.ip);
		port = new SimpleIntegerProperty();
		port.bindBidirectional(client.port);
		errorMessage = new SimpleStringProperty();
		errorMessage.bindBidirectional(client.errorMessage);
		isGoal = new SimpleBooleanProperty();
		isGoal.bind(pgm.isGoal);
		redraw = new SimpleBooleanProperty();
		redraw.set(false);
	}
	
	public PipeGameThemeModel getTheme() {
		return theme;
	}
	public void setTheme(PipeGameThemeModel _theme) {
		theme = _theme;
		themeType = new SimpleIntegerProperty();
		themeType.bindBidirectional(theme.themeType);
	}
	
	public void changeTheme() {
		theme.loadMedia();
		redraw.set(!redraw.get());
		theme.playMusic();
	}
	
	public void muteSound() {
		theme.mute();
	}
	
	
	public String sendToServer(String message) {
		return client.sendToServer(message);
	}
	
	public void rotatePipe(int x, int y) {
		pgm.rotatePipe(x, y);
	}
	
	public void loadGame(String fileName) {
		this.pgm.loadGame(fileName);
	}

	public void saveGame(File file) {
		this.pgm.saveGame(file);
	}
	
	public boolean isStartOrGoal(int x, int y) {
		return pgm.isStartorGoal(x, y);
	}
	

}
