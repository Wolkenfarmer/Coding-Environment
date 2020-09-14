package infSources;

import environment.ExperimentElement;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * For now just a mock class for {@link environment.pages.InfSourcePage}. 
 * Will later be extended to a full version of the user input information source.
 * @author Wolkenfarmer
 */
public class UserInput implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "User input";
	/** The protocol / data type / structure that this experiment element gives.*/
	private static String protocol = "string";
	/** The index of this experiment element. Indices only have to be unique inside the own category.*/
	private static byte index = 1;
	/** Defines the type of this information source. This variable has for information sources currently no use-case.*/
	private static byte type = 0;
	/** Layout container representing the given root from {@link environment.pages.guiElements.InformationSegment} to attach the GUI-elements to 
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * It's content ({@link #tfUserText}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page, it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	private static boolean builtGui;
	/** The text field where the user can write his/her input into it. It will be directly attached to {@link #root}.*/
	private static TextField tfUserText;
	
	
	/** @see environment.ExperimentElement#buildGui(Pane)*/
	public void buildGui(Pane parent) {
		root = parent;
		
		tfUserText = new TextField();
        tfUserText.setFont(environment.Main.fNormalText);
        tfUserText.setPromptText("User input");
        tfUserText.setStyle("-fx-text-inner-color: WHITESMOKE;");
        tfUserText.setBackground(environment.Main.baNormalButton);
        tfUserText.setPrefWidth(root.getPrefWidth());
        
        builtGui = true;
        root.getChildren().add(tfUserText);
	}
	
	
	/** @see environment.ExperimentElement#reloadGui(Pane)*/
	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().add(tfUserText);
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
