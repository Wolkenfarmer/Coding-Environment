package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.Run;
import de.wolkenfarmer.environment.pages.gui_elements.Arrow;
import de.wolkenfarmer.environment.pages.gui_elements.ModelFactory;

import java.util.Arrays;

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
 * Links with {@link #bSetModInput} up to the {@link InputHandler input handler page}, 
 * with {@link #bSetModEncoder} and {@link #bSetModDecoder} up to the {@link Transcoder transcoder page}, 
 * with {@link #bSetModNoise} up to the {@link NoiseSource noise source page} and coming soon
 * with {@link #bSetModDestination} to a TODO page.
 * See {@link #Home(Group)} for more information about the GUI.
 * @author Wolkenfarmer
 */
public class Home {
	/** Layout container representing the given root from {@link de.wolkenfarmer.environment.Main} to attach the GUI-elements to.
	 * It's content ({@link #hbHeading}, {@link #pSettings}, {@link #pControls}, {@link #pResults}) gets build in {@link #Home(Group)}.
	 * When loading another page its content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reload(Group, boolean)} will be used to reattach  the content to the root.*/
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
		/** Label which displays the subheading "Settings". It's part of {@link #pSettings}.*/
		private static Label lSetHeading;
		/** Layout container for the elements of the model in {@link #pSettings}. 
		 * Contains {@link #bSetModInput}, {@link #bSetModEncoder}, {@link #bSetModNoise}, {@link #bSetModDecoder} 
		 * and {@link #bSetModDestination} */
		private static Pane pSetModel;
			/** Input handler button of the model in settings. Uses {@link de.wolkenfarmer.environment.Main#BG_GRAY} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link InputHandler}.*/
			static Button bSetModInput;
			/** Encoder button of the model in settings. Uses {@link de.wolkenfarmer.environment.Main#BG_GRAY} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link Transcoder}*/
			static Button bSetModEncoder;
			/** Noise source button of the model in settings. Uses {@link de.wolkenfarmer.environment.Main#BG_GRAY} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link NoiseSource}*/
			static Button bSetModNoise;
			/** Decoder button of the model in settings. Uses {@link de.wolkenfarmer.environment.Main#BG_GRAY} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to {@link Transcoder}*/
			static Button bSetModDecoder;
			/** Destination button of the model in settings. Uses {@link de.wolkenfarmer.environment.Main#BG_GRAY} as background.
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			private static Button bSetModDestination;
			/** Relation for the model in settings. Connects {@link #bSetModInput} with {@link #bSetModEncoder}.
			 * @see Arrow*/
			private static Group gSetModRelInToEn;
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
		/** Label which displays the subheading "Last Results". It's part of {@link #pResults}.*/
		private static Label lResHeading;
		/** The table displaying the last result below {@link #lResHeading}. 
		 * It gets the result from {@link de.wolkenfarmer.environment.Result#updateResult()}.
		 * Contains {@link #tvResTabDescription} and {@link #tvResTabValue} and is part of {@link #pResults}.
		 * @see de.wolkenfarmer.environment.pages.css */
		private static TableView<String[]> tvResTable;
			/** The first column of {@link #tvResTable} displaying the descriptions of the values.*/
			private static TableColumn<String[], String> tvResTabDescription;
			/** The first column of {@link #tvResTable} displaying the values to the descriptions.*/
			private static TableColumn<String[], String> tvResTabValue;
	/** Layout container for the controls segment. Contains {@link #lConHeading} and {@link #vbConButtons} and gets added to {@link #root}.*/
	private static Pane pControls;
		/** Label which displays the subheading "Controls". It's part of {@link #pControls}.*/
		private static Label lConHeading;
		/** Layout container for the buttons below {@link #lConHeading}. 
		 * Contains {@link #bConButRun}, {@link #bConButSaveResult} and {@link #bConButHelp} and is part of {@link #pControls}.*/
		private static VBox vbConButtons;
			/** The run button of the controls segment. Uses {@link de.wolkenfarmer.environment.Main#BG_GREEN} as background.
			 * Contains {@link #hbConButRun} and is part of {@link #vbConButtons}. 
			 * It calls 
			 * {@link de.wolkenfarmer.environment.Run#run(de.wolkenfarmer.environment.ExperimentElement, 
			 * de.wolkenfarmer.environment.ExperimentElement, de.wolkenfarmer.environment.ExperimentElement, 
			 * de.wolkenfarmer.environment.ExperimentElement) run} with {@link de.wolkenfarmer.environment.Main#selectedInputHandler}, 
			 * {@link de.wolkenfarmer.environment.Main#selectedTranscoder}, 
			 * {@link de.wolkenfarmer.environment.Main#selectedPrePost} and {@link de.wolkenfarmer.environment.Main#selectedNoiSource}.*/
			private static Button bConButRun;
				/** Layout container for the buttons description. This is needed in order to align the heading the center of the button. 
				 * Contains {@link #lConButRun} and is part of {@link #bConButRun}.*/
				private static HBox hbConButRun;
					/** Label which displays {@link #bConButRun}'s description "Run". It's part of {@link #hbConButRun}.*/
					private static Label lConButRun;
			/** The save last results button of the controls segment. Uses {@link de.wolkenfarmer.environment.Main#BG_BROWN} as background.
			 * Contains {@link #hbConButSaveResult} and is part of {@link #vbConButtons}. It's functionality is currently a TODO and 
			 * therefore the button is invisible.*/
			private static Button bConButSaveResult;
				/** Layout container for the buttons description. This is needed in order to align the heading the center of the button. 
				 * Contains {@link #lConButSaveResult} and is part of {@link #bConButSaveResult}.*/
				private static HBox hbConButSaveResult;
					/** Label which displays {@link #bConButSaveResult}'s description "Save last result". 
					 * It's part of {@link #hbConButSaveResult}.*/
					private static Label lConButSaveResult;
			/** The help button of the controls segment. Uses {@link de.wolkenfarmer.environment.Main#BG_PURPLE} as background.
			 * Contains {@link #hbConButHelp} and is part of {@link #vbConButtons}. It's functionality is currently a TODO and 
			 * therefore the button is invisible.*/
			private static Button bConButHelp;
				/** Layout container for the buttons description. This is needed in order to align the heading the center of the button. 
				 * Contains {@link #lConButHelp} and is part of {@link #bConButHelp}.*/
				private static HBox hbConButHelp;
					/** Label which displays {@link #bConButHelp}'s description "Help". It's part of {@link #hbConButHelp}.*/
					private static Label lConButHelp;
	
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	private static EventHandler<ActionEvent> evTranscoderPressed;
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	private static EventHandler<MouseEvent> evTranscoderEntered;
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	private static EventHandler<MouseEvent> evTranscoderExited;
	
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
	public Home(Group parent) {
		root = parent;
		
		hbHeading = new HBox();
		hbHeading.setLayoutX(Main.pos1);
		hbHeading.setLayoutY(Main.pos1 / 3);
		hbHeading.setPrefWidth(Main.contentWidth);
			lHeadline = new Label();
			lHeadline.setText("Coding Environment");
			lHeadline.setTextFill(Constants.C_NORMAL);
			lHeadline.setFont(Constants.F_HEADING_BOLD);
			lHeadline.setAlignment(Pos.CENTER_LEFT);
			
			rHeadlineSpacer = new Region();
			HBox.setHgrow(rHeadlineSpacer, Priority.ALWAYS);
			
			vbHeadline = new VBox();
			vbHeadline.setAlignment(Pos.CENTER_RIGHT);
				lHeadlineVersion = new Label();
				lHeadlineVersion.setText("Version 0.2");
				lHeadlineVersion.setTextFill(Constants.C_NORMAL);
				lHeadlineVersion.setFont(Constants.F_NORMAL);
				lHeadlineVersion.setAlignment(Pos.TOP_RIGHT);
				
				lHeadlineBy = new Label();
				lHeadlineBy.setText("By Wolkenfarmer");
				lHeadlineBy.setTextFill(Constants.C_NORMAL);
				lHeadlineBy.setFont(Constants.F_SMALL_ITALIC);
				lHeadlineBy.setAlignment(Pos.BOTTOM_RIGHT);
			vbHeadline.getChildren().addAll(lHeadlineVersion, lHeadlineBy);
		hbHeading.getChildren().addAll(lHeadline, rHeadlineSpacer, vbHeadline);
		
		
		pSettings = new Pane();
		pSettings.setLayoutX(Main.pos1);
		pSettings.setLayoutY(hbHeading.getLayoutY() + Constants.I_DISTANCE_HEADING);
		pSettings.setPrefWidth(Main.contentWidth);
			lSetHeading = new Label();
			lSetHeading.setText("Settings");
			lSetHeading.setTextFill(Constants.C_NORMAL);
			lSetHeading.setFont(Constants.F_SUBHEADING);
			
			pSetModel = new Pane();
			pSetModel.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
				cSetModFactory = new ModelFactory(Main.contentWidth); 
				bSetModInput = cSetModFactory.buildButton(0, 0, (byte) 0);
				bSetModEncoder = cSetModFactory.buildButton(3, 0, (byte) 1);
				bSetModNoise = cSetModFactory.buildButton(5.5f, 2, (byte) 2);
				bSetModDecoder = cSetModFactory.buildButton(8, 0, (byte) 3);
				bSetModDestination = cSetModFactory.buildButton(11, 0, (byte) 4);
				
				gSetModRelInToEn = cSetModFactory.buildRelation(2, 1, ((short) 1), false, "message");
				gSetModRelEnToDe = cSetModFactory.buildRelation(5, 1, ((short) 3), false, "signal / channel");
				gSetModRelDeToDe = cSetModFactory.buildRelation(10, 1, ((short) 1), false, "message");
				gSetModRelNoToCh = cSetModFactory.buildRelation(6.5f, 2, ((short) 1), true, "");
			pSetModel.getChildren().addAll(bSetModInput, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination, 
					gSetModRelInToEn, gSetModRelEnToDe, gSetModRelDeToDe, gSetModRelNoToCh);
		pSettings.getChildren().addAll(lSetHeading, pSetModel);
		
		
		pResults = new Pane();
		pResults.setLayoutX(Main.pos1);
		pResults.setLayoutY(pSettings.getLayoutY() + Main.calcHeight(pSettings) + Constants.I_DISTANCE_SEGMENT);
		pResults.setPrefWidth(Main.stageWidth / 2);
			lResHeading = new Label();
			lResHeading.setText("Last Results");
			lResHeading.setTextFill(Constants.C_NORMAL);
			lResHeading.setFont(Constants.F_SUBHEADING);
			pResults.getChildren().add(lResHeading);
			
			
			tvResTable = new TableView<String[]>();
			tvResTable.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
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
			lConHeading.setTextFill(Constants.C_NORMAL);
			lConHeading.setFont(Constants.F_SUBHEADING);			
			
			vbConButtons = new VBox();
			vbConButtons.setPrefWidth(pControls.getPrefWidth());
			vbConButtons.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
			vbConButtons.setSpacing(20);
				bConButRun = new Button();
				bConButRun.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButRun.setPrefHeight(50);
				bConButRun.setBackground(Constants.BG_GREEN);
				bConButRun.setBorder(Constants.B_NORMAL);
					hbConButRun = new HBox();
						lConButRun = new Label();
						lConButRun.setText("Run");
						lConButRun.setTextFill(Constants.C_NORMAL);
						lConButRun.setFont(Constants.F_NORMAL);
						lConButRun.setWrapText(false);
						lConButRun.setTextAlignment(TextAlignment.CENTER);
					hbConButRun.getChildren().add(lConButRun);
					hbConButRun.setAlignment(Pos.CENTER);
				bConButRun.setGraphic(hbConButRun);
				
				bConButSaveResult = new Button();
				bConButSaveResult.setVisible(false);
				bConButSaveResult.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButSaveResult.setPrefHeight(50);
				bConButSaveResult.setBackground(Constants.BG_BROWN);
				bConButSaveResult.setBorder(Constants.B_NORMAL);
					hbConButSaveResult = new HBox();
						lConButSaveResult = new Label();
						lConButSaveResult.setText("Save last result");
						lConButSaveResult.setTextFill(Constants.C_NORMAL);
						lConButSaveResult.setFont(Constants.F_NORMAL);
						lConButSaveResult.setWrapText(false);
						lConButSaveResult.setTextAlignment(TextAlignment.CENTER);
					hbConButSaveResult.getChildren().add(lConButSaveResult);
					hbConButSaveResult.setAlignment(Pos.CENTER);
				bConButSaveResult.setGraphic(hbConButSaveResult);
				
				bConButHelp = new Button();
				bConButHelp.setVisible(false);
				bConButHelp.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButHelp.setPrefHeight(50);
				bConButHelp.setBackground(Constants.BG_PURPLE);
				bConButHelp.setBorder(Constants.B_NORMAL);
					hbConButHelp = new HBox();
						lConButHelp = new Label();
						lConButHelp.setText("Help");
						lConButHelp.setTextFill(Constants.C_NORMAL);
						lConButHelp.setFont(Constants.F_NORMAL);
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
	 * This could also be done in {@link #Home(Group)} but for a better to look at program it's in a separate method.
	 */
	private void addControlsListener() {
		bConButRun.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButRun got pressed!");
				Run.run(Main.selectedInputHandler, Main.selectedPrePost, Main.selectedTranscoder, Main.selectedNoiSource);
	        }
	    });
		bConButRun.setOnMouseEntered(Constants.EH_BUTTON_GREEN_ENTERED);
		bConButRun.setOnMouseExited(Constants.EH_BUTTON_GREEN_EXITED);
		
		bConButSaveResult.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButSaveResults got pressed!");
	        }
	    });
		bConButSaveResult.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButSaveResult.setBackground(Constants.BG_BROWN_DARK);
			}
	    });
		bConButSaveResult.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButSaveResult.setBackground(Constants.BG_BROWN);
			}
		});
		
		bConButHelp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButHelp got pressed!");
	        }
	    });
		bConButHelp.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButHelp.setBackground(Constants.BG_PURPLE_DARK);
			}
	    });
		bConButHelp.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButHelp.setBackground(Constants.BG_PURPLE);
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
		bSetModInput.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModInput got pressed!");
				root.getChildren().clear();
				if (Main.inputHandler == null) {
					Main.inputHandler = new InputHandler(root);
				} else {
					Main.inputHandler.reload(root);
				}
	        }
	    });
		
		bSetModEncoder.setOnAction(evTranscoderPressed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModEncoder or bSetModDecoder got pressed!");
				root.getChildren().clear();
				if (Main.transcoder == null) {
					Main.transcoder = new Transcoder(root);
				} else {
					Main.transcoder.reload(root);
				}
	        }
	    });
		
		bSetModNoise.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModNoise got pressed!");
				root.getChildren().clear();
				if (Main.noiseSource == null) {
					Main.noiseSource = new NoiseSource(root);
				} else {
					Main.noiseSource.reload(root);
				}
	        }
	    });
		
		bSetModDestination.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModDestination got pressed!");
	        }
	    });
		
		bSetModEncoder.setOnMouseEntered(evTranscoderEntered = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModEncoder.setBackground(Constants.BG_GRAY_DARK);
				bSetModDecoder.setBackground(Constants.BG_GRAY_DARK);
			}
		});
		bSetModEncoder.setOnMouseExited(evTranscoderExited = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModEncoder.setBackground(Constants.BG_GRAY);
				bSetModDecoder.setBackground(Constants.BG_GRAY);
			}
		});
		
		bSetModInput.setOnMouseEntered(Constants.EH_BUTTON_GRAY_ENTERED);
		bSetModInput.setOnMouseExited(Constants.EH_BUTTON_GRAY_EXITED);
		bSetModNoise.setOnMouseEntered(Constants.EH_BUTTON_GRAY_ENTERED);
		bSetModNoise.setOnMouseExited(Constants.EH_BUTTON_GRAY_EXITED);
