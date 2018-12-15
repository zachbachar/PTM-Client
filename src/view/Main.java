package view;
	
import javafx.application.Application;
import javafx.stage.Stage;
import model.PipeGameModel;
import viewModel.PipeGameViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			PipeGameModel pgm = new PipeGameModel();
			PipeGameViewModel vm = new PipeGameViewModel(pgm);
			//pgm.addObserver(vm);
			
			FXMLLoader fxl=new FXMLLoader();
			BorderPane root= fxl.load(getClass().getResource("MainWindow.fxml").openStream());
			MainWindowController mwc=fxl.getController(); // View
			mwc.setViewModel(vm);
			//vm.addObserver(mwc);
			
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
