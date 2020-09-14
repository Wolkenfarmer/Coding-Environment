package enDecoder;

import environment.ExperimentElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * For now just a mock class for {@link environment.pages.EnDecoderPage}. 
 * Will later be extended to a full version of the user input information source.
 * @author Wolkenfarmer
 */
public class Mock implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Mock";
	/** The protocol / data type / structure that this experiment element gives.*/
	private static String protocol = "wuff";
	/** The index of this experiment element. Indices only have to be unique inside the own category.*/
	private static byte index = 2;
	/** Defines whether this is an en- / decoder or a pre-en- / post-decoder. This experiment element can be both and reads it's use-case from
	 * {@link #tgEnDePrePost} and sets it in {@link #save()}.
	 * 0: en- / decoder
	 * 1: pre- / post-*/
	private static byte type = 0;
	/** Layout container representing the given root from {@link environment.pages.guiElements.InformationSegment} to attach the GUI-elements to 
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * It's content ({@link #mock}, {@link #rbEnDe}, {@link #rbPrePost}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page, it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	public static boolean builtGui;
	/** Mock label for testing the layout-loading. It will be directly attached to {@link #root}.*/
	private static Label mock;
	/** Toggle group for setting the {@link #type} of this experiment element.*/
	private static ToggleGroup tgEnDePrePost;
	/** Radio button for representing {@link #type} = 0. It's part of {@link #tgEnDePrePost} and will be directly attached to {@link #root}.*/
	private static RadioButton rbEnDe;
	/** Radio button for representing {@link #type} = 1. It's part of {@link #tgEnDePrePost} and will be directly attached to {@link #root}.*/
	private static RadioButton rbPrePost;
	

	/** @see environment.ExperimentElement#buildGui(Pane)*/
	public void buildGui(Pane parent) {
		root = parent;
		
		mock = new Label();
		mock.setText("Mock-Gui has been loaded!\n"
				+ "Can be set as en- / decoder as well as pre-encoder / post-decoder.");
		mock.setFont(environment.Main.fNormalText);
		mock.setTextFill(environment.Main.cNormal);
		mock.setPrefWidth(root.getPrefWidth());
		mock.setWrapText(true);
		
		tgEnDePrePost = new ToggleGroup();
			rbEnDe = new RadioButton("En- / decoder");
	        rbEnDe.setToggleGroup(tgEnDePrePost);
	        rbEnDe.setFont(environment.Main.fNormalText);
	        rbEnDe.setLayoutY(100);
	        rbEnDe.setTextFill(environment.Main.cNormal);
	        rbEnDe.setAlignment(Pos.TOP_LEFT);
	        rbEnDe.setSelected(true);
	         
	        rbPrePost = new RadioButton("Pre-encoder / post-decoder");
	        rbPrePost.setToggleGroup(tgEnDePrePost);
	        rbPrePost.setFont(environment.Main.fNormalText);
	        rbPrePost.setLayoutY(140);
	        rbPrePost.setTextFill(environment.Main.cNormal);
	        rbPrePost.setAlignment(Pos.TOP_LEFT);
        
        builtGui = true;
        root.getChildren().addAll(mock, rbEnDe, rbPrePost);
		
	}


	/** @see environment.ExperimentElement#reloadGui(Pane)*/
	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().addAll(mock, rbEnDe, rbPrePost);
	}
	
	
	/** @see environment.ExperimentElement#save()*/
	public void save() {
		System.out.println(name + " saved!");
		if (tgEnDePrePost.getSelectedToggle() == rbEnDe) {
			type = 0;
		} else {
			type = 1;
		}
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
