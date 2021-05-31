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
 * The noise source page (a sub-page of the {@link Home home page}).
 * The noise source for the communication experiment can be set here.
 * This page extends from {@link Settings} (like {@link InputHandler} and {@link Transcoder}).
 * See {@link #NoiseSource(Group)} for more information about the GUI.
 * @author Wolkenfarmer
 */
public class NoiseSource extends Settings {
	/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #NoiseSource(Group)}.*/
	private double segmentWidth;
	// heading
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "Noise Source" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	// Overview
		/** Label which displays the subheading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains {@link #aOveModRelNoToCh}, {@link #bOveModSource} and {@link #aOveModRelEnToDe} 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the source in the overview. It's used as a better rectangle. 
			 * The button gets instantiated in {@link #NoiseSource(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedNoiSource}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModSource;
			/** Relation for the model in overview. Displays the protocol of the selected noise source. 
			 * Connects {@link #bOveModSource} with {@link #aOveModRelNoToCh} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelNoToCh;
			/** Relation for the model in overview. 
			 * Represents the channel in the middle of the {@link Home#pSetModel settings model} in the {@link Home home page}.
			 * Displays "channel | " and the protocol type of the {@link Main#selectedTranscoder selected transcoder}.
			 * It's part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelEnToDe;
	// Options
		/** The button showing the {@link de.wolkenfarmer.noise_sources.Deselect deselect} option under {@link #pOptions option}. 
		 * Can be used to deactivate the noise source.
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButDeselect;
		/** The button showing the {@link de.wolkenfarmer.noise_sources.IndividualChanges individual changes} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButIndChanges;
		/** The button showing the {@link de.wolkenfarmer.noise_sources.MixUpChanges mix up changes} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButMixUpChanges;
	// Information segment
	
	
	/**
	 * Builds the noise source page of the application.
	 * For building it's content and updating the de.wolkenfarmer.environment accordingly to the picked option {@link OverviewButton}, {@link OptionButton} and
	 * {@link InformationSegment} get used.
	 * The noise source page gets scaled accordingly to {@link Main#stageWidth}.
	 * If the height of it's content exceeds {@link Main#stageHeight}, the {@link Main#scrollbar scroll bar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public NoiseSource(Group parent) {
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
			lHeaHere.setText("Noise Source");
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
			
			segmentWidth = pOverview.getPrefWidth() / 4;
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
				bOveModSource = new OverviewButton(segmentWidth, "Noise Source", Main.selectedNoiSource.getName());
				
				double y = bOveModSource.getHeightW() / 2;
				aOveModRelNoToCh = new Arrow(segmentWidth, y, segmentWidth * 3, y, 15, 10, false, "noise", 0);
				aOveModRelEnToDe = new Arrow(segmentWidth * 3, 0, segmentWidth * 3, y * 2, 5, 10, true, "signal / channel", segmentWidth);
			pOveModel.getChildren().addAll(bOveModSource, aOveModRelNoToCh, aOveModRelEnToDe);
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
				bOptButDeselect = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_Deselect.getName());
				bOptButIndChanges = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_IndividualChanges.getName());
				bOptButMixUpChanges = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_MixUpChanges.getName());
			vbOptButtons.getChildren().addAll(bOptButDeselect, bOptButIndChanges, bOptButMixUpChanges);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    
	    
	    pInformation = new InformationSegment((byte) 2, Main.pos1 * 3, pOptions.getLayoutY(), Main.calcHeight(pOptions));
		    bOptButDeselect.setOnActionW(Main.noiSource_Deselect, this, pInformation);
			bOptButIndChanges.setOnActionW(Main.noiSource_IndividualChanges, this, pInformation);
			bOptButMixUpChanges.setOnActionW(Main.noiSource_MixUpChanges, this, pInformation);
		
		
		addListener();
		if (Main.calcHeight(pOptions) >= Main.calcHeight(pInformation)) {
			Main.updateScrollbar(pOptions);
		} else {
			Main.updateScrollbar(pInformation);
		}
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	
	
	/**
	 * Updates the {@link #pOveModel overview model} of this page to fit the {@link Main#selectedNoiSource currently selected noise source}.
	 * For this {@link OverviewButton#setSelectedItem(String)} gets used and {@link #aOveModRelNoToCh} 
	 * gets rebuild and added to {@link #pOveModel}.
	 * @param changed Is currently not used for this page.
	 */
	public void updateOveModel(byte changed) {
		bOveModSource.setSelectedItem(Main.selectedNoiSource.getName());
		
		double y = bOveModSource.getHeightW() / 2;
		pOveModel.getChildren().remove(aOveModRelNoToCh);
		aOveModRelNoToCh = new Arrow(segmentWidth, y, segmentWidth * 3, y, 15, 10, false, "noise", 0);
		pOveModel.getChildren().add(aOveModRelNoToCh);
	}
}
