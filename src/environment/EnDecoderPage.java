package environment;

import infSources.UserInput;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextFlow;

public class EnDecoderPage {
	/**
	 * Saves the selected encoder / decoder for further use in the environment. This byte specifies the displayed text in 
	 * {@link Homepage#bSetModSource} and {@link #lConSelectedItem}. <br>
	 * 0: No option picked
	 * 1: Gallager-Code
	 * 2: example 2
	 */
	static byte selectedOption = 0;
	
	/** Layout container representing the given root from {@link Homepage} to attach the GUI-elements to.
	 * It's content ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation}) gets build in {@link #EnDecoderPage(Group)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reload(Group)} will be used to re-attach the content to the root.*/
	private static Group root;
	/** Layout container for the heading segment. Contains {@link #lHeaHome} and {@link #lHeaHere} and gets added to {@link #root}.*/
	private static TextFlow tfHeading;
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "En- / decoder" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	/** Layout container for the overview segment. Contains {@link #lOveHeading} and {@link #pOveModel} and gets added to {@link #root}.*/
	private static Pane pOverview;
		/** Label which displays the sub-heading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains TODO 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
//			/** The button showing the source in the overview. It's used as a better rectangle. Contains {@link #hbOveModSouContent} 
//			 * and is part of {@link #pOveModel}.*/
//			private static Button bOveModSource;
//				/** Layout container for the content of the information source button. Contains {@link #vbOveModSouConHeading}, 
//				 * {@link #liOveModSouConDiffer} and {@link #vbOveModSouSelectedItem} and is part of {@link #bOveModSource}.*/
//				private static HBox hbOveModSouContent;
//					/** Layout container for the heading of the information source button. Contains {@link #lOveModSouConHeading}
//					 * and is part of {@link #hbOveModSouContent}.*/
//					private static VBox vbOveModSouConHeading;
//						/** Label displaying the button's heading "Information source". It's part of {@link #vbOveModSouConHeading}.*/
//						private static Label lOveModSouConHeading;
//					/** A line to differ between {@link #vbOveModSouConHeading} and {@link #vbOveModSouSelectedItem} 
//					 * inside of {@link #bOveModSource}. It's part of {@link #hbOveModSouContent}.*/
//					private static Line liOveModSouConDiffer;
//					/** Layout container for the selected item section of the information source button. Contains {@link #lConSelectedItemHead}
//					 * and {@link #lConSelectedItem} and is part of {@link #hbOveModSouContent}.*/
//					private static VBox vbOveModSouSelectedItem;
//						/** Label displaying the the selected item section's head "selected item:" of the information source button. 
//						 * It's part of {@link #vbOveModSouSelectedItem}.*/
//						private static Label lConSelectedItemHead;
//						/** Label displaying the the selected item section's selection of the information source button. 
//						 * By default, "nothing selected" will be displayed, but gets updated to the currently picked information source.
//						 * It's part of {@link #vbOveModSouSelectedItem}.*/
//						private static Label lConSelectedItem;
//			/** The button showing the encoder in the overview. It's used as a better rectangle. Contains {@link #vbOveModEncHeading} 
//			 * and is part of {@link #pOveModel}.*/
//			private static Button bOveModEncoder;
//				/** Layout container for the content of encoder button. Contains {@link #lOveModEncHeading} and is part of {@link #bOveModEncoder}.*/
//				private static VBox vbOveModEncHeading;
//					/** Label displaying the button's heading "Encoder". It's part of {@link #vbOveModEncHeading}.*/
//					private static Label lOveModEncHeading;
//			/** Relation for the model in overview. Connects {@link #bOveModSource} with {@link #bOveModEncoder} and is part of {@link #pOveModel}.*/
//			private static Arrow aOveModRelation;
	/** Layout container for the options segment. Displays the different en- / decoder.
	 * Contains {@link #lOptHeading} and {@link #vbOptButtons} and gets added to {@link #root}.*/
	private static Pane pOptions;
		/** Label which displays the sub-heading "Options". It's part of {@link #pOptions}.*/
		private static Label lOptHeading;
		/** Layout container for the buttons of options. Contains {@link #bOptButGallager} and {@link #bOptButTODO} 
		 * and is part of {@link #pOptions}.*/
		private static VBox vbOptButtons;
			/** The button showing the Gallager-Code option under options. Contains {@link #hbOptButGallager} and is part of {@link #vbOptButtons}.*/
			private static Button bOptButGallager;
				/** Layout container for the content of Gallager-Code button. 
				 * Contains {@link #lOptButGallager} and is part of {@link #bOptButGallager}.*/
				private static HBox hbOptButGallager;
					/** Label displaying the button's heading "Gallager-Code". It's part of {@link #hbOptButGallager}.*/
					private static Label lOptButGallager;
			/** The button showing the TODO option under options. Contains {@link #hbOptButTODO} and is 
			 * part of {@link #vbOptButtons}.*/
			private static Button bOptButTODO;
				/** Layout container for the content of TODO button. 
				 * Contains {@link #lOptButTODO} and is part of {@link #bOptButTODO}.*/
				private static HBox hbOptButTODO;
					/** Label displaying the button's heading "TODO". It's part of {@link #hbOptButTODO}.*/
					private static Label lOptButTODO;
	/** Layout container for the information segment. Displays the information of the picked en- / decoder in {@link #pOptions}.
	 * Contains {@link #lInfHeading} and {@link #pInfContent} and gets added to {@link #root}.*/
	private static Pane pInformation;
		/** Label which displays the sub-heading "Information" by default, but gets updated to fit the currently picked en- / decoder. 
		 * It's part of {@link #pInformation}.*/
		private static Label lInfHeading;
		/** Layout container for the information content elements. Contains {@link #lInfConDefault} by default,
		 * but gets updated by the currently picked en- / decoder. It's part of {@link #pInformation}.*/
		private static Pane pInfContent;
			/** Label which displays "No option picked" if {@link #selectedOption} == 0. It's part of {@link #pInfContent}.*/
			private static Label lInfConDefault;
			
			
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
			lHeaHere.setText("En- / decoder");
			lHeaHere.setTextFill(Color.WHITESMOKE);
			lHeaHere.setFont(Main.fHeading);
			lHeaHere.setAlignment(Pos.CENTER_LEFT);
		tfHeading.getChildren().addAll(lHeaHome, lHeaHere);
		
		
		addListener();
		Main.updateScrollbar(tfHeading);
		root.getChildren().addAll(tfHeading);
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
	 * Reloads the source page. Re-attaches the page's elements ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation})
	 * and {@link Main#krlBackHome}. In addition, {@link Main#updateScrollbar(Region)} gets called 
	 * (see {@link #SourcePage(Group)} for more information relating to it's view-cases).
	 * This method gets called by the {@link Homepage homepage}, 
	 * when the page is already not null and {@link Homepage#bSetModEncoder} or {@link Homepage#bSetModDecoder} gets pressed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	void reload(Group parent) {
		root = parent;
		Main.updateScrollbar(tfHeading);
		root.getChildren().addAll(tfHeading);
		Main.scene.setOnKeyReleased(Main.krlBackHome);		
	}
}
