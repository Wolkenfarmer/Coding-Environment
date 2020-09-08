package environment;

import infSources.UserInput;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * The information source page (a sub-page of {@link Homepage}).
 * The information source for the communication experiment can be set here.
 * See {@link #SourcePage(Group)} for more information about the UI.
 * @author Wolkenfarmer
 */
public class SourcePage {
	/**
	 * Saves the selected information source for further use in the environment. This byte specifies the displayed text in 
	 * {@link Homepage#bSetModSource} and {@link #lConSelectedItem}. <br>
	 * 0: No option picked
	 * 1: {@link infSources.UserInput user input}
	 * 2: random digit book
	 */
	static byte selectedOption = 0;
	
	/** Layout container representing the given root from {@link Homepage} to attach the GUI-elements to.
	 * It's content ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation}) gets build in {@link #SourcePage(Group)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reload(Group)} will be used to re-attach the content to the root.*/
	private static Group root;
	/** Layout container for the heading segment. Contains {@link #lHeaHome} and {@link #lHeaHere} and gets added to {@link #root}.*/
	private static TextFlow tfHeading;
		/** Label which displays the heading segment "CE \  " (bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the heading segment "Information source" (not bold). It's part of {@link #tfHeading}.*/
		private static Label lHeaHere;
	/** Layout container for the overview segment. Contains {@link #lOveHeading} and {@link #pOveModel} and gets added to {@link #root}.*/
	private static Pane pOverview;
		/** Label which displays the sub-heading "Overview". It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. Contains {@link #bOveModSource}, {@link #bOveModEncoder} and {@link #aOveModRelation} 
		 * and is part of {@link #pOverview}.*/
		private static Pane pOveModel;
			/** The button showing the source in the overview. It's used as a better rectangle. Contains {@link #hbOveModSouContent} 
			 * and is part of {@link #pOveModel}.*/
			private static Button bOveModSource;
				/** Layout container for the content of the information source button. Contains {@link #vbOveModSouConHeading}, 
				 * {@link #liOveModSouConDiffer} and {@link #vbOveModSouSelectedItem} and is part of {@link #bOveModSource}.*/
				private static HBox hbOveModSouContent;
					/** Layout container for the heading of the information source button. Contains {@link #lOveModSouConHeading}
					 * and is part of {@link #hbOveModSouContent}.*/
					private static VBox vbOveModSouConHeading;
						/** Label displaying the button's heading "Information source". It's part of {@link #vbOveModSouConHeading}.*/
						private static Label lOveModSouConHeading;
					/** A line to differ between {@link #vbOveModSouConHeading} and {@link #vbOveModSouSelectedItem} 
					 * inside of {@link #bOveModSource}. It's part of {@link #hbOveModSouContent}.*/
					private static Line liOveModSouConDiffer;
					/** Layout container for the selected item section of the information source button. Contains {@link #lConSelectedItemHead}
					 * and {@link #lConSelectedItem} and is part of {@link #hbOveModSouContent}.*/
					private static VBox vbOveModSouSelectedItem;
						/** Label displaying the the selected item section's head "selected item:" of the information source button. 
						 * It's part of {@link #vbOveModSouSelectedItem}.*/
						private static Label lConSelectedItemHead;
						/** Label displaying the the selected item section's selection of the information source button. 
						 * By default, "nothing selected" will be displayed, but gets updated to the currently picked information source.
						 * It's part of {@link #vbOveModSouSelectedItem}.*/
						private static Label lConSelectedItem;
			/** The button showing the encoder in the overview. It's used as a better rectangle. Contains {@link #vbOveModEncHeading} 
			 * and is part of {@link #pOveModel}.*/
			private static Button bOveModEncoder;
				/** Layout container for the content of encoder button. Contains {@link #lOveModEncHeading} and is part of {@link #bOveModEncoder}.*/
				private static VBox vbOveModEncHeading;
					/** Label displaying the button's heading "Encoder". It's part of {@link #vbOveModEncHeading}.*/
					private static Label lOveModEncHeading;
			/** Relation for the model in overview. Connects {@link #bOveModSource} with {@link #bOveModEncoder} and is part of {@link #pOveModel}.*/
			private static Arrow aOveModRelation;
	/** Layout container for the options segment. Displays the different information sources.
	 * Contains {@link #lOptHeading} and {@link #vbOptButtons} and gets added to {@link #root}.*/
	private static Pane pOptions;
		/** Label which displays the sub-heading "Options". It's part of {@link #pOptions}.*/
		private static Label lOptHeading;
		/** Layout container for the buttons of options. Contains {@link #bOptButUserInput} and {@link #bOptButBook} 
		 * and is part of {@link #pOptions}.*/
		private static VBox vbOptButtons;
			/** The button showing the user input option under options. Contains {@link #hbOptButUserInput} and is part of {@link #vbOptButtons}.*/
			private static Button bOptButUserInput;
				/** Layout container for the content of user input button. 
				 * Contains {@link #lOptButUserInput} and is part of {@link #bOptButUserInput}.*/
				private static HBox hbOptButUserInput;
					/** Label displaying the button's heading "User Input". It's part of {@link #hbOptButUserInput}.*/
					private static Label lOptButUserInput;
			/** The button showing the random digit book option under options. Contains {@link #hbOptButBook} and is 
			 * part of {@link #vbOptButtons}.*/
			private static Button bOptButBook;
				/** Layout container for the content of random digit book button. 
				 * Contains {@link #lOptButBook} and is part of {@link #bOptButBook}.*/
				private static HBox hbOptButBook;
					/** Label displaying the button's heading "Random digit book". It's part of {@link #hbOptButBook}.*/
					private static Label lOptButBook;
	/** Layout container for the information segment. Displays the information of the picked information source in {@link #pOptions}.
	 * Contains {@link #lInfHeading} and {@link #pInfContent} and gets added to {@link #root}.*/
	private static Pane pInformation;
		/** Label which displays the sub-heading "Information" by default, but gets updated to fit the currently picked information source. 
		 * It's part of {@link #pInformation}.*/
		private static Label lInfHeading;
		/** Layout container for the information content elements. Contains {@link #lInfConDefault} by default,
		 * but gets updated by the currently picked information source. It's part of {@link #pInformation}.*/
		private static Pane pInfContent;
			/** Label which displays "No option picked" if {@link #selectedOption} == 0. It's part of {@link #pInfContent}.*/
			private static Label lInfConDefault;
			
			
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
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Main.distanceToSubheading);
				buildOveSourceButton("nothing selected");
				
				bOveModEncoder = new Button();
				bOveModEncoder.setBackground(Main.baNormalButton);
				bOveModEncoder.setBorder(Main.boNormal);
				bOveModEncoder.setMaxWidth(Main.stageWidth - Main.calcWidth(bOveModSource) - Main.stageWidth / 16);
					vbOveModEncHeading = new VBox();
						lOveModEncHeading = new Label();
						lOveModEncHeading.setText("Encoder");
						lOveModEncHeading.setTextFill(Color.WHITESMOKE);
						lOveModEncHeading.setFont(Main.fNormalText);
						lOveModEncHeading.setWrapText(true);
						lOveModEncHeading.setTextAlignment(TextAlignment.CENTER);
						lOveModEncHeading.setPrefHeight(Main.calcHeightLabel(lOveModEncHeading, bOveModEncoder.getMaxWidth()));
					vbOveModEncHeading.getChildren().add(lOveModEncHeading);
					vbOveModEncHeading.setAlignment(Pos.CENTER_LEFT);
				bOveModEncoder.setPrefHeight(Main.calcHeight(bOveModSource) + 1);
				bOveModEncoder.setGraphic(vbOveModEncHeading);
				bOveModEncoder.setLayoutX(Main.pos1 * 6 - Main.calcWidth(bOveModEncoder));
				
				buildOveArrow();
			pOveModel.getChildren().addAll(bOveModSource, aOveModRelation, bOveModEncoder);
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
				bOptButUserInput = new Button();
				bOptButUserInput.setPrefWidth(vbOptButtons.getPrefWidth() - 1);
				bOptButUserInput.setPrefHeight(50);
				bOptButUserInput.setBackground(Main.baNormalButton);
				bOptButUserInput.setBorder(Main.boNormal);
					hbOptButUserInput = new HBox();
						lOptButUserInput = new Label();
						lOptButUserInput.setText("User Input");
						lOptButUserInput.setTextFill(Color.WHITESMOKE);
						lOptButUserInput.setFont(Main.fNormalText);
						lOptButUserInput.setWrapText(false);
						lOptButUserInput.setTextAlignment(TextAlignment.CENTER);
					hbOptButUserInput.getChildren().add(lOptButUserInput);
					hbOptButUserInput.setAlignment(Pos.CENTER);
				bOptButUserInput.setGraphic(hbOptButUserInput);
				
				bOptButBook = new Button();
				bOptButBook.setPrefWidth(vbOptButtons.getPrefWidth() - 1);
				bOptButBook.setPrefHeight(50);
				bOptButBook.setBackground(Main.baNormalButton);
				bOptButBook.setBorder(Main.boNormal);
					hbOptButBook = new HBox();
						lOptButBook = new Label();
						lOptButBook.setText("Random digit book");
						lOptButBook.setTextFill(Color.WHITESMOKE);
						lOptButBook.setFont(Main.fNormalText);
						lOptButBook.setWrapText(false);
						lOptButBook.setTextAlignment(TextAlignment.CENTER);
					hbOptButBook.getChildren().add(lOptButBook);
					hbOptButBook.setAlignment(Pos.CENTER);
				bOptButBook.setGraphic(hbOptButBook);
			vbOptButtons.getChildren().addAll(bOptButUserInput, bOptButBook);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    	    
	    
	    pInformation = new Pane();
		pInformation.setLayoutX(Main.pos1 * 3);
		pInformation.setLayoutY(pOptions.getLayoutY());
		pInformation.setPrefWidth(Main.stageWidth / 2);
			lInfHeading = new Label();
			lInfHeading.setText("Information");
			lInfHeading.setTextFill(Color.WHITESMOKE);
			lInfHeading.setFont(Main.fSubheading);
			
			pInfContent = new Pane();
			pInfContent.setLayoutY(Main.distanceToSubheading);
			pInfContent.setPrefHeight(Main.stageHeight - pInformation.getLayoutY() - pInfContent.getLayoutY() - Main.pos1 / 3);
			pInfContent.setMinHeight(180 - pInfContent.getLayoutY());					// Minimum height -> would end with 3. button of pOptions
				lInfConDefault = new Label();
				lInfConDefault.setText("No option picked");
				lInfConDefault.setTextFill(Color.INDIANRED);
				lInfConDefault.setFont(Main.fNormallTextItalic);
			pInfContent.getChildren().add(lInfConDefault);
		pInformation.getChildren().addAll(lInfHeading, pInfContent);
		
		
		addListener();
		Main.updateScrollbar(pInformation);
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	
	
	/**
	 * Adds the listener to the Buttons of {@link SourcePage} and sets {@link Main#krlBackHome} as key released listener. 
	 * They individually change the background of the buttons depending on whether the mouse hovers over it or not 
	 * and define the action of the buttons when clicked.
	 * This could also be done in {@link #SourcePage(Group)} but for a better to look at program it's in a separate method.
	 */
	private void addListener() {
		Main.scene.setOnKeyReleased(Main.krlBackHome);
		
		bOptButUserInput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bOptButUserInput got pressed!");
				bOptButUserInput.setBackground(Main.baGreenFocusedButton);
				bOptButBook.setBackground(Main.baNormalButton);
				bOptButUserInput.setOnMouseEntered(Main.evButGreEntered);
				bOptButUserInput.setOnMouseExited(Main.evButGreExited);
				bOptButBook.setOnMouseEntered(Main.evButEntered);
				bOptButBook.setOnMouseExited(Main.evButExited);
				
				lInfHeading.setText("Information \\  User input");
				pOveModel.getChildren().removeAll(bOveModSource, aOveModRelation);
				buildOveSourceButton("user input");
				buildOveArrow();
				pOveModel.getChildren().addAll(bOveModSource, aOveModRelation);
				
				pInfContent.getChildren().clear();
				if (UserInput.getBuiltGuiDeprecated()) {
					Main.infSource_UserInput.reloadGui(pInfContent);
				} else {
					Main.infSource_UserInput.buildGui(pInfContent);
				}
				
				selectedOption = 1;
				Main.boUpdateSettingsModelHomepage = true;
	        }
	    });
		
		bOptButBook.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButBook got pressed!");
				bOptButUserInput.setBackground(Main.baNormalButton);
				bOptButBook.setBackground(Main.baGreenFocusedButton);
				bOptButUserInput.setOnMouseEntered(Main.evButEntered);
				bOptButUserInput.setOnMouseExited(Main.evButExited);
				bOptButBook.setOnMouseEntered(Main.evButGreEntered);
				bOptButBook.setOnMouseExited(Main.evButGreExited);
				
				lInfHeading.setText("Information \\  Random digit book");
				pOveModel.getChildren().removeAll(bOveModSource, aOveModRelation);
				buildOveSourceButton("random digit book");
				buildOveArrow();
				pOveModel.getChildren().addAll(bOveModSource, aOveModRelation);
				
				pInfContent.getChildren().clear();
				
				selectedOption = 2;
				Main.boUpdateSettingsModelHomepage = true;
	        }
	    });
		
		bOptButUserInput.setOnMouseEntered(Main.evButEntered);
		bOptButUserInput.setOnMouseExited(Main.evButExited);
		bOptButBook.setOnMouseEntered(Main.evButEntered);
		bOptButBook.setOnMouseExited(Main.evButExited);
	}
	
	
	/**
	 * Builds / instantiates the source button of {@link #pOveModel}. This is a separate method from {@link #SourcePage(Group)} in order 
	 * to be able to separately re-build the model if an information source gets chosen.
	 * This method gets called by the constructor and the buttons event handler from {@link #vbOptButtons} (see {@link #addListener()}). 
	 * @param selectedItem Defines the text to be displayed as selected information source item.
	 */
	private void buildOveSourceButton(String selectedItem) {
		bOveModSource = new Button();
		bOveModSource.setBackground(Main.baNormalButton);
		bOveModSource.setBorder(Main.boNormal);
		bOveModSource.setMaxWidth(Main.stageWidth / 2);
			hbOveModSouContent = new HBox();
			hbOveModSouContent.setSpacing(20);
			hbOveModSouContent.setPadding(new Insets(5));
				vbOveModSouConHeading = new VBox();
				vbOveModSouConHeading.setMaxWidth(Main.stageWidth / 2 / 3);
					lOveModSouConHeading = new Label();
					lOveModSouConHeading.setText("Information Source");
					lOveModSouConHeading.setTextFill(Color.WHITESMOKE);
					lOveModSouConHeading.setFont(Main.fNormalText);
					lOveModSouConHeading.setWrapText(true);
					lOveModSouConHeading.setTextAlignment(TextAlignment.CENTER);
					lOveModSouConHeading.setPrefHeight(Main.calcHeightLabel(lOveModSouConHeading, vbOveModSouConHeading.getMaxWidth()));
					lOveModSouConHeading.setPrefWidth(Main.calcWidthLabel(lOveModSouConHeading) + 20);
				vbOveModSouConHeading.getChildren().add(lOveModSouConHeading);
				vbOveModSouConHeading.setAlignment(Pos.CENTER_LEFT);
				
				liOveModSouConDiffer = new Line();
				liOveModSouConDiffer.setStroke(Color.WHITESMOKE);
				
				vbOveModSouSelectedItem = new VBox();
				vbOveModSouSelectedItem.setMaxWidth(bOveModSource.getMaxWidth() / 3 * 2 - 
						(hbOveModSouContent.getSpacing() + hbOveModSouContent.getPadding().getLeft() * 2));
					lConSelectedItemHead = new Label();
					lConSelectedItemHead.setText("selected item:");
					lConSelectedItemHead.setTextFill(Color.WHITESMOKE);
					lConSelectedItemHead.setFont(Main.fSmallText);
					lConSelectedItemHead.setWrapText(true);
					lConSelectedItemHead.setPrefHeight(Main.calcHeightLabel(lConSelectedItemHead, 
							Main.stageWidth / 2 - (hbOveModSouContent.getSpacing() + hbOveModSouContent.getPadding().getLeft() * 2)
							- vbOveModSouConHeading.getMaxWidth()));
					lConSelectedItemHead.setPrefWidth(Main.calcWidthLabel(lConSelectedItemHead) + 20);
					lConSelectedItemHead.setMaxWidth(Main.stageWidth / 2 - (hbOveModSouContent.getSpacing() + 
							hbOveModSouContent.getPadding().getLeft() * 2) - vbOveModSouConHeading.getMaxWidth());
					
					
					lConSelectedItem = new Label();
					lConSelectedItem.setText(selectedItem);
					lConSelectedItem.setTextFill(Color.INDIANRED);
					lConSelectedItem.setFont(Main.fSmallTextItalic);
					lConSelectedItem.setWrapText(true);
					lConSelectedItem.setPadding(new Insets(0, 0, 0, 10));
					lConSelectedItem.setPrefHeight(Main.calcHeightLabel(lConSelectedItem, 
							Main.stageWidth / 2 - (hbOveModSouContent.getSpacing() + hbOveModSouContent.getPadding().getLeft() * 2)
							- vbOveModSouConHeading.getMaxWidth()));
					lConSelectedItem.setPrefWidth(Main.calcWidthLabel(lConSelectedItem) + 20);
					lConSelectedItem.setMaxWidth(Main.stageWidth / 2 - (hbOveModSouContent.getSpacing() + 
							hbOveModSouContent.getPadding().getLeft() * 2) - vbOveModSouConHeading.getMaxWidth());
				vbOveModSouSelectedItem.getChildren().addAll(lConSelectedItemHead, lConSelectedItem);
				vbOveModSouSelectedItem.setAlignment(Pos.CENTER_LEFT);
				
				bOveModSource.setPrefWidth(bOveModSource.getMaxWidth() 
						- ((vbOveModSouConHeading.getMaxWidth() - Main.calcWidth(vbOveModSouConHeading))
						+ (vbOveModSouSelectedItem.getMaxWidth() - Main.calcWidth(vbOveModSouSelectedItem))));
			hbOveModSouContent.getChildren().addAll(vbOveModSouConHeading, liOveModSouConDiffer, vbOveModSouSelectedItem);	
			liOveModSouConDiffer.setEndY(Main.calcHeight(hbOveModSouContent) - 10);
		bOveModSource.setGraphic(hbOveModSouContent);
	}
	
	/**
	 * Builds / instantiates the arrow from {@link Arrow#getArrow(double, double, double, double, double, double, boolean, String)}
	 * for {@link #pOveModel}. This is a separate method from {@link #SourcePage(Group)} in order to be able to separately re-build the model
	 * if an information source gets chosen.
	 * This method gets called by the constructor and the buttons event handler from {@link #vbOptButtons} (see {@link #addListener()}). 
	 */
	private void buildOveArrow() {
		aOveModRelation = new Arrow();
		double yArrow = Main.calcHeight(bOveModSource) / 2;
		aOveModRelation = aOveModRelation.getArrow(Main.calcWidth(bOveModSource), yArrow, bOveModEncoder.getLayoutX(), yArrow, 
				20, 15, false, "message");
	}
	
	
	/**
	 * Reloads the source page. Re-attaches the page's elements ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation})
	 * and {@link Main#krlBackHome}. In addition, {@link Main#updateScrollbar(Region)} gets called 
	 * (see {@link #SourcePage(Group)} for more information relating to it's view-cases).
	 * This method gets called by the {@link Homepage homepage}, when the page is already not null and {@link Homepage#bSetModSource} gets pressed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	void reload(Group parent) {
		root = parent;
		Main.updateScrollbar(pInformation);
		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
		Main.scene.setOnKeyReleased(Main.krlBackHome);		
	}
}
