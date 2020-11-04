package environment.pages;

import java.util.Arrays;

import environment.Main;
import environment.Run;
import environment.pages.guiElements.Arrow;
import environment.pages.guiElements.ModelFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 * The home page of the application with access to every part of the program.
 * Links with {@link #bSetModSource} up to the {@link InfSourcePage information source page}, 
 * with {@link #bSetModEncoder} and {@link #bSetModDecoder} up to the {@link EnDecoderPage en- / decoder page}, 
 * with {@link #bSetModNoise} up to the {@link NoiSourcePage noise source page} and coming soon
 * with {@link #bSetModDestination} to a TODO page.
 * See {@link #Homepage(Group)} for more information about the GUI.
 * @author Wolkenfarmer
 */
public class Homepage {
	/** Layout container representing the given root from {@link environment.Main} to attach the GUI-elements to.
	 * It's content ({@link #hbHeading}, {@link #pSettings}, {@link #pControls}, {@link #pResults}) gets build in {@link #Homepage(Group)}.
	 * When loading another page it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reload(Group, boolean)} will be used to re-attach the content to the root.*/
	private static Group root;
	/** Layout container for the headline segment. 
	 * Contains {@link #lHeadline}, {@link #rHeadlineSpacer} and {@link #vbHeadline} and gets added to {@link #root}.*/
	private static HBox hbHeading;
		/** Label which displays the headline "Coding Environment". It's part of {@link #hbHeading}.*/
		private static Label lHeadline;
		/** A spacer for the right-hand-side-layout of {@link #vbHeadline}. It's part of {@link #hbHeading}.*/
		private static Region rHeadlineSpacer;
		/** Layout container for the headline elements on the right side. 
		 * Contains {@link #lHeadlineVersion} and {@link #lHeadlineVersion} and is part of {@link #hbHeading}.*/
		private static VBox vbHeadline;
			/** Label which displays the current version of this program. It's part of {@link #vbHeadline} and this again of {@link #hbHeading}.*/
			private static Label lHeadlineVersion;
			/** Label which displays developer of this program. It's part of {@link #vbHeadline} and this again of {@link #hbHeading}.*/
			private static Label lHeadlineBy;
	/** Layout container for the settings segment. Contains {@link #lSetHeading} and {@link #pSetModel} and gets added to {@link #root}.*/
	private static Pane pSettings;
		/** Label which displays the sub-heading "Settings". It's part of {@link #pSettings}.*/
		private static Label lSetHeading;
		/** Layout container for the elements of the model in {@link #pSettings}. 
		 * Contains {@link #bSetModSource}, {@link #bSetModEncoder}, {@link #bSetModNoise}, {@link #bSetModDecoder} and {@link #bSetModDestination} */
		private static Pane pSetModel;
			/** Information source button of the model in settings. Uses {@link environment.Main#baNormalButton} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link InfSourcePage}.*/
			static Button bSetModSource;
			/** Encoder button of the model in settings. Uses {@link environment.Main#baNormalButton} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link EnDecoderPage}*/
			static Button bSetModEncoder;
			/** Noise source button of the model in settings. Uses {@link environment.Main#baNormalButton} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link NoiSourcePage}*/
			static Button bSetModNoise;
			/** Decoder button of the model in settings. Uses {@link environment.Main#baNormalButton} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link EnDecoderPage}*/
			static Button bSetModDecoder;
			/** Destination button of the model in settings. Uses {@link environment.Main#baNormalButton} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			private static Button bSetModDestination;
			/** Relation for the model in settings. Connects {@link #bSetModSource} with {@link #bSetModEncoder}.
			 * @see Arrow*/
			private static Group gSetModRelSoToEn;
			/** Relation for the model in settings. Connects {@link #bSetModEncoder} with {@link #bSetModDecoder}.
			 * @see Arrow*/
			private static Group gSetModRelEnToDe;
			/** Relation for the model in settings. Connects {@link #bSetModDecoder} with {@link #bSetModDestination}.
			 * @see Arrow*/
			private static Group gSetModRelDeToDe;
			/** Relation for the model in settings. Connects {@link #bSetModNoise} with {@link #gSetModRelEnToDe}.
			 * @see Arrow*/
			private static Group gSetModRelNoToCh;
	/** Layout container for the results segment. Contains {@link #lResHeading} and {@link #tvResTable} and gets added to {@link #root}.*/
	private static Pane pResults;
		/** Label which displays the sub-heading "Last Results". It's part of {@link #pResults}.*/
		private static Label lResHeading;
		/** The table displaying the last result below {@link #lResHeading}. 
		 * It gets the result from {@link environment.Result#updateResult()}.
		 * Contains {@link #tvResTabDescription} and {@link #tvResTabValue} and is part of {@link #pResults}.
		 * @see environment.pages.css */
		private static TableView<String[]> tvResTable;
			/** The first column of {@link #tvResTable} displaying the descriptions of the values.*/
			private static TableColumn<String[], String> tvResTabDescription;
			/** The first column of {@link #tvResTable} displaying the values to the descriptions.*/
			private static TableColumn<String[], String> tvResTabValue;
	/** Layout container for the controls segment. Contains {@link #lConHeading} and {@link #vbConButtons} and gets added to {@link #root}.*/
	private static Pane pControls;
		/** Label which displays the sub-heading "Controls". It's part of {@link #pControls}.*/
		private static Label lConHeading;
		/** Layout container for the buttons below {@link #lConHeading}. 
		 * Contains {@link #bConButRun}, {@link #bConButSaveResult} and {@link #bConButHelp} and is part of {@link #pControls}.*/
		private static VBox vbConButtons;
			/** The run button of the controls segment. Uses {@link environment.Main#baGreenButton} as background.
			 * Contains {@link #hbConButRun} and is part of {@link #vbConButtons}. 
			 * It calls {@link environment.Run#run(environment.ExperimentElement, environment.ExperimentElement, environment.ExperimentElement, 
			 * environment.ExperimentElement) run} with {@link environment.Main#selectedInfSource}, {@link environment.Main#selectedEnDecoder}, 
			 * {@link environment.Main#selectedPrePost} and {@link environment.Main#selectedNoiSource}.*/
			private static Button bConButRun;
				/** Layout container for the buttons description. This is needed in order to align the heading the center of the button. 
				 * Contains {@link #lConButRun} and is part of {@link #bConButRun}.*/
				private static HBox hbConButRun;
					/** Label which displays {@link #bConButRun}'s description "Run". It's part of {@link #hbConButRun}.*/
					private static Label lConButRun;
			/** The save last results button of the controls segment. Uses {@link environment.Main#baBrownButton} as background.
			 * Contains {@link #hbConButSaveResult} and is part of {@link #vbConButtons}. It's functionality is currently a TODO and 
			 * therefore the button is invisible.*/
			private static Button bConButSaveResult;
				/** Layout container for the buttons description. This is needed in order to align the heading the center of the button. 
				 * Contains {@link #lConButSaveResult} and is part of {@link #bConButSaveResult}.*/
				private static HBox hbConButSaveResult;
					/** Label which displays {@link #bConButSaveResult}'s description "Save last result". It's part of {@link #hbConButSaveResult}.*/
					private static Label lConButSaveResult;
			/** The help button of the controls segment. Uses {@link environment.Main#baPurpleButton} as background.
			 * Contains {@link #hbConButHelp} and is part of {@link #vbConButtons}. It's functionality is currently a TODO and 
			 * therefore the button is invisible.*/
			private static Button bConButHelp;
				/** Layout container for the buttons description. This is needed in order to align the heading the center of the button. 
				 * Contains {@link #lConButHelp} and is part of {@link #bConButHelp}.*/
				private static HBox hbConButHelp;
					/** Label which displays {@link #bConButHelp}'s description "Help". It's part of {@link #hbConButHelp}.*/
					private static Label lConButHelp;
	
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	private static EventHandler<ActionEvent> evEnDecoderPressed;
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	private static EventHandler<MouseEvent> evEnDecoderEntered;
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	private static EventHandler<MouseEvent> evEnDecoderExited;
	
	/** Reference to the model factory for building the {@link ModelFactory#buildButton(float, float, byte) buttons} 
	 * and {@link ModelFactory#buildRelation(float, float, short, boolean, String) relations} in {@link #pSetModel}.*/
	private static ModelFactory cSetModFactory;
	
	
	/**
	 * Builds the home page of the application.
	 * This constructor uses {@link ModelFactory#buildButton(float, float, byte)} for the buttons 
	 * and {@link ModelFactory#buildRelation(float, float, short, boolean, String)} for the relations in {@link #pSetModel}.
	 * The home page gets scaled accordingly to {@link Main#stageHeight} and {@link Main#stageWidth}.
	 * Normally, the height of {@link #pResults} gets calculated in order to not exceed the screen's size, 
	 * but if the screen is too small to even fit {@link #pControls} on it, the controls height will be the minimum height of results 
	 * and {@link Main#scrollbar scroll bar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */
	public Homepage(Group parent) {
		root = parent;
		
		hbHeading = new HBox();
		hbHeading.setLayoutX(Main.pos1);
		hbHeading.setLayoutY(Main.pos1 / 3);
		hbHeading.setPrefWidth(Main.contentWidth);
			lHeadline = new Label();
			lHeadline.setText("Coding Environment");
			lHeadline.setTextFill(Main.cNormal);
			lHeadline.setFont(Main.fHeadline);
			lHeadline.setAlignment(Pos.CENTER_LEFT);
			
			rHeadlineSpacer = new Region();
			HBox.setHgrow(rHeadlineSpacer, Priority.ALWAYS);
			
			vbHeadline = new VBox();
			vbHeadline.setAlignment(Pos.CENTER_RIGHT);
				lHeadlineVersion = new Label();
				lHeadlineVersion.setText("Version 0.9");
				lHeadlineVersion.setTextFill(Main.cNormal);
				lHeadlineVersion.setFont(Main.fNormalText);
				lHeadlineVersion.setAlignment(Pos.TOP_RIGHT);
				
				lHeadlineBy = new Label();
				lHeadlineBy.setText("By Wolkenfarmer");
				lHeadlineBy.setTextFill(Main.cNormal);
				lHeadlineBy.setFont(Main.fSmallTextItalic);
				lHeadlineBy.setAlignment(Pos.BOTTOM_RIGHT);
			vbHeadline.getChildren().addAll(lHeadlineVersion, lHeadlineBy);
		hbHeading.getChildren().addAll(lHeadline, rHeadlineSpacer, vbHeadline);
		
		
		pSettings = new Pane();
		pSettings.setLayoutX(Main.pos1);
		pSettings.setLayoutY(hbHeading.getLayoutY() + Main.distanceToHeading);
		pSettings.setPrefWidth(Main.contentWidth);
			lSetHeading = new Label();
			lSetHeading.setText("Settings");
			lSetHeading.setTextFill(Main.cNormal);
			lSetHeading.setFont(Main.fSubheading);
			
			pSetModel = new Pane();
			pSetModel.setLayoutY(Main.distanceToSubheading);
				cSetModFactory = new ModelFactory(Main.contentWidth); 
				bSetModSource = cSetModFactory.buildButton(0, 0, (byte) 0);
				bSetModEncoder = cSetModFactory.buildButton(3, 0, (byte) 1);
				bSetModNoise = cSetModFactory.buildButton(5.5f, 2, (byte) 2);
				bSetModDecoder = cSetModFactory.buildButton(8, 0, (byte) 3);
				bSetModDestination = cSetModFactory.buildButton(11, 0, (byte) 4);
				
				gSetModRelSoToEn = cSetModFactory.buildRelation(2, 1, ((short) 1), false, "message");
				gSetModRelEnToDe = cSetModFactory.buildRelation(5, 1, ((short) 3), false, "signal / channel");
				gSetModRelDeToDe = cSetModFactory.buildRelation(10, 1, ((short) 1), false, "message");
				gSetModRelNoToCh = cSetModFactory.buildRelation(6.5f, 2, ((short) 1), true, "");
			pSetModel.getChildren().addAll(bSetModSource, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination, 
					gSetModRelSoToEn, gSetModRelEnToDe, gSetModRelDeToDe, gSetModRelNoToCh);
		pSettings.getChildren().addAll(lSetHeading, pSetModel);
		
		
		pResults = new Pane();
		pResults.setLayoutX(Main.pos1);
		pResults.setLayoutY(pSettings.getLayoutY() + Main.calcHeight(pSettings) + Main.distanceToSegment);
		pResults.setPrefWidth(Main.stageWidth / 2);
			lResHeading = new Label();
			lResHeading.setText("Last Results");
			lResHeading.setTextFill(Main.cNormal);
			lResHeading.setFont(Main.fSubheading);
			pResults.getChildren().add(lResHeading);
			
			
			tvResTable = new TableView<String[]>();
			tvResTable.setLayoutY(Main.distanceToSubheading);
			tvResTable.setPrefHeight(Main.stageHeight - pResults.getLayoutY() - tvResTable.getLayoutY() - Main.pos1 / 3);
			tvResTable.setMinHeight(250 - tvResTable.getLayoutY());					// Minimum height -> ends with pControls (assuming 3 buttons)
			tvResTable.setPrefWidth(Main.stageWidth / 2);
			    tvResTabDescription = new TableColumn<>("Description");
			    tvResTabDescription.setResizable(true);
			    tvResTabDescription.setSortable(false);
			    tvResTabDescription.setMinWidth(150);
			    tvResTabDescription.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
			        public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
			            String[] x = p.getValue();
			            if (x != null && x.length>0) {
			                return new SimpleStringProperty(x[0]);
			            } else {
			                return new SimpleStringProperty("<no name>");
			            }
			        }
			    });
			    
			    tvResTabValue = new TableColumn<>("Value");
			    tvResTabValue.setResizable(true);
			    tvResTabValue.setSortable(false);
			    tvResTabDescription.setMinWidth(200);
			    tvResTabValue.prefWidthProperty().bind(tvResTable.widthProperty().subtract(tvResTabDescription.getMinWidth() + 20));
			    tvResTabValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
			        public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
			            String[] x = p.getValue();
			            if (x != null && x.length>1) {
			                return new SimpleStringProperty(x[1]);
			            } else {
			                return new SimpleStringProperty("<no value>");
			            }
			        }
			    });
			    
			    String[][] mockdata = new String[1][2];
				for (int i = 0; i < mockdata.length; i++) {
					mockdata[i][0] = "Aspect...";
					mockdata[i][1] = "Value...";
				}
		    tvResTable.getColumns().add(tvResTabDescription);
		    tvResTable.getColumns().add(tvResTabValue);
		    tvResTable.getItems().addAll(Arrays.asList(mockdata));
		pResults.getChildren().add(tvResTable);
		
		
		pControls = new Pane();
		pControls.setLayoutX(Main.stageWidth / 8 * 5.5);
		pControls.setLayoutY(pResults.getLayoutY());
		pControls.setPrefWidth(Main.stageWidth / 8 * 1.5);
			lConHeading = new Label();
			lConHeading.setText("Controls");
			lConHeading.setTextFill(Main.cNormal);
			lConHeading.setFont(Main.fSubheading);			
			
			vbConButtons = new VBox();
			vbConButtons.setPrefWidth(pControls.getPrefWidth());
			vbConButtons.setLayoutY(Main.distanceToSubheading);
			vbConButtons.setSpacing(20);
				bConButRun = new Button();
				bConButRun.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButRun.setPrefHeight(50);
				bConButRun.setBackground(Main.baGreenButton);
				bConButRun.setBorder(Main.boNormal);
					hbConButRun = new HBox();
						lConButRun = new Label();
						lConButRun.setText("Run");
						lConButRun.setTextFill(Main.cNormal);
						lConButRun.setFont(Main.fNormalText);
						lConButRun.setWrapText(false);
						lConButRun.setTextAlignment(TextAlignment.CENTER);
					hbConButRun.getChildren().add(lConButRun);
					hbConButRun.setAlignment(Pos.CENTER);
				bConButRun.setGraphic(hbConButRun);
				
				bConButSaveResult = new Button();
				bConButSaveResult.setVisible(false);
				bConButSaveResult.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButSaveResult.setPrefHeight(50);
				bConButSaveResult.setBackground(Main.baBrownButton);
				bConButSaveResult.setBorder(Main.boNormal);
					hbConButSaveResult = new HBox();
						lConButSaveResult = new Label();
						lConButSaveResult.setText("Save last result");
						lConButSaveResult.setTextFill(Main.cNormal);
						lConButSaveResult.setFont(Main.fNormalText);
						lConButSaveResult.setWrapText(false);
						lConButSaveResult.setTextAlignment(TextAlignment.CENTER);
					hbConButSaveResult.getChildren().add(lConButSaveResult);
					hbConButSaveResult.setAlignment(Pos.CENTER);
				bConButSaveResult.setGraphic(hbConButSaveResult);
				
				bConButHelp = new Button();
				bConButHelp.setVisible(false);
				bConButHelp.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButHelp.setPrefHeight(50);
				bConButHelp.setBackground(Main.baPurpleButton);
				bConButHelp.setBorder(Main.boNormal);
					hbConButHelp = new HBox();
						lConButHelp = new Label();
						lConButHelp.setText("Help");
						lConButHelp.setTextFill(Main.cNormal);
						lConButHelp.setFont(Main.fNormalText);
						lConButHelp.setWrapText(false);
						lConButHelp.setTextAlignment(TextAlignment.CENTER);
					hbConButHelp.getChildren().add(lConButHelp);
					hbConButHelp.setAlignment(Pos.CENTER);
				bConButHelp.setGraphic(hbConButHelp);
			vbConButtons.getChildren().addAll(bConButRun, bConButSaveResult, bConButHelp);
	    pControls.getChildren().addAll(lConHeading, vbConButtons);
        
        
        addSettingsListener();
        addControlsListener();
        Main.updateScrollbar(pControls);
        root.getChildren().addAll(hbHeading, pSettings, pResults, pControls);
	}
	
	
	/**
	 * Adds the listener to the buttons of {@link #pControls}. 
	 * They individually change the background of the buttons depending on whether the mouse hovers over it or not 
	 * and define the action of the buttons when clicked.
	 * This could also be done in {@link #Homepage(Group)} but for a better to look at program it's in a separate method.
	 */
	private void addControlsListener() {
		bConButRun.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButRun got pressed!");
				Run.run(Main.selectedInfSource, Main.selectedPrePost, Main.selectedEnDecoder, Main.selectedNoiSource);
	        }
	    });
		bConButRun.setOnMouseEntered(Main.evButGreEntered);
		bConButRun.setOnMouseExited(Main.evButGreExited);
		
		bConButSaveResult.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButSaveResults got pressed!");
	        }
	    });
		bConButSaveResult.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButSaveResult.setBackground(Main.baBrownFocusedButton);
			}
	    });
		bConButSaveResult.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButSaveResult.setBackground(Main.baBrownButton);
			}
		});
		
		bConButHelp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButHelp got pressed!");
	        }
	    });
		bConButHelp.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButHelp.setBackground(Main.baPurpleFocusedButton);
			}
	    });
		bConButHelp.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButHelp.setBackground(Main.baPurpleButton);
			}
		});
	}
	
	
	/**
	 * Adds the listener to the buttons of {@link #pSettings}. 
	 * They individually change the background of the buttons depending on whether the mouse hovers over it or not 
	 * and define the action of the buttons when clicked.
	 * In order to be able to rebuild the settings section individually if an update occurred (see {@link Main#boUpdateSettingsModelHomepage}),
	 * this method is separate from {@link #addControlsListener()}.
	 */
	private void addSettingsListener() {
		bSetModSource.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModSource got pressed!");
				root.getChildren().clear();
				if (Main.infSourcePage == null) {
					Main.infSourcePage = new InfSourcePage(root);
				} else {
					Main.infSourcePage.reload(root);
				}
	        }
	    });
		
		bSetModEncoder.setOnAction(evEnDecoderPressed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModEncoder or bSetModDecoder got pressed!");
				root.getChildren().clear();
				if (Main.enDecoderPage == null) {
					Main.enDecoderPage = new EnDecoderPage(root);
				} else {
					Main.enDecoderPage.reload(root);
				}
	        }
	    });
		
		bSetModNoise.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModNoise got pressed!");
				root.getChildren().clear();
				if (Main.noiSourcePage == null) {
					Main.noiSourcePage = new NoiSourcePage(root);
				} else {
					Main.noiSourcePage.reload(root);
				}
	        }
	    });
		
		bSetModDestination.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModDestination got pressed!");
	        }
	    });
		
		bSetModEncoder.setOnMouseEntered(evEnDecoderEntered = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModEncoder.setBackground(Main.baNormalFocusedButton);
				bSetModDecoder.setBackground(Main.baNormalFocusedButton);
			}
		});
		bSetModEncoder.setOnMouseExited(evEnDecoderExited = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModEncoder.setBackground(Main.baNormalButton);
				bSetModDecoder.setBackground(Main.baNormalButton);
			}
		});
		
		bSetModSource.setOnMouseEntered(Main.evButEntered);
		bSetModSource.setOnMouseExited(Main.evButExited);
		bSetModNoise.setOnMouseEntered(Main.evButEntered);
		bSetModNoise.setOnMouseExited(Main.evButExited);
