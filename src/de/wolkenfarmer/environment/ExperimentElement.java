package de.wolkenfarmer.environment;

import de.wolkenfarmer.environment.pages.Home;
import de.wolkenfarmer.environment.pages.Settings;
import de.wolkenfarmer.environment.pages.gui_elements.InformationSegment;
import javafx.scene.layout.Pane;

/**
 * Defines the basic methods of the experiment elements ({@link de.wolkenfarmer.transcoder}, 
 * {@link de.wolkenfarmer.input_handlers} and {@link de.wolkenfarmer.noise_sources}).
 * @author Wolkenfarmer
 */
public interface ExperimentElement {
	/**
	 * Makes the experiment element fulfill it's purpose of either giving the information, or en- / decoding it or altering it (noise source).
	 * Gets called by {@link de.wolkenfarmer.environment.Run#run(ExperimentElement, ExperimentElement, ExperimentElement, ExperimentElement)}.
	 * @param task Specifies the requested task from this experiment element. 
	 * Currently only used for the {@link de.wolkenfarmer.transcoder transcoder}
	 * to specify whether the data should be encoded (0) or decoded (1). 
	 * {@link de.wolkenfarmer.input_handlers input handlers} will always set the data and 
	 * {@link de.wolkenfarmer.noise_sources noise sources} will always alter the data for the communication experiment.
	 * @param data The data which will be used for the requested task.
	 * @return Returns the modified {@link UniDataType data} 
	 * or if a deselect-option (like {@link de.wolkenfarmer.noise_sources.Deselect}) was selected the unmodified data. 
	 */
	public UniDataType doJob(byte task, UniDataType data);
	/**
	 * Builds the GUI of the experiment element to be displayed in 
	 * {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment#pInfContent}.
	 * This method gets called by 
	 * {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement, Settings, InformationSegment)}
	 * if {@link #getBuiltGui()} == false.
	 * The GUI gets scaled accordingly to 
	 * {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment#pInfContent}s width (parentWidth).
	 * @param parentWidth The width of the layout container to which the GUI will be attached to.
	 */
	public void buildGui(double parentWidth);
	/**
	 * Saves the currently set specifications in it's GUI in order to be added "as is" to the communication element and 
	 * get displayed as set in the {@link Settings#pOverview settings-page's overview model} and 
	 * in the {@link Home#pSetModel home-page's settings model}.
	 */
	public void save();
	/** Loads the GUI of the experiment element to be displayed in 
	 * {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment#pInfContent}.
	 * This method gets called by 
	 * {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement, Settings, InformationSegment)}
	 * if {@link #getBuiltGui()} == false.
	 * @return Returns the Pane (/root) containing the GUI.*/
	public Pane getGui();
	/** Gives the buildGui boolean. Indicates, whether the GUI has yet to be build or not.
	 * @return builtGui boolean*/
	public boolean getBuiltGui();
	/** Gives the name.
	 * @return name*/
	public String getName();
	/** Gives the type of the experiment element (currently only used for differentiating between transcoder and pre- / post-transcoder.
	 * @return type*/
	public byte getType();
}
