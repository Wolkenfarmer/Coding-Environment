package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.gui_elements.Arrow;
import de.wolkenfarmer.environment.pages.gui_elements.InformationSegment;
import de.wolkenfarmer.environment.pages.gui_elements.OptionButton;
import de.wolkenfarmer.environment.pages.gui_elements.OverviewButton;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * The input handler page (a sub-page of the {@link Home home page}).
 * The input handler for the communication experiment can be set here.
 * This page extends from {@link Settings} (like {@link Transcoder} and {@link NoiseSource}).
 * See {@link #InputHandler(Group)} for more information about the GUI.
 * @author Wolkenfarmer
 */
public class InputHandler extends Settings {
	/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #InputHandler(Group)}.*/
	private double segmentWidth;
	// heading
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "Input handler" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	// Overview
		/** Label which displays the subheading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains {@link #aOveModRelToIn}, {@link #bOveModInput} and {@link #aOveModRelInTo} 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the input handler in the overview. It's used as a better rectangle. 
			 * The button gets instantiated in {@link #InputHandler(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedInputHandler}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModInput;
			/** Relation for the model in overview. Connects the start of {@link #pOveModel} with {@link #bOveModInput} 
			 * and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelToIn;
			/** Relation for the model in overview. Displays "message | " and the protocol type of the input handler.
			 * Connects {@link #bOveModInput} with the end of {@link #pOveModel} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelInTo;
	// Options
		/** The button showing the {@link de.wolkenfarmer.input_handlers.UserInput user input} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButUserInput;
		/** The button showing the {@link de.wolkenfarmer.input_handlers.RandomDigitBook random digit book} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButBook;
	// Information segment
			
			
	/**
	 * Builds the input handler page of the application.
	 * For building it's content and updating the de.wolkenfarmer.environment accordingly to the picked option 
	 * {@link OverviewButton}, {@link OptionButton} and {@link InformationSegment} get used.
	 * The input handler page gets scaled accordingly to {@link Main#stageWidth}.
	 * If the height of it's content exceeds {@link Main#stageHeight}, the {@link Main#scrollbar scroll bar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public InputHandler(Group parent) {
		root = parent;
		
		tfHeading = new TextFlow();
		tfHeading.setLayoutX(Main.pos1);
		tfHeading.setLayoutY(Main.pos1 / 3);
		tfHeading.setPrefWidth(Main.contentWidth);
			lHeaHome = new Label();
			lHeaHome.setText("CE \\  ");
			lHeaHome.setTextFill(Constants.C_NORMAL);
			lHeaHome.setFont(Constants.F_HEADING_BOLD);
			lHeaHome.setAlignment(Pos.CENTER_LEFT);
				
			lHeaHere = new Label();
			lHeaHere.setText("Input Handler");
			lHeaHere.setTextFill(Constants.C_NORMAL);
			lHeaHere.setFont(Constants.F_HEADING);
			lHeaHere.setAlignment(Pos.CENTER_LEFT);
		tfHeading.getChildren().addAll(lHeaHome, lHeaHere);
		
		
		
		pOverview = new Pane();
		pOverview.setLayoutX(Main.pos1);
		pOverview.setLayoutY(tfHeading.getLayoutY() + Constants.I_DISTANCE_HEADING);
		pOverview.setPrefWidth(Main.contentWidth);
			lOveHeading = new Label();
			lOveHeading.setText("Overview");
			lOveHeading.setTextFill(Constants.C_NORMAL);
			lOveHeading.setFont(Constants.F_SUBHEADING);
			
			segmentWidth = pOverview.getPrefWidth() / 3;
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
				bOveModInput = new OverviewButton(segmentWidth, "Input Handler", Main.selectedInputHandler.getName());
				bOveModInput.setLayoutX(segmentWidth);
				
				double y = bOveModInput.getHeightW() / 2;
				aOveModRelToIn = new Arrow().getArrow(0, y, segmentWidth, y, 15, 10, false, "you", 0);
				aOveModRelInTo = new Arrow().getArrow(segmentWidth * 2, y, segmentWidth * 3, y, 15, 10, false, "message", 0);
			pOveModel.getChildren().addAll(aOveModRelToIn, bOveModInput, aOveModRelInTo);
		pOverview.getChildren().addAll(lOveHeading, pOveModel);
		
		
		
		pOptions = new Pane();
		pOptions.setLayoutX(Main.pos1);
		pOptions.setLayoutY(pOverview.getLayoutY() + Main.calcHeight(pOverview) + Constants.I_DISTANCE_SEGMENT);
		pOptions.setPrefWidth(Main.stageWidth / 8 * 1.5);
			lOptHeading = new Label();
			lOptHeading.setText("Options");
			lOptHeading.setTextFill(Constants.C_NORMAL);
			lOptHeading.setFont(Constants.F_SUBHEADING);			
			
			vbOptButtons = new VBox();
			vbOptButtons.setPrefWidth(pOptions.getPrefWidth());
			vbOptButtons.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
			vbOptButtons.setSpacing(20);
				bOptButUserInput = new OptionButton(pOptions.getPrefWidth(), Main.inputHandler_UserInput.getName());
				bOptButBook = new OptionButton(pOptions.getPrefWidth(), Main.inputHandler_RandomDigitBook.getName());
			vbOptButtons.getChildren().addAll(bOptButUserInput, bOptButBook);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    	    
	    
	    pInformation = new InformationSegment((byte) 0, Main.pos1 * 3, pOptions.getLayoutY(), Main.calcHeight(pOptions));
		    bOptButUserInput.setOnActionW(Main.inputHandler_UserInput, this, pInformation);
		    bOptButBook.setOnActionW(Main.inputHandler_RandomDigitBook, this, pInformation);
		
		
		addListener();
		if (Main.calcHeight(pOptions) >= Main.calcHeight(pInformation)) {
			Main.updateScrollbar(pOptions);
		} else {
			Main.updateScrollbar(pInformation);
		}
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	

	/**
	 * Updates the {@link #pOveModel overview model} of this page to fit the {@link Main#selectedInputHandler currently selected input handler}.
	 * For this {@link OverviewButton#setSelectedItem(String)} gets used and
	 * the {@link Arrow relation} gets rebuild and added to {@link #pOveModel}.
	 * @param changed Is currently not used for this page.
	 */
	public void updateOveModel(byte changed) {
		bOveModInput.setSelectedItem(Main.selectedInputHandler.getName());
		
		double y = bOveModInput.getHeightW() / 2;
		pOveModel.getChildren().remove(aOveModRelInTo);
		aOveModRelInTo = new Arrow().getArrow(segmentWidth * 2, y, segmentWidth * 3, y, 15, 10, false, "message", 0);
		pOveModel.getChildren().add(aOveModRelInTo);
	}
}
