package view;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.PipeGameThemeModel;
import viewModel.PipeGameViewModel;


public class MainWindowController implements Initializable {

	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	StringProperty gameData;
	PipeGameViewModel vm;
	PipeGameThemeModel theme;
	IntegerProperty themeType;
	
	char[][] pipeData = {
			{ 's', ' ', '-', 'F' },
			{ '-', 'L', '-', '7' },
			{ 'J', '|', '-', 'g' }};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTheme();
		pipeGameDisplayer.setTheme(theme);
		pipeGameDisplayer.setGameData(pipeData);
	}
	
	private void initTheme() {
		themeType = new SimpleIntegerProperty();
		theme = new PipeGameThemeModel();
		theme.themeType.bindBidirectional(this.themeType);
		themeType.addListener((val, s, t) ->{
			theme.loadMedia();
		});
		themeType.set(1);
	}
	
	public void changeTheme() {
		switch (themeType.get()) {
		case 1:
			themeType.set(2);
			break;
		case 2:
			themeType.set(1);
			break;

		default:
			break;		
		}
	}


	public void setViewModel(PipeGameViewModel _vm) {
		vm = _vm;
		gameData = new SimpleStringProperty();
		vm.gameData.bindBidirectional(gameData);
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pipeData.length; i++) {
			for (int j = 0; j < pipeData[i].length; j++) {
				sb.append(pipeData[i][j]);
			}
			sb.append(System.lineSeparator());
		}
		gameData.set(sb.toString());
		
		gameData.addListener((val, s, t) -> {
			ArrayList<char[]> board = new ArrayList<>();
			String[] rows = gameData.get().split(System.lineSeparator());

			for (String row : rows) {
				board.add(row.toCharArray());
			}
			pipeData = board.toArray(new char[board.size()][]);

			
			pipeGameDisplayer.setGameData(pipeData);
		});
		
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
	
	public void openFile() {
		System.out.println("Open File.");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Stage File");
		fileChooser.setInitialDirectory(new File("./resources"));

		FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
		fileChooser.getExtensionFilters().add(txtFilter);
		fileChooser.setSelectedExtensionFilter(txtFilter);
		File choosen = fileChooser.showOpenDialog(null);

		if (choosen != null) {
			System.out.println(choosen.getName());
			vm.loadGame(choosen.getAbsolutePath());
		}
	}

	public void saveFile() {
		System.out.println("Saving into File.");
		FileChooser fc = new FileChooser();
		fc.setTitle("Choose location");
		FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
		fc.getExtensionFilters().add(txtFilter);
		fc.setSelectedExtensionFilter(txtFilter);

		File chosenFile = fc.showSaveDialog(null);

		if (chosenFile == null) {
			return;
		}
		vm.saveGame(chosenFile);
	}
	
	
}
