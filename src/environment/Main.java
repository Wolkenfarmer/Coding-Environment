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
*/

public class Main extends Application{
	private static BorderPane root;
	private static Scene loadingScene;
	private static Label label;
	private static ArrayList<String> input = new ArrayList<String>();

	/**
	 * Reference to the window-height for the entire program.
	 */
	public static double stageHeight;
	/**
	 * Reference to the window-width for the entire program.
	 */
	public static double stageWidth;
	/**
	 * Unified font for big labels in the center of a scene (e.g. "loading...") 
	 */
	public static Font fCenter = Font.font("Arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 80);

	/**
	 * Main method of the program calling launch.
	 * Starts the application by calling launch(args) which calls up start.
	 * @param args Used for calling launch (javafx Application)
	 * @see #start(Stage)
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * (Builds) and starts the javafx application.
	 * Creates the stage (window) for the program and shows a temporary loading-screen.
	 * @param stage Makes up the window and is required for a javafx application.
	 */
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

	/**
	 * Just to test whether private methods get included or not.
	 * @return wuff
	 */
	private String test() {
		return "wuff";
	}
}
