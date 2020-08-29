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
 * Main class hosting the javafx Application.
 * Gets called on start of the program and is the base for the javafx application.
 * Links up to the homepage scene and provides some basic styles / layouts for the entire program (like fonts or backgrounds).
 * @author Wolkenfarmer
 * @version 1.0
*/

public class Main extends Application{
	/**
	 * Layout-group to contain the {@link #label} and start up the {@link #loadingScene}
	 * @see	 <a href="https://www.educba.com/javafx-applications/">Javafx Application basic structure</a>
	*/
	private static BorderPane root;
	/**
	 * Scene for starting up the stage while homepage is still loading
	 * @see	 <a href="https://www.educba.com/javafx-applications/">Javafx Application basic structure</a>
	*/
	private static Scene loadingScene;
	/**
	 * Displaying "loading ..."
	 */
	private static Label label;
	/**
	 * Input handling.
	 * This ArrayList gets filled / used in {@link #start(Stage)} by the scene listeners. 
	 * In this scene it's only used for a short cut to close the program (Esc).
	 */
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
	 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html">Javafx Application documentation</a>
	 * @see #start(Stage)
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * (Builds) and starts the javafx application.
	 * Creates the stage (window) for the program and shows a temporary loading-screen.
	 * @param stage Makes up the window and is required for a javafx application.
	 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html#start-javafx.stage.Stage-">
	 * Javafx Application start() documentation</a>
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
}
