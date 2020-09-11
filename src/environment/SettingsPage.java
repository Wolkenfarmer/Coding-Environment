package environment;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * @author Wolkenfarmer
 */
abstract class SettingsPage {
	/** Layout container representing the given root from {@link Homepage} to attach the GUI-elements to.
	 * It's content ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation}) gets build in {@link #EnDecoderPage(Group)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reload(Group)} will be used to re-attach the content to the root.*/
	static Group root;
	/** Layout container for the heading segment. Contains {@link #lHeaHome} and {@link #lHeaHere} and gets added to {@link #root}.*/
	TextFlow tfHeading;
	/** Layout container for the overview segment. Contains {@link #lOveHeading} and {@link #pOveModel} and gets added to {@link #root}.*/
	Pane pOverview;
	/** Layout container for the options segment. Displays the different en- / decoder.
	 * Contains {@link #lOptHeading} and {@link #vbOptButtons} and gets added to {@link #root}.*/
	Pane pOptions;
		/** Label which displays the sub-heading "Options". It's part of {@link #pOptions}.*/
		Label lOptHeading;
		/** Layout container for the buttons of options. Contains {@link #bOptButGallager} and {@link #bOptButTODO} 
		 * and is part of {@link #pOptions}.*/
		VBox vbOptButtons;
	/** Layout container for the information segment. Displays the information of the picked en- / decoder in {@link #pOptions}.
	 * Contains {@link #lInfHeading} and {@link #pInfContent} and gets added to {@link #root}.*/
	InformationSegment pInformation;
	
	
	/**
	 * Reloads the en- / decoder page. Re-attaches the page's elements ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, 
	 * {@link #pInformation}) and {@link Main#krlBackHome}. In addition, {@link Main#updateScrollbar(Region)} gets called 
	 * (see {@link #EnDecoderPage(Group)} for more information relating to it's view-cases).
	 * This method gets called by the {@link Homepage homepage}, 
	 * when the page is already not null and {@link Homepage#bSetModEncoder} or {@link Homepage#bSetModDecoder} gets pressed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	void reload(Group parent) {
		root = parent;
		Main.updateScrollbar(pOptions);
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
		Main.scene.setOnKeyReleased(Main.krlBackHome);		
	}
	
	
	abstract void updateOveModel(byte changed);
}
