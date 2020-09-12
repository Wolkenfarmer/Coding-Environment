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
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

/**
 * The information source page (a sub-page of the {@link Homepage home page}).
 * The information source for the communication experiment can be set here.
 * This page extends from {@link SettingsPage} (like {@link EnDecoderPage} and TODO).
 * See {@link #InfSourcePage(Group)} for more information about the GUI.
 * @author Wolkenfarmer
 */
public class InfSourcePage extends SettingsPage {
	/** Saves the width of an segment in {@link #pOveModel} which gets calculated in {@link #InfSourcePage(Group)}.*/
	private double segmentWidth;
	// heading
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "Information source" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	// Overview
		/** Label which displays the sub-heading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains {@link #aOveModRelToSo}, {@link #bOveModSource} and {@link #aOveModRelSoTo} 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the source in the overview. It's used as a better rectangle. 
			 * The button gets instantiated in {@link #InfSourcePage(Group)} and 
			 * updated in {@link #updateOveModel(byte)} to fit {@link Main#selectedInfSource}. It's part of {@link #pOveModel}.
			 * @see OverviewButton*/
			private static OverviewButton bOveModSource;
			/** Relation for the model in overview. Connects the start of {@link #pOveModel} with {@link #bOveModSource} 
			 * and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelToSo;
			/** Relation for the model in overview. Displays "message | " and the protocol type of the information source.
			 * Connects {@link #bOveModSource} with the end of {@link #pOveModel} and is part of {@link #pOveModel}.
			 * @see Arrow*/
			private static Arrow aOveModRelSoTo;
	// Options
		/** The button showing the {@link infSources.UserInput user input} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButUserInput;
		/** The button showing the {@link infSources.RandomDigitBook random digit book} option under {@link #pOptions option}. 
		 * It's part of {@link #vbOptButtons}.*/
		private static OptionButton bOptButBook;
	// Information segment
			
			
	/**
	 * Builds the information source page of the application.
	 * For building it's content and updating the environment accordingly to the picked options {@link OverviewButton}, {@link OptionButton} and
	 * {@link InformationSegment} get used.
	 * The information source page gets scaled accordingly to {@link Main#stageHeight} and {@link Main#stageWidth}.
	 * Normally, the height of {@link #pInformation} gets calculated in order to not exceed the screen's size, 
	 * but if the screen is too small to even fit {@link #pOptions} on it, 
	 * the options height will be the minimum height of the information segment and {@link Main#scrollbar scroll bar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public InfSourcePage(Group parent) {
		root = parent;
		
		tfHeading = new TextFlow();
		tfHeading.setLayoutX(Main.pos1);
		tfHeading.setLayoutY(Main.pos1 / 3);
		tfHeading.setPrefWidth(Main.contentWidth);
			lHeaHome = new Label();
			lHeaHome.setText("CE \\  ");
			lHeaHome.setTextFill(Color.WHITESMOKE);
			lHeaHome.setFont(Main.fHeadline);
			lHeaHome.setAlignment(Pos.CENTER_LEFT);
				
			lHeaHere = new Label();
			lHeaHere.setText("Information Source");
			lHeaHere.setTextFill(Color.WHITESMOKE);
			lHeaHere.setFont(Main.fHeading);
			lHeaHere.setAlignment(Pos.CENTER_LEFT);
		tfHeading.getChildren().addAll(lHeaHome, lHeaHere);
		
		
		
		pOverview = new Pane();
		pOverview.setLayoutX(Main.pos1);
		pOverview.setLayoutY(tfHeading.getLayoutY() + Main.distanceToHeading);
		pOverview.setPrefWidth(Main.contentWidth);
			lOveHeading = new Label();
			lOveHeading.setText("Overview");
			lOveHeading.setTextFill(Color.WHITESMOKE);
			lOveHeading.setFont(Main.fSubheading);
			
			segmentWidth = pOverview.getPrefWidth() / 3;
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Main.distanceToSubheading);
				String currentProtocol;
				switch (Main.selectedInfSource) {
				case 0:
					bOveModSource = new OverviewButton(segmentWidth, "Information Source", "nothing selected");
					currentProtocol = "-";
					break;
				case 1:
					bOveModSource = new OverviewButton(segmentWidth, "Information Source", Main.infSource_UserInput.getName());
					currentProtocol = "message | " + Main.infSource_UserInput.getProtocol();
					break;
				case 2:
					bOveModSource = new OverviewButton(segmentWidth, "Information Source", Main.infSource_RandomDigitBook.getName());
					currentProtocol = "message | " + Main.infSource_RandomDigitBook.getProtocol();
					break;
				default:
					bOveModSource = new OverviewButton(segmentWidth, "Information Source", "Information source not found");
					currentProtocol = "-";
				}
				bOveModSource.setLayoutX(segmentWidth);
				
				double y = bOveModSource.getHeightW() / 2;
				aOveModRelToSo = new Arrow().getArrow(0, y, segmentWidth, y, 15, 10, false, "you");
				aOveModRelSoTo = new Arrow().getArrow(segmentWidth * 2, y, segmentWidth * 3, y, 15, 10, false, currentProtocol);
			pOveModel.getChildren().addAll(aOveModRelToSo, bOveModSource, aOveModRelSoTo);
		pOverview.getChildren().addAll(lOveHeading, pOveModel);
		
		
		
		pOptions = new Pane();
		pOptions.setLayoutX(Main.pos1);
		pOptions.setLayoutY(pOverview.getLayoutY() + Main.calcHeight(pOverview) + Main.distanceToSegment);
		pOptions.setPrefWidth(Main.stageWidth / 8 * 1.5);
			lOptHeading = new Label();
			lOptHeading.setText("Options");
			lOptHeading.setTextFill(Color.WHITESMOKE);
			lOptHeading.setFont(Main.fSubheading);			
			
			vbOptButtons = new VBox();
			vbOptButtons.setPrefWidth(pOptions.getPrefWidth());
			vbOptButtons.setLayoutY(Main.distanceToSubheading);
			vbOptButtons.setSpacing(20);
				bOptButUserInput = new OptionButton(pOptions.getPrefWidth(), Main.infSource_UserInput.getName());
				bOptButBook = new OptionButton(pOptions.getPrefWidth(), Main.infSource_RandomDigitBook.getName());
			vbOptButtons.getChildren().addAll(bOptButUserInput, bOptButBook);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    	    
	    
	    pInformation = new InformationSegment((byte) 0, Main.pos1 * 3, pOptions.getLayoutY(), Main.calcHeight(pOptions));
		    bOptButUserInput.setOnActionW(Main.infSource_UserInput, this, pInformation);
		    bOptButBook.setOnActionW(Main.infSource_RandomDigitBook, this, pInformation);
		
		
		addListener();
		Main.updateScrollbar(pInformation);
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	

	/**
	 * Updates the {@link #pOveModel overview model} of this page to fit the {@link Main#selectedInfSource currently selected information source}.
	 * For this {@link OverviewButton#setSelectedItem(String)} gets used and
	 * the {@link Arrow relation} gets rebuild and added to {@link #pOveModel}.
	 * @param changed Is currently not used for this page.
	 */
	public void updateOveModel(byte changed) {
		String currentProtocol;
		switch (Main.selectedInfSource) {
		case 0:
			bOveModSource.setSelectedItem("nothing selected");
			currentProtocol = "-";
			break;
		case 1:
			bOveModSource.setSelectedItem(Main.infSource_UserInput.getName());
			currentProtocol = "message | " + Main.infSource_UserInput.getProtocol();
			break;
		case 2:
			bOveModSource.setSelectedItem(Main.infSource_RandomDigitBook.getName());
			currentProtocol = "message | " + Main.infSource_RandomDigitBook.getProtocol();
			break;
		default:
			bOveModSource.setSelectedItem("Information source not found");
			currentProtocol = "-";
		}
		
		double y = bOveModSource.getHeightW() / 2;
		pOveModel.getChildren().remove(aOveModRelSoTo);
		aOveModRelSoTo = new Arrow().getArrow(segmentWidth * 2, y, segmentWidth * 3, y, 15, 10, false, currentProtocol);
		pOveModel.getChildren().add(aOveModRelSoTo);
	}
}
