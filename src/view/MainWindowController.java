package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.PipeGameThemeModel;
import viewModel.PipeGameViewModel;

public class MainWindowController implements Initializable {

	@FXML
	PipeGameDisplayer pipeGameDisplayer;
	@FXML
	Label movesLabel;
	Integer movesCounter = 0;
	StringProperty gameData;
	StringProperty solution;
	StringProperty errorMessage;
	PipeGameViewModel vm;
	PipeGameThemeModel theme;
	IntegerProperty themeType;
	BooleanProperty isGoal;
	BooleanProperty redraw;
	Stage stage;

	char[][] pipeData = { { 's', ' ', '-', 'F' }, { '-', 'L', '-', '7' }, { 'J', '|', '-', 'g' } };

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pipeGameDisplayer.setTheme(new PipeGameThemeModel());
		pipeGameDisplayer.setGameData(pipeData);
	}

	public void changeTheme() {
		vm.changeTheme();
	}

	public void setViewModel(PipeGameViewModel _vm) {
		vm = _vm;
		vm.setTheme(pipeGameDisplayer.getTheme());
		themeType = new SimpleIntegerProperty();
		vm.themeType.bindBidirectional(this.themeType);
		themeType.set(1);
		vm.changeTheme();
		gameData = new SimpleStringProperty();
		solution = new SimpleStringProperty();
		errorMessage = new SimpleStringProperty();
		vm.gameData.bindBidirectional(gameData);
		vm.solution.bindBidirectional(solution);
		vm.errorMessage.bindBidirectional(errorMessage);
		isGoal = new SimpleBooleanProperty();
		isGoal.bind(vm.isGoal);
		redraw = new SimpleBooleanProperty();
		vm.redraw.bindBidirectional(redraw);
		winningListener();

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
		
		redraw.addListener((val, s, t) -> {
			pipeGameDisplayer.redraw();
		});

		solution.addListener((val, s, t) -> {
			String[] sol = solution.get().split(System.lineSeparator());
			for (String line : sol) {
				String[] str = line.split(",");
				int y = Integer.parseInt(str[0]);
				int x = Integer.parseInt(str[1]);
				int times = Integer.parseInt(str[2]);
				for (int i = 0; i < times; i++) {
					vm.rotatePipe(x, y);
				}
			}
		});

		pipeGameDisplayer.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent click) -> {
			double w = pipeGameDisplayer.getWidth() / pipeData[0].length;
			double h = pipeGameDisplayer.getHeight() / pipeData.length;
			int x = (int) (click.getX() / w);
			int y = (int) (click.getY() / h);
			System.out.println("clicked: " + x + "," + y);
			if(!vm.isStartOrGoal(x, y)) {
				movesCounter++;
				movesLabel.setText(movesCounter.toString());
			}
			vm.rotatePipe(x, y);
		});
		
		errorMessage.addListener((val, s, t) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error!");
			alert.setHeaderText(null);
			alert.setContentText(errorMessage.get());
			alert.showAndWait();
		});
	}

	public void solve() {
		solution.set(vm.sendToServer(gameData.get()));
	}
	
	public void closeApp() {
		System.exit(1);
	}
	

	public void showSettingsWindow() throws IOException {
		FXMLLoader fxl = new FXMLLoader();
		BorderPane root = fxl.load(getClass().getResource("SettingsWindow.fxml").openStream());
		SettingsWindowController swc = fxl.getController();
		Scene scene = new Scene(root, 300, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage stage = new Stage();
		stage.setScene(scene);
		swc.setStage(stage);
		swc.setViewModel(vm);
		stage.show();
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

	public void mute() {
		pipeGameDisplayer.mute();
	}

	public void alertWonMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Congratulations");
		alert.setHeaderText(null);
		alert.setContentText("You Win!");
		alert.showAndWait();
	}

	public void winningListener() {
		isGoal.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					alertWonMessage();
				} else if (newValue == null) {
					return;
				}

			}

		});
	}

}
