package enDecoder;

import environment.ExperimentElement;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * For now just a mock class for {@link environment.EnDecoderPage}. 
 * Will later be extended to a full version of the user input information source.
 * @author Wolkenfarmer
 */
public class StringToByte implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "String to byte[]";
	/** The protocol / data type / structure that this experiment element gives.*/
	private static String protocol = "byte[]";
	/** The index of this experiment element. Indices only have to be unique inside the own category.*/
	private static byte index = 1;
	/** Defines whether this is an en- / decoder or a pre-en- / post-decoder. This experiment element is only an pre-en- / post-decoder.
	 * 0: en- / decoder
	 * 1: pre- / post-*/
	private static byte type = 1;
	/** Layout container representing the given root from {@link environment.InformationSegment} to attach the GUI-elements to 
	 * (gets added via {@link environment.OptionButton#setOnActionW(ExperimentElement, environment.SettingsPage, environment.InformationSegment)}).
	 * It's content ({@link #mock}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page, it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	public static boolean builtGui;
	/** Mock label for testing the layout-loading. It will be directly attached to {@link #root}.*/
	private static Label mock;
	

	/** @see environment.ExperimentElement#buildGui(Pane)*/
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


	/** @see environment.ExperimentElement#reloadGui(Pane)*/
	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().addAll(mock);
	}
	
	
	/** @see environment.ExperimentElement#save()*/
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
	/** @return {@link #builtGui}
	 * @see environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #protocol}
	 * @see environment.ExperimentElement#getProtocol()*/
	public String getProtocol() {return protocol;}
	/** @return {@link #index}
	 * @see environment.ExperimentElement#getIndex()*/
	public byte getIndex() {return index;}
	/** @return {@link #type}
	 * @see environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
