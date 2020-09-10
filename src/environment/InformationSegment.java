package environment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @author Wolkenfarmer
 */
public class InformationSegment extends Pane {
	private byte refType;
	private HBox hbHeading;
		/** Label which displays the sub-heading "Information" by default, but gets updated to fit the currently picked en- / decoder. 
		 * It's part of {@link #pInformation}.*/
		private Label lHeading;
		private Button bHeaSav;
			/** Layout container for the content of the options' button button. Contains {@link #lHeaSavContent}.*/
			private HBox hbHeaSavContent;
				/** Label displaying the button's heading. It's part of {@link #hbHeaSavContent}.*/
				private Label lHeaSavContent;
	/** Layout container for the information content elements. Contains {@link #lInfConDefault} by default,
	 * but gets updated by the currently picked en- / decoder.*/
	private static Pane pInfContent;
		private static Label lInfConDefault;

	
	public InformationSegment(byte refType, double layoutX, double layoutY, double minHeight) {
		this.refType = refType;
		this.setLayoutX(layoutX);
		this.setLayoutY(layoutY);
		this.setPrefWidth(Main.stageWidth / 2);
			bHeaSav = new Button();
			bHeaSav.setBorder(Main.boNormal);
			bHeaSav.setBackground(Main.baGreenButton);
			bHeaSav.setOnMouseEntered(Main.evButGreEntered);
			bHeaSav.setOnMouseExited(Main.evButGreExited);
				hbHeaSavContent = new HBox();
					lHeaSavContent = new Label();
					lHeaSavContent.setText("save & add");
					lHeaSavContent.setTextFill(Color.WHITESMOKE);
					lHeaSavContent.setFont(Main.fNormalText);
					lHeaSavContent.setWrapText(true);
					lHeaSavContent.setTextAlignment(TextAlignment.CENTER);
					lHeaSavContent.setAlignment(Pos.CENTER);
					bHeaSav.setMinWidth(Main.calcWidthLabel(lHeaSavContent));
				hbHeaSavContent.getChildren().addAll(lHeaSavContent);
				hbHeaSavContent.setAlignment(Pos.CENTER);
			bHeaSav.setGraphic(hbHeaSavContent);
			bHeaSav.setLayoutX(this.getPrefWidth() - Main.calcWidth(bHeaSav));
			
			lHeading = new Label();
			lHeading.setText("Information");
			lHeading.setTextFill(Color.WHITESMOKE);
			lHeading.setFont(Main.fSubheading);
			lHeading.setPrefWidth(bHeaSav.getLayoutX());
			
			bHeaSav.setPrefHeight(Main.calcHeightLabel(lHeading, Double.MAX_VALUE));
			
			pInfContent = new Pane();
			pInfContent.setLayoutY(Main.distanceToSubheading);
			pInfContent.setPrefHeight(Main.stageHeight - this.getLayoutY() - pInfContent.getLayoutY() - Main.pos1 / 3);
			pInfContent.setPrefWidth(this.getPrefWidth());
			pInfContent.setMinHeight(minHeight - pInfContent.getLayoutY());
				lInfConDefault = new Label();
				lInfConDefault.setText("No option picked");
				lInfConDefault.setTextFill(Color.INDIANRED);
				lInfConDefault.setFont(Main.fNormallTextItalic);
			pInfContent.getChildren().add(lInfConDefault);
		this.getChildren().addAll(bHeaSav, lHeading, pInfContent);
	}
	
	
	public void setHeading(String newName) {
		lHeading.setText(newName);
	}
	
	
	public void setSaveAddReference(ExperimentElement reference, OptionsButton button) {
		bHeaSav.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("\"Save & add\" got pressed!");
				reference.save();
				
				switch (refType) {
				case 0: // information source
					break;
				case 1: // EnDecoder	
					if (reference.getType() == 0) {
						Main.selectedEnDecoder = reference.getIndex();
						
						for (int i = 0; i < EnDecoderPage.vbOptButtons.getChildren().size(); i++) {
							if (((OptionsButton) EnDecoderPage.vbOptButtons.getChildren().get(i)).getMode() == 1) {
								((OptionsButton) EnDecoderPage.vbOptButtons.getChildren().get(i)).setMode((byte) 0);
							}
						}
						button.setMode((byte) 1);
					} else {
						Main.selectedPrePost = reference.getIndex();
						
						for (int i = 0; i < EnDecoderPage.vbOptButtons.getChildren().size(); i++) {
							if (((OptionsButton) EnDecoderPage.vbOptButtons.getChildren().get(i)).getMode() == 2) {
								((OptionsButton) EnDecoderPage.vbOptButtons.getChildren().get(i)).setMode((byte) 0);
							}
						}
						button.setMode((byte) 2);
					}
					break;
				case 2: // noise source
					break;
				default:
					System.out.println("Type not found");
				}
								
				Main.boUpdateSettingsModelHomepage = true;
	        }
	    });
	}
	
	
	public Pane getContent() {return pInfContent;}
}
