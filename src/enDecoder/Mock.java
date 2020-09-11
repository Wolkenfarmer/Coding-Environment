package enDecoder;

import environment.ExperimentElement;
import environment.Main;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Wolkenfarmer
 */
public class Mock implements ExperimentElement {
	private static String name = "Mock";
	private static String protocol = "wuff";
	private static byte index = 2;
	private static byte type = 0;
	/** Layout container representing the given root from {@link environment.SourcePage#pInfContent} to attach the GUI-elements to.
	 * It's content ({@link #tfUserText}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #builtGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	public static boolean builtGui;
	private static Label mock;
	private static ToggleGroup tgEnDePrePost;
	private static RadioButton rbEnDe;
	private static RadioButton rbPrePost;
	

	public void buildGui(Pane parent) {
		root = parent;
		
		mock = new Label();
		mock.setText("Mock-Gui has been loaded!\n"
				+ "Can be set as en- / decoder as well as pre-encoder / post-decoder.");
		mock.setFont(environment.Main.fNormalText);
		mock.setTextFill(Color.WHITESMOKE);
		mock.setPrefWidth(root.getPrefWidth());
		mock.setWrapText(true);
		
		tgEnDePrePost = new ToggleGroup();
			rbEnDe = new RadioButton("En- / decoder");
	        rbEnDe.setToggleGroup(tgEnDePrePost);
	        rbEnDe.setFont(Main.fNormalText);
	        rbEnDe.setLayoutY(100);
	        rbEnDe.setTextFill(Color.WHITESMOKE);
	        rbEnDe.setAlignment(Pos.TOP_LEFT);
	        rbEnDe.setSelected(true);
	         
	        rbPrePost = new RadioButton("Pre-encoder / post-decoder");
	        rbPrePost.setToggleGroup(tgEnDePrePost);
	        rbPrePost.setFont(Main.fNormalText);
	        rbPrePost.setLayoutY(140);
	        rbPrePost.setTextFill(Color.WHITESMOKE);
	        rbPrePost.setAlignment(Pos.TOP_LEFT);
        
        builtGui = true;
        root.getChildren().addAll(mock, rbEnDe, rbPrePost);
		
	}


	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().addAll(mock, rbEnDe, rbPrePost);
	}
	
	
	public void save() {
		System.out.println(name + " saved!");
		if (tgEnDePrePost.getSelectedToggle() == rbEnDe) {
			type = 0;
		} else {
			type = 1;
		}
	}
	
	
	public boolean getBuiltGui() {return builtGui;}
	public String getName() {return name;}
	public String getProtocol() {return protocol;}
	public byte getIndex() {return index;}
	public byte getType() {return type;}
}
