package environment;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

/**
 * Main class hosting the javafx Application.
 * Gets called on start of the program and is the base for the javafx application.
 * Builds the stage (window) with some basic setup like FullScreen-Mode or the {@link #scrollbar} along with its calculations.
 * In addition, this class provides the program with some basic layouts like the headline font {@link #fHeadline}. 
 * @author Wolkenfarmer
 * @version 1.0
*/

public class Main extends Application{
	/**
	 * The Scene for the {@link #start(Stage)}.
	 * @see	 <a href="https://www.educba.com/javafx-applications/">Javafx Application basic structure</a>
	 */
	static Scene scene;
	/**
	 * Layout-group to contains the {@link #scrollbar} and start up the {@link #scene}. 
	 * This layout-group need to be seperate from {@link #root}, 
	 * because root gets moved along it's y-axis while scrolling / using the scrollbar, 
	 * while the scrollbar itself should not move in sbRoot.
	 * @see	 <a href="https://www.educba.com/javafx-applications/">Javafx Application basic structure</a>
	*/
	static Group sbRoot;
	/**
	 * Layout-group to contain the content of the pages (like {@link environment.Homepage}).
	 * This layout-group need to be seperate from {@link #sbRoot}, 
	 * because root gets moved along it's y-axis while scrolling / using the {@link #scrollbar}, 
	 * while the scrollbar itself should not move in sbRoot.
	*/
	static Group root;
	/**
	 * The scrollbar for all pages nested in {@link #sbRoot}. 
	 * EventHandler and Listener with its calculations are in {@link #start(Stage)}.
	 */
	static ScrollBar scrollbar;
	/**
	 * Reference to the window-height for the entire program. 
	 * This useful for the layout-calculations of the pages' contents.
	 */
	static double stageHeight;
	/**
	 * Reference to the window-width for the entire program.
	 * This useful for the layout-calculations of the pages' contents.
	 */
	static double stageWidth;
	/**
	 * Describes the height of content of each page. 
	 * This value gets calculated / get overwritten for each page in order for the {@link #scrollbar} to compute its movement space correctly.
	 */
	static double contentHeight;
	/**
	 * Input handling.
	 * This ArrayList gets filled / used in {@link #start(Stage)} by the scene listeners. 
	 * In this scene it's only used for a short cut to close the program (Esc).
	 */
	static ArrayList<String> input = new ArrayList<String>();
	
	/** Unified referenceable font for the layout of each page.*/
	static Font fHeadline = new Font("Arial", 40);
	/** Unified referenceable font for the layout of each page.*/
    static Font fSubheadline = new Font("Arial", 35);
    /** Unified referenceable font for the layout of each page.*/
    static Font fNormalText = new Font("Arial", 20);
    /** Unified referenceable font for the layout of each page.*/
	static Font fSmallText = new Font("Arial", 15);
	/** Unified referenceable font for the layout of each page.*/
	static Font fSmallTextItalic = Font.font("Arial", FontPosture.ITALIC, 15);
	/** Unified referenceable button background for the layout of each page.*/
    static Background bGreenButton = new Background (new BackgroundFill(Color.rgb(0, 90, 0), new CornerRadii(10),  null));
	/** Unified referenceable button background (focused) for the layout of each page.*/
    static Background bGreenFocusedButton = new Background (new BackgroundFill(Color.rgb(0, 70, 0), new CornerRadii(10),  null));

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
	 * Sets up the stage and links it up to the {@link #scene}. 
	 * Both are modified with some basic setup like FullScreen-Mode or the {@link #scrollbar} (which gets added via {@link #sbRoot} to the scene). 
	 * In addition, the keyboard and scrollbar listeners are written here and added to the scene.
	 * Lastly, {@link environment.Homepage} gets called.
	 * @param stage Makes up the window and is required for a javafx application.
	 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html#start-javafx.stage.Stage-">
	 * Javafx Application start() documentation</a>
	 */
	public void start(Stage stage) throws Exception {
		sbRoot = new Group();
		root = new Group();
		scene = new Scene(sbRoot, Color.grayRgb(40));
        
		stage = new Stage();
		stage.setTitle("Source / Channel Coding Environment");
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setResizable(false);   
		stage.setScene(scene);
		stage.show();
		stageWidth = stage.getWidth();
		stageHeight = stage.getHeight();
		System.out.println("Stage size: " + stageWidth + " * " + stageHeight);
		
		scrollbar = new ScrollBar();
        scrollbar.setOrientation(Orientation.VERTICAL);
        scrollbar.setMin(0);
        scrollbar.setPrefWidth(20);
        scrollbar.setPrefHeight(scene.getHeight());
        scrollbar.setLayoutX(scene.getWidth()-scrollbar.getWidth());
        sbRoot.getChildren().addAll(scrollbar, root);
        		
		
		// listener
		// Keyboard input handling
		scene.setOnKeyPressed(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    String code = e.getCode().toString();
                    if (!input.contains(code)) input.add(code);
                }
            }
    	);
		scene.setOnKeyReleased(
            new EventHandler<KeyEvent>() {
                public void handle(KeyEvent e) {
                    if (input.contains("ESCAPE")) {
                    	Platform.exit();
                    }
                }
            }
        );
		
		// scroll bar
				scrollbar.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						if(e.getY() > 0 && e.getY() < scene.getHeight()) {
							double i = (contentHeight - scene.getHeight()) * (e.getY() / scene.getHeight());
							if (e.getY() < (scene.getHeight() / 2)) {
								i = i - ((((contentHeight - scene.getHeight()) * 0.5) - i) * (1 / (scene.getHeight() * 0.02)));
							} else {
								i = i + ((i - ((contentHeight - scene.getHeight()) * 0.5)) * (1 / (scene.getHeight() * 0.02)));
							}
							scrollbar.setValue(i);
							if (scrollbar.getValue() < 0) {
								scrollbar.setValue(0);
							} else if (scrollbar.getValue() > (contentHeight - scene.getHeight())) {
								scrollbar.setValue(contentHeight - scene.getHeight());
							}
						}
					}
				});		
				scrollbar.valueProperty().addListener(new ChangeListener<Number>() {
				    public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				    	root.setLayoutY(-scrollbar.getValue());
				    }
				});
				scene.addEventHandler(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
					public void handle(ScrollEvent e) {				
						if (scrollbar.isVisible()) {
							scrollbar.setValue(scrollbar.getValue() - e.getDeltaY());
							if (scrollbar.getValue() < 0) {
								scrollbar.setValue(0);
							} else if (scrollbar.getValue() > (contentHeight - scene.getHeight())) {
								scrollbar.setValue(contentHeight - scene.getHeight());
							}
						}
					}
				});		
		
		
		new Homepage(root);
	}
}
