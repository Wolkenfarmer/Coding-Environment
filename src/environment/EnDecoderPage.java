package environment;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

/**
 * The en- / decoder page (a sub-page of {@link Homepage}).
 * The en- / decoder for the communication experiment can be set here.
 * See {@link #EnDecoderPage(Group)} for more information about the UI.
 * @author Wolkenfarmer
 */
public class EnDecoderPage extends SettingsPage {
	static boolean ovePrePostDisplaying;
	private double segmentWidthEnDe;
	private double segmentWidthPrePost;
	// Heading
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "En- / Decoder" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	// Overview
		/** Label which displays the sub-heading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains TODO 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the pre-encoder in the overview. It's used as a better rectangle. It's part of {@link #pOveModel}.*/
			private static OverviewButton bOveModPreencoder;
			/** The button showing the post-decoder in the overview. It's used as a better rectangle. It's part of {@link #pOveModel}.*/
			private static OverviewButton bOveModPostdecoder;
			/** The button showing the encoder in the overview. It's used as a better rectangle. It's part of {@link #pOveModel}.*/
			private static OverviewButton bOveModEncoder;
			/** The button showing the decoder in the overview. It's used as a better rectangle. It's part of {@link #pOveModel}.*/
			private static OverviewButton bOveModDecoder;
			/** Relations for the model in overview. Connect the start of {@link #pOveModel} with either {@link #bOveModPreencoder} or
			 * {@link #bOveModEncoder} and is part of {@link #pOveModel}.*/
			private static Arrow aOveModRelToEn;
			/** Relation for the model in overview. Connects {@link #bOveModPreencoder} with {@link #bOveModEncoder} 
			 * and is part of {@link #pOveModel}.*/
			private static Arrow aOveModRelEnToEn;
			/** Relation for the model in overview. Won't be used if {@link #selectedPrePost} == 0. 
			 * Connects {@link #bOveModEncoder} with {@link #bOveModDecoder} and is part of {@link #pOveModel}.*/
			private static Arrow aOveModRelEnToDe;
			/** Relation for the model in overview. Won't be used if {@link #selectedPrePost} == 0. 
			 * Connects {@link #bOveModDecoder} with {@link #bOveModPostdecoder} and is part of {@link #pOveModel}.*/
			private static Arrow aOveModRelDeToDe;
			/** Relation for the model in overview. Connects either {@link #bOveModDecoder} or {@link #bOveModPostdecoder} with 
			 * the end of {@link #pOveModel} and is part of {@link #pOveModel}.*/
			private static Arrow aOveModRelDeTo;
	// Options
		/** The button showing the Gallager-Code option under options. It's part of {@link #vbOptButtons}.*/
		private static OptionsButton bOptButGallager;
		/** The button showing the TODO option under options. It's 
		 * part of {@link #vbOptButtons}.*/
		private static OptionsButton bOptButMock;
		private static OptionsButton bOptButStringToByte;
	// Information
			
	
	/**
	 * Builds the en- / decoder page of the application.
	 * The en- / decoder page gets scaled accordingly to {@link Main#stageHeight} and {@link Main#stageWidth}.
	 * Normally, the height of {@link #pInformation} gets calculated in order to not exceed the screen's size, 
	 * but if the screen is too small to even fit {@link #pOptions} on it, 
	 * the options height will be the minimum height of the information segment and {@link Main#scrollbar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public EnDecoderPage(Group parent) {
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
			lHeaHere.setText("En- / Decoder");
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
			
			segmentWidthEnDe = pOverview.getPrefWidth() / 8;
			segmentWidthPrePost = pOverview.getPrefWidth() / 14;
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Main.distanceToSubheading);
				String currentlySelectedEnDecoder;
				switch (Main.selectedEnDecoder) {
				case 0:
					currentlySelectedEnDecoder = "nothing selected";
					break;
				case 1:
					currentlySelectedEnDecoder = Main.enDecoder_Gallager.getName();
					break;
				case 2:
					currentlySelectedEnDecoder = Main.enDecoder_Mock.getName();
					break;
				default:
					currentlySelectedEnDecoder = "En- / decoder not found";
				}
				
				bOveModPreencoder = new OverviewButton(segmentWidthPrePost * 2, "Pre-encoder", "");
				bOveModPreencoder.setLayoutX(segmentWidthPrePost);
				bOveModPostdecoder = new OverviewButton(segmentWidthPrePost * 2, "Post-decoder", "");
				bOveModPostdecoder.setLayoutX(segmentWidthPrePost * 11);
				
				
				if (Main.selectedPrePost == 0) {
					bOveModEncoder = new OverviewButton(segmentWidthEnDe * 2, "Encoder", currentlySelectedEnDecoder);
					bOveModEncoder.setLayoutX(segmentWidthEnDe);
					bOveModDecoder = new OverviewButton(segmentWidthEnDe * 2, "Decoder", currentlySelectedEnDecoder);
					bOveModDecoder.setLayoutX(segmentWidthEnDe * 5);
					
					double y = bOveModEncoder.getHeightW() / 2;
					aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthEnDe, y, 15, 10, false, "message");
					aOveModRelEnToDe = new Arrow().getArrow(segmentWidthEnDe * 3, y, segmentWidthEnDe * 5, y, 15, 10, false, "signal / channel");
					aOveModRelDeTo = new Arrow().getArrow(segmentWidthEnDe * 7, y, segmentWidthEnDe * 8, y, 15, 10, false, "message");
					
					ovePrePostDisplaying = false;
				} else {
					bOveModEncoder = new OverviewButton(segmentWidthPrePost * 2, "Encoder", currentlySelectedEnDecoder);
					bOveModEncoder.setLayoutX(segmentWidthPrePost * 4);
					bOveModDecoder = new OverviewButton(segmentWidthPrePost * 2, "Decoder", currentlySelectedEnDecoder);
					bOveModDecoder.setLayoutX(segmentWidthPrePost * 8);
					
					String currentPrePostProtocol;
					switch (Main.selectedPrePost) {
					case 0:
						bOveModPreencoder.setSelectedItem("nothing selected");
						bOveModPostdecoder.setSelectedItem("nothing selected");
						currentPrePostProtocol = "-";
						break;
					case 1:
						bOveModPreencoder.setSelectedItem(Main.enDecoder_StringToByte.getName());
						bOveModPostdecoder.setSelectedItem(Main.enDecoder_StringToByte.getName());
						currentPrePostProtocol = "byte[]";
						break;
					case 2:
						bOveModPreencoder.setSelectedItem(Main.enDecoder_Mock.getName());
						bOveModPostdecoder.setSelectedItem(Main.enDecoder_Mock.getName());
						currentPrePostProtocol = "wuff";
						break;
					default:
						bOveModPreencoder.setSelectedItem("Pre-en- / post-decoder not found");
						bOveModPostdecoder.setSelectedItem("Pre-en- / post-decoder not found");
						currentPrePostProtocol = "-";
					}
					
					double y;
					double y1 = bOveModEncoder.getHeightW() / 2;
					double y2 = bOveModPostdecoder.getHeightW() / 2;
					if (y1 >= y2) {
						y = y2;
					} else {
						y = y1;
					}
					aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthPrePost, y, 10, 10, false, "message");
					aOveModRelEnToEn = new Arrow().getArrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, false, currentPrePostProtocol);
					aOveModRelEnToDe = new Arrow().getArrow(segmentWidthPrePost * 6, y, segmentWidthPrePost * 8, y, 10, 10, false, "signal / channel");
					aOveModRelDeToDe = new Arrow().getArrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, false, currentPrePostProtocol);
					aOveModRelDeTo = new Arrow().getArrow(segmentWidthPrePost * 13, y, segmentWidthPrePost * 14, y, 10, 10, false, "message");
					
					
					pOveModel.getChildren().addAll(bOveModPreencoder, aOveModRelEnToEn, aOveModRelDeToDe, bOveModPostdecoder);
					ovePrePostDisplaying = true;
				}
			pOveModel.getChildren().addAll(aOveModRelToEn, bOveModEncoder, aOveModRelEnToDe, bOveModDecoder, aOveModRelDeTo);
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
				bOptButGallager = new OptionsButton(pOptions.getPrefWidth(), Main.enDecoder_Gallager.getName());
				bOptButMock = new OptionsButton(pOptions.getPrefWidth(), Main.enDecoder_Mock.getName());
				bOptButStringToByte = new OptionsButton(pOptions.getPrefWidth(), Main.enDecoder_StringToByte.getName());
			vbOptButtons.getChildren().addAll(bOptButGallager, bOptButMock, bOptButStringToByte);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    
	    
	    pInformation = new InformationSegment((byte) 1, Main.pos1 * 3, pOptions.getLayoutY(), Main.calcHeight(pOptions));
		    bOptButGallager.setOnActionW(Main.enDecoder_Gallager, this, pInformation);
		    bOptButMock.setOnActionW(Main.enDecoder_Mock, this, pInformation);
		    bOptButStringToByte.setOnActionW(Main.enDecoder_StringToByte, this, pInformation);
		    
		
		addListener();
		Main.updateScrollbar(pOptions);
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	
	
	void updateOveModel(byte changed) {
		String currentlySelectedEnDecoder;
		String currentlySelectedPrePost;
		String currentPrePostProtocol;
		
		switch (Main.selectedEnDecoder) {
		case 0:
			currentlySelectedEnDecoder = "nothing selected";
			break;
		case 1:
			currentlySelectedEnDecoder = Main.enDecoder_Gallager.getName();
			break;
		case 2:
			currentlySelectedEnDecoder = Main.enDecoder_Mock.getName();
			break;
		default:
			currentlySelectedEnDecoder = "En- / decoder not found";
		}
		
		switch (Main.selectedPrePost) {
		case 0:
			currentlySelectedPrePost = "nothing selected";
			currentPrePostProtocol = "-";
			break;
		case 1:
			currentlySelectedPrePost = Main.enDecoder_StringToByte.getName();
			currentPrePostProtocol = Main.enDecoder_StringToByte.getProtocol();
			break;
		case 2:
			currentlySelectedPrePost = Main.enDecoder_Mock.getName();
			currentPrePostProtocol = Main.enDecoder_Mock.getProtocol();
			break;
		default:
			currentlySelectedPrePost = "Pre-en- / post-decoder not found";
			currentPrePostProtocol = "-";
		}
		
		double y, y1, y2;
		
		switch (changed) {
		case 0:						// en- / decoder changes
			bOveModEncoder.setSelectedItem(currentlySelectedEnDecoder);
			bOveModDecoder.setSelectedItem(currentlySelectedEnDecoder);
			break;
		case 1:						// pre- / post- changed
			bOveModPreencoder.setSelectedItem(currentlySelectedPrePost);
			bOveModPostdecoder.setSelectedItem(currentlySelectedPrePost);
			
			y1 = bOveModEncoder.getHeightW() / 2;
			y2 = bOveModPostdecoder.getHeightW() / 2;
			if (y1 >= y2) {
				y = y2;
			} else {
				y = y1;
			}
			pOveModel.getChildren().removeAll(aOveModRelEnToEn, aOveModRelDeToDe);
			aOveModRelEnToEn = new Arrow().getArrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, false, currentPrePostProtocol);
			aOveModRelDeToDe = new Arrow().getArrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, false, currentPrePostProtocol);
			pOveModel.getChildren().addAll(aOveModRelEnToEn, aOveModRelDeToDe);
			break;
		case 2:						// view-setup: !pre-/post- -> pre-/post-
			bOveModPreencoder.rebuild(segmentWidthPrePost * 2, "Pre-encoder", currentlySelectedPrePost);
			bOveModPreencoder.setLayoutX(segmentWidthPrePost);
			bOveModEncoder.rebuild(segmentWidthPrePost * 2, "Encoder", currentlySelectedEnDecoder);
			bOveModEncoder.setLayoutX(segmentWidthPrePost * 4);
			bOveModDecoder.rebuild(segmentWidthPrePost * 2, "Decoder", currentlySelectedEnDecoder);
			bOveModDecoder.setLayoutX(segmentWidthPrePost * 8);
			bOveModPostdecoder.rebuild(segmentWidthPrePost * 2, "Post-decoder", currentlySelectedPrePost);
			bOveModPostdecoder.setLayoutX(segmentWidthPrePost * 11);
			
			y1 = bOveModEncoder.getHeightW() / 2;
			y2 = bOveModPostdecoder.getHeightW() / 2;
			if (y1 >= y2) {
				y = y2;
			} else {
				y = y1;
			}
			pOveModel.getChildren().removeAll(aOveModRelToEn, aOveModRelEnToDe, aOveModRelDeTo);
			aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthPrePost, y, 10, 10, false, "message");
			aOveModRelEnToEn = new Arrow().getArrow(segmentWidthPrePost * 3, y, segmentWidthPrePost * 4, y, 10, 10, false, currentPrePostProtocol);
			aOveModRelEnToDe = new Arrow().getArrow(segmentWidthPrePost * 6, y, segmentWidthPrePost * 8, y, 10, 10, false, "signal / channel");
			aOveModRelDeToDe = new Arrow().getArrow(segmentWidthPrePost * 10, y, segmentWidthPrePost * 11, y, 10, 10, false, currentPrePostProtocol);
			aOveModRelDeTo = new Arrow().getArrow(segmentWidthPrePost * 13, y, segmentWidthPrePost * 14, y, 10, 10, false, "message");
			
			pOveModel.getChildren().addAll(aOveModRelToEn, bOveModPreencoder, aOveModRelEnToEn, aOveModRelEnToDe, 
					aOveModRelDeToDe, bOveModPostdecoder, aOveModRelDeTo);
			ovePrePostDisplaying = true;
			break;
		case 3: 					// view-setup: pre-/post- -> !pre-/post-
			pOveModel.getChildren().removeAll(bOveModPreencoder, aOveModRelEnToEn, aOveModRelDeToDe, bOveModPostdecoder);
			
			bOveModEncoder.rebuild(segmentWidthEnDe * 2, "Encoder", currentlySelectedEnDecoder);
			bOveModEncoder.setLayoutX(segmentWidthEnDe * 1);
			bOveModDecoder.rebuild(segmentWidthEnDe * 2, "Decoder", currentlySelectedEnDecoder);
			bOveModDecoder.setLayoutX(segmentWidthEnDe * 5);
			
			y = bOveModEncoder.getHeightW() / 2;
			pOveModel.getChildren().removeAll(aOveModRelToEn, aOveModRelEnToDe, aOveModRelDeTo);
			aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidthEnDe, y, 15, 10, false, "message");
			aOveModRelEnToDe = new Arrow().getArrow(segmentWidthEnDe * 3, y, segmentWidthEnDe * 5, y, 15, 10, false, "signal / channel");
			aOveModRelDeTo = new Arrow().getArrow(segmentWidthEnDe * 7, y, segmentWidthEnDe * 8, y, 15, 10, false, "message");
			
			pOveModel.getChildren().addAll(aOveModRelToEn, aOveModRelEnToDe, aOveModRelDeTo);
			ovePrePostDisplaying = false;
			break;
		}
	}
	
	
	
	private void addListener() {
		Main.scene.setOnKeyReleased(Main.krlBackHome);
	}
	
	
	
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
}
