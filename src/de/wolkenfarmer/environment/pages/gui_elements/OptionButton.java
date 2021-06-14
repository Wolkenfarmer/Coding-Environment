package de.wolkenfarmer.environment.pages.gui_elements;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Main;
//import de.wolkenfarmer.environment.pages.Transcoder;
import de.wolkenfarmer.environment.pages.Settings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

/**
 * The option button for the {@link Settings#vbOptButtons settings pages options-segments}. <br>
 * Its action listener gets set in {@link #setOnActionW(ExperimentElement)}.
 * It's able to display the name of the {@link ExperimentElement} experiment element. 
 * Its height gets correctly calculated to fit the given text and gets updated if needed. 
 * @author Wolkenfarmer
 */
public class OptionButton extends Button {
	/**
	 * Saves the current mode of the button and defines its current look. <br>
	 * 0: Not set as any element for the communication experiment. Uses {@link Constants#BG_GRAY}.<br>
	 * 1: Set as the element for the communication experiment. Uses {@link Constants#BG_GREEN}.<br>
	 * 2: Has currently no use case. Uses {@link Constants#BG_BROWN}.
	 * */
	private byte currentMode = 0;
	/** Layout container for the content of the option button. Contains {@link #l}.*/
	private HBox hb;
		/** Label displaying the button's heading. It's part of {@link #hb}.*/
		private Label l;
		
	
	/**
	 * Builds the option button accordingly to the width and with the provided texts. <br>
	 * The overview button's height gets scaled in order to fit the included text needed height. 
	 * @param width The {@link #width width} of the button.
	 * @param heading The text for the {@link #l heading} of the button.
	 */
	public OptionButton(double width, String heading) {
		this.setPrefWidth(width);
		this.setBorder(Constants.B_NORMAL);
			hb = new HBox();
				l = new Label();
				l.setText(heading);
				l.setTextFill(Constants.C_NORMAL);
				l.setFont(Constants.F_NORMAL);
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
	
	
	/**
	 * Sets the the on action event handler for the option button. <br>
	 * If pressed, {@link #setSelected(boolean)} gets set to true for this button and 
	 * to false for the previously selected option button on the {@link Settings page}.
	 * In addition, the {@link Settings#pInformation information segment} of the settings page gets updated to display 
	 * the content of the {@link ExperimentElement experiment element} connected to this button via 
	 * {@link ExperimentElement#getGui()} (if {@link ExperimentElement#getBuiltGui()} returns false, 
	 * {@link ExperimentElement#buildGui(double)} will be called before)
	 * and {@link InformationSegment#setSaveAddReference(ExperimentElement, OptionButton)} gets called.
	 * @param reference The {@link ExperimentElement experiment element} of which the content should be displayed if pressed.
	 * @since 0.2
	 */
	public void setOnActionW(ExperimentElement reference) {
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Options Button: " + l.getText() + " got pressed!");
				
				Settings.pInformation.setSaveAddReference(reference, (OptionButton) t.getSource());
				
				for (int i = 0; i < Settings.vbOptButtons.getChildren().size(); i++) {
					((OptionButton) Settings.vbOptButtons.getChildren().get(i)).setSelected(false);
				}
				((OptionButton) t.getSource()).setSelected(true);
				
				if (!reference.getBuiltGui()) {
					reference.buildGui(Settings.pInformation.getContent().getPrefWidth());
				} 	
				Settings.pInformation.getContent().getChildren().add(reference.getGui());
				Settings.pInformation.setHeading("Information | " + reference.getName(true));
				Settings.updateHeight();
	        }
	    });
	}
	
	
	/**
	 * Sets the {@link #currentMode current mode} of the option button. <br>
	 * Modifies the look of the button.
	 * @param mode The new mode of the button.
	 */
	public void setMode(byte mode) {
		switch (mode) {
		case 0:
			this.setBackground(Constants.BG_GRAY);
			this.setOnMouseEntered(Constants.EH_BUTTON_GRAY_ENTERED);
			this.setOnMouseExited(Constants.EH_BUTTON_GRAY_EXITED);
			currentMode = 0;
			break;
		case 1:
			this.setBackground(Constants.BG_GREEN);
			this.setOnMouseEntered(Constants.EH_BUTTON_GREEN_ENTERED);
			this.setOnMouseExited(Constants.EH_BUTTON_GREEN_EXITED);
			currentMode = 1;
			break;
		case 2:																// currently not used
			this.setBackground(Constants.BG_BROWN);
			this.setOnMouseEntered(Constants.EH_BUTTON_BROWN_ENTERED);
			this.setOnMouseExited(Constants.EH_BUTTON_BROWN_EXITED);
			currentMode = 2;
			break;
		default:
			System.out.println("Options Button \"" + l.getText() + "\": Mode \"" + mode + "\" not specified");
		}
	}
	
	/**
	 * Sets the border of the button to {@link Constants#B_SELECTED} if selected or {@link Constants#B_NORMAL} if not. <br>
	 * Gets called from {@link #setOnActionW(ExperimentElement)}.
	 * @param selected Defines whether the button is now selected or not.
	 */
	public void setSelected(boolean selected) {
		if (selected) this.setBorder(Constants.B_SELECTED);
		else this.setBorder(Constants.B_NORMAL);
	}
	
	
	/**
	 * Returns the {@link #currentMode} for {@link #setOnActionW(ExperimentElement)}, 
	 * which previously has been set via {@link #setMode(byte)}.
	 * @return Returns {@link #currentMode}.
	 */
	public byte getMode() {return currentMode;}
}
