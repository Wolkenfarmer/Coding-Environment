package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.gui_elements.Arrow;
import de.wolkenfarmer.environment.pages.gui_elements.OptionButton;
import de.wolkenfarmer.environment.pages.gui_elements.OverviewButton;

/**
 * The settings page 'transcoder page' (a settings sub-page of the {@link Home home page}). <br>
 * The transcoder for the communication experiment can be set here.
 * This class extends from {@link Settings} and is just responsible for the transcoder page specific parts 
 * of the settings page. 
 * @author Wolkenfarmer
 */
public class Transcoder extends Settings {
	// Overview
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedTranscoder encoder} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. The button gets build in the {@link #Transcoder() constructor} 
		 * and updated in {@link #updateOveModel()}. It gets added to the model.*/
		private static OverviewButton bOveModEncoder;
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedTranscoder decoder} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. The button gets build in the {@link #Transcoder() constructor} 
		 * and updated in {@link #updateOveModel()}. It gets added to the model.*/
		private static OverviewButton bOveModDecoder;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects the start of {@link #pOveModel} with {@link #bOveModEncoder}. 
		 * It gets added to the model.*/
		private static Arrow aOveModRelToEn;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects {@link #bOveModEncoder} with {@link #bOveModDecoder}. It gets added to the model.*/
		private static Arrow aOveModRelEnToDe;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects {@link #bOveModDecoder} with the end of {@link #pOveModel}. 
		 * It gets added to the model.*/
		private static Arrow aOveModRelDeTo;
		
	// Options
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.transcoder.Deselect deselect} 
		 * option under {@link #pOptions options}. <br>
		 * Can be used to deactivate the transcoder. 
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
	 * @since 0.2
	 */
	public Transcoder() {		
		//Heading
		lHeaHere.setText("Transcoder");

		
		//Overview
		segmentWidth = pOverview.getPrefWidth() / 8;
			String currentlySelectedEnDecoder = Main.selectedTranscoder.getName(false);
			bOveModEncoder = new OverviewButton(segmentWidth * 2, "Encoder", currentlySelectedEnDecoder);
			bOveModDecoder = new OverviewButton(segmentWidth * 2, "Decoder", currentlySelectedEnDecoder);
			
				bOveModEncoder.setLayoutX(segmentWidth);
				bOveModDecoder.setLayoutX(segmentWidth * 5);
				
				double y = bOveModEncoder.getHeightW() / 2;
				aOveModRelToEn = new Arrow(0, y, segmentWidth, y, 15, 10, false, "message", 0);
				aOveModRelEnToDe = new Arrow(segmentWidth * 3, y, segmentWidth * 5, y, 15, 10, false, "signal / channel", 0);
				aOveModRelDeTo = new Arrow(segmentWidth * 7, y, segmentWidth * 8, y, 15, 10, false, "message", 0);
				
		pOveModel.getChildren().addAll(aOveModRelToEn, bOveModEncoder, aOveModRelEnToDe, bOveModDecoder, aOveModRelDeTo);
		
		
		//Options
		bOptButDeselect = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_Deselect.getName(true));
		bOptButDeselect.setOnActionW(Main.transcoder_Deselect);
		bOptButDeselect.setMode((byte) 1);
		bOptButParityCheck = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_ParityCheck.getName(true));
		bOptButParityCheck.setOnActionW(Main.transcoder_ParityCheck);
		bOptButRepetitionCode = new OptionButton(pOptions.getPrefWidth(), Main.transcoder_RepetitionCode.getName(true));
		bOptButRepetitionCode.setOnActionW(Main.transcoder_RepetitionCode);
		
		vbOptButtons.getChildren().addAll(bOptButDeselect, bOptButParityCheck, bOptButRepetitionCode);
	}
	
	
	/**
	 * Readds the transcoder pages specific elements to the {@link Settings settings page} general setup.
	 * In addition, the {@link Settings#lHeaHere heading} gets updated to the pages name.
	 * @since 0.2
	 */
	static void load() {
		lHeaHere.setText("Transcoder");
		pOveModel.getChildren().addAll(aOveModRelToEn, bOveModEncoder, aOveModRelEnToDe, bOveModDecoder, aOveModRelDeTo);
		vbOptButtons.getChildren().addAll(bOptButDeselect, bOptButParityCheck, bOptButRepetitionCode);
	}
	
	
	/**
	 * Updates the {@link #pOveModel overviews model} to fit the {@link Main#selectedTranscoder selected transcoder}. <br>
	 * For this, {@link #bOveModEncoder}s and {@link #bOveModDecoder}s {@link OverviewButton#setSelectedItem(String)} gets called.
	 */
	static void updateOveModel() {
		String currentlySelectedEnDecoder = Main.selectedTranscoder.getName(false);
		bOveModEncoder.setSelectedItem(currentlySelectedEnDecoder);
		bOveModDecoder.setSelectedItem(currentlySelectedEnDecoder);
			
	}
}
