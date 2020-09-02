package environment;

import java.util.Arrays;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 * The homepage of the application with access to every part of the program.
 * This class builds the UI of the Homepage with the help of {@link environment.ModelFactory} for elements of the model of settings.
 * @author Wolkenfarmer
 */
public class Homepage {
	/** Layout container for the headline elements {@link #lHeadline}, {@link #rHeadlineSpacer} and {@link #vbHeadline}.*/
	HBox hbHeadline;
		/** Label which displays the headline "Source / Channel Coding Environment". It's part of {@link #hbHeadline}.*/
		Label lHeadline;
		/** A spacer for the right-hand-side-layout of {@link #vbHeadline}. It's part of {@link #hbHeadline}.*/
		Region rHeadlineSpacer;
		/** Layout container for the headline elements on the right side. 
		 * Contains {@link #bSetModSource}, {@link #bSetModEncoder}, {@link #bSetModNoise}, {@link #bSetModDecoder} and {@link #bSetModDestination} 
		 * and is part of {@link #hbHeadline}.*/
		VBox vbHeadline;
			/** Label which displays the current version of this program. It's part of {@link #vbHeadline} and this again of {@link #hbHeadline}.*/
			Label lHeadlineVersion;
			/** Label which displays developer of this program. It's part of {@link #vbHeadline} and this again of {@link #hbHeadline}.*/
			Label lHeadlineBy;
	/** Layout container for the settings elements {@link #lSetHeading} and {@link #pSetModel}.*/
	Pane pSettings;
		/** Label which displays the subheadline "Settings". It's part of {@link #pSettings}.*/
		Label lSetHeading;
		/** Layout container for the elements of the model in {@link #pSettings}. 
		 * Contains {@link #bSetModSource}, {@link #bSetModEncoder}, {@link #bSetModNoise}, {@link #bSetModDecoder} and {@link #bSetModDestination} */
		Pane pSetModel;
			/** Information source button of the model in settings. 
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModSource;
			/** Encoder button of the model in settings. 
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModEncoder;
			/** Noise source button of the model in settings. 
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModNoise;
			/** Decoder button of the model in settings. 
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModDecoder;
			/** Destination button of the model in settings. 
			 * It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModDestination;
			/** Relation for the model in settings. Connects {@link #bSetModSource} with {@link #bSetModEncoder}.*/
			Group gSetModRelSoToEn;
			/** Relation for the model in settings. Connects {@link #bSetModEncoder} with {@link #bSetModDecoder}.*/
			Group gSetModRelEnToDe;
			/** Relation for the model in settings. Connects {@link #bSetModDecoder} with {@link #bSetModDestination}.*/
			Group gSetModRelDeToDe;
			/** Relation for the model in settings. Connects {@link #bSetModNoise} with {@link #gSetModRelEnToDe}.*/
			Group gSetModRelNoToCh;
	/** Layout container for the results elements.*/
	Pane pResults;
		Label lResHeading;
		TableView<String[]> tvResTable;
			TableColumn<String[], String> tvResTabDescription;
			TableColumn<String[], String> tvResTabValue;
	/** Layout container for the buttons (at the bottom of the page).*/
	Pane pControls;
		Label lConHeading;
		VBox vbConButtons;
			Button bConButRun;
				HBox hbConButRun;
				Label lConButRun;
			Button bConButSaveResults;
				HBox hbConButSaveResults;
				Label lConButSaveResults;
			Button bConButHelp;
				HBox hbConButHelp;
				Label lConButHelp;
	
	
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	EventHandler<ActionEvent> evEnDecoderPressed;
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	EventHandler<MouseEvent> evEnDecoderEntered;
	/** Unified EventHandler for {@link #bSetModDecoder} and {@link #bSetModEncoder}.*/
	EventHandler<MouseEvent> evEnDecoderExited;
	
