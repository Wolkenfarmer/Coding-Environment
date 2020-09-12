package environment;

import javafx.scene.layout.Pane;

/**
 * Defines the basic methods of the experiment elements ({@link enDecoder}, {@link infSources} and {@link noiSources}).
 * @author Wolkenfarmer
 */
public interface ExperimentElement {
	
	/**
	 * Builds the GUI of the experiment element to be displayed in {@link environment.InformationSegment#pInfContent}.
	 * This method gets called by {@link environment.OptionButton#setOnActionW(ExperimentElement, SettingsPage, InformationSegment)}
	 * if {@link #getBuiltGui()} == false.
	 * The GUI gets scaled accordingly to {@link environment.InformationSegment#pInfContent}'s size.
	 * @param parent The layout container to attach it's GUI-elements to.
	 */
	public void buildGui(Pane parent);
	/**
	 * Reloads the GUI of the experiment element to be displayed in {@link environment.InformationSegment#pInfContent}.
	 * This method gets called by {@link environment.OptionButton#setOnActionW(ExperimentElement, SettingsPage, InformationSegment)}
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
	/** Gives the protocol / data type / structure which the experiment element gives.
	 * @return protocol*/
	public String getProtocol();
	/** Gives the index of the experiment element.
	 * @return index*/
	public byte getIndex();
	/** Gives the type of the experiment element (currently only used for differentiating between e- / decoder and pre-en / post-decoder.
	 * @return type*/
	public byte getType();
}
