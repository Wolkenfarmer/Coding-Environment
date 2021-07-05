package de.wolkenfarmer.experiment_elements.noise_sources;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.logic.Main;
import de.wolkenfarmer.environment.logic.Run;
import de.wolkenfarmer.environment.logic.UniDataType;
import de.wolkenfarmer.experiment_elements.ExperimentElement;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * The {@link de.wolkenfarmer.experiment_elements.noise_sources noise source} "individual changes" which is selectable on the 
 * {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}.
 * This noise source changes single bits in a char array. 
 * It works with probabilities (see {@link #changeRate}, {@link #tgChangeRate}).
 * @author Wolkenfarmer
 * @see #doJob(byte, UniDataType) doJob() for further information
 */
public class IndividualChanges extends ExperimentElement {
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
	 * Sets the {@link #name name} of the experiment element.
	 * @since 0.2
	 */
	public IndividualChanges() {name = "Individual changes";}
	
	
	/** 
	 * Modifies the input accordingly to {@link #changeRate}.
	 * It iterates through every bit of the message randomly changing single bits according to the set {@link #changeRate change rate}.
	 * However, the '-' pieces dividing each unit in the {@link UniDataType#charBinary char binary array} won't be touched 
	 * due to being crucial for later decoding and them not existing in normal data transfers 
	 * (therefore: still representative communication experiment).
	 * In addition, a pre-changed and post-changed version will be set as {@link Run#originalCode original code} and
	 * {@link Run#changedCode changed code}.
	 * @param task Not used for {@link de.wolkenfarmer.experiment_elements.noise_sources noise sources}.
	 * @param data The binary char[] which will be modified.
	 * @return Returns the modified data.
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		char[] charBinary = data.getCharBinary();
		Run.originalCode = new String(charBinary);
		
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
		
		Run.changedCode = new String(charBinary);
		data.setCharBinary(charBinary);
		return data;
	}
	
	
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
}
