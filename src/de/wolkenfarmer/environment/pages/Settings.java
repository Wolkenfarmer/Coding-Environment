package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.gui_elements.InformationSegment;
import de.wolkenfarmer.environment.pages.gui_elements.OptionButton;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * Abstract class for the sub-pages {@link InputHandler}, {@link Transcoder} and {@link NoiseSource} .
 * Gets used for {@link OptionButton} and {@link InformationSegment} in order to have a unified access to the pages / 
 * implement a unified system for handling the pages.
 * @author Wolkenfarmer
 */
public abstract class Settings {
	/** Layout container representing the given root from {@link Home home page} to attach the GUI-elements to.
	 * It's content ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation}) gets build in the corresponding constructors.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reload(Group)} will be used to reattach the content to the root.*/
	static Group root;
	/** Layout container for the heading segment. 
	 * It's content will be added in the constructors of the sub-classes. It gets added to {@link #root}.*/
	TextFlow tfHeading;
	/** Layout container for the overview segment. 
	 * It's content will be added in the constructors of the sub-classes. It gets added to {@link #root}.*/
	Pane pOverview;
	/** Layout container for the options segment. It's content will be added in the constructors of the sub-classes.
	 * Contains {@link #lOptHeading} and {@link #vbOptButtons} and gets added to {@link #root}.*/
	Pane pOptions;
		/** Label which displays the subheading "Options". It's part of {@link #pOptions}.*/
		Label lOptHeading;
		/** Layout container for the buttons of options. It's content will be added in the constructors of the sub-classes. 
		 * It's part of {@link #pOptions}.*/
		public VBox vbOptButtons;
	/** Layout container for the information segment. 
	 * Displays the information of the selected {@link ExperimentElement experiment element} in {@link #pOptions}.
	 * It gets added to {@link #root}.
	 * @see InformationSegment*/
	InformationSegment pInformation;
	
	
	/**
	 * Reloads the settings page. Re-attaches the page's elements ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, 
	 * {@link #pInformation}) and {@link Main#krlBackHome}. In addition, {@link Main#updateScrollbar(Region)} gets called.
	 * This method gets called by the {@link Home home page}, 
	 * when the page is already not null and the corresponding button(s) get(s) pressed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	void reload(Group parent) {
		root = parent;
		if (Main.calcHeight(pOptions) >= Main.calcHeight(pInformation)) {
			Main.updateScrollbar(pOptions);
		} else {
			Main.updateScrollbar(pInformation);
		}
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
		Main.scene.setOnKeyReleased(Main.krlBackHome);		
	}
	
	
	/**
	 * Sets the on-key-released-listener {@link Main#krlBackHome} for the scene. 
	 * Gets called when building the page or {@link #reload(Group) reloading}.
	 */
	void addListener() {
		Main.scene.setOnKeyReleased(Main.krlBackHome);
	}
	
	
	/**
	 * Updates the {@link #pOverview overview's} model to fit the selected element in {@link Main}.
	 * @param changed Specifies what has to be updated in the model.
	 */
	public abstract void updateOveModel(byte changed);
	
	
	/**
	 * Updates the {@link Main#scrollbar scroll bar} when the 
	 * {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement, Settings, InformationSegment) 
	 * information segment's content changed}. 
	 */
	public void updateHeight() {
		if (Main.calcHeight(pOptions) >= Main.calcHeight(pInformation)) {
			Main.updateScrollbar(pOptions);
		} else {
			Main.updateScrollbar(pInformation);
		}
		root.getChildren().addAll(pOptions, pInformation);
	}
}
