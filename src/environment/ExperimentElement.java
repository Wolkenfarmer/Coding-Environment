package environment;

import environment.pages.Homepage;
import environment.pages.SettingsPage;
import environment.pages.guiElements.InformationSegment;
import javafx.scene.layout.Pane;

/**
 * Defines the basic methods of the experiment elements ({@link enDecoder}, {@link infSources} and {@link noiSources}).
 * @author Wolkenfarmer
 */
public interface ExperimentElement {
	/**
	 * Makes the experiment element fulfill it's purpose of either giving the information, or en- / decoding it or altering it (noise source).
	 * Gets called by {@link environment.Run#run(ExperimentElement, ExperimentElement, ExperimentElement, ExperimentElement)}.
	 * @param task Specifies the requested task from this experiment element. Currently only used for the {@link enDecoder en- / decoder}
	 * to specify whether the data should be encoded (0) or decoded (1). {@link infSources information sources} will always set the data and 
	 * {@link noiSources noise sources} will always alter the data for the communication experiment.
	 * @param data The data which will be used for the requested task.
	 * @return Returns the modified {@link UniDataType data} 
	 * or if a deselect-option (like {@link noiSources.Deselect}) was selected the unmodified data. 
	 */
	public UniDataType doJob(byte task, UniDataType data);
	/**
	 * Builds the GUI of the experiment element to be displayed in {@link environment.pages.guiElements.InformationSegment#pInfContent}.
	 * This method gets called by {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, SettingsPage, InformationSegment)}
	 * if {@link #getBuiltGui()} == false.
	 * The GUI gets scaled accordingly to {@link environment.pages.guiElements.InformationSegment#pInfContent}'s size.
	 * @param parent The layout container to attach it's GUI-elements to.
	 */
	public void buildGui(Pane parent);
	/**
	 * Reloads the GUI of the experiment element to be displayed in {@link environment.pages.guiElements.InformationSegment#pInfContent}.
	 * This method gets called by {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, SettingsPage, InformationSegment)}
	 * if {@link #getBuiltGui()} == false.
	 * @param parent The layout container to attach it's GUI-elements to.
	 */
	public void reloadGui(Pane parent);
	/**
	 * Saves the currently set specifications in it's GUI in order to be added "as is" to the communication element and 
	 * get displayed as set in the {@link SettingsPage#pOverview settings-page's overview model} and 
	 * in the {@link Homepage#pSetModel home-page's settings model}.
	 */
	public void save();
	/** Gives the buildGui boolean. Indicates, whether the GUI has yet to be build or not.
	 * @return builtGui boolean*/
	public boolean getBuiltGui();
	/** Gives the name.
	 * @return name*/
	public String getName();
	/** Gives the type of the experiment element (currently only used for differentiating between en- / decoder and pre-en / post-decoder.
	 * @return type*/
	public byte getType();
}
