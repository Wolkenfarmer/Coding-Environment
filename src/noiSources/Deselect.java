package noiSources;

import environment.ExperimentElement;
import environment.UniDataType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Represents the option to completely disable the noise source in {@link environment.pages.NoiSourcePage} by setting it to this. 
 * @author Wolkenfarmer
 */
public class Deselect implements ExperimentElement {
	/** Name of this experiment element.*/
	private String name = "nothing selected";
	/** The protocol / data type / structure that this experiment element gives.*/
	private static String protocol = "-";
	/** Defines the type of this information source. This variable has for noise sources currently no use-case.*/
	private static byte type = 0;
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
	
	
	/** @see environment.ExperimentElement#doJob(byte, UniDataType)*/
	public UniDataType doJob(byte task, UniDataType data) {return data;}

	
	/** @see environment.ExperimentElement#buildGui(Pane)*/
	public void buildGui(Pane parent) {
		root = parent;
		
		l = new Label();
		l.setText("\"Save & add\" this option in order to disable the noise source for the communication experiment.");
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
	/** Differs between being called from the {@link environment.pages.Homepage home page} and the 
	 * {@link environment.pages.EnDecoderPage en- / decoder page}. 
	 * In the second case it displays "Deselect", because "nothing selected" would be weird button name.
	 * However, getting the calling method from the StackTrace for differentiating between the corresponding 
	 * {@link environment.pages.guiElements.OverviewButton overview button} and {@link environment.pages.guiElements.OverviewButton option button}
	 * doesn't work because of the unreasonable universe.
	 * @return {@link #name}
	 * @see environment.ExperimentElement#getName()*/
	public String getName() {
		if (Thread.currentThread().getStackTrace()[2].getClassName() == "environment.pages.NoiSourcePage") {
			return "Deselect";
		} else {
			return name;
		}
	}
	/** @return {@link #protocol}
	 * @see environment.ExperimentElement#getProtocol()*/
	public String getProtocol() {return protocol;}
	/** @return {@link #type}
	 * @see environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
