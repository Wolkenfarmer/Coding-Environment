package environment.pages;

import environment.Main;
import environment.pages.guiElements.Arrow;
import environment.pages.guiElements.InformationSegment;
import environment.pages.guiElements.OptionButton;
import environment.pages.guiElements.OverviewButton;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * The noise source page (a sub-page of the {@link Homepage home page}).
 * The noise source for the communication experiment can be set here.
 * This page extends from {@link SettingsPage} (like {@link InfSourcePage} and {@link EnDecoderPage}).
 * See {@link #NoiSourcePage(Group)} for more information about the GUI.
 * @author Wolkenfarmer
 */
public class NoiSourcePage extends SettingsPage {
	/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #NoiSourcePage(Group)}.*/
	private double segmentWidth;
	// heading
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "Noise Source" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	// Overview
		/** Label which displays the sub-heading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains {@link #aOveModRelNoToCh}, {@link #bOveModSource} and {@link #aOveModRelEnToDe} 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the source in the overview. It's used as a better rectangle. 
			 * The button gets instantiated in {@link #NoiSourcePage(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedNoiSource}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModSource;
			/** Relation for the model in overview. Displays the protocol of the selected noise source. 
			 * Connects {@link #bOveModSource} with {@link #aOveModRelNoToCh} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelNoToCh;
			/** Relation for the model in overview. 
			 * Represents the channel in the middle of the {@link Homepage#pSetModel settings model} in the {@link Homepage home page}.
			 * Displays "channel | " and the protocol type of the {@link Main#selectedEnDecoder selected en- / decoder}.
			 * It's part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelEnToDe;
	// Options
		/** The button showing the {@link noiSources.IndividualChanges individual changes} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButIndChanges;
		/** The button showing the {@link noiSources.MixUpChanges mix up changes} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButMixUpChanges;
	// Information segment
	
	
	/**
	 * Builds the noise source page of the application.
	 * For building it's content and updating the environment accordingly to the picked option {@link OverviewButton}, {@link OptionButton} and
	 * {@link InformationSegment} get used.
	 * The information source page gets scaled accordingly to {@link Main#stageHeight} and {@link Main#stageWidth}.
	 * Normally, the height of {@link #pInformation} gets calculated in order to not exceed the screen's size, 
	 * but if the screen is too small to even fit {@link #pOptions} on it, 
	 * the options height will be the minimum height of the information segment and {@link Main#scrollbar scroll bar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public NoiSourcePage(Group parent) {
		root = parent;
		
		tfHeading = new TextFlow();
		tfHeading.setLayoutX(Main.pos1);
		tfHeading.setLayoutY(Main.pos1 / 3);
		tfHeading.setPrefWidth(Main.contentWidth);
			lHeaHome = new Label();
			lHeaHome.setText("CE \\  ");
			lHeaHome.setTextFill(Main.cNormal);
			lHeaHome.setFont(Main.fHeadline);
			lHeaHome.setAlignment(Pos.CENTER_LEFT);
				
			lHeaHere = new Label();
			lHeaHere.setText("Noise Source");
			lHeaHere.setTextFill(Main.cNormal);
			lHeaHere.setFont(Main.fHeading);
			lHeaHere.setAlignment(Pos.CENTER_LEFT);
		tfHeading.getChildren().addAll(lHeaHome, lHeaHere);
		
		
		pOverview = new Pane();
		pOverview.setLayoutX(Main.pos1);
		pOverview.setLayoutY(tfHeading.getLayoutY() + Main.distanceToHeading);
		pOverview.setPrefWidth(Main.contentWidth);
			lOveHeading = new Label();
			lOveHeading.setText("Overview");
			lOveHeading.setTextFill(Main.cNormal);
			lOveHeading.setFont(Main.fSubheading);
			
			segmentWidth = pOverview.getPrefWidth() / 4;
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Main.distanceToSubheading);
				String currentProtocol;
				switch (Main.selectedNoiSource) {
				case 0:
					bOveModSource = new OverviewButton(segmentWidth, "Noise Source", "nothing selected");
					currentProtocol = "-";
					break;
				case 1:
					bOveModSource = new OverviewButton(segmentWidth, "Noise Source", Main.noiSource_IndividualChanges.getName());
					currentProtocol = "noise | " + Main.noiSource_IndividualChanges.getProtocol();
					break;
				case 2:
					bOveModSource = new OverviewButton(segmentWidth, "Noise Source", Main.noiSource_MixUpChanges.getName());
					currentProtocol = "noise | " + Main.noiSource_MixUpChanges.getProtocol();
					break;
				default:
					bOveModSource = new OverviewButton(segmentWidth, "Noise Source", "Noise source not found");
					currentProtocol = "-";
				}
				
				double y = bOveModSource.getHeightW() / 2;
				aOveModRelNoToCh = new Arrow().getArrow(segmentWidth, y, segmentWidth * 3, y, 15, 10, false, currentProtocol, 0);
				
				String enDecoderProtocol;
				switch (Main.selectedEnDecoder) {
				case 0:
					enDecoderProtocol = "nothing selected";
					break;
				case 1:
					enDecoderProtocol = Main.enDecoder_Gallager.getProtocol();
					break;
				case 2:
					enDecoderProtocol= Main.enDecoder_Mock.getProtocol();
					break;
				default:
					enDecoderProtocol = "en- / decoder index \"" + Main.selectedEnDecoder + "\" not found";
				}
				aOveModRelEnToDe = new Arrow().getArrow(segmentWidth * 3, 0, segmentWidth * 3, y * 2, 5, 10, true, 
						"En- / decoder protocol:\n" + enDecoderProtocol, segmentWidth);
			pOveModel.getChildren().addAll(bOveModSource, aOveModRelNoToCh, aOveModRelEnToDe);
		pOverview.getChildren().addAll(lOveHeading, pOveModel);
		
		
		pOptions = new Pane();
		pOptions.setLayoutX(Main.pos1);
		pOptions.setLayoutY(pOverview.getLayoutY() + Main.calcHeight(pOverview) + Main.distanceToSegment);
		pOptions.setPrefWidth(Main.stageWidth / 8 * 1.5);
			lOptHeading = new Label();
			lOptHeading.setText("Options");
			lOptHeading.setTextFill(Main.cNormal);
			lOptHeading.setFont(Main.fSubheading);			
			
			vbOptButtons = new VBox();
			vbOptButtons.setPrefWidth(pOptions.getPrefWidth());
			vbOptButtons.setLayoutY(Main.distanceToSubheading);
			vbOptButtons.setSpacing(20);
				bOptButIndChanges = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_IndividualChanges.getName());
				bOptButMixUpChanges = new OptionButton(pOptions.getPrefWidth(), Main.noiSource_MixUpChanges.getName());
			vbOptButtons.getChildren().addAll(bOptButIndChanges, bOptButMixUpChanges);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    
	    
	    pInformation = new InformationSegment((byte) 2, Main.pos1 * 3, pOptions.getLayoutY(), Main.calcHeight(pOptions));
	    	bOptButIndChanges.setOnActionW(Main.noiSource_IndividualChanges, this, pInformation);
	    	bOptButMixUpChanges.setOnActionW(Main.noiSource_MixUpChanges, this, pInformation);
		
		
		addListener();
		Main.updateScrollbar(pOptions);
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	
	
	/**
	 * Updates the {@link #pOveModel overview model} of this page to fit the {@link Main#selectedInfSource currently selected noise source}.
	 * For this {@link OverviewButton#setSelectedItem(String)} gets used and {@link #aOveModRelNoToCh} 
	 * gets rebuild and added to {@link #pOveModel}.
	 * @param changed Is currently not used for this page.
	 */
	public void updateOveModel(byte changed) {
		String currentProtocol;
		switch (Main.selectedNoiSource) {
		case 0:
			bOveModSource.setSelectedItem("nothing selected");
			currentProtocol = "-";
			break;
		case 1:
			bOveModSource.setSelectedItem(Main.noiSource_IndividualChanges.getName());
			currentProtocol = "message | " + Main.noiSource_IndividualChanges.getProtocol();
			break;
		case 2:
			bOveModSource.setSelectedItem(Main.noiSource_MixUpChanges.getName());
			currentProtocol = "message | " + Main.noiSource_MixUpChanges.getProtocol();
			break;
		default:
			bOveModSource.setSelectedItem("Noise source index \"" + Main.selectedNoiSource + "\" not found");
			currentProtocol = "-";
		}
		
		double y = bOveModSource.getHeightW() / 2;
		pOveModel.getChildren().remove(aOveModRelNoToCh);
		aOveModRelNoToCh = new Arrow().getArrow(segmentWidth, y, segmentWidth * 3, y, 15, 10, false, currentProtocol, 0);
		pOveModel.getChildren().add(aOveModRelNoToCh);
	}
	
	
	/**
	 * Updates the {@link #pOveModel overview model} of this page to fit the {@link Main#selectedEnDecoder currently selected en- decoder protocol}.
	 * For this {@link #aOveModRelEnToDe} gets rebuild and added to {@link #pOveModel}.
	 * This method gets called from {@link InformationSegment#setSaveAddReference(environment.ExperimentElement, OptionButton, SettingsPage)}.
	 */
	public void updateOveModEnDeProtocol() {
		String enDecoderProtocol;
		switch (Main.selectedEnDecoder) {
		case 0:
			enDecoderProtocol = "nothing selected";
			break;
		case 1:
			enDecoderProtocol = Main.enDecoder_Gallager.getProtocol();
			break;
		case 2:
			enDecoderProtocol= Main.enDecoder_Mock.getProtocol();
			break;
		default:
			enDecoderProtocol = "en- / decoder index \"" + Main.selectedEnDecoder + "\" not found";
		}
		double y = bOveModSource.getHeightW() / 2;
		
		if (pOveModel.getChildren().contains(aOveModRelEnToDe)) {
			pOveModel.getChildren().remove(aOveModRelEnToDe);
		}
		aOveModRelEnToDe = new Arrow().getArrow(segmentWidth * 3, 0, segmentWidth * 3, y * 2, 5, 10, true, 
				"En- / decoder protocol:\n" + enDecoderProtocol, segmentWidth);
		pOveModel.getChildren().add(aOveModRelEnToDe);
	}

}
