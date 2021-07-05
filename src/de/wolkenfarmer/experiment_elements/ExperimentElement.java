package de.wolkenfarmer.experiment_elements;

import de.wolkenfarmer.environment.logic.UniDataType;

import javafx.scene.layout.Pane;

/**
 * Defines the basic methods of the experiment elements ({@link de.wolkenfarmer.experiment_elements.transcoder}, 
 * {@link de.wolkenfarmer.experiment_elements.input_handlers} and {@link de.wolkenfarmer.experiment_elements.noise_sources}). <br>
 * This class can be inherited as base for other experiment elements. On its own however, it has no functionality.
 * @author Wolkenfarmer
 * @since 0.2
 */
public abstract class ExperimentElement {
	/** Name of the experiment element.*/
	protected String name;
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.gui_elements.OptionButton#setOnActionW(ExperimentElement)}).
	 * Its content gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	protected Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	protected boolean builtGui;
	
	
	/**
	 * Makes the experiment element fulfill its purpose of either giving the information, or en- / decoding it or altering it (noise source). <br>
	 * Gets called by {@link de.wolkenfarmer.environment.logic.Run#run(ExperimentElement, ExperimentElement, ExperimentElement)}.
	 * This method has to be overwritten in order for the experiment element to have any distinct functionality hence it is abstract.
	 * @param task Specifies the requested task from this experiment element. 
	 * Currently only used for the {@link de.wolkenfarmer.experiment_elements.transcoder transcoder}
	 * to specify whether the data should be encoded (0) or decoded (1). 
	 * {@link de.wolkenfarmer.experiment_elements.input_handlers input handlers} will always set the data and 
	 * {@link de.wolkenfarmer.experiment_elements.noise_sources noise sources} will always alter the data for the communication experiment.
	 * @param data The data which will be used for the requested task.
	 * @return Returns the modified {@link UniDataType data} 
	 * or if a deselect-option (like {@link de.wolkenfarmer.experiment_elements.noise_sources.DeselectNoiseSource}) was selected the unmodified data. 
	 */
	public abstract UniDataType doJob(byte task, UniDataType data);
	/**
	 * Builds the GUI of the experiment element to be displayed in 
	 * {@link de.wolkenfarmer.environment.gui_elements.InformationSegment#pInfContent}. <br>
	 * This method gets called by 
	 * {@link de.wolkenfarmer.environment.gui_elements.OptionButton#setOnActionW(ExperimentElement)} if {@link #getBuiltGui()} == false.
	 * The GUI gets scaled accordingly to 
	 * {@link de.wolkenfarmer.environment.gui_elements.InformationSegment#pInfContent}s width (parentWidth).
	 * Every experiment element should have its own GGUI and explanation hence the method is abstract.
	 * @param parentWidth The width of the layout container to which the GUI will be attached to.
	 */
	public abstract void buildGui(double parentWidth);
	/**
	 * Saves the currently set specifications in it's GUI in order to be added "as is" to the communication element and 
	 * get displayed as set in the {@link de.wolkenfarmer.environment.pages.Settings#pOverview settings-page's overview model} and 
	 * in the {@link de.wolkenfarmer.environment.pages.Home#pSetModel home-page's settings model}.
	 */
	public void save() {System.out.println(name + " saved!");}
	/** 
	 * Loads the GUI of the experiment element to be displayed in 
	 * {@link de.wolkenfarmer.environment.gui_elements.InformationSegment#pInfContent}. <br>
	 * This method gets called by 
	 * {@link de.wolkenfarmer.environment.gui_elements.OptionButton#setOnActionW(ExperimentElement)}
	 * if {@link #getBuiltGui()} == false.
	 * @return Returns the Pane (/root) containing the GUI.
	 */
	public Pane getGui() {return root;}
	/** 
	 * Gives the {@link #builtGui} boolean. <br>
	 * Indicates, whether the GUI has yet to be build or not.
	 * @return builtGui boolean
	 */
	public boolean getBuiltGui() {return builtGui;}
	/** 
	 * Returns the name of the experiment element. <br>
	 * The name can vary depending on whether this gets displayed on the option button or not. 
	 * This gets used e.g. for the deselect options.
	 * @param optionButton Specifies whether the method call comes for naming an option button or not.
	 * @return name
	 * @since 0.2
	 */
	public String getName(boolean optionButton) {return name;}
}
