import java.time.LocalTime;
import javafx.application.Application;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {
	public static void main(String[] args) {
		System.out.println("Start WBClock");
		Application.launch(args);	
	}
		double dragStartX = 0;
		double dragStartY = 0;

	@Override
	public void start(Stage stage) {
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("WBClock");
		stage.setAlwaysOnTop(true);

		final double hankei = 80 ;
		final double more = 20 ;
		Group root = new Group();
		Canvas canvas = new Canvas((hankei + more * 2) * 2, (hankei + more * 2) * 2);
		GraphicsContext gc =  canvas.getGraphicsContext2D();
		gc.setFill(null);

		int nowSecond;

		Timeline clock = new Timeline (
			new KeyFrame(Duration.millis(10),
			new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
   					LocalTime now = LocalTime.now();

					boolean secondColor;
					boolean minuteColor;
					boolean hourColor;

					if(now.getMinute() % 2 == 0) {
						secondColor = true;
					}else {
						secondColor = false;
					}
					if(now.getHour() % 2 == 0) {
						minuteColor = true;
					}else {
						minuteColor = false;
					}
					if(now.getHour() / 12 == 0) {
						hourColor = true;
					}else {
						hourColor = false;
					}

					try {
						gc.setFill(setColor(!hourColor));
						gc.fillArc(0 ,0, (hankei + more * 2) * 2 , (hankei + more * 2) * 2 , 90 , 360 - (30 * (now.getHour() % 12) + 30 * (double)now.getMinute() / 60 + (double)now.getSecond() / 120) ,ArcType.ROUND);
						gc.setFill(setColor(hourColor));
						gc.fillArc(0, 0, (hankei + more * 2) * 2 , (hankei + more * 2) * 2 , 90 , - (30 * (now.getHour() % 12) + 30 * (double)now.getMinute() / 60 + (double)now.getSecond() / 120) ,ArcType.ROUND);
						gc.setFill(setColor(!minuteColor));
						gc.fillArc(more , more , (hankei + more) * 2 , (hankei + more) * 2, 90 , 360 - (6 * now.getMinute() + 6 * (double)now.getSecond() / 60) ,ArcType.ROUND);
						gc.setFill(setColor(minuteColor));
						gc.fillArc(more , more , (hankei + more) * 2 , (hankei + more) * 2, 90 , - (6 * now.getMinute() + 6 * (double)now.getSecond() / 60) ,ArcType.ROUND);
						gc.setFill(setColor(!secondColor));
						gc.fillArc(more * 2,more * 2, hankei * 2 , hankei * 2, 90 , 360 - (6 * now.getSecond() + 6 * (double)now.getNano() / 1000000000) ,ArcType.ROUND);
						gc.setFill(setColor(secondColor));
						gc.fillArc(more * 2,more * 2, hankei * 2 , hankei * 2, 90 , - (6 * now.getSecond() + 6 * (double)now.getNano() / 1000000000) ,ArcType.ROUND);
						root.getChildren().addAll(canvas);
					} catch(java.lang.IllegalArgumentException e) {
						
					}
				}	
			}
		));
   		
		Scene scene = new Scene(root, (hankei + more * 2) * 2, (hankei + more * 2) * 2);

		scene.setOnMousePressed(e -> {
            dragStartX = e.getSceneX();
            dragStartY = e.getSceneY();
        });

        scene.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() - dragStartX);
            stage.setY(e.getScreenY() - dragStartY);
        });

		clock.setCycleCount(Timeline.INDEFINITE);
		clock.play();

        stage.setScene(scene); 
        stage.getScene().setFill(null); 
        stage.getScene().getRoot().setStyle("-fx-backgroud-color: transparent"); 
        stage.show();
	}

	public Color setColor(boolean c) { 
		if(c == true) {
			return Color.WHITE;
		}else {
			return Color.BLACK;
		}
	}
}

