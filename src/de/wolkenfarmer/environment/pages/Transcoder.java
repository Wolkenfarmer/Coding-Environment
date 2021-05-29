package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
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
 * The transcoder page (a sub-page of the {@link Home home page}).
 * The transcoder and pre- / post-transcoder for the communication experiment can be set here.
 * This page extends from {@link Settings} (like {@link InputHandler} and {@link NoiseSource}).
 * See {@link #Transcoder(Group)} for more information about the GUI.
 * @author Wolkenfarmer
 */
public class Transcoder extends Settings {
	/** Saves whether the pre- / post-transcoder are displayed at the moment or not. 
	 * This is important in order to know whether the overview model has to be rebuild or not. Gets updated in {@link #Transcoder(Group)}
	 * and {@link #updateOveModel(byte)} and used for defining the changed-specification in 
	 * {@link InformationSegment#setSaveAddReference(ExperimentElement, OptionButton, Settings)} for {@link #reload(Group)}.*/
	public static boolean ovePrePostDisplaying;
	/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #Transcoder(Group)}
	 * in the view case of not displaying the pre- / post-transcoder.*/
	private double segmentWidthEnDe;
	/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #Transcoder(Group)}
	 * in the view case of displaying the pre- / post-transcoder.*/
	private double segmentWidthPrePost;
	// Heading
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "En- / Decoder" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	// Overview
		/** Label which displays the subheading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains {@link #bOveModEncoder}, {@link #bOveModDecoder}, {@link #aOveModRelToEn},
		 * {@link #aOveModRelEnToDe} and {@link #aOveModRelDeTo} and in the view case of additionally displaying the pre- / post-transcoder
		 * additionally {@link #bOveModPreencoder}, {@link #bOveModPostdecoder}, {@link #aOveModRelEnToEn} and {@link #aOveModRelDeToDe}
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the pre-encoder in the overview. It's used as a better rectangle. 
			 * Won't be used if {@link Main#selectedPrePost} == 0.
			 * The button gets instantiated in {@link #Transcoder(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedPrePost}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModPreencoder;
			/** The button showing the post-decoder in the overview. It's used as a better rectangle. 
			 * Won't be used if {@link Main#selectedPrePost} == 0.
			 * The button gets instantiated in {@link #Transcoder(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedPrePost}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModPostdecoder;
			/** The button showing the encoder in the overview. It's used as a better rectangle.
			 * The button gets instantiated in {@link #Transcoder(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedTranscoder}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModEncoder;
			/** The button showing the decoder in the overview. It's used as a better rectangle. 
			 * The button gets instantiated in {@link #Transcoder(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedTranscoder}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModDecoder;
			/** Relations for the model in overview. Connect the start of {@link #pOveModel} with either {@link #bOveModPreencoder} or
			 * {@link #bOveModEncoder} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelToEn;
			/** Relation for the model in overview. Connects {@link #bOveModPreencoder} with {@link #bOveModEncoder} 
			 * and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelEnToEn;
			/** Relation for the model in overview. Won't be used if {@link Main#selectedPrePost} == 0. 
			 * Connects {@link #bOveModEncoder} with {@link #bOveModDecoder} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelEnToDe;
			/** Relation for the model in overview. Won't be used if {@link Main#selectedPrePost} == 0. 
			 * Connects {@link #bOveModDecoder} with {@link #bOveModPostdecoder} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelDeToDe;
			/** Relation for the model in overview. Connects either {@link #bOveModDecoder} or {@link #bOveModPostdecoder} with 
			 * the end of {@link #pOveModel} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelDeTo;
	// Options
		/** The button showing the {@link de.wolkenfarmer.transcoder.DeselectPrePost deselect} option under {@link #pOptions option}. 
		 * Can be used to deactivate the pre- / post-transcoder.
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButDeselect;
		/** The button showing the {@link de.wolkenfarmer.transcoder.ParityCheck Binary Parity Check} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButParityCheck;
		/** The button showing the {@link de.wolkenfarmer.transcoder.RepetitionCode Repetition Code} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButRepetitionCode;
	// Information
			
	
	/**
	 * Builds the transcoder page of the application.
	 * For building it's content and updating the de.wolkenfarmer.environment accordingly to the picked options 
	 * {@link OverviewButton}, {@link OptionButton} and {@link InformationSegment} get used.
	 * There are two different view cases for the overview model depending on whether a 
	 * {@link Main#selectedPrePost pre- / post-transcoder is selected or not}.
	 * If there is one selected, they will also be displayed in the model, but if none is selected, they won't.
	 * The transcoder page gets scaled accordingly to {@link Main#stageWidth}.
	 * If the height of it's content exceeds {@link Main#stageHeight}, the {@link Main#scrollbar scroll bar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public Transcoder(Group parent) {
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
			lHeaHere.setText("Transcoder");
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
			
			segmentWidthEnDe = pOverview.getPrefWidth() / 8;
			segmentWidthPrePost = pOverview.getPrefWidth() / 14;
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
				String currentlySelectedEnDecoder = Main.selectedTranscoder.getName();
				
				bOveModEncoder = new OverviewButton(segmentWidthEnDe * 2, "Encoder", currentlySelectedEnDecoder);
				bOveModDecoder = new OverviewButton(segmentWidthEnDe * 2, "Decoder", currentlySelectedEnDecoder);
				bOveModPreencoder = new OverviewButton(segmentWidthPrePost * 2, "Pre-encoder", "");
				bOveModPreencoder.setLayoutX(segmentWidthPrePost);
				bOveModPostdecoder = new OverviewButton(segmentWidthPrePost * 2, "Post-decoder", "");
				bOveModPostdecoder.setLayoutX(segmentWidthPrePost * 11);
				
				
				if (Main.selectedPrePost == Main.transcoder_DeselectPrePost) {
					bOveModEncoder.setLayoutX(segmentWidthEnDe);
					bOveModDecoder.setLayoutX(segmentWidthEnDe * 5);
					
					double y = bOveModEncoder.getHeightW() / 2;
					aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthEnDe, y, 15, 10, false, "message", 0);
					aOveModRelEnToDe = new Arrow().getArrow(segmentWidthEnDe * 3, y, segmentWidthEnDe * 5, y, 
							15, 10, false, "signal / channel", 0);
					aOveModRelDeTo = new Arrow().getArrow(segmentWidthEnDe * 7, y, segmentWidthEnDe * 8, y, 
							15, 10, false, "message", 0);
					
					ovePrePostDisplaying = false;
				} else {
					bOveModEncoder.setLayoutX(segmentWidthPrePost * 4);
					bOveModDecoder.setLayoutX(segmentWidthPrePost * 8);
					
					bOveModPreencoder.setSelectedItem(Main.selectedPrePost.getName());
					bOveModPostdecoder.setSelectedItem(Main.selectedPrePost.getName());
					
					double y;
					double y1 = bOveModEncoder.getHeightW() / 2;
					double y2 = bOveModPostdecoder.getHeightW() / 2;
					if (y1 >= y2) {
						y = y2;
					} else {
						y = y1;
					}
					aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthPrePost, y, 10, 10, false, "message", 0);
					aOveModRelEnToEn = new Arrow().getArrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, 
							false, "", 0);
					aOveModRelEnToDe = new Arrow().getArrow(segmentWidthPrePost * 6, y, segmentWidthPrePost * 8, y, 10, 10, 
							false, "signal / channel", 0);
					aOveModRelDeToDe = new Arrow().getArrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, 
							false, "", 0);
					aOveModRelDeTo = new Arrow().getArrow(segmentWidthPrePost * 13, y, segmentWidthPrePost * 14, y, 10, 10, 
							false, "message", 0);
					
					
					pOveModel.getChildren().addAll(bOveModPreencoder, aOveModRelEnToEn, aOveModRelDeToDe, bOveModPostdecoder);
					ovePrePostDisplaying = true;
				}
			pOveModel.getChildren().addAll(aOveModRelToEn, bOveModEncoder, aOveModRelEnToDe, bOveModDecoder, aOveModRelDeTo);
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
				//bOptButDeselect = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_DeselectPrePost.getName());
				bOptButParityCheck = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_ParityCheck.getName());
				bOptButRepetitionCode = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_RepetitionCode.getName());
			vbOptButtons.getChildren().addAll(bOptButParityCheck, bOptButRepetitionCode);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    
	    
	    pInformation = new InformationSegment((byte) 1, Main.pos1 * 3, pOptions.getLayoutY(), Main.calcHeight(pOptions));
	    	//bOptButDeselect.setOnActionW(Main.transcoder_DeselectPrePost, this, pInformation);
		    bOptButParityCheck.setOnActionW(Main.transcoder_ParityCheck, this, pInformation);
		    bOptButRepetitionCode.setOnActionW(Main.transcoder_RepetitionCode, this, pInformation);
		    
		
		addListener();
		if (Main.calcHeight(pOptions) >= Main.calcHeight(pInformation)) {
			Main.updateScrollbar(pOptions);
		} else {
			Main.updateScrollbar(pInformation);
		}
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	
	
	/**
	 * Updates the {@link #pOveModel overview model} to fit the {@link Main#selectedTranscoder selected transcoder} as well as the
	 * {@link Main#selectedPrePost selected pre- / post-transcoder}.
	 * This method differentiates between four "changed" cases: <br>
	 * 0: Only the transcoder changed.<br>
	 * 1: Only the pre- / post-transcoder changed.<br>
	 * 2: The pre- / post-transcoder weren't displayed before but now got added. Consequently, the overview model has to be partially rebuild.<br>
	 * 3: The pre- / post-transcoder were displayed before but now got removed. Consequently, the overview model has to be partially rebuild.
	 * @param changed Specifies what has to be updated in the model.
	 * @see Settings
	 */
	public void updateOveModel(byte changed) {
		String currentlySelectedEnDecoder;
		String currentlySelectedPrePost;
		
		currentlySelectedEnDecoder = Main.selectedTranscoder.getName();
		currentlySelectedPrePost = Main.selectedPrePost.getName();
		
		double y, y1, y2;
		
		switch (changed) {
		case 0:						// transcoder changes
			bOveModEncoder.setSelectedItem(currentlySelectedEnDecoder);
			bOveModDecoder.setSelectedItem(currentlySelectedEnDecoder);
			break;
		case 1:						// pre- / post- changed
			bOveModPreencoder.setSelectedItem(currentlySelectedPrePost);
			bOveModPostdecoder.setSelectedItem(currentlySelectedPrePost);
			
			y1 = bOveModEncoder.getHeightW() / 2;
			y2 = bOveModPostdecoder.getHeightW() / 2;
			if (y1 >= y2) {
				y = y2;
			} else {
				y = y1;
			}
			pOveModel.getChildren().removeAll(aOveModRelEnToEn, aOveModRelDeToDe);
			aOveModRelEnToEn = new Arrow().getArrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, 
					false, "", 0);
			aOveModRelDeToDe = new Arrow().getArrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, 
					false, "", 0);
			pOveModel.getChildren().addAll(aOveModRelEnToEn, aOveModRelDeToDe);
			break;
		case 2:						// view-setup: !pre-/post- -> pre-/post-
			bOveModPreencoder.rebuild(segmentWidthPrePost * 2, "Pre-encoder", currentlySelectedPrePost);
			bOveModPreencoder.setLayoutX(segmentWidthPrePost);
			bOveModEncoder.rebuild(segmentWidthPrePost * 2, "Encoder", currentlySelectedEnDecoder);
			bOveModEncoder.setLayoutX(segmentWidthPrePost * 4);
			bOveModDecoder.rebuild(segmentWidthPrePost * 2, "Decoder", currentlySelectedEnDecoder);
			bOveModDecoder.setLayoutX(segmentWidthPrePost * 8);
			bOveModPostdecoder.rebuild(segmentWidthPrePost * 2, "Post-decoder", currentlySelectedPrePost);
			bOveModPostdecoder.setLayoutX(segmentWidthPrePost * 11);
			
			y1 = bOveModEncoder.getHeightW() / 2;
			y2 = bOveModPostdecoder.getHeightW() / 2;
			if (y1 >= y2) {
				y = y2;
			} else {
				y = y1;
			}
			pOveModel.getChildren().removeAll(aOveModRelToEn, aOveModRelEnToDe, aOveModRelDeTo);
			aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthPrePost, y, 10, 10, false, "message", 0);
			aOveModRelEnToEn = new Arrow().getArrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, 
					false, "", 0);
			aOveModRelEnToDe = new Arrow().getArrow(segmentWidthPrePost * 6, y, segmentWidthPrePost * 8, y, 10, 10, 
					false, "signal / channel", 0);
			aOveModRelDeToDe = new Arrow().getArrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, 
					false, "", 0);
			aOveModRelDeTo = new Arrow().getArrow(segmentWidthPrePost * 13, y, segmentWidthPrePost * 14, y, 10, 10, 
					false, "message", 0);
			
			pOveModel.getChildren().addAll(aOveModRelToEn, bOveModPreencoder, aOveModRelEnToEn, aOveModRelEnToDe, 
					aOveModRelDeToDe, bOveModPostdecoder, aOveModRelDeTo);
			ovePrePostDisplaying = true;
			break;
		case 3: 					// view-setup: pre-/post- -> !pre-/post-
			pOveModel.getChildren().removeAll(bOveModPreencoder, aOveModRelEnToEn, aOveModRelDeToDe, bOveModPostdecoder);
			
			bOveModEncoder.rebuild(segmentWidthEnDe * 2, "Encoder", currentlySelectedEnDecoder);
			bOveModEncoder.setLayoutX(segmentWidthEnDe * 1);
			bOveModDecoder.rebuild(segmentWidthEnDe * 2, "Decoder", currentlySelectedEnDecoder);
			bOveModDecoder.setLayoutX(segmentWidthEnDe * 5);
			
			y = bOveModEncoder.getHeightW() / 2;
			pOveModel.getChildren().removeAll(aOveModRelToEn, aOveModRelEnToDe, aOveModRelDeTo);
			aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthEnDe, y, 15, 10, false, "message", 0);
			aOveModRelEnToDe = new Arrow().getArrow(segmentWidthEnDe * 3, y, segmentWidthEnDe * 5, y, 15, 10, false, "signal / channel", 0);
			aOveModRelDeTo = new Arrow().getArrow(segmentWidthEnDe * 7, y, segmentWidthEnDe * 8, y, 15, 10, false, "message", 0);
			
			pOveModel.getChildren().addAll(aOveModRelToEn, aOveModRelEnToDe, aOveModRelDeTo);
			ovePrePostDisplaying = false;
			break;
		}
	}
	
	
	/**
	 * Selects the deselect option for 
	 * {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment#setSaveAddReference(ExperimentElement, OptionButton, Settings)} 
	 * if a option previously set as pre- / post-transcoder becomes the transcoder and therefore {@link #bOptButDeselect} has to be set
	 * even though there is no direct reference to it in this context.
	 */
	public void selectDeselectOption() {
		bOptButDeselect.setMode((byte) 2);
	}
}