	/**
	 * Builds the homepage of the application.
	 * For the building of the buttons in the model in settings {@link environment.ModelFactory} is used.
	 * Ultimately, the height of the content gets calculated and the scrollbar accordingly updated.
	 * @param root A group to attach it's layout parts to.
	 */
	public Homepage(Group root) {
		double pos1 = Main.stageWidth / 8;
		double pos7 = Main.stageWidth / 8 * 7;
		double contentWidth = pos7 - pos1;
		
		hbHeadline = new HBox();
		hbHeadline.setLayoutX(pos1);
		hbHeadline.setLayoutY(pos1 / 3);
		hbHeadline.setPrefWidth(contentWidth);
			lHeadline = new Label();
			lHeadline.setText("Source / Channel Coding Environment");
			lHeadline.setTextFill(Color.WHITESMOKE);
			lHeadline.setFont(Main.fHeadline);
			lHeadline.setAlignment(Pos.CENTER_LEFT);
			
			rHeadlineSpacer = new Region();
			HBox.setHgrow(rHeadlineSpacer, Priority.ALWAYS);
			
			vbHeadline = new VBox();
			vbHeadline.setAlignment(Pos.CENTER_RIGHT);
				lHeadlineVersion = new Label();
				lHeadlineVersion.setText("Version 1.0");
				lHeadlineVersion.setTextFill(Color.WHITESMOKE);
				lHeadlineVersion.setFont(Main.fNormalText);
				lHeadlineVersion.setAlignment(Pos.TOP_RIGHT);
				
				lHeadlineBy = new Label();
				lHeadlineBy.setText("by Wolkenfarmer");
				lHeadlineBy.setTextFill(Color.WHITESMOKE);
				lHeadlineBy.setFont(Main.fSmallText);
				lHeadlineBy.setAlignment(Pos.BOTTOM_RIGHT);
			vbHeadline.getChildren().addAll(lHeadlineVersion, lHeadlineBy);
		hbHeadline.getChildren().addAll(lHeadline, rHeadlineSpacer, vbHeadline);
		
		
		pSettings = new Pane();
		pSettings.setLayoutX(pos1);
		pSettings.setLayoutY(hbHeadline.getLayoutY() + Main.calcHeight(hbHeadline) + 50);
		pSettings.setPrefWidth(contentWidth);
			lSetHeading = new Label();
			lSetHeading.setText("Settings");
			lSetHeading.setTextFill(Color.WHITESMOKE);
			lSetHeading.setFont(Main.fSubheadline);
			
			pSetModel = new Pane();
			pSetModel.setLayoutY(60);
				ModelFactory cSetModelFactory = new ModelFactory(contentWidth); 
				bSetModSource = cSetModelFactory.buildButton(0, 0, "information source", true);
				bSetModEncoder = cSetModelFactory.buildButton(3, 0, "encoder", true);
				bSetModNoise = cSetModelFactory.buildButton(5.5f, 2, "noise source", true);
				bSetModDecoder = cSetModelFactory.buildButton(8, 0, "decoder", true);
				bSetModDestination = cSetModelFactory.buildButton(11, 0, "destination", false);
				
				gSetModRelSoToEn = cSetModelFactory.buildRelation(2, 1, ((short) 1), false, "message");
				gSetModRelEnToDe = cSetModelFactory.buildRelation(5, 1, ((short) 3), false, "signal / channel");
				gSetModRelDeToDe = cSetModelFactory.buildRelation(10, 1, ((short) 1), false, "message");
				gSetModRelNoToCh = cSetModelFactory.buildRelation(6.5f, 2, ((short) 1), true, "");
			pSetModel.getChildren().addAll(bSetModSource, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination, 
					gSetModRelSoToEn, gSetModRelEnToDe, gSetModRelDeToDe, gSetModRelNoToCh);
		pSettings.getChildren().addAll(lSetHeading, pSetModel);
		
		
		pResults = new Pane();
		pResults.setLayoutX(pos1);
		pResults.setLayoutY(pSettings.getLayoutY() + Main.calcHeight(pSettings) + 50);
		pResults.setPrefWidth(contentWidth);
			lResHeading = new Label();
			lResHeading.setText("Last Results");
			lResHeading.setTextFill(Color.WHITESMOKE);
			lResHeading.setFont(Main.fSubheadline);
			pResults.getChildren().add(lResHeading);
			
			
			tvResTable = new TableView<String[]>();
			tvResTable.setLayoutY(60);
			tvResTable.setPrefHeight(Main.stageHeight - pResults.getLayoutY() - tvResTable.getLayoutY() - pos1 / 3);
			tvResTable.setMinHeight(250 - tvResTable.getLayoutY());							// Minimum height -> ends with pControls
			tvResTable.setPrefWidth(Main.stageWidth / 2);
			    tvResTabDescription = new TableColumn<>("Description");
			    tvResTabDescription.setResizable(false);
			    tvResTabDescription.setPrefWidth(150);
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
			    tvResTabValue.setResizable(false);
			    tvResTabValue.prefWidthProperty().bind(tvResTable.widthProperty().subtract(tvResTabDescription.getPrefWidth() + 16));
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
			    
			    String[][] mockdata = new String[50][2];
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
			lConHeading.setTextFill(Color.WHITESMOKE);
			lConHeading.setFont(Main.fSubheadline);			
			
			vbConButtons = new VBox();
			vbConButtons.setPrefWidth(pControls.getPrefWidth());
			vbConButtons.setLayoutY(60);
			vbConButtons.setSpacing(20);
				bConButRun = new Button();
				bConButRun.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButRun.setPrefHeight(50);
				bConButRun.setBackground(Main.baGreenButton);
				bConButRun.setBorder(Main.boNormalWhite);
					hbConButRun = new HBox();
						lConButRun = new Label();
						lConButRun.setText("Run");
						lConButRun.setTextFill(Color.WHITESMOKE);
						lConButRun.setFont(Main.fNormalText);
						lConButRun.setWrapText(false);
						lConButRun.setTextAlignment(TextAlignment.CENTER);
					hbConButRun.getChildren().add(lConButRun);
					hbConButRun.setAlignment(Pos.CENTER);
				bConButRun.setGraphic(hbConButRun);
				
				bConButSaveResults = new Button();
				bConButSaveResults.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButSaveResults.setPrefHeight(50);
				bConButSaveResults.setBackground(Main.baBrownButton);
				bConButSaveResults.setBorder(Main.boNormalWhite);
					hbConButSaveResults = new HBox();
						lConButSaveResults = new Label();
						lConButSaveResults.setText("Save last results");
						lConButSaveResults.setTextFill(Color.WHITESMOKE);
						lConButSaveResults.setFont(Main.fNormalText);
						lConButSaveResults.setWrapText(false);
						lConButSaveResults.setTextAlignment(TextAlignment.CENTER);
					hbConButSaveResults.getChildren().add(lConButSaveResults);
					hbConButSaveResults.setAlignment(Pos.CENTER);
				bConButSaveResults.setGraphic(hbConButSaveResults);
				
				bConButHelp = new Button();
				bConButHelp.setPrefWidth(vbConButtons.getPrefWidth() - 1);
				bConButHelp.setPrefHeight(50);
				bConButHelp.setBackground(Main.baPurpleButton);
				bConButHelp.setBorder(Main.boNormalWhite);
					hbConButHelp = new HBox();
						lConButHelp = new Label();
						lConButHelp.setText("Help");
						lConButHelp.setTextFill(Color.WHITESMOKE);
						lConButHelp.setFont(Main.fNormalText);
						lConButHelp.setWrapText(false);
						lConButHelp.setTextAlignment(TextAlignment.CENTER);
					hbConButHelp.getChildren().add(lConButHelp);
					hbConButHelp.setAlignment(Pos.CENTER);
				bConButHelp.setGraphic(hbConButHelp);
			vbConButtons.getChildren().addAll(bConButRun, bConButSaveResults, bConButHelp);
	    pControls.getChildren().addAll(lConHeading, vbConButtons);
		
		
		Main.contentHeight = pResults.getLayoutY() + Main.calcHeight(pResults) + pos1 / 3;
		Main.scrollbar.setMax(Main.contentHeight - Main.scene.getHeight());
		Main.scrollbar.setBlockIncrement(Main.contentHeight);
        if (Main.scene.getHeight() >= Main.contentHeight) {Main.scrollbar.setVisible(false);}
        
        
        root.getChildren().addAll(hbHeadline, pSettings, pResults, pControls);
        addButtonListener();
	}
	
	
	/**
	 * Adds the listener to the Buttons of {@link environment.Homepage}. 
	 */
	private void addButtonListener() {
		// Model
		bSetModSource.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModSource got pressed!");
	        }
	    });
		bSetModSource.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModSource.setBackground(Main.baNormalFocusedButton);
			}
	    });
		bSetModSource.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModSource.setBackground(Main.baNormalButton);
			}
		});
		
		bSetModEncoder.setOnAction(evEnDecoderPressed = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModEncoder or bSetModDecoder got pressed!");
	        }
	    });
		bSetModEncoder.addEventHandler(MouseEvent.MOUSE_ENTERED, evEnDecoderEntered = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModEncoder.setBackground(Main.baNormalFocusedButton);
				bSetModDecoder.setBackground(Main.baNormalFocusedButton);
			}
	    });
		bSetModEncoder.addEventHandler(MouseEvent.MOUSE_EXITED, evEnDecoderExited = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModEncoder.setBackground(Main.baNormalButton);
				bSetModDecoder.setBackground(Main.baNormalButton);
			}
		});
		
		bSetModDecoder.setOnAction(evEnDecoderPressed);
		bSetModDecoder.addEventHandler(MouseEvent.MOUSE_ENTERED, evEnDecoderEntered);
		bSetModDecoder.addEventHandler(MouseEvent.MOUSE_EXITED, evEnDecoderExited);
		
		bSetModNoise.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModNoise got pressed!");
	        }
	    });
		bSetModNoise.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModNoise.setBackground(Main.baNormalFocusedButton);
			}
	    });
		bSetModNoise.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModNoise.setBackground(Main.baNormalButton);
			}
		});
		
		bSetModDestination.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bSetModDestination got pressed!");
	        }
	    });
		bSetModDestination.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModDestination.setBackground(Main.baNormalFocusedButton);
			}
	    });
		bSetModDestination.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bSetModDestination.setBackground(Main.baNormalButton);
			}
		});
		
		
		//Controls
		bConButRun.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButRun got pressed!");
	        }
	    });
		bConButRun.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButRun.setBackground(Main.baGreenFocusedButton);
			}
	    });
		bConButRun.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButRun.setBackground(Main.baGreenButton);
			}
		});
		
		bConButSaveResults.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButSaveResults got pressed!");
	        }
	    });
		bConButSaveResults.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButSaveResults.setBackground(Main.baBrownFocusedButton);
			}
	    });
		bConButSaveResults.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButSaveResults.setBackground(Main.baBrownButton);
			}
		});
		
		bConButHelp.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("bConButHelp got pressed!");
	        }
	    });
		bConButHelp.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButHelp.setBackground(Main.baPurpleFocusedButton);
			}
	    });
		bConButHelp.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				bConButHelp.setBackground(Main.baPurpleButton);
			}
		});
	}
}
