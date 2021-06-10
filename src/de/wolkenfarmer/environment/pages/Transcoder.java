package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.gui_elements.Arrow;
import de.wolkenfarmer.environment.pages.gui_elements.InformationSegment;
import de.wolkenfarmer.environment.pages.gui_elements.OptionButton;
import de.wolkenfarmer.environment.pages.gui_elements.OverviewButton;

/**
 * The settings page 'transcoder page' (a settings sub-page of the {@link Home home page}). <br>
 * The transcoder and pre- / post-transcoder for the communication experiment can be set here.
 * This class extends from {@link Settings} and is just responsible for the transcoder page specific parts 
 * of the settings page. 
 * @author Wolkenfarmer
 */
public class Transcoder extends Settings {
	// Overview
		/** Saves whether the pre- / post-transcoder is displayed at the moment or not. <br>
		 * This is important in order to know whether the overview model has to be rebuild or not. Gets updated in {@link #Transcoder()}
		 * and {@link #updateOveModel(byte)} and used for defining the changed-specification in 
		 * {@link InformationSegment#setSaveAddReference(ExperimentElement, OptionButton)} for {@link #load()}.*/
		public static boolean ovePrePostDisplaying;
		/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #Transcoder()}
		 * in the view case of not displaying the pre- / post-transcoder.*/
		private static double segmentWidthEnDe;
		/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #Transcoder()}
		 * in the view case of displaying the pre- / post-transcoder.*/
		private static double segmentWidthPrePost;
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedPrePost pre-encoder} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. Won't be used if {@link Main#selectedPrePost} == 0. 
		 * The button gets build in the {@link #Transcoder() constructor} and updated in {@link #updateOveModel(byte)}. 
		 * It gets added to the model.*/
		private static OverviewButton bOveModPreencoder;
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedPrePost post-decoder} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. Won't be used if {@link Main#selectedPrePost} == 0. 
		 * The button gets build in the {@link #Transcoder() constructor} and updated in {@link #updateOveModel(byte)}. 
		 * It gets added to the model.*/
		private static OverviewButton bOveModPostdecoder;
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedTranscoder encoder} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. The button gets build in the {@link #Transcoder() constructor} 
		 * and updated in {@link #updateOveModel(byte)}. It gets added to the model.*/
		private static OverviewButton bOveModEncoder;
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedTranscoder decoder} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. The button gets build in the {@link #Transcoder() constructor} 
		 * and updated in {@link #updateOveModel(byte)}. It gets added to the model.*/
		private static OverviewButton bOveModDecoder;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects the start of {@link #pOveModel} with either {@link #bOveModPreencoder} or {@link #bOveModEncoder}. 
		 * It gets added to the model.*/
		private static Arrow aOveModRelToEn;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects {@link #bOveModPreencoder} with {@link #bOveModEncoder}. It gets added to the model.*/
		private static Arrow aOveModRelEnToEn;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Won't be used if {@link Main#selectedPrePost} == 0.
		 * Connects {@link #bOveModEncoder} with {@link #bOveModDecoder}. It gets added to the model.*/
		private static Arrow aOveModRelEnToDe;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Won't be used if {@link Main#selectedPrePost} == 0.
		 * Connects {@link #bOveModDecoder} with {@link #bOveModPostdecoder}. It gets added to the model.*/
		private static Arrow aOveModRelDeToDe;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects either {@link #bOveModDecoder} or {@link #bOveModPostdecoder} with the end of {@link #pOveModel}. 
		 * It gets added to the model.*/
		private static Arrow aOveModRelDeTo;
		
	// Options
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.transcoder.DeselectPrePost deselect} 
		 * option under {@link #pOptions options}. <br>
		 * Can be used to deactivate the pre- / post-transcoder. 
		 * It gets build in the {@link #Transcoder() constructor} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButDeselect;
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.transcoder.ParityCheck Binary Parity Check} 
		 * option under {@link #pOptions options}. <br>
		 * It gets build in the {@link #Transcoder() constructor} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButParityCheck;
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.transcoder.RepetitionCode Repetition Code} 
		 * option under {@link #pOptions options}. <br>
		 * It gets build in the {@link #Transcoder() constructor} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButRepetitionCode;
			
	
	/**
	 * Builds the transcoder pages specific elements and adds them to the {@link Settings settings page} general setup. <br>
	 * Consequently, the parts of the {@link Settings#pOveModel overview model} get build here 
	 * as well as the {@link Settings#vbOptButtons option buttons}.
	 * In addition, the {@link Settings#lHeaHere heading} gets updated to the pages name.
	 * There are two different view cases for the overview model depending on whether a 
	 * {@link Main#selectedPrePost pre- / post-transcoder is selected or not}.
	 * If there is one selected, they will also be displayed in the model, but if none is selected, they won't.
	 * @since 0.2
	 */
	public Transcoder() {		
		//Heading
		lHeaHere.setText("Transcoder");

		
		//Overview
		segmentWidthEnDe = pOverview.getPrefWidth() / 8;
		segmentWidthPrePost = pOverview.getPrefWidth() / 14;
		
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
				aOveModRelToEn = new Arrow(0, y, segmentWidthEnDe, y, 15, 10, false, "message", 0);
				aOveModRelEnToDe = new Arrow(segmentWidthEnDe * 3, y, segmentWidthEnDe * 5, y, 15, 10, false, "signal / channel", 0);
				aOveModRelDeTo = new Arrow(segmentWidthEnDe * 7, y, segmentWidthEnDe * 8, y, 15, 10, false, "message", 0);
				
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
				aOveModRelToEn = new Arrow(0, y, segmentWidthPrePost, y, 10, 10, false, "message", 0);
				aOveModRelEnToEn = new Arrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, false, "", 0);
				aOveModRelEnToDe = new Arrow(segmentWidthPrePost * 6, y, segmentWidthPrePost * 8, y, 10, 10, false, "signal / channel", 0);
				aOveModRelDeToDe = new Arrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, false, "", 0);
				aOveModRelDeTo = new Arrow(segmentWidthPrePost * 13, y, segmentWidthPrePost * 14, y, 10, 10, false, "message", 0);
				
				
				pOveModel.getChildren().addAll(bOveModPreencoder, aOveModRelEnToEn, aOveModRelDeToDe, bOveModPostdecoder);
				ovePrePostDisplaying = true;
			}
		pOveModel.getChildren().addAll(aOveModRelToEn, bOveModEncoder, aOveModRelEnToDe, bOveModDecoder, aOveModRelDeTo);
		
		
		//Options
		//bOptButDeselect = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_DeselectPrePost.getName());
		//bOptButDeselect.setOnActionW(Main.transcoder_DeselectPrePost);
		bOptButParityCheck = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_ParityCheck.getName());
		bOptButParityCheck.setOnActionW(Main.transcoder_ParityCheck);
		bOptButRepetitionCode = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_RepetitionCode.getName());
		bOptButRepetitionCode.setOnActionW(Main.transcoder_RepetitionCode);
		
		vbOptButtons.getChildren().addAll(bOptButParityCheck, bOptButRepetitionCode);
	}
	
	
	/**
	 * Readds the transcoder pages specific elements to the {@link Settings settings page} general setup. <br>
	 * In addition, the {@link Settings#lHeaHere heading} gets updated to the pages name.
	 * @since 0.2
	 */
	static void load() {
		lHeaHere.setText("Noise Source");
		if (Main.selectedPrePost != Main.transcoder_DeselectPrePost) 
			pOveModel.getChildren().addAll(bOveModPreencoder, aOveModRelEnToEn, aOveModRelDeToDe, bOveModPostdecoder);
		pOveModel.getChildren().addAll(aOveModRelToEn, bOveModEncoder, aOveModRelEnToDe, bOveModDecoder, aOveModRelDeTo);
		vbOptButtons.getChildren().addAll(bOptButParityCheck, bOptButRepetitionCode);
	}
	
	
	/**
	 * Updates the {@link #pOveModel overviews model} to fit the {@link Main#selectedTranscoder selected transcoder} as well as the
	 * {@link Main#selectedPrePost selected pre- / post-transcoder}. <br>
	 * For this, multiple layout elements of the overviews model get rebuild if needed.
	 * This method differentiates between four "changed" cases: <br>
	 * 0: Only the transcoder changed.<br>
	 * 1: Only the pre- / post-transcoder changed.<br>
	 * 2: The pre- / post-transcoder weren't displayed before but now got added. Consequently, the overview model has to be partially rebuild.<br>
	 * 3: The pre- / post-transcoder were displayed before but now got removed. Consequently, the overview model has to be partially rebuild.
	 * @param changed Specifies what has to be updated in the model.
	 */
	static void updateOveModel(byte changed) {
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
			aOveModRelEnToEn = new Arrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, false, "", 0);
			aOveModRelDeToDe = new Arrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, false, "", 0);
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
			aOveModRelToEn = new Arrow(0, y, segmentWidthPrePost, y, 10, 10, false, "message", 0);
			aOveModRelEnToEn = new Arrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, false, "", 0);
			aOveModRelEnToDe = new Arrow(segmentWidthPrePost * 6, y, segmentWidthPrePost * 8, y, 10, 10, false, "signal / channel", 0);
			aOveModRelDeToDe = new Arrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, false, "", 0);
			aOveModRelDeTo = new Arrow(segmentWidthPrePost * 13, y, segmentWidthPrePost * 14, y, 10, 10, false, "message", 0);
			
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
			aOveModRelToEn = new Arrow(0, y, segmentWidthEnDe, y, 15, 10, false, "message", 0);
			aOveModRelEnToDe = new Arrow(segmentWidthEnDe * 3, y, segmentWidthEnDe * 5, y, 15, 10, false, "signal / channel", 0);
			aOveModRelDeTo = new Arrow(segmentWidthEnDe * 7, y, segmentWidthEnDe * 8, y, 15, 10, false, "message", 0);
			
			pOveModel.getChildren().addAll(aOveModRelToEn, aOveModRelEnToDe, aOveModRelDeTo);
			ovePrePostDisplaying = false;
			break;
		}
	}
	
	
	/**
	 * Selects the deselect option for {@link InformationSegment#setSaveAddReference(ExperimentElement, OptionButton)} 
	 * if a option previously set as pre- / post-transcoder becomes the transcoder and therefore {@link #bOptButDeselect} has to be set
	 * even though there is no direct reference to it in this context.
	 */
	public static void selectDeselectOption() {
		bOptButDeselect.setMode((byte) 2);
	}
}
