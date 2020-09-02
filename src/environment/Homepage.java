package environment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

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
	/** Layout container for the settings elements {@link #lSetHeadline} and {@link #pSetModel}.*/
	Pane pSettings;
		/** Label which displays the subheadline "Settings". It's part of {@link #pSettings}.*/
		Label lSetHeadline;
		/** Layout container for the elements of the model in {@link #pSettings}. 
		 * Contains {@link #bSetModSource}, {@link #bSetModEncoder}, {@link #bSetModNoise}, {@link #bSetModDecoder} and {@link #bSetModDestination} */
		Pane pSetModel;
			/** Information source button of the model in settings. It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModSource;
			/** Encoder button of the model in settings. It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModEncoder;
			/** Noise source button of the model in settings. It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModNoise;
			/** Decoder button of the model in settings. It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModDecoder;
			/** Destination button of the model in settings. It's part of {@link #pSetModel} and this again of {@link #pSettings}. Links up to TODO*/
			Button bSetModDestination;
			Group gSetModRelSoToEn;
			Group gSetModRelEnToDe;
			Group gSetModRelDeToDe;
			Group gSetModRelNoToCh;
	/** Layout container for the results elements.*/
	Group gResults;
	/** Layout container for the buttons (at the bottom of the page).*/
	Group gButtons;
	
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
		pSettings.setLayoutY(hbHeadline.getLayoutY() + 120);
		pSettings.setPrefWidth(contentWidth);
			lSetHeadline = new Label();
			lSetHeadline.setText("Settings");
			lSetHeadline.setTextFill(Color.WHITESMOKE);
			lSetHeadline.setFont(Main.fSubheadline);
			
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
		pSettings.getChildren().addAll(lSetHeadline, pSetModel);
		
		root.getChildren().addAll(hbHeadline, pSettings);
		addButtonListener();
		
		
		Main.contentHeight = lHeadline.getLayoutY() + pos1 / 3;
		Main.scrollbar.setMax(Main.contentHeight - Main.scene.getHeight());
		Main.scrollbar.setBlockIncrement(Main.contentHeight);
        if (Main.scene.getHeight() >= Main.contentHeight) {Main.scrollbar.setVisible(false);}
	}
	
	
	/**
	 * Adds the listener to the Buttons of {@link environment.Homepage}. 
	 */
	private void addButtonListener() {
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
	}
}
