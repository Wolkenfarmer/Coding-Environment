package enDecoder;

import environment.ExperimentElement;
import environment.UniDataType;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * For now just a mock class for {@link environment.pages.EnDecoderPage}. 
 * @author Wolkenfarmer
 */
public class Mock implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Mock";
	/** Defines whether this is an en- / decoder or a pre-en- / post-decoder. This experiment element can be both and reads it's use-case from
	 * {@link #tgEnDePrePost} and sets it in {@link #save()}.
	 * 0: en- / decoder
	 * 1: pre- / post-*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link environment.pages.guiElements.InformationSegment}
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * Its content ({@link #mock}, {@link #rbEnDe}, {@link #rbPrePost}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	/** Mock label for testing the layout-loading. It will be directly attached to {@link #root}.*/
	private static Label mock;
	/** Toggle group for setting the {@link #type} of this experiment element.*/
	private static ToggleGroup tgEnDePrePost;
	/** Radio button for representing {@link #type} = 0. It's part of {@link #tgEnDePrePost} and will be directly attached to {@link #root}.*/
	private static RadioButton rbEnDe;
	/** Radio button for representing {@link #type} = 1. It's part of {@link #tgEnDePrePost} and will be directly attached to {@link #root}.*/
	private static RadioButton rbPrePost;
	
	
	/** @see environment.ExperimentElement#doJob(byte, UniDataType)*/
	public UniDataType doJob(byte task, UniDataType data) {return data;}
	

	/** @see environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
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
	
	
	/** @see environment.ExperimentElement#save()*/
	public void save() {
		System.out.println(name + " saved!");
		if (tgEnDePrePost.getSelectedToggle() == rbEnDe) {
			type = 0;
		} else {
			type = 1;
		}
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