//		bSetModDestination.setOnMouseEntered(Main.evButEntered);
//		bSetModDestination.setOnMouseExited(Main.evButExited);
		
		bSetModDecoder.setOnAction(evTranscoderPressed);
		bSetModDecoder.addEventHandler(MouseEvent.MOUSE_ENTERED, evTranscoderEntered);
		bSetModDecoder.addEventHandler(MouseEvent.MOUSE_EXITED, evTranscoderExited);
	}
	
	
	/**
	 * Reloads the home page. Re-attaches the page's elements ({@link #hbHeading}, {@link #pSettings}, {@link #pResults}, {@link #pControls})
	 * and {@link Main#krlClose}.
	 * Rebuilds the buttons of {@link #pSetModel} if changes on the communication experiment setup were made.
	 * In addition, {@link Main#updateScrollbar(Region)} gets called 
	 * (see {@link #Home(Group)} for more information relating to it's view-cases).
	 * This method gets called by the sub-pages when they get closed.
	 * @param parent Layout container to attach it's layout parts to.
	 * @param updateSettingsModel Defines whether changes on the communication experiment setup were made and the buttons have to be rebuild 
	 * or not.
	 */
	public void reload(Group parent, boolean updateSettingsModel) {
		root = parent;
		
		if (updateSettingsModel) {
			pSettings.getChildren().remove(pSetModel);
			pSetModel.getChildren().removeAll(bSetModInput, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination);
			
			bSetModInput = cSetModFactory.buildButton(0, 0, (byte) 0);
			bSetModEncoder = cSetModFactory.buildButton(3, 0, (byte) 1);
			bSetModNoise = cSetModFactory.buildButton(5.5f, 2, (byte) 2);
			bSetModDecoder = cSetModFactory.buildButton(8, 0, (byte) 3);
			bSetModDestination = cSetModFactory.buildButton(11, 0, (byte) 4);
			
			addSettingsListener();
			pSetModel.getChildren().addAll(bSetModInput, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination);
			pSettings.getChildren().add(pSetModel);
			
			pResults.setLayoutY(pSettings.getLayoutY() + Main.calcHeight(pSettings) + Constants.I_DISTANCE_SEGMENT);
			tvResTable.setPrefHeight(Main.stageHeight - pResults.getLayoutY() - tvResTable.getLayoutY() - Main.pos1 / 3);
			pControls.setLayoutY(pResults.getLayoutY());
		}
		
		Main.updateScrollbar(pControls);
		root.getChildren().addAll(hbHeading, pSettings, pResults, pControls);
		Main.scene.setOnKeyReleased(Main.krlClose);
	}
	
	
	/**
	 * Updates {@link #tvResTable the result table}. It gets called by {@link de.wolkenfarmer.environment.Result#updateResult()}.
	 * @param results The new content to e displayed by the table.
	 */
	public void updateResultTable(ObservableList<String[]> results) {
		tvResTable.getItems().setAll(results);
	}
}
