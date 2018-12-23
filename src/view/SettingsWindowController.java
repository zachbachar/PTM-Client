package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import viewModel.PipeGameViewModel;

public class SettingsWindowController implements Initializable {

	@FXML
	TextField ipField;
	@FXML
	TextField portField;
	@FXML
	ToggleGroup themeGroup;
	@FXML
	RadioButton bright;
	@FXML
	RadioButton dark;
	PipeGameViewModel vm;
	IntegerProperty themeType;
	StringProperty ip;
	IntegerProperty port;
	
	Stage stage;
	
	public void setStage(Stage _stage) {
		stage = _stage;
	}
	
	public void setViewModel(PipeGameViewModel _vm) {
		vm = _vm;
		themeType.bindBidirectional(vm.themeType);
		ip = new SimpleStringProperty();
		vm.ip.bindBidirectional(ip);
		port = new SimpleIntegerProperty();
		vm.port.bindBidirectional(port);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		themeType = new SimpleIntegerProperty();
		themeType.addListener((val, s, t) ->{
			vm.changeTheme();
			switch (Integer.parseInt(t.toString())) {
			case 1:
				themeGroup.selectToggle(bright);
				break;
			case 2:
				themeGroup.selectToggle(dark);
				break;
			default:
				break;
			}
		});
		
		themeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle arg2) {
				RadioButton checked = (RadioButton) themeGroup.getSelectedToggle();
				themeType.set(Integer.parseInt(checked.getId()));
				System.out.println("checked: " + checked.getId());
			}
		});	
		
		portField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                		portField.setText(oldValue);
                }
            }
        });
	}
	
	
	
	public void dissmiss() {
		stage.close();
	}
	
	public void save(){
		if(!ipField.getText().equals(""))
			ip.set(ipField.getText());
		if(!portField.getText().equals(""))
			port.set(Integer.parseInt(portField.getText()));
	}
}
