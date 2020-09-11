package enDecoder;

import environment.ExperimentElement;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Wolkenfarmer
 */
public class StringToByte implements ExperimentElement {
	private static String name = "String to byte[]";
	private static String protocol = "byte[]";
	private static byte index = 1;
	private static byte type = 1;
	/** Layout container representing the given root from {@link environment.SourcePage#pInfContent} to attach the GUI-elements to.
	 * It's content ({@link #tfUserText}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #builtGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	public static boolean builtGui;
	private static Label mock;
	

	public void buildGui(Pane parent) {
		root = parent;
		
		mock = new Label();
		mock.setText("Strin-to-byte[]-Gui has been loaded!\n"
				+ "Can only be set as pre-encoder / post-decoder.");
		mock.setFont(environment.Main.fNormalText);
		mock.setTextFill(Color.WHITESMOKE);
		mock.setPrefWidth(root.getPrefWidth());
		mock.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(mock);
		
	}


	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().addAll(mock);
	}
	
	
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
	public boolean getBuiltGui() {return builtGui;}
	public String getName() {return name;}
	public String getProtocol() {return protocol;}
	public byte getIndex() {return index;}
	public byte getType() {return type;}
}
