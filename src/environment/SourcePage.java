package environment;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

/**
 * The information source page (a sub-page of {@link Homepage}).
 * The information source for the communication experiment can be set here.
 * See {@link #SourcePage(Group)} for more information about the UI.
 * @author Wolkenfarmer
 */
public class SourcePage extends SettingsPage {
	private double segmentWidth;
	// heading
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "Information source" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	// Overview
		/** Label which displays the sub-heading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains {@link #bOveModSource}, {@link #bOveModEncoder} and {@link #aOveModRelation} 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the source in the overview. It's used as a better rectangle. Contains {@link #hbOveModSouContent} 
			 * and is part of {@link #pOveModel}.*/
			private static OverviewButton bOveModSource;
			/** Relation for the model in overview. Connects {@link #bOveModSource} with {@link #bOveModEncoder} and is part of {@link #pOveModel}.*/
			private static Arrow aOveModRelToSo;
			private static Arrow aOveModRelSoTo;
	// Options
		/** The button showing the user input option under options. Contains {@link #hbOptButUserInput} and is part of {@link #vbOptButtons}.*/
		private static OptionsButton bOptButUserInput;
		/** The button showing the random digit book option under options. Contains {@link #hbOptButBook} and is 
		 * part of {@link #vbOptButtons}.*/
		private static OptionsButton bOptButBook;
	// Information segment
			
			
	/**
	 * Builds the information source page of the application.
	 * The information source page gets scaled accordingly to {@link Main#stageHeight} and {@link Main#stageWidth}.
	 * Normally, the height of {@link #pInformation} gets calculated in order to not exceed the screen's size, 
	 * but if the screen is too small to even fit {@link #pOptions} on it, 
	 * the options height will be the minimum height of the information segment and {@link Main#scrollbar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public SourcePage(Group parent) {
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
				switch (Main.selectedSource) {
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
				bOptButUserInput = new OptionsButton(pOptions.getPrefWidth(), Main.infSource_UserInput.getName());
				bOptButBook = new OptionsButton(pOptions.getPrefWidth(), Main.infSource_RandomDigitBook.getName());
			vbOptButtons.getChildren().addAll(bOptButUserInput, bOptButBook);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    	    
	    
	    pInformation = new InformationSegment((byte) 0, Main.pos1 * 3, pOptions.getLayoutY(), Main.calcHeight(pOptions));
		    bOptButUserInput.setOnActionW(Main.infSource_UserInput, this, pInformation);
		    bOptButBook.setOnActionW(Main.infSource_RandomDigitBook, this, pInformation);
		
		
		addListener();
		Main.updateScrollbar(pInformation);
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	
	
	private void addListener() {
		Main.scene.setOnKeyReleased(Main.krlBackHome);
	}
	

	void updateOveModel(byte changed) {
		String currentProtocol;
		switch (Main.selectedSource) {
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
