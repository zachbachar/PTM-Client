package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class PipeGameThemeModel{

	public IntegerProperty themeType;
	private Image straightPipeImage = null;
	private Image angeledPipeImage = null;
	private Image startImage = null;
	private Image goalImage = null;
	private Image bgImage = null;
	private Media sound = null;
	
	public PipeGameThemeModel() {
		themeType = new SimpleIntegerProperty();
	}
	
	public void loadMedia() {
		straightPipeImage = new Image("file:resources/straightPipe" + themeType.get() + ".jpg");
		angeledPipeImage = new Image("file:resources/angeledPipe" + themeType.get() + ".jpg");
		startImage = new Image("file:resources/start" + themeType.get() + ".jpg");
		goalImage = new Image("file:resources/goal" + themeType.get() + ".jpg");
		bgImage = new Image("file:resources/bg" + themeType.get() + ".jpg");
	}
	
	
	public Image getStraightPipeImage() {
		return straightPipeImage;
	}

	
	public Image getAngeledPipeImage() {
		return angeledPipeImage;
	}

	
	public Image getBackgroundImage() {
		return bgImage;
	}

	
	public Media getBackgroundSound() {
		return sound;
	}


	
	public Image getStartImage() {
		return startImage;
	}


	
	public Image getGoalImage() {
		return goalImage;
	}

}
