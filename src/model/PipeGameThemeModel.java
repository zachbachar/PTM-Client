package model;

import static javafx.scene.media.AudioClip.INDEFINITE;

import java.nio.file.Paths;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PipeGameThemeModel{

	public IntegerProperty themeType;
	private Image straightPipeImage = null;
	private Image angeledPipeImage = null;
	private Image startImage = null;
	private Image goalImage = null;
	private Image bgImage = null;
	private Media sound = null;
	private Media soundTwo = null;
	MediaPlayer mediaPlayer;
	Boolean isMuted=false;
	
	public PipeGameThemeModel() {
		themeType = new SimpleIntegerProperty();
	}
	
	public void loadMedia() {
		straightPipeImage = new Image("file:resources/straightPipe" + themeType.get() + ".jpg");
		angeledPipeImage = new Image("file:resources/angeledPipe" + themeType.get() + ".jpg");
		startImage = new Image("file:resources/start" + themeType.get() + ".jpg");
		goalImage = new Image("file:resources/goal" + themeType.get() + ".jpg");
		bgImage = new Image("file:resources/bg" + themeType.get() + ".jpg");
		sound = new Media(Paths.get("./resources/BackroundTheme.wav").toUri().toString());
		soundTwo = new Media(Paths.get("./resources/themeSoundTwo.wav").toUri().toString());
		//add sound number 2 and play it from playMusic  with check
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
	
	public void playMusic() {
		if(mediaPlayer != null) {
			mediaPlayer.stop();
		}
		if(themeType.get()==1) {
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.setCycleCount(INDEFINITE);
		}
		else {
			mediaPlayer = new MediaPlayer(soundTwo);
			mediaPlayer.setAutoPlay(true);
			mediaPlayer.setCycleCount(INDEFINITE);
		}
	}
	public void mute() {
		if(!isMuted) {
			mediaPlayer.setVolume(0.0);
			isMuted = true;
		}
		else {
			mediaPlayer.setVolume(100.0);
			isMuted = false;
		}	
	}
	

}
