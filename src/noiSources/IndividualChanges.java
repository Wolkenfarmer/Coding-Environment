package noiSources;

import java.util.Random;

import environment.ExperimentElement;
import environment.Main;
import environment.UniDataType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * The {@link noiSources noise source} "individual changes" which is selectable on the {@link environment.pages.NoiSourcePage noise source page}.
 * This noise source changes single bits in a byte / with "-" marked segment in a char array. 
 * It assumes, that every segment is as big as the first one and it is only / mainly designed to just switch 0 and 1 in the selected spot.
 * In addition, it works with probabilities (see {@link #changeRate}, {@link #tgChangeRate}).
 * @author Wolkenfarmer
 */
public class IndividualChanges implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Individual changes";
	/** Defines the type of this information source. This variable has for noise sources currently no use-case.*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link environment.pages.guiElements.InformationSegment}
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * It's content ({@link #lDescription}, {@link #rbCha100}, {@link #rbCha25}, {@link #rbCha0}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	
	/** Saves the set probability of changing a single bit in a segment. 
	 * 1 equals 100%, 4 25% and so on until 256, which is the smallest saveable probability at the moment, but this can easily be increased.
	 * It gets set by {@link #tgChangeRate} and it's standard is 1.*/
	private static float changeRate = 1;
	
	/** Label displaying the description for this experiment element. It gets directly attached to {@link #root}.*/
	private static Label lDescription;
	/** The toggle group containing the different options for the {@link #changeRate change rate}. 
	 * Connects {@link #rbCha100} and {@link #rbCha25}.*/
	private static ToggleGroup tgChangeRate;
	/** The radio button of {@link #tgChangeRate} which represents the change rate of 100%. It's directly attached to {@link #root}.*/
	private static RadioButton rbCha100;
	/** The radio button of {@link #tgChangeRate} which represents the change rate of 25%. It's directly attached to {@link #root}.*/
	private static RadioButton rbCha25;
	/** The radio button of {@link #tgChangeRate} which represents the change rate of 0%. It's directly attached to {@link #root}.*/
	private static RadioButton rbCha0;
	
	
	/** 
	 * Modifies the input accordingly to {@link #changeRate}.
	 * Firstly, the segment width gets calculated of the char[] (they get divided by '-'). 
	 * Then it iterates through every segment changing some single bits per segment according to {@link #changeRate} on the way.
	 * The '-' pieces however won't be touched due to being crucial for later decoding and 
	 * them not being there in normal data transfers (still representative communication experiment).
	 * @param task Not used for {@link noiSources noise sources}.
	 * @param data The char[] which will be modified.
	 * @return Returns the modified data.
	 * @see environment.ExperimentElement#doJob(byte, UniDataType)
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		char[] charBinary = data.getCharBinary();
		System.out.println("___indivChanges: " + new String(charBinary));
				
		short segmentWidth = 0;
		boolean blockDistanceFound = false;
		while (!blockDistanceFound) {
			if (charBinary[segmentWidth] != '-' ) {
				segmentWidth++;
			} else {
				blockDistanceFound = true;
			}
		}
		
		Random random = new Random();
		float changeRateRelative = 256 / changeRate;
		short r;
		
		for (int i = 0; i < charBinary.length - 1; i += segmentWidth + 1) {
			r = (short) (random.nextInt(256) + 1);
			if (r <= changeRateRelative) {
				short charNum = (short) random.nextInt(segmentWidth);
				int k = i + charNum;
				if (charBinary[k] == '1') {
					charBinary[k] = '0';
				} else {
					charBinary[k] = '1';
				}
			}
		}
		
		System.out.println("___indivChanges: " + new String(charBinary));
		data.setCharBinary(charBinary);
		return data;
	}
	
	
	/** @see environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		lDescription = new Label();
		lDescription.setText("This noise source switches characters in the data individually. "
				+ "Set below how many sections (e.g. ASCII String: one char) of the input should be affected.");
		lDescription.setFont(Main.fNormalText);
		lDescription.setTextFill(Main.cNormal);
		lDescription.setAlignment(Pos.TOP_LEFT);
		lDescription.setWrapText(true);
		lDescription.setPrefWidth(root.getPrefWidth());
		lDescription.setPrefHeight(Main.calcHeightLabel(lDescription, parentWidth + 10));
		
		tgChangeRate = new ToggleGroup();
			rbCha100 = new RadioButton("= 100%");
			rbCha100.setLayoutY(lDescription.getPrefHeight() + 30);
		    rbCha100.setToggleGroup(tgChangeRate);
		    rbCha100.setFont(Main.fNormalText);
		    rbCha100.setTextFill(Main.cNormal);
		    rbCha100.setPrefWidth(parentWidth);
		    rbCha100.setPrefHeight(Main.calcHeight(rbCha100));
		    rbCha100.setSelected(true);
		    rbCha100.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {changeRate = 1;}
		    });
         
	        rbCha25 = new RadioButton("≈ 25%");
	        rbCha25.setLayoutY(rbCha100.getLayoutY() + rbCha100.getPrefHeight() + 15);
	        rbCha25.setToggleGroup(tgChangeRate);
	        rbCha25.setFont(Main.fNormalText);
	        rbCha25.setTextFill(Main.cNormal);
	        rbCha25.setPrefWidth(parentWidth);
	        rbCha25.setPrefHeight(Main.calcHeight(rbCha25));
	        rbCha25.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {changeRate = 4;}
		    });
	        
	        rbCha0 = new RadioButton("= 0%");
	        rbCha0.setLayoutY(rbCha25.getLayoutY() + rbCha25.getPrefHeight() + 15);
	        rbCha0.setToggleGroup(tgChangeRate);
	        rbCha0.setFont(Main.fNormalText);
		    rbCha0.setTextFill(Main.cNormal);
		    rbCha0.setPrefWidth(parentWidth);
		    rbCha0.setPrefHeight(Main.calcHeight(rbCha0));
		    rbCha0.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {changeRate = 257;}
		    });
        
        root.getChildren().addAll(lDescription, rbCha100, rbCha25, rbCha0);
        builtGui = true;
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
