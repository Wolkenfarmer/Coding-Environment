package enDecoder;

import environment.ExperimentElement;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Represents the option to disable the pre-en / post-decoder in {@link environment.pages.EnDecoderPage} by setting it to this. 
 * @author Wolkenfarmer
 */
public class DeselectPrePost implements ExperimentElement {
	/** Name of this experiment element.*/
	private String name = "Deselect pre-en- / post-decoder";
	/** The protocol / data type / structure that this experiment element gives.*/
	private static String protocol = "-";
	/** Defines whether this is an en- / decoder or a pre-en- / post-decoder. This experiment element is only an en- / decoder.
	 * 0: en- / decoder
	 * 1: pre- / post-*/
	private static byte type = 1;
	/** Layout container representing the given root from {@link environment.pages.guiElements.InformationSegment} to attach the GUI-elements to 
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * It's content ({@link #l}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page, it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	public static boolean builtGui;
	/** Label which explains the function of this element. It will be directly attached to {@link #root}.*/
	private static Label l;

	
	/** @see environment.ExperimentElement#buildGui(Pane)*/
	public void buildGui(Pane parent) {
		root = parent;
		
		l = new Label();
		l.setText("\"Save & add\" this option in order to disable the pre-en- / post-decoder for the communication experiment.");
		l.setFont(environment.Main.fNormalText);
		l.setTextFill(environment.Main.cNormal);
		l.setPrefWidth(root.getPrefWidth());
		l.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(l);
	}
	

	/** @see environment.ExperimentElement#reloadGui(Pane)*/
	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().addAll(l);
	}

	/** @see environment.ExperimentElement#save()*/
	public void save() {}
	/** @return {@link #builtGui}
	 * @see environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #protocol}
	 * @see environment.ExperimentElement#getProtocol()*/
	public String getProtocol() {return protocol;}
	/** @return {@link #type}
	 * @see environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
