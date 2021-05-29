package de.wolkenfarmer.noise_sources;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.UniDataType;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * The {@link de.wolkenfarmer.noise_sources noise source} "individual changes" which is selectable on the 
 * {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}.
 * This noise source changes single bits in a char array. 
 * It works with probabilities (see {@link #changeRate}, {@link #tgChangeRate}).
 * @author Wolkenfarmer
 * @see #doJob(byte, UniDataType) doJob() for further information
 */
public class IndividualChanges implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Individual changes";
	/** Defines the type of this input handler. This variable has for noise sources currently no use-case.*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton
	 * #setOnActionW(ExperimentElement, de.wolkenfarmer.environment.pages.Settings, 
	 * de.wolkenfarmer.environment.pages.gui_elements.InformationSegment)}).
	 * It's content ({@link #lDescription}, {@link #rbCha25}, {@link #rbCha15}, {@link #rbCha5}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	
	/** Saves the set probability of changing a bit in the data. 
	 * 1000 equals 0‰, 975 25‰, 900 1% and so on (a bit will be changed if a random int between 1 and 1000 is bigger 
	 * than this variable's value. It gets set by {@link #tgChangeRate} and its standard is 975.*/
	private static float changeRate = 975;
	
	/** Label displaying the description for this experiment element. It gets directly attached to {@link #root}.*/
	private static Label lDescription;
	/** The toggle group containing the different options for the {@link #changeRate change rate}. 
	 * Connects {@link #rbCha25} and {@link #rbCha15}.*/
	private static ToggleGroup tgChangeRate;
	/** The radio button of {@link #tgChangeRate} which represents the {@link #changeRate change rate} of 25‰. 
	 * It's directly attached to {@link #root}.*/
	private static RadioButton rbCha25;
	/** The radio button of {@link #tgChangeRate} which represents the {@link #changeRate change rate} of 15‰. 
	 * It's directly attached to {@link #root}.*/
	private static RadioButton rbCha15;
	/** The radio button of {@link #tgChangeRate} which represents the {@link #changeRate change rate} of 5‰. 
	 * It's directly attached to {@link #root}.*/
	private static RadioButton rbCha5;
	
	
	/** 
	 * Modifies the input accordingly to {@link #changeRate}.
	 * It iterates through every bit of the message randomly changing single bits according to the set {@link #changeRate change rate}.
	 * However, the '-' pieces dividing each unit in the {@link de.wolkenfarmer.environment.UniDataType#charBinary char binary array} won't be touched 
	 * due to being crucial for later decoding and them not existing in normal data transfers 
	 * (therefore: still representative communication experiment).
	 * In addition, a pre-changed and post-changed version will be set as {@link de.wolkenfarmer.environment.Run#originalCode original code} and
	 * {@link de.wolkenfarmer.environment.Run#changedCode changed code}.
	 * @param task Not used for {@link de.wolkenfarmer.noise_sources noise sources}.
	 * @param data The binary char[] which will be modified.
	 * @return Returns the modified data.
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		char[] charBinary = data.getCharBinary();
		de.wolkenfarmer.environment.Run.originalCode = new String(charBinary);
		
		Random random = new Random();
		int r;
		
		for (int i = 0; i < charBinary.length - 1; i++) {
			if (charBinary[i] == '-') i++;
			
			r = random.nextInt(1000) + 1;
			if (r > changeRate) {
				if (charBinary[i] == '1') {
					charBinary[i] = '0';
				} else {
					charBinary[i] = '1';
				}
			}
		}
		
		de.wolkenfarmer.environment.Run.changedCode = new String(charBinary);
		data.setCharBinary(charBinary);
		return data;
	}
	
	
	/** @see de.wolkenfarmer.environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		lDescription = new Label();
		lDescription.setText("This noise source switches single bits in the data. "
				+ "Set below how many bits should be affected.");
		lDescription.setFont(Constants.F_NORMAL);
		lDescription.setTextFill(Constants.C_NORMAL);
		lDescription.setAlignment(Pos.TOP_LEFT);
		lDescription.setWrapText(true);
		lDescription.setPrefWidth(root.getPrefWidth());
		lDescription.setPrefHeight(Main.calcHeightLabel(lDescription, parentWidth + 10));
		
		tgChangeRate = new ToggleGroup();
			rbCha25 = new RadioButton("≈ 25‰");
			rbCha25.setLayoutY(lDescription.getPrefHeight() + 30);
		    rbCha25.setToggleGroup(tgChangeRate);
		    rbCha25.setFont(Constants.F_NORMAL);
		    rbCha25.setTextFill(Constants.C_NORMAL);
		    rbCha25.setPrefWidth(parentWidth);
		    rbCha25.setPrefHeight(Main.calcHeight(rbCha25));
		    rbCha25.setSelected(true);
		    rbCha25.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {changeRate = 975;}
		    });
         
	        rbCha15 = new RadioButton("≈ 15‰");
	        rbCha15.setLayoutY(rbCha25.getLayoutY() + rbCha25.getPrefHeight() + 15);
	        rbCha15.setToggleGroup(tgChangeRate);
	        rbCha15.setFont(Constants.F_NORMAL);
	        rbCha15.setTextFill(Constants.C_NORMAL);
	        rbCha15.setPrefWidth(parentWidth);
	        rbCha15.setPrefHeight(Main.calcHeight(rbCha15));
	        rbCha15.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {changeRate = 985;}
		    });
	        
	        rbCha5 = new RadioButton("≈ 5‰");
	        rbCha5.setLayoutY(rbCha15.getLayoutY() + rbCha15.getPrefHeight() + 15);
	        rbCha5.setToggleGroup(tgChangeRate);
	        rbCha5.setFont(Constants.F_NORMAL);
		    rbCha5.setTextFill(Constants.C_NORMAL);
		    rbCha5.setPrefWidth(parentWidth);
		    rbCha5.setPrefHeight(Main.calcHeight(rbCha5));
		    rbCha5.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {changeRate = 995;}
		    });
        
        root.getChildren().addAll(lDescription, rbCha25, rbCha15, rbCha5);
        builtGui = true;
	}
	
	
	/** @see de.wolkenfarmer.environment.ExperimentElement#save()*/
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
	/** @see de.wolkenfarmer.environment.ExperimentElement#getGui()*/
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #type}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
