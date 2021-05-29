package de.wolkenfarmer;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
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

/**
 * This class provides the whole application with some constants. 
 * It holds mainly values for the GUI like {@link #C_NORMAL} as well as some basic button listeners like {@link #EH_BUTTON_GRAY_ENTERED}.
 * These constants are gathered up here in order to have a quick overview over the hard-coded aspects of the application, 
 * to have easy access to them and to be able to easily alter them. 
 * Constants are not supposed to be modified during runtime which is why they are all final. 
 * In additions, they are all static for minor performance improvements.
 * @author Wolkenfarmer
 */
public final class Constants {
	/** Standard distance from headings to the content below them.*/
	public static final int I_DISTANCE_HEADING = 80;
	/** Standard distance from subheadings to the content below them.*/
	public static final int I_DISTANCE_SUBHEADING = 60;
	/** Standard distance from segments to one another.*/
	public static final int I_DISTANCE_SEGMENT = 50;
	
	
	/** Standard color for layouts such as text.*/
	public static final Color C_NORMAL = Color.WHITESMOKE;
	/** Standard color pink.*/
	public static final Color C_PINK = Color.rgb(250, 80, 130);
	/** Standard color yellow.*/
	public static final Color C_YELLOW = Color.YELLOW;
	/** Standard color light gray.*/
	public static final Color C_GRAY_LIGHT = Color.grayRgb(230);
	/** Standard color dark gray.*/
	public static final Color C_GRAY_DARK = Color.grayRgb(20);
	
	
	/** Standard font for headings.*/
	public static final Font F_HEADING = new Font("Arial", 50);
	/** Standard font for bold headings.*/
	public static final Font F_HEADING_BOLD = Font.font("Arial", FontWeight.BOLD, 50);
	/** Standard font for subheadings.*/
	public static final Font F_SUBHEADING = new Font("Arial", 35);
    /** Standard font for normal text.*/
    public static final Font F_NORMAL = new Font("Arial", 20);
    /** Standard font for normal italic text.*/
    public static final Font F_NORMAL_ITALIC = Font.font("Arial", FontPosture.ITALIC, 20);
    /** Standard font for small text.*/
    public static final Font F_SMALL = new Font("Arial", 15);
	/** Standard font for small italic text.*/
    public static final Font F_SMALL_ITALIC = Font.font("Arial", FontPosture.ITALIC, 15);
    
    
    /** Standard corner radius for backgrounds.*/
    public static final CornerRadii CR_NORMAL_BACKGROUND = new CornerRadii(12);
	/** Standard corner radius for borders.*/
    public static final CornerRadii CR_NORMAL_BORDER = new CornerRadii(10);
    
    
    /** Standard transparent background.*/
    public static final Background BG_TRANSPARENT = new Background (new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY,  Insets.EMPTY));
	/** Standard gray background for e.g. buttons.*/
    public static final Background BG_GRAY = new Background (new BackgroundFill(Color.grayRgb(90), CR_NORMAL_BACKGROUND,  null));
    /** Standard gray background for e.g. focused buttons.*/
    public static final Background BG_GRAY_DARK = new Background (new BackgroundFill(Color.grayRgb(70), CR_NORMAL_BACKGROUND,  null));
    /** Standard green background for e.g. buttons.*/
    public static final Background BG_GREEN = new Background (new BackgroundFill(Color.rgb(0, 90, 0), CR_NORMAL_BACKGROUND,  null));
    /** Standard green background for e.g. focused buttons.*/
    public static final Background BG_GREEN_DARK = new Background (new BackgroundFill(Color.rgb(0, 70, 0), CR_NORMAL_BACKGROUND,  null));
    /** Standard brown background for e.g. buttons.*/
    public static final Background BG_BROWN = new Background (new BackgroundFill(Color.rgb(100, 50 , 0), CR_NORMAL_BACKGROUND,  null));
    /** Standard brown background for e.g. focused buttons.*/
    public static final Background BG_BROWN_DARK = new Background (new BackgroundFill(Color.rgb(80, 30, 0), CR_NORMAL_BACKGROUND,  null));
    /** Standard purple background for e.g. buttons.*/
    public static final Background BG_PURPLE = new Background (new BackgroundFill(Color.rgb(90, 0 , 60), CR_NORMAL_BACKGROUND,  null));
    /** Standard purple background for e.g. focused buttons.*/
    public static final Background BG_PURPLE_DARK = new Background (new BackgroundFill(Color.rgb(70, 30, 40), CR_NORMAL_BACKGROUND,  null));
    /** Standard red background for e.g. buttons.*/
    public static final Background BG_RED = new Background (new BackgroundFill(Color.rgb(120, 0, 0), CR_NORMAL_BACKGROUND,  null));
    
    
	/** Standard border.*/
    public static final Border B_NORMAL = 
    		new Border(new BorderStroke(C_NORMAL, BorderStrokeStyle.SOLID, CR_NORMAL_BORDER, BorderWidths.DEFAULT));
    /** Standard border for selected items.*/
    public static final Border B_SELECTED = 
    		new Border(new BorderStroke(C_YELLOW, BorderStrokeStyle.SOLID, CR_NORMAL_BORDER, new BorderWidths(4)));
    /** Standard border for not interactive items.*/
    public static final Border B_NOT_INTERACTIVE = 
    		new Border(new BorderStroke(C_GRAY_DARK, BorderStrokeStyle.SOLID, CR_NORMAL_BORDER, new BorderWidths(2)));
    
    
    /** Standard event handler for changing the background of a gray button when the mouse enters it.*/
    public static final EventHandler<MouseEvent> EH_BUTTON_GRAY_ENTERED = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			((Region) e.getSource()).setBackground(Constants.BG_GRAY_DARK);
		}
	};
    /** Standard event handler for changing the background of a gray button when the mouse exits it.*/
    public static final EventHandler<MouseEvent> EH_BUTTON_GRAY_EXITED = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			((Region) e.getSource()).setBackground(Constants.BG_GRAY);
		}
	};
	/** Standard event handler for changing the background of a green button when the mouse enters it.*/
    public static final EventHandler<MouseEvent> EH_BUTTON_GREEN_ENTERED = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			((Region) e.getSource()).setBackground(Constants.BG_GREEN_DARK);
		}
	};
	/** Standard event handler for changing the background of a green button when the mouse exits it.*/
    public static final EventHandler<MouseEvent> EH_BUTTON_GREEN_EXITED = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			((Region) e.getSource()).setBackground(Constants.BG_GREEN);
		}
	};
	/** Standard event handler for changing the background of a brown button when the mouse enters it.*/
    public static final EventHandler<MouseEvent> EH_BUTTON_BROWN_ENTERED = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			((Region) e.getSource()).setBackground(Constants.BG_BROWN_DARK);
		}
	};
	/** Standard event handler for changing the background of a brown button when the mouse exits it.*/
    public static final EventHandler<MouseEvent> EH_BUTTON_BROWN_EXITED = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			((Region) e.getSource()).setBackground(Constants.BG_BROWN);
		}
	};
}
