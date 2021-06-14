package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.gui_elements.Arrow;
import de.wolkenfarmer.environment.pages.gui_elements.OptionButton;
import de.wolkenfarmer.environment.pages.gui_elements.OverviewButton;

/**
 * The settings page 'noise source page' (a settings sub-page of the {@link Home home page}). <br>
 * The noise source for the communication experiment can be set here.
 * This class extends from {@link Settings} and is just responsible for the noise source page specific parts 
 * of the settings page. 
 * @author Wolkenfarmer
 */
public class NoiseSource extends Settings {
	// Overview
		/** The {@link OverviewButton overview button} showing the {@link Main#selectedNoiSource selected noise source} 
		 * in the {@link #pOveModel model of the overview}. <br>
		 * It's used as a better rectangle. The button gets build in the {@link #NoiseSource() constructor} and 
		 * updated in {@link #updateOveModel(byte)}. It gets added to the model.*/
		private static OverviewButton bOveModSource;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Connects {@link #bOveModSource} with {@link #aOveModRelNoToCh}. It gets added to the model.*/
		private static Arrow aOveModRelNoToCh;
		/** Relation {@link Arrow arrow} for the {@link #pOveModel model of the overview}. <br>
		 * Represents the channel in the middle of the {@link Home#pSetModel settings model} in the {@link Home home page}.
		 * Displays "signal / channel". Connects the top with the bottom of the model. It gets added to the model.*/
		private static Arrow aOveModRelEnToDe;
	// Options
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.noise_sources.Deselect deselect} 
		 * option under {@link #pOptions options}. <br>
		 * It gets instantiated in {@link #load} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButDeselect;
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.noise_sources.IndividualChanges individual changes} 
		 * option under {@link #pOptions options}. <br>
		 * It gets instantiated in {@link #load} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButIndChanges;
		/** The {@link OptionButton option button} showing the {@link de.wolkenfarmer.noise_sources.MixUpChanges mix up changes} 
		 * option under {@link #pOptions options}. <br>
		 * It gets instantiated in {@link #load} and is part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButMixUpChanges;
	
		
	/**
	 * Builds the noise source pages specific elements and adds them to the {@link Settings settings page} general setup. <br>
	 * Consequently, the parts of the {@link Settings#pOveModel overview model} get build here 
	 * as well as the {@link Settings#vbOptButtons option buttons}.
	 * In addition, the {@link Settings#lHeaHere heading} gets updated to the pages name.
	 * @since 0.2
	 */
	public NoiseSource() {
		//Heading
		lHeaHere.setText("Noise Source");
			
		//Overview
		segmentWidth = pOverview.getPrefWidth() / 4;
			bOveModSource = new OverviewButton(segmentWidth, "Noise Source", Main.selectedNoiSource.getName(false));
			
			double y = bOveModSource.getHeightW() / 2;
			aOveModRelNoToCh = new Arrow(segmentWidth, y, segmentWidth * 3, y, 15, 10, false, "noise", 0);
			aOveModRelEnToDe = new Arrow(segmentWidth * 3, 0, segmentWidth * 3, y * 2, 5, 10, true, "signal / channel", segmentWidth);
		pOveModel.getChildren().addAll(bOveModSource, aOveModRelNoToCh, aOveModRelEnToDe);	
		
		//Options
		bOptButDeselect = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_Deselect.getName(true));
		bOptButDeselect.setOnActionW(Main.noiSource_Deselect);
		bOptButIndChanges = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_IndividualChanges.getName(true));
		bOptButIndChanges.setOnActionW(Main.noiSource_IndividualChanges);
		bOptButMixUpChanges = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_MixUpChanges.getName(true));
		bOptButMixUpChanges.setOnActionW(Main.noiSource_MixUpChanges);
		
		vbOptButtons.getChildren().addAll(bOptButDeselect, bOptButIndChanges, bOptButMixUpChanges);
	}
	
	
	/**
	 * Readds the noise source pages specific elements to the {@link Settings settings page} general setup. <br>
	 * In addition, the {@link Settings#lHeaHere heading} gets updated to the pages name.
	 * @since 0.2
	 */
	static void load() {
		lHeaHere.setText("Noise Source");
		pOveModel.getChildren().addAll(bOveModSource, aOveModRelNoToCh, aOveModRelEnToDe);
		vbOptButtons.getChildren().addAll(bOptButDeselect, bOptButIndChanges, bOptButMixUpChanges);
	}
	
	
	/**
	 * Updates the {@link #pOveModel overviews model} to fit the {@link Main#selectedNoiSource currently selected noise source}. <br>
	 * For this, {@link #bOveModSource}s {@link OverviewButton#setSelectedItem(String)} gets called.
	 */
	static void updateOveModel() {
		bOveModSource.setSelectedItem(Main.selectedNoiSource.getName(false));
	}
}
