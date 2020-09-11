package environment;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @author Wolkenfarmer
 */
public class OptionsButton extends Button {
	private byte currentMode = 0;
	/** Layout container for the content of the options' button button. Contains {@link #l}.*/
	private HBox hb;
		/** Label displaying the button's heading. It's part of {@link #hb}.*/
		private Label l;
		
	public OptionsButton(double width, String heading) {
		this.setPrefWidth(width);
		this.setBorder(Main.boNormal);
			hb = new HBox();
				l = new Label();
				l.setText(heading);
				l.setTextFill(Color.WHITESMOKE);
				l.setFont(Main.fNormalText);
				l.setWrapText(true);
				l.setTextAlignment(TextAlignment.CENTER);
				l.setAlignment(Pos.CENTER);
			width = width - 6;
			hb.setPrefHeight(Main.calcHeightLabel(l, width) + 10);
			hb.getChildren().addAll(l);
			hb.setAlignment(Pos.CENTER);
		this.setPrefHeight(hb.getPrefHeight() + 10);
		this.setGraphic(hb);
		this.setMode((byte) 0);
	}
	
	
	public void setOnActionW(ExperimentElement reference, SettingsPage page, InformationSegment infSegment) {
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Options Button: " + l.getText() + " got pressed!");
				
				infSegment.setSaveAddReference(reference, (OptionsButton) t.getSource(), page);
				
				for (int i = 0; i < page.vbOptButtons.getChildren().size(); i++) {
					((OptionsButton) page.vbOptButtons.getChildren().get(i)).setSelected(false);
				}
				((OptionsButton) t.getSource()).setSelected(true);
				
				infSegment.getContent().getChildren().clear();
				if (reference.getBuiltGui()) {
					reference.reloadGui(infSegment.getContent());
				} else {
					reference.buildGui(infSegment.getContent());
				}				
	        }
	    });
	}
	
	
	public void setMode(byte mode) {
		switch (mode) {
		case 0:
			this.setBackground(Main.baNormalButton);
			this.setOnMouseEntered(Main.evButEntered);
			this.setOnMouseExited(Main.evButExited);
			currentMode = 0;
			break;
		case 1:
			this.setBackground(Main.baGreenButton);
			this.setOnMouseEntered(Main.evButGreEntered);
			this.setOnMouseExited(Main.evButGreExited);
			currentMode = 1;
			break;
		case 2:
			this.setBackground(Main.baBrownButton);
			this.setOnMouseEntered(Main.evButBroEntered);
			this.setOnMouseExited(Main.evButBroExited);
			currentMode = 2;
			break;
		default:
			System.out.println("Options Button \"" + l.getText() + "\": Mode \"" + mode + "\" not specified");
		}
	}
	
	
	public void setSelected(boolean selected) {
		if (selected) {
			this.setBorder(Main.boSelected);
		} else {
			this.setBorder(Main.boNormal);
		}
	}
	
	
	public byte getMode() {return currentMode;}
}
