package environment;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;

/**
 * The en- / decoder page (a sub-page of {@link Homepage}).
 * The en- / decoder for the communication experiment can be set here.
 * See {@link #EnDecoderPage(Group)} for more information about the UI.
 * @author Wolkenfarmer
 */
public class EnDecoderPage {
	/**
	 * Saves the selected encoder / decoder for further use in the environment. This byte specifies the displayed text in 
	 * {@link Homepage#bSetModSource} and {@link #bOveModEncoder} / {@link #bOveModDecoder}. <br>
	 * 0: No option picked
	 * 1: Gallager-Code
	 * 2: example 2
	 */
	static byte selectedEnDecoder = 1;
	/**
	 * Saves the selected pre-en- / post-decoder for further use in the environment. This byte specifies the displayed text in 
	 * {@link Homepage#bSetModSource} and {@link #bOveModPreencoder} / {@link #bOveModPostdecoder}. <br>
	 * 0: No option picked
	 * 1: String to byte[]
	 */
	static byte selectedPrePost = 1;
	
	/** Layout container representing the given root from {@link Homepage} to attach the GUI-elements to.
	 * It's content ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation}) gets build in {@link #EnDecoderPage(Group)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reload(Group)} will be used to re-attach the content to the root.*/
	private static Group root;
	/** Layout container for the heading segment. Contains {@link #lHeaHome} and {@link #lHeaHere} and gets added to {@link #root}.*/
	private static TextFlow tfHeading;
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "En- / Decoder" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	/** Layout container for the overview segment. Contains {@link #lOveHeading} and {@link #pOveModel} and gets added to {@link #root}.*/
	private static Pane pOverview;
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
//	/** Layout container for the options segment. Displays the different en- / decoder.
//	 * Contains {@link #lOptHeading} and {@link #vbOptButtons} and gets added to {@link #root}.*/
//	private static Pane pOptions;
//		/** Label which displays the sub-heading "Options". It's part of {@link #pOptions}.*/
//		private static Label lOptHeading;
//		/** Layout container for the buttons of options. Contains {@link #bOptButGallager} and {@link #bOptButTODO} 
//		 * and is part of {@link #pOptions}.*/
//		private static VBox vbOptButtons;
//			/** The button showing the Gallager-Code option under options. Contains {@link #hbOptButGallager} and is part of {@link #vbOptButtons}.*/
//			private static Button bOptButGallager;
//				/** Layout container for the content of Gallager-Code button. 
//				 * Contains {@link #lOptButGallager} and is part of {@link #bOptButGallager}.*/
//				private static HBox hbOptButGallager;
//					/** Label displaying the button's heading "Gallager-Code". It's part of {@link #hbOptButGallager}.*/
//					private static Label lOptButGallager;
//			/** The button showing the TODO option under options. Contains {@link #hbOptButTODO} and is 
//			 * part of {@link #vbOptButtons}.*/
//			private static Button bOptButTODO;
//				/** Layout container for the content of TODO button. 
//				 * Contains {@link #lOptButTODO} and is part of {@link #bOptButTODO}.*/
//				private static HBox hbOptButTODO;
//					/** Label displaying the button's heading "TODO". It's part of {@link #hbOptButTODO}.*/
//					private static Label lOptButTODO;
//	/** Layout container for the information segment. Displays the information of the picked en- / decoder in {@link #pOptions}.
//	 * Contains {@link #lInfHeading} and {@link #pInfContent} and gets added to {@link #root}.*/
//	private static Pane pInformation;
//		/** Label which displays the sub-heading "Information" by default, but gets updated to fit the currently picked en- / decoder. 
//		 * It's part of {@link #pInformation}.*/
//		private static Label lInfHeading;
//		/** Layout container for the information content elements. Contains {@link #lInfConDefault} by default,
//		 * but gets updated by the currently picked en- / decoder. It's part of {@link #pInformation}.*/
//		private static Pane pInfContent;
//			/** Label which displays "No option picked" if {@link #selectedEnDecoder} == 0. It's part of {@link #pInfContent}.*/
//			private static Label lInfConDefault;
			
	
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
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Main.distanceToSubheading);
			buildOverviewModel();
		pOverview.getChildren().addAll(lOveHeading, pOveModel);
		
		
		addListener();
		Main.updateScrollbar(pOverview);
		root.getChildren().addAll(tfHeading, pOverview);
	}
	
	
	/**
	 * Builds / instantiates the overview {@link OverviewButton buttons} and {@link Arrow arrows} for {@link #pOveModel} . 
	 * This is a separate method from {@link #EnDecoderPage(Group)} in order 
	 * to be able to separately re-build the model if an en- / decoder gets chosen.
	 * It gets the option to be displayed from {@link #selectedEnDecoder} and {@link #selectedPrePost}.
	 * This method gets called by the constructor and the buttons event handler from {@link #vbOptButtons} (see {@link #addListener()}). 
	 */
	private void buildOverviewModel() {
		pOveModel.getChildren().clear();
		
		String currentlySelectedEnDecoder;
		switch (selectedEnDecoder) {
		case 0:
			currentlySelectedEnDecoder = "nothing selected";
			break;
		case 1:
			currentlySelectedEnDecoder = "Gallager-Code";
			break;
		case 2:
			currentlySelectedEnDecoder = "Example 2";
			break;
		default:
			currentlySelectedEnDecoder = "En- / decoder not found";
		}
		
		
		if (selectedPrePost == 0) {
			double segmentWidth = pOverview.getPrefWidth() / 8;
			
			bOveModEncoder = new OverviewButton(segmentWidth * 2, "Encoder", currentlySelectedEnDecoder);
			bOveModEncoder.setLayoutX(segmentWidth);
			bOveModDecoder = new OverviewButton(segmentWidth * 2, "Decoder", currentlySelectedEnDecoder);
			bOveModDecoder.setLayoutX(segmentWidth * 5);
			
			double y = bOveModEncoder.getHeightW() / 2;
			aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidth, y, 15, 10, false, "message");
			aOveModRelEnToDe = new Arrow().getArrow(segmentWidth * 3, y, segmentWidth * 5, y, 15, 10, false, "signal / channel");
			aOveModRelDeTo = new Arrow().getArrow(segmentWidth * 7, y, segmentWidth * 8, y, 15, 10, false, "message");
			
		} else {
			double segmentWidth = pOverview.getPrefWidth() / 14;
			String currentlySelectedPrePost;
			String currentPrePostProtocol;
			switch (selectedPrePost) {
			case 0:
				currentlySelectedPrePost = "nothing selected";
				currentPrePostProtocol = "-";
				break;
			case 1:
				currentlySelectedPrePost = "String to byte[]";
				currentPrePostProtocol = "byte[]";
				break;
			default:
				currentlySelectedPrePost = "Pre-en- / post-decoder not found";
				currentPrePostProtocol = "-";
			}
			
			bOveModPreencoder = new OverviewButton(segmentWidth * 2, "Pre-encoder", currentlySelectedPrePost);
			bOveModPreencoder.setLayoutX(segmentWidth);
			bOveModEncoder = new OverviewButton(segmentWidth * 2, "Encoder", currentlySelectedEnDecoder);
			bOveModEncoder.setLayoutX(segmentWidth * 4);
			bOveModDecoder = new OverviewButton(segmentWidth * 2, "Decoder", currentlySelectedEnDecoder);
			bOveModDecoder.setLayoutX(segmentWidth * 8);
			bOveModPostdecoder = new OverviewButton(segmentWidth * 2, "Post-decoder", currentlySelectedPrePost);
			bOveModPostdecoder.setLayoutX(segmentWidth * 11);
			
			double y;
			double y1 = bOveModEncoder.getHeightW() / 2;
			double y2 = bOveModPostdecoder.getHeightW() / 2;
			if (y1 >= y2) {
				y = y2;
			} else {
				y = y1;
			}
			aOveModRelToEn = new Arrow().getArrow(0, y, segmentWidth, y, 10, 10, false, "message");
			aOveModRelEnToEn = new Arrow().getArrow(segmentWidth * 3, y, segmentWidth * 4, y, 10, 10, false, currentPrePostProtocol);
			aOveModRelEnToDe = new Arrow().getArrow(segmentWidth * 6, y, segmentWidth * 8, y, 10, 10, false, "signal / channel");
			aOveModRelDeToDe = new Arrow().getArrow(segmentWidth * 10, y, segmentWidth * 11, y, 10, 10, false, currentPrePostProtocol);
			aOveModRelDeTo = new Arrow().getArrow(segmentWidth * 13, y, segmentWidth * 14, y, 10, 10, false, "message");
			
			
			pOveModel.getChildren().addAll(bOveModPreencoder, aOveModRelEnToEn, aOveModRelDeToDe, bOveModPostdecoder);
		}
		pOveModel.getChildren().addAll(aOveModRelToEn, bOveModEncoder, aOveModRelEnToDe, bOveModDecoder, aOveModRelDeTo);
	}
	
	
	
	private void addListener() {
		Main.scene.setOnKeyReleased(Main.krlBackHome);
		
//		bOptButUserInput.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent t) {
//				System.out.println("bOptButUserInput got pressed!");
//				bOptButUserInput.setBackground(Main.baGreenFocusedButton);
//				bOptButBook.setBackground(Main.baNormalButton);
//				bOptButUserInput.setOnMouseEntered(Main.evButGreEntered);
//				bOptButUserInput.setOnMouseExited(Main.evButGreExited);
//				bOptButBook.setOnMouseEntered(Main.evButEntered);
//				bOptButBook.setOnMouseExited(Main.evButExited);
//				
//				lInfHeading.setText("Information \\  User input");
//				pOveModel.getChildren().removeAll(bOveModSource, aOveModRelation);
//				buildOveSourceButton("user input");
//				buildOveArrow();
//				pOveModel.getChildren().addAll(bOveModSource, aOveModRelation);
//				
//				pInfContent.getChildren().clear();
//				if (UserInput.builtUI) {
//					Main.infSource_UserInput.reloadUI(pInfContent);
//				} else {
//					Main.infSource_UserInput.buildUI(pInfContent);
//				}
//				
//				selectedOption = 1;
//				Main.boUpdateSettingsModelHomepage = true;
//	        }
//	    });
//		
//		bOptButBook.setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent t) {
//				System.out.println("bConButBook got pressed!");
//				bOptButUserInput.setBackground(Main.baNormalButton);
//				bOptButBook.setBackground(Main.baGreenFocusedButton);
//				bOptButUserInput.setOnMouseEntered(Main.evButEntered);
//				bOptButUserInput.setOnMouseExited(Main.evButExited);
//				bOptButBook.setOnMouseEntered(Main.evButGreEntered);
//				bOptButBook.setOnMouseExited(Main.evButGreExited);
//				
//				lInfHeading.setText("Information \\  Random digit book");
//				pOveModel.getChildren().removeAll(bOveModSource, aOveModRelation);
//				buildOveSourceButton("random digit book");
//				buildOveArrow();
//				pOveModel.getChildren().addAll(bOveModSource, aOveModRelation);
//				
//				pInfContent.getChildren().clear();
//				
//				selectedOption = 2;
//				Main.boUpdateSettingsModelHomepage = true;
//	        }
//	    });
//		
//		bOptButUserInput.setOnMouseEntered(Main.evButEntered);
//		bOptButUserInput.setOnMouseExited(Main.evButExited);
//		bOptButBook.setOnMouseEntered(Main.evButEntered);
//		bOptButBook.setOnMouseExited(Main.evButExited);
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
		Main.updateScrollbar(tfHeading);
		root.getChildren().addAll(tfHeading, pOverview);
		Main.scene.setOnKeyReleased(Main.krlBackHome);		
	}
}
