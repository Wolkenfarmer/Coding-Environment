package environment;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * @author Wolkenfarmer
 * @version 0.1
 * @since 28.08.2020
*/

public class Main extends Application{
	private static BorderPane root;
	private static Scene loadingScene;
	private static Label label;
	private static ArrayList<String> input = new ArrayList<String>();

	
	public static double stageHeight;
	public static double stageWidth;
	public static Font fCenter = Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 80);

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage stage) throws Exception {
		root = new BorderPane();
		root.setBackground(Background.EMPTY);
		loadingScene = new Scene(root, Color.grayRgb(40));
        
		stage = new Stage();
		stage.setTitle("Source / Channel Coding Environment");
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.setResizable(false);   
		stage.setScene(loadingScene);
		stage.show();
		stageWidth = stage.getWidth();
		stageHeight = stage.getHeight();
		System.out.println("Stage size: " + stageWidth + " * " + stageHeight);
		
		root.setPrefSize(stageWidth, stageHeight);
		
		label = new Label();
		label.setText("loading ...");
		label.setTextFill(Color.WHITESMOKE);
		label.setFont(fCenter);
		root.setCenter(label);
		
		
		// listener
		loadingScene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) input.add(code);
                }
            }
    	);
		loadingScene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    if (input.contains("ESCAPE")) {
                    	Platform.exit();
                    }
                }
            }
        );
	}

}
