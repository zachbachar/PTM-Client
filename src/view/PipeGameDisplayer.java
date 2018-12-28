package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import model.PipeGameThemeModel;

public class PipeGameDisplayer extends Canvas{

	char[][] gameData;
	StringProperty straightPipeFileName;
	StringProperty angeledPipeFileName;
	PipeGameThemeModel theme;
	boolean isMuted = false;
	MediaPlayer mediaPlayer;

	public PipeGameThemeModel getTheme() {
		return theme;
	}


	public void setTheme(PipeGameThemeModel theme) {
		this.theme = theme;
	}



	public char[][] getGameData() {
		return gameData;
	}



	public void setGameData(char[][] gameData) {
		this.gameData = gameData;
		redraw();
	}



	public StringProperty getStraightPipeFileName() {
		return straightPipeFileName;
	}



	public void setStraightPipeFileName(StringProperty straightPipeFileName) {
		this.straightPipeFileName.set(straightPipeFileName.get());
	}



	public StringProperty getAngeledPipeFileName() {
		return angeledPipeFileName;
	}



	public void setAngeledPipeFileName(StringProperty angeledPipeFileName) {
		this.angeledPipeFileName.set(angeledPipeFileName.get());
	}
	
	public PipeGameDisplayer() {
		straightPipeFileName = new SimpleStringProperty();
		angeledPipeFileName = new SimpleStringProperty();
	}

	public void redraw() {
		if (gameData != null) {
			double W = getWidth();
			double H = getHeight();
			double w = W / gameData[0].length;
			double h = H / gameData.length;
			
			GraphicsContext gc = getGraphicsContext2D();
			
			Image sPipe = theme.getStraightPipeImage();
			Image aPipe = theme.getAngeledPipeImage();
			Image start = theme.getStartImage();
			Image goal = theme.getGoalImage();
			Image bg = theme.getBackgroundImage();
			
			gc.clearRect(0, 0, W, H);
			gc.drawImage(bg, 0, 0, W, H);
			
			for (int i = 0; i < gameData.length; i++) {
				for (int j = 0; j < gameData[i].length; j++) {
					if (gameData[i][j] != ' ') {
						if(aPipe == null | sPipe == null)
							gc.fillRect(j*w, i*h, w, h);
						else {
							if (gameData[i][j] == 's')
								gc.drawImage(start, j * w, i * h, w, h);
							else if (gameData[i][j] == 'g')
								gc.drawImage(goal, j * w, i * h, w, h);
							else if (gameData[i][j] == '|')
								gc.drawImage(sPipe, j * w, i * h, w, h);
							if (gameData[i][j] == '-') 
								gc.drawImage(rotate(sPipe, 90), j * w, i * h, w, h);
							if (gameData[i][j] == 'L')
								gc.drawImage(aPipe, j * w, i * h, w, h);
							else if (gameData[i][j] == 'F') 
								gc.drawImage(rotate(aPipe, 90), j * w, i * h, w, h);
							else if (gameData[i][j] == '7') 
								gc.drawImage(rotate(aPipe, 180), j * w, i * h, w, h);
							else if(gameData[i][j] == 'J') 
								gc.drawImage(rotate(aPipe, 270), j*w, i*h, w, h);
						}
					}
				}
			}
		}
	}
	
	private Image rotate(Image img, double rotation) {
		ImageView iv = new ImageView(img);
		iv.setRotate(rotation);
		SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
		return iv.snapshot(params, null);
	}
	
	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double minHeight(double width) {
		return 64;
	}

	@Override
	public double maxHeight(double width) {
		return 1000;
	}

	@Override
	public double prefHeight(double width) {
		return minHeight(width);
	}

	@Override
	public double minWidth(double height) {
		return 0;
	}

	@Override
	public double maxWidth(double height) {
		return 10000;
	}

	@Override
	public void resize(double width, double height) {
		super.setWidth(width);
		super.setHeight(height);
		this.redraw();
	}



	
}
