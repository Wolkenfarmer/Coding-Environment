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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Main class hosting the javafx Application.
 * Gets called on start of the program and is the base for the javafx application.
 * Builds the stage (window) with some basic setup like FullScreen-Mode or the {@link #scrollbar} along with its calculations.
 * In addition, this class provides the program with some basic layouts like the headline font {@link #fHeadline}.
 * Ultimately, {@link environment.Homepage} gets called.
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
	 * A dummyScene in order to calculate the dimensions of layout objects while building. 
	 * The scene gets connected with {@link #dummyRoot} in {@link #start(Stage)}. 
	 * This scene is needed for {@link #calcHeight(Region)} and {@link #calcHeightLabel(Label, double)} 
	 * in order to be able to applyCSS and layout() the given objects without having to attach them to {@link #scene} 
	 * where the entire page is added to.
	 */
	static Scene dummyScene;
	/**
	 * A dummyRoot for the {@link #dummyScene} in order to calculate the dimensions of layout objects while building.
	 * This is needed to setup the scene.
	 */
	static Group dummyRoot;
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
	
	/** Unified referenceable font for layouts.*/
	static Font fHeadline = Font.font("Arial", FontWeight.BOLD, 50);
	/** Unified referenceable font for layouts.*/
    static Font fSubheadline = new Font("Arial", 35);
    /** Unified referenceable font for layouts.*/
    static Font fNormalText = new Font("Arial", 20);
    /** Unified referenceable font for layouts.*/
	static Font fSmallText = new Font("Arial", 15);
	/** Unified referenceable font for layouts.*/
	static Font fSmallTextItalic = Font.font("Arial", FontPosture.ITALIC, 15);
	/** Unified referenceable corner radius for layouts.*/
	static CornerRadii crNormal = new CornerRadii(10);
	/** Unified referenceable button background for layouts.*/
    static Background baNormalButton = new Background (new BackgroundFill(Color.rgb(90, 90, 90), crNormal,  null));
	/** Unified referenceable button background (focused) for layouts.*/
    static Background baNormalFocusedButton = new Background (new BackgroundFill(Color.rgb(70, 70, 70), crNormal,  null));
	/** Unified referenceable button background for layouts.*/
    static Background baGreenButton = new Background (new BackgroundFill(Color.rgb(0, 90, 0), crNormal,  null));
	/** Unified referenceable button background (focused) for layouts.*/
    static Background baGreenFocusedButton = new Background (new BackgroundFill(Color.rgb(0, 70, 0), crNormal,  null));
	/** Unified referenceable Border for layouts.*/
    static Border boNormalWhite = new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, crNormal, BorderWidths.DEFAULT));

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
		dummyScene = new Scene(dummyRoot = new Group());
        
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
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!input.contains(code)) input.add(code);
            }
        });
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (input.contains("ESCAPE")) {
                	Platform.exit();
                }
            }
        });
		
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
	
	
	/**
	 * Calculates the Height of a given Region.
	 * JavaFX layout calculations just work by applying CSS and a layout pass, which occures usually after a page is finished building.
	 * Therefore, the region gets temporarily attached to {@link #dummyScene} (instead of {@link #scene}) 
	 * in order to get the height before the page is finished building.
	 * This is necessary because the height is relevant to continue building the page.
	 * @param r The region (or subclass of it) of which the height gets calculated.
	 * @return Returns the height of the region.
	 */
	public static double calcHeight(Region r) {
		dummyRoot.getChildren().add(r);
		dummyRoot.applyCss();
		dummyRoot.layout();
		dummyRoot.getChildren().remove(r);
		return r.getHeight();
	}
	
	/**
	 * Calculates the Height of a given Label.
	 * JavaFX layout calculations just work by applying CSS and a layout pass, which occures usually after a page is finished building.
	 * Therefore, the label gets temporarily attached to {@link #dummyScene} (instead of {@link #scene}) 
	 * in order to get the height before the page is finished building. 
	 * For this to work firstly the width of the label with its content gets calculated and then divided by the parent Objects width to get the 
	 * number of lines. Then the height of one individual line gets muliplied with it. In addition, an approximation for the distance of each line
	 * gets added. This is necessary because the height is relevant to continue building the page.
	 * <p>
	 * Note: This calculation is _not_ exact and is based on approximations, because neither the insets nor the padding of the parent or the label
	 * give back the exact spacing between them. Ultimately, there is no known way to get the spacing between each individual line in pixel depending
	 * on it's size.
	 * @param l The label of which the height gets calculated.
	 * @param parentWidth The width of the parent object in order to calculate the nubmer of lines.
	 * @return Returns the height of a label
	 */
	public static double calcHeightLabel(Label l, double parentWidth) {
		dummyRoot.getChildren().add(l);
		dummyRoot.applyCss();
		dummyRoot.layout();
		double lines = Math.ceil(l.getWidth() / (parentWidth - 20));
		double height = (l.getHeight() * lines) + (l.getLineSpacing() + (l.getFont().getSize()) * (lines - 1));
		dummyRoot.getChildren().remove(l);
		return height;
	}
}
