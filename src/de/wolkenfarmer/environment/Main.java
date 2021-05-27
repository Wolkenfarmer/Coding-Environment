package de.wolkenfarmer.environment;

import java.util.ArrayList;

import de.wolkenfarmer.environment.pages.Transcoder;
import de.wolkenfarmer.environment.pages.gui_elements.OverviewButton;
import de.wolkenfarmer.environment.pages.Home;
import de.wolkenfarmer.environment.pages.InputHandler;
import de.wolkenfarmer.environment.pages.NoiseSource;
import de.wolkenfarmer.input_handlers.RandomDigitBook;
import de.wolkenfarmer.input_handlers.UserInput;
import de.wolkenfarmer.noise_sources.Deselect;
import de.wolkenfarmer.noise_sources.IndividualChanges;
import de.wolkenfarmer.noise_sources.MixUpChanges;
import de.wolkenfarmer.transcoder.DeselectPrePost;
import de.wolkenfarmer.transcoder.ParityCheck;
import de.wolkenfarmer.transcoder.RepetitionCode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
 * Main class hosting the JavaFX Application.
 * Gets called on start of the program and is the base for the JavaFX application.
 * Builds the stage (window) with some basic setup like full screen mode or the {@link #scrollbar scroll bar} along with its calculations.
 * In addition, this class provides the program with some unified layouts like the {@link #fHeadline headline font} 
 * and hosts the instances to the pages and experiment elements (like {@link de.wolkenfarmer.input_handlers.UserInput}).
 * Ultimately, {@link de.wolkenfarmer.environment.pages.Home} gets called.
 * @author Wolkenfarmer
 * @version 0.2
*/
public class Main extends Application {
	/**
	 * The scene for {@link #start(Stage)}.
	 * Uses the style sheets from {@link de.wolkenfarmer.environment.pages.css}.
	 * @see	<a href="https://www.educba.com/javafx-applications/">JavaFX Application basic structure</a>
	 * @see	<a href="https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html">JavaFX CSS support</a>
	 */
	public static Scene scene;
	/**
	 * Layout group to contains the {@link #scrollbar scroll bar} and start up the {@link #scene scene}. 
	 * This layout group needs to be separate from {@link #root}, 
	 * because the root gets moved along its y-axis while scrolling / using the scroll bar, 
	 * while the scroll bar itself should not move in sbRoot.
	 * @see	 <a href="https://www.educba.com/javafx-applications/">Javafx Application basic structure</a>
	*/
	static Group sbRoot;
	/**
	 * Layout group to contain the content of the pages (like {@link de.wolkenfarmer.environment.pages.Home}).
	 * The root gets carried from one page to another when loading them in order to connect its layout-elements to it.
	 * This layout group needs to be separate from the {@link #sbRoot scroll bar root}, 
	 * because root gets moved along its y axis during {@link #scrollbar scrolling}, 
	 * while the scroll bar itself should not move in sbRoot.
	*/
	public static Group root;
	/**
	 * The scroll bar for all pages nested in the {@link #sbRoot scroll bar root}. 
	 * EventHandler and Listener with its calculations are in {@link #start(Stage)}.
	 */
	public static ScrollBar scrollbar;
	/**
	 * A dummyScene in order to calculate the dimensions of layout objects while building. 
	 * The scene gets connected with {@link #dummyRoot dummy root} in {@link #start(Stage)}. 
	 * This scene is needed for {@link #calcHeight(Region)} and {@link #calcHeightLabel(Label, double)} 
	 * in order to be able to applyCSS and layout() the given objects without having to attach them to the {@link #scene scene} 
	 * where the entire page is added to.
	 */
	@SuppressWarnings("unused")
	private static Scene dummyScene;
	/**
	 * A dummy root for the {@link #dummyScene dummy scene} in order to calculate the dimensions of layout objects while building.
	 * This is needed to setup the scene.
	 */
	private static Group dummyRoot;
	/**
	 * Reference to the window height for the entire program. 
	 * This gets used for the layout calculations of the pages' contents.
	 */
	public static double stageHeight;
	/**
	 * Reference to the window width for the entire program.
	 * This gets used for the layout calculations of the pages' contents.
	 */
	public static double stageWidth;
	/** Describes the height of content of each page. 
	 * This value gets calculated for each page in order for the {@link #scrollbar scroll bar} to compute its movement space correctly.*/
	public static double contentHeight;
	/** Describes the layoutX from where the content of the pages start. This gets used for the layout calculations of the pages' contents.*/
	public static double pos1;
	/** Describes the layoutX from where the content of the pages end. This gets used for the layout calculations of the pages' contents.*/
	public static double pos7;
	/** Describes width of the content of the pages. This gets used for the layout calculations of the pages' contents.*/
	public static double contentWidth;
	
	/** Input handling. This ArrayList gets filled / used in {@link #start(Stage)} by the scene listeners.*/
	public static ArrayList<String> input = new ArrayList<String>();
	/** Input handling. This unified event handler checks the {@link #input input} for (Esc) and closes the program when pressed.
	 * This event handler is only used for the {@link Home home page}.
	 * @see de.wolkenfarmer.environment.pages.Home#reload(Group, boolean) */
	public static EventHandler<KeyEvent> krlClose;
	/** Input handling. This event handler checks the {@link #input input} for (Esc) and passes back to the {@link Home home page} 
	 * when pressed. This event handler is used for the direct sub-pages of the home page.*/
	public static EventHandler<KeyEvent> krlBackHome;
	/** Boolean which defines whether the {@link Home#pSetModel home page model} has to be rebuild on the next call of the page or not. 
	 * It gets changed to true if there is a setup change of the communication experiment from the {@link InputHandler input handler page}, 
	 * the {@link Transcoder transcoder page} or the {@link NoiseSource noise source page} 
	 * and back to false when {@link #krlBackHome the page has been reloaded}.*/
	public static boolean boUpdateSettingsModelHomepage;
	
	/** Standard distance from subheadings to the content below them.*/
	public static int distanceToHeading = 80;
	/** Standard distance from subheadings to the content below them.*/
	public static int distanceToSubheading = 60;
	/** Standard distance from segments to one another.*/
	public static int distanceToSegment = 50;
	/** Unified referenceable color for layouts.*/
	public static Color cNormal = Color.WHITESMOKE;
	/** Unified referenceable color for layouts.*/
	public static Color cPink = Color.rgb(250, 80, 130);
	/** Unified referenceable color for layouts.*/
	public static Color cYellow = Color.YELLOW;
	/** Unified referenceable color for layouts.*/
	public static Color cLightGray = Color.grayRgb(230);
	/** Unified referenceable color for layouts.*/
	public static Color cDarkGray = Color.grayRgb(20);
	/** Unified referenceable font for layouts.*/
	public static Font fHeadline = Font.font("Arial", FontWeight.BOLD, 50);
	/** Unified referenceable font for layouts.*/
	public static Font fHeading = new Font("Arial", 50);
	/** Unified referenceable font for layouts.*/
	public static Font fSubheading = new Font("Arial", 35);
    /** Unified referenceable font for layouts.*/
    public static Font fNormalText = new Font("Arial", 20);
    /** Unified referenceable font for layouts.*/
    public static Font fNormalTextItalic = Font.font("Arial", FontPosture.ITALIC, 20);
    /** Unified referenceable font for layouts.*/
    public static Font fSmallText = new Font("Arial", 15);
	/** Unified referenceable font for layouts.*/
    public static Font fSmallTextItalic = Font.font("Arial", FontPosture.ITALIC, 15);
	/** Unified referenceable corner radius for layouts.*/
    public static CornerRadii crNormalbo = new CornerRadii(10);
    /** Unified referenceable corner radius for layouts.*/
    public static CornerRadii crNormalBa = new CornerRadii(12);
    /** Unified referenceable button background for layouts.*/
    public static Background baTransparent = new Background (new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY,  Insets.EMPTY));
	/** Unified referenceable button background for layouts.*/
    public static Background baNormalButton = new Background (new BackgroundFill(Color.grayRgb(90), crNormalBa,  null));
	/** Unified referenceable button background (focused) for layouts.*/
    public static Background baNormalFocusedButton = new Background (new BackgroundFill(Color.grayRgb(70), crNormalBa,  null));
	/** Unified referenceable button background (green) for layouts.*/
    public static Background baGreenButton = new Background (new BackgroundFill(Color.rgb(0, 90, 0), crNormalBa,  null));
	/** Unified referenceable button background (green, focused) for layouts.*/
    public static Background baGreenFocusedButton = new Background (new BackgroundFill(Color.rgb(0, 70, 0), crNormalBa,  null));
    /** Unified referenceable button background (brown) for layouts.*/
    public static Background baBrownButton = new Background (new BackgroundFill(Color.rgb(100, 50 , 0), crNormalBa,  null));
	/** Unified referenceable button background (brown, focused) for layouts.*/
    public static Background baBrownFocusedButton = new Background (new BackgroundFill(Color.rgb(80, 30, 0), crNormalBa,  null));
    /** Unified referenceable button background (purple) for layouts.*/
    public static Background baPurpleButton = new Background (new BackgroundFill(Color.rgb(90, 0 , 60), crNormalBa,  null));
	/** Unified referenceable button background (purple, focused) for layouts.*/
    public static Background baPurpleFocusedButton = new Background (new BackgroundFill(Color.rgb(70, 30, 40), crNormalBa,  null));
    /** Unified referenceable button background (red) for layouts.*/
    public static Background baRedButton = new Background (new BackgroundFill(Color.rgb(120, 0, 0), crNormalBa,  null));
	/** Unified referenceable Border for layouts.*/
    public static Border boNormal = new Border(new BorderStroke(cNormal, BorderStrokeStyle.SOLID, crNormalbo, BorderWidths.DEFAULT));
    /** Unified referenceable Border for layouts.*/
    public static Border boSelected = new Border(new BorderStroke(cYellow, BorderStrokeStyle.SOLID, crNormalbo, new BorderWidths(4)));
    /** Unified referenceable Border for layouts.*/
    public static Border boNotInteractive = new Border(new BorderStroke(cDarkGray, BorderStrokeStyle.SOLID, crNormalbo, new BorderWidths(2)));
    /** Unified referenceable event handler for changing the background of a normal button when the mouse enters it.*/
    public static EventHandler<MouseEvent> evButEntered;
    /** Unified referenceable event handler for changing the background of a normal button when the mouse exits it.*/
    public static EventHandler<MouseEvent> evButExited;
	/** Unified referenceable event handler for changing the background of a green button when the mouse enters it.*/
    public static EventHandler<MouseEvent> evButGreEntered;
	/** Unified referenceable event handler for changing the background of a green button when the mouse exits it.*/
    public static EventHandler<MouseEvent> evButGreExited;
	/** Unified referenceable event handler for changing the background of a brown button when the mouse enters it.*/
    public static EventHandler<MouseEvent> evButBroEntered;
	/** Unified referenceable event handler for changing the background of a brown button when the mouse exits it.*/
    public static EventHandler<MouseEvent> evButBroExited;

    /** Static reference to the home page in order for the pages to have simple access to one another. 
     * It gets initialized in {@link #start(Stage)}.*/
    public static Home home;
    /** Static reference to the input handler page in order for the pages to have simple access to one another. 
     * Gets initialized in {@link Home}.*/
    public static InputHandler inputHandler;
    /** Static reference to the transcoder page in order for the pages to have simple access to one another. 
     * Gets initialized in {@link Home}.*/
    public static Transcoder transcoder;
    /** Static reference to the noise source page in order for the pages to have simple access to one another. 
     * Gets initialized in {@link Home}.*/
    public static NoiseSource noiseSource;
    
    
    
    /** Static reference to the input handler "Deselect" in order for the 
     * {@link de.wolkenfarmer.environment.pages.InputHandler input handler page} to have simple access to it.*/
    public static de.wolkenfarmer.input_handlers.Deselect inputHandler_Deselect = new de.wolkenfarmer.input_handlers.Deselect();
    /** Static reference to the input handler "User input" in order for {@link de.wolkenfarmer.environment.pages.InputHandler}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static UserInput inputHandler_UserInput = new UserInput();
    /** Static reference to the input handler "Random digit book" in order for the 
     * {@link de.wolkenfarmer.environment.pages.InputHandler input handler page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static RandomDigitBook inputHandler_RandomDigitBook = new RandomDigitBook();
    /** Static reference to the transcoder "Deselect" in order for the 
     * {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static de.wolkenfarmer.transcoder.Deselect transcoder_Deselect = new de.wolkenfarmer.transcoder.Deselect();
    /** Static reference to the pre- / post-transcoder "Deselect" in order for the 
     * {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static DeselectPrePost transcoder_DeselectPrePost = new DeselectPrePost();
    /** Static reference to the transcoder "ParityCheck-Code" in order for the 
     * {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static ParityCheck transcoder_ParityCheck = new ParityCheck();
    /** Static reference to the transcoder "RepetitionCode" in order for the 
     * {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static RepetitionCode transcoder_RepetitionCode = new RepetitionCode();
    /** Static reference to the noise source "Deselect" in order for the 
     * {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static de.wolkenfarmer.noise_sources.Deselect noiSource_Deselect = new Deselect();
    /** Static reference to the noise source "Individual changes" in order for the 
     * {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static IndividualChanges noiSource_IndividualChanges = new IndividualChanges();
    /** Static reference to the noise source "Mix-up changes" in order for the 
     * {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}, 
     * {@link de.wolkenfarmer.environment.Run} and {@link de.wolkenfarmer.environment.Result} to have simple access to it.*/
    public static MixUpChanges noiSource_MixUpChanges = new MixUpChanges();
    
    
    /**
     * Saves the selected input handler for further use in the {@link de.wolkenfarmer.environment environment} 
     * This input handler will be used for the {@link Run communication experiment} and for providing the
     * displayed text in {@link Home#bSetModInput} and {@link OverviewButton#lSelectedItem} (if instantiated from {@link InputHandler}).
     */
    public static ExperimentElement selectedInputHandler = inputHandler_Deselect;
    /**
     * Saves the selected transcoder for further use in the {@link de.wolkenfarmer.environment environment} 
     * This transcoder will be used for the {@link Run communication experiment} and for providing the
     * displayed text in {@link Home#bSetModEncoder} / {@link Home#bSetModDecoder} and {@link OverviewButton#lSelectedItem}
     * (if instantiated from {@link Transcoder}).
     */
    public static ExperimentElement selectedTranscoder = transcoder_Deselect;
    /**
     * Saves the selected pre- / post-transcoder for further use in the {@link de.wolkenfarmer.environment environment} 
     * This pre- / post-transcoder will be used for the {@link Run communication experiment} and for providing the
     * displayed text in {@link OverviewButton#lSelectedItem} (if instantiated from {@link Transcoder}).
     */
    public static ExperimentElement selectedPrePost = transcoder_DeselectPrePost;
    /**
	 * Saves the selected noise source for further use in the {@link de.wolkenfarmer.environment environment} 
	 * This noise source will be used for the {@link Run communication experiment} and for providing the
	 * displayed text in {@link Home#bSetModNoise} and {@link OverviewButton#lSelectedItem} (if instantiated from {@link NoiseSource}).<br>
	 */
    public static ExperimentElement selectedNoiSource = noiSource_Deselect;
    
    
    
    
	/**
	 * Main method of the program calling launch.
	 * Starts the application by calling {@link #launch(String...)} which calls up start. 
	 * @param args Used for calling launch (JavaFX Application)
	 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html">Javafx Application documentation</a>
	 * @see #start(Stage)
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Builds and starts the JavaFX application.
	 * Sets up the stage and links it up to the {@link #scene}. 
	 * Both are modified with some basic setup like full-screen mode or the {@link #scrollbar scroll bar} 
	 * (which gets added via the {@link #sbRoot scroll bar root} to the scene). 
	 * In addition, the keyboard, scroll bar and some basic button listeners are written here and 
	 * {@link #krlClose} and scroll bar listeners get added to the scene.
	 * Lastly, the {@link de.wolkenfarmer.environment.pages.Home home page} gets called.
	 * @param stage Makes up the window and is required for a JavaFX application.
	 * @see <a href="https://docs.oracle.com/javase/8/javafx/api/javafx/application/Application.html#start-javafx.stage.Stage-">
	 * JavaFX Application start() documentation</a>
	 */
	public void start(Stage stage) throws Exception {
		sbRoot = new Group();
		root = new Group();
		scene = new Scene(sbRoot, Color.grayRgb(40));
		scene.getStylesheets().addAll("de/wolkenfarmer/environment/pages/css/tableView.css", 
				"de/wolkenfarmer/environment/pages/css/scrollbar.css", 
				"de/wolkenfarmer/environment/pages/css/textArea.css");
		dummyScene = new Scene(dummyRoot = new Group());
		        
		stage = new Stage();
		stage.setTitle("Coding Environment");
		stage.setFullScreen(true);
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setResizable(false);   
		stage.setScene(scene);
	
		stage.show();
		stageWidth = stage.getWidth();
		stageHeight = stage.getHeight();
		System.out.println("Stage size: " + stageWidth + " * " + stageHeight);
		
		pos1 = stageWidth / 8;
		pos7 = stageWidth / 8 * 7;
		contentWidth = pos7 - pos1;
		
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
		scene.setOnKeyReleased(krlClose = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (input.contains("ESCAPE")) {
                	Platform.exit();
                }
                input.remove(e.getCode().toString());
            }
        });
		krlBackHome = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (Main.input.contains("ESCAPE")) {
                	root.getChildren().clear();
                	Main.home.reload(root, boUpdateSettingsModelHomepage);
                	boUpdateSettingsModelHomepage = false;
                }
                input.remove(e.getCode().toString());
            }
        };
		
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
		
		
		evButEntered = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				((Region) e.getSource()).setBackground(Main.baNormalFocusedButton);
			}
		};
		evButExited = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				((Region) e.getSource()).setBackground(Main.baNormalButton);
			}
		};
		evButGreEntered = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				((Region) e.getSource()).setBackground(Main.baGreenFocusedButton);
			}
		};
		evButGreExited = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				((Region) e.getSource()).setBackground(Main.baGreenButton);
			}
		};
		evButBroEntered = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				((Region) e.getSource()).setBackground(Main.baBrownFocusedButton);
			}
		};
		evButBroExited = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				((Region) e.getSource()).setBackground(Main.baBrownButton);
			}
		};
		
		home = new Home(root);
	}
	
	
	/**
	 * Calculates the width of a given Region.
	 * JavaFX layout calculations just work by applying CSS and a layout pass, which occurs usually after a page is finished building.
	 * Therefore, the region gets temporarily attached to the {@link #dummyScene dummy scene} (instead of the {@link #scene actual scene}) 
	 * in order to get the width before the page is finished building.
	 * This is necessary because the width is sometimes relevant to continue building the page.
	 * @param r The region (or subclass of it) of which the width gets calculated.
	 * @return Returns the width of the region.
	 */
	public static double calcWidth(Region r) {
		dummyRoot.getChildren().add(r);
		dummyRoot.applyCss();
		dummyRoot.layout();
		dummyRoot.getChildren().remove(r);
		return r.getWidth();
	}
	
	/**
	 * Calculates the height of a given Region.
	 * JavaFX layout calculations just work by applying CSS and a layout pass, which occurs usually after a page is finished building.
	 * Therefore, the region gets temporarily attached to the {@link #dummyScene dummy scene} (instead of the {@link #scene actual scene}) 
	 * in order to get the height before the page is finished building.
	 * This is necessary because the height is sometimes relevant to continue building the page.
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
	 * Calculates the width of a given Label.
	 * JavaFX layout calculations just work by applying CSS and a layout pass, which occurs usually after a page is finished building.
	 * Therefore, the label gets temporarily attached to the {@link #dummyScene dummy scene} (instead of the {@link #scene actual scene}) 
	 * in order to get the width before the page is finished building. 
	 * This is necessary because the width is sometimes relevant to continue building the page.
	 * @param l The label of which the height gets calculated.
	 * @return Returns the width of a label.
	 */
	public static double calcWidthLabel(Label l) {
		dummyRoot.getChildren().add(l);
		dummyRoot.applyCss();
		dummyRoot.layout();
		dummyRoot.getChildren().remove(l);
		return l.getWidth();
	}
	
	/**
	 * Calculates the height of a given Label.
	 * JavaFX layout calculations just work by applying CSS and a layout pass, which occurs usually after a page is finished building.
	 * Therefore, the label gets temporarily attached to the {@link #dummyScene dummy scene} (instead of the {@link #scene actual scene}) 
	 * in order to get the height before the page is finished building. 
	 * For this to work firstly the width of the label with its content gets calculated and then divided by the parent Objects width to get the 
	 * number of lines. Then the height of one individual line gets multiplied by it. In addition, an approximation for the distance of each line
	 * gets added. This is necessary because the height is sometimes relevant to continue building the page.
	 * <p>
	 * Note: This calculation is _not_ exact and is based on approximations, because neither the insets nor the padding of the parent 
	 * or the label give back the exact spacing between them. 
	 * Ultimately, there is no known way to get the spacing between each individual line in pixel depending on its size.
	 * @param l The label of which the height gets calculated.
	 * @param parentWidth The width of the parent object in order to calculate the number of lines.
	 * @return Returns the height of a label.
	 */
	public static double calcHeightLabel(Label l, double parentWidth) {
		dummyRoot.getChildren().add(l);
		dummyRoot.applyCss();
		dummyRoot.layout();
		double lines = Math.ceil(l.getWidth() / (parentWidth - 10));
		double height = (l.getHeight() * lines * 1.25) + (l.getLineSpacing() + (l.getFont().getSize()) * (lines - 1));
		dummyRoot.getChildren().remove(l);
		if (stageWidth < 1200) {
			height += l.getHeight() * 0.5;
		} else if (stageWidth < 850) {
			height += l.getHeight();
		}
		return height;
	}
	
	
	/**
	 * Updates the {@link #scrollbar scroll bar} when loading another page. Firstly it resets the layout of {@link #root}, 
	 * secondly it calculates the new {@link #contentHeight}, then sets the new values for the scroll bar and lastly sets 
	 * it's visibility (appears only if the content height is bigger than the screen's height). 
	 * However, if the screen is big enough, the scroll bar won't be displayed, because the content's height gets set to fit it perfectly. 
	 * @param lastObject The last layout object on the page in order to calculate the {@link #contentHeight}.
	 */
	public static void updateScrollbar(Region lastObject) {
		root.setLayoutY(0);
		scrollbar.setValue(0);
		contentHeight = lastObject.getLayoutY() + calcHeight(lastObject) + pos1 / 3;
		scrollbar.setMax(contentHeight - scene.getHeight());
		scrollbar.setBlockIncrement(contentHeight);
        if (scene.getHeight() >= contentHeight) {
        	scrollbar.setVisible(false);
        } else {
        	scrollbar.setVisible(true);
        }
	}
}