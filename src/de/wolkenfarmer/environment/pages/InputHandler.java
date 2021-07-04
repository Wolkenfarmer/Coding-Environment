package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.environment.gui_elements.Arrow;
import de.wolkenfarmer.environment.gui_elements.OptionButton;
import de.wolkenfarmer.environment.gui_elements.OverviewButton;
import de.wolkenfarmer.environment.logic.Main;

/**
 * The settings page 'input handler page' (a settings sub-page of the {@link Home home page}). <br>
 * The input handler for the communication experiment can be set here.
 * This class extends from {@link Settings} and is just responsible for the input handler page specific parts 
 * of the settings page. 
 * @author Wolkenfarmer
 */
public class InputHandler extends Settings {
	// Overview
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedInputHandler selected input handler} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. The button gets build in the {@link #InputHandler() constructor} and 
		 * updated in {@link #updateOveModel(byte)}. It gets added to the model.*/
		private static OverviewButton bOveModInput;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects the left side of the model with {@link #bOveModInput}. It gets added to the model.*/
		private static Arrow aOveModRelToIn;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Displays "message". Connects {@link #bOveModInput} with the right side of the model. It gets added to the model.*/
		private static Arrow aOveModRelInTo;
	// Options
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.experiment_elements.input_handlers.Deselect deselect} 
		 * option under {@link #pOptions options}. <br>
		 * Can be used to deactivate the transcoder. 
		 * It gets build in the {@link #InputHandler() constructor} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButDeselect;
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.experiment_elements.input_handlers.UserInput user input} 
		 * option under {@link #pOptions options}. <br>
		 * It gets instantiated in {@link #load} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButUserInput;
			
	
	/**
	 * Builds the input handler pages specific elements and adds them to the {@link Settings settings page} general setup. <br>
	 * Consequently, the parts of the {@link Settings#pOveModel overview model} get build here 
	 * as well as the {@link Settings#vbOptButtons option buttons}.
	 * In addition, the {@link Settings#lHeaHere heading} gets updated to the pages name.
	 * @since 0.2
	 */
	public InputHandler() {
		//Heading
		lHeaHere.setText("Input Handler");
		
		//Overview
		segmentWidth = pOverview.getPrefWidth() / 3;
			bOveModInput = new OverviewButton(segmentWidth, "Input Handler", Main.selectedInputHandler.getName(false));
			bOveModInput.setLayoutX(segmentWidth);
			
			double y = bOveModInput.getHeightW() / 2;
			aOveModRelToIn = new Arrow(0, y, segmentWidth, y, 15, 10, false, "you", 0);
			aOveModRelInTo = new Arrow(segmentWidth * 2, y, segmentWidth * 3, y, 15, 10, false, "message", 0);
		pOveModel.getChildren().addAll(aOveModRelToIn, bOveModInput, aOveModRelInTo);
		
		//Options
		bOptButDeselect = new OptionButton(pOptions.getPrefWidth(), Main.inputHandler_Deselect.getName(true));
		bOptButDeselect.setOnActionW(Main.inputHandler_Deselect);
		bOptButDeselect.setMode((byte) 1);
		bOptButUserInput = new OptionButton(pOptions.getPrefWidth(), Main.inputHandler_UserInput.getName(true));
		bOptButUserInput.setOnActionW(Main.inputHandler_UserInput);
		
		vbOptButtons.getChildren().addAll(bOptButDeselect, bOptButUserInput);
	}
		
		
		
	/**
	 * Readds the input handler page specific elements to the {@link Settings settings page} general setup. <br>
	 * In addition, the {@link Settings#lHeaHere heading} gets updated to the pages name.
	 * @since 0.2
	 */
	static void load() {
		lHeaHere.setText("Input Handler");
		pOveModel.getChildren().addAll(aOveModRelToIn, bOveModInput, aOveModRelInTo);
		vbOptButtons.getChildren().addAll(bOptButDeselect, bOptButUserInput);
	}
	

	/**
	 * Updates the {@link #pOveModel overviews model} to fit the {@link Main#selectedInputHandler currently selected input handler}. <br>
	 * For this, {@link #bOveModInput}s {@link OverviewButton#setSelectedItem(String)} gets called.
	 */
	static void updateOveModel() {
		bOveModInput.setSelectedItem(Main.selectedInputHandler.getName(false));
	}
}
