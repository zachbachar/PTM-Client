package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class PipeGameDisplayer extends Canvas{

	String[][] gameData;
	StringProperty straightPipeFileName;
	StringProperty angeledPipeFileName;


	public String[][] getGameData() {
		return gameData;
	}



	public void setGameData(String[][] gameData) {
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
			Image sPipe = null;
			Image aPipe = null;
			
			sPipe = new Image("./straightPipe.jpg");
			aPipe = new Image("./angeledPipe.jpg");
			
			gc.clearRect(0, 0, W, H);
			
			for (int i = 0; i < gameData.length; i++) {
				for (int j = 0; j < gameData[i].length; j++) {
					if (gameData[i][j] != " ") {
						if(aPipe == null | sPipe == null)
							gc.fillRect(j*w, i*h, w, h);
						else {
							if (gameData[i][j].equals("s"))
								gc.fillRect(j*w, i*h, w, h);
							else if (gameData[i][j].equals("g"))
								gc.fillRect(j*w, i*h, w, h);
							else if (gameData[i][j].equals("|"))
								gc.drawImage(sPipe, j * w, i * h, w, h);
							if (gameData[i][j].equals("-")) 
								gc.drawImage(rotate(sPipe, 90), j * w, i * h, w, h);
							if (gameData[i][j].equals("L"))
								gc.drawImage(rotate(aPipe, 90), j * w, i * h, w, h);
							else if (gameData[i][j].equals("F")) //rotate once
								gc.drawImage(rotate(aPipe, 180), j * w, i * h, w, h);
							else if (gameData[i][j].equals("7")) //rotate twice
								gc.drawImage(rotate(aPipe, 270), j * w, i * h, w, h);
							else if(gameData[i][j].equals("J")) //rotate three times
								gc.drawImage(rotate(aPipe, 360), j*w, i*h, w, h);
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
}