//		bSetModDestination.setOnMouseEntered(Main.evButEntered);
//		bSetModDestination.setOnMouseExited(Main.evButExited);
		
		bSetModDecoder.setOnAction(evEnDecoderPressed);
		bSetModDecoder.addEventHandler(MouseEvent.MOUSE_ENTERED, evEnDecoderEntered);
		bSetModDecoder.addEventHandler(MouseEvent.MOUSE_EXITED, evEnDecoderExited);
	}
	
	
	/**
	 * Reloads the home page. Re-attaches the page's elements ({@link #hbHeading}, {@link #pSettings}, {@link #pResults}, {@link #pControls})
	 * and {@link Main#krlClose}.
	 * Rebuilds the buttons of {@link #pSetModel} if changes on the communication experiment setup were made.
	 * In addition, {@link Main#updateScrollbar(Region)} gets called 
	 * (see {@link #Homepage(Group)} for more information relating to it's view-cases).
	 * This method gets called by the sub-pages when they get closed.
	 * @param parent Layout container to attach it's layout parts to.
	 * @param updateSettingsModel Defines whether changes on the communication experiment setup were made and the buttons have to be revuild or not.
	 */
	public void reload(Group parent, boolean updateSettingsModel) {
		root = parent;
		
		if (updateSettingsModel) {
			pSettings.getChildren().remove(pSetModel);
			pSetModel.getChildren().removeAll(bSetModSource, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination);
			
			bSetModSource = cSetModFactory.buildButton(0, 0, (byte) 0);
			bSetModEncoder = cSetModFactory.buildButton(3, 0, (byte) 1);
			bSetModNoise = cSetModFactory.buildButton(5.5f, 2, (byte) 2);
			bSetModDecoder = cSetModFactory.buildButton(8, 0, (byte) 3);
			bSetModDestination = cSetModFactory.buildButton(11, 0, (byte) 4);
			
			addSettingsListener();
			pSetModel.getChildren().addAll(bSetModSource, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination);
			pSettings.getChildren().add(pSetModel);
			
			pResults.setLayoutY(pSettings.getLayoutY() + Main.calcHeight(pSettings) + Main.distanceToSegment);
			tvResTable.setPrefHeight(Main.stageHeight - pResults.getLayoutY() - tvResTable.getLayoutY() - Main.pos1 / 3);
			pControls.setLayoutY(pResults.getLayoutY());
		}
		
		Main.updateScrollbar(pControls);
		root.getChildren().addAll(hbHeading, pSettings, pResults, pControls);
		Main.scene.setOnKeyReleased(Main.krlClose);
	}
	
	
	/**
	 * Updates {@link #tvResTable the result table}. It gets called by {@link environment.Result#updateResult()}.
	 * @param results The new content to e displayed by the table.
	 */
	public void updateResultTable(ObservableList<String[]> results) {
		tvResTable.getItems().setAll(results);
	}
}
