package enDecoder;

import environment.ExperimentElement;
import environment.UniDataType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * For now just a mock class for {@link environment.pages.EnDecoderPage}. 
 * Will later be extended to a full version of the user input information source.
 * @author Wolkenfarmer
 */
public class Gallager implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Gallager-Code";
	/** Defines whether this is an en- / decoder or a pre-en- / post-decoder. This experiment element is only an en- / decoder.
	 * 0: en- / decoder
	 * 1: pre- / post-*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link environment.pages.guiElements.InformationSegment}
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * It's content ({@link #mock}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	/** Mock label for testing the layout-loading. It will be directly attached to {@link #root}.*/
	private static Label mock;
	
	
	/** @see environment.ExperimentElement#doJob(byte, UniDataType)*/
	public UniDataType doJob(byte task, UniDataType data) {return data;}
	

	/** @see environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		mock = new Label();
		mock.setText("Galleger-Code-Gui has been loaded!\n"
				+ "Can only be set as en-/decoder.");
		mock.setFont(environment.Main.fNormalText);
		mock.setTextFill(environment.Main.cNormal);
		mock.setPrefWidth(root.getPrefWidth());
		mock.setWrapText(true);
        
        builtGui = true;
        root.getChildren().add(mock);
	}

	
	/** @see environment.ExperimentElement#save()*/
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
	/** @see environment.ExperimentElement#getGui()*/
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}
	 * @see environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #type}
	 * @see environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
