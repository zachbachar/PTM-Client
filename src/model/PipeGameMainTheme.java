package model;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class PipeGameMainTheme implements Theme {

	Image straightPipeImage = null;
	Image angeledPipeImage = null;
	Image startImage = null;
	Image goalImage = null;
	Image bgImage = null;
	Media sound = null;
	
	public PipeGameMainTheme() {
		straightPipeImage = new Image("file:resources/straightPipe.jpg");
		angeledPipeImage = new Image("file:resources/angeledPipe.jpg");
		startImage = new Image("file:resources/start.jpg");
		goalImage = new Image("file:resources/goal.jpg");
		bgImage = new Image("file:resources/bg.jpg");
	}
	
	
	@Override
	public Image getStraightPipeImage() {
		return straightPipeImage;
	}

	@Override
	public Image getAngeledPipeImage() {
		return angeledPipeImage;
	}

	@Override
	public Image getBackgroundImage() {
		return bgImage;
	}

	@Override
	public Media getBackgroundSound() {
		return null;
	}


	@Override
	public Image getStartImage() {
		return startImage;
	}


	@Override
	public Image getGoalImage() {
		return goalImage;
	}

}
