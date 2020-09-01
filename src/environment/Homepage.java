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
 * @author Wolkenfarmer
 */
public class Homepage {
	HBox hbHeadline;
		Label lHeadline;
		Region rHeadlineSpacer;
		VBox vbHeadline;
			Label lHeadlineVersion;
			Label lHeadlineBy;
	Pane pSettings;
		Label lSetHeadline;
		Pane pSetModel;
			Button bSetModSource;
			Button bSetModEncoder;
			Button bSetModNoise;
			Button bSetModDecoder;
			Button bSetModDestination;
	Group gResults;
	Group gButtons;
	
	EventHandler<ActionEvent> evEnDecoderPressed;
	EventHandler<MouseEvent> evEnDecoderEntered;
	EventHandler<MouseEvent> evEnDecoderExited;
	
	/**
	 * Builds the homepage of the application.
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
			pSetModel.getChildren().addAll(bSetModSource, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination);
		pSettings.getChildren().addAll(lSetHeadline, pSetModel);
		
		root.getChildren().addAll(hbHeadline, pSettings);
		addButtonListener();
		
		
		Main.contentHeight = lHeadline.getLayoutY() + pos1 / 3;
		Main.scrollbar.setMax(Main.contentHeight - Main.scene.getHeight());
		Main.scrollbar.setBlockIncrement(Main.contentHeight);
        if (Main.scene.getHeight() >= Main.contentHeight) {Main.scrollbar.setVisible(false);}
	}
	
	
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
