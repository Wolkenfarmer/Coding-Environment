package infSources;

import environment.ExperimentElement;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * For now just a mock class for {@link environment.SourcePage}. 
 * Will later be extended to a full version of the user input information source.
 * @author Wolkenfarmer
 */
public class UserInput implements ExperimentElement {
	private static String name = "User input";
	private static byte index = 1;
	private static byte type = 0;
	/** Layout container representing the given root from {@link environment.SourcePage#pInfContent} to attach the GUI-elements to.
	 * It's content ({@link #tfUserText}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #builtGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	private static boolean builtGui;
	/** The text field where the user can write his/her input into it. It will be directly attached to {@link #root}.*/
	private static TextField tfUserText;
	
	
	/**
	 * Builds the UI of the user input information source.
	 * This method gets called by {@link environment.SourcePage} if {@link #builtGui} == false.
	 * The UI gets scaled accordingly to {@link environment.SourcePage#pInfContent}'s size.
	 * @param parent Layout container to attach it's layout parts to.
	 * @see environment.SourcePage#addListener()
	 */
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
	
	
	/**
	 * Reloads the UI of the user input information source.
	 * This method gets called by {@link environment.SourcePage} if {@link #builtGui} == true.
	 * @param parent Layout container to attach it's layout parts to.
	 * @see environment.SourcePage#addListener()
	 */
	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().add(tfUserText);
	}
	
	
	public void save() {
		System.out.println(name + " saved!");
	}


	public boolean getBuiltGui() {return builtGui;}
	public static boolean getBuiltGuiDeprecated() {return builtGui;}
	public String getName() {return name;}
	public byte getIndex() {return index;}
	public byte getType() {return type;}
}
