package de.wolkenfarmer.environment.pages.gui_elements;

import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.Transcoder;
import de.wolkenfarmer.environment.pages.Settings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

/**
 * The option button for the {@link Settings#vbOptButtons settings pages' options-segments}.
 * It's action listener gets set in {@link #setOnActionW(ExperimentElement, Settings, InformationSegment)}.
 * It's able to display the name of the {@link ExperimentElement} experiment element. 
 * It's height gets correctly calculated to fit the given text and gets updated if needed. 
 * @author Wolkenfarmer
 */
public class OptionButton extends Button {
	/**
	 * Saves the current mode of the button and defines it's current look.<br>
	 * 0: Not set as any element for the communication experiment. Uses {@link Main#baNormalButton}.<br>
	 * 1: Set as the element for the communication experiment. Uses {@link Main#baGreenButton}.<br>
	 * 2: At the moment only used in {@link Transcoder} if the experiment element is set as pre- / post-transcoder and not as e- / decoder.
	 * Uses {@link Main#baBrownButton}.
	 * */
	private byte currentMode = 0;
	/** Layout container for the content of the option button. Contains {@link #l}.*/
	private HBox hb;
		/** Label displaying the button's heading. It's part of {@link #hb}.*/
		private Label l;
		
	
		/**
		 * Builds the option button accordingly to the width and with the provided texts. 
		 * The overview button's height gets scaled in order to fit the included text needed height. 
		 * @param width The {@link #width width} of the button.
		 * @param heading The text for the {@link #l heading} of the button.
		 */
	public OptionButton(double width, String heading) {
		this.setPrefWidth(width);
		this.setBorder(Main.boNormal);
			hb = new HBox();
				l = new Label();
				l.setText(heading);
				l.setTextFill(Main.cNormal);
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
	
	
	/**
	 * Sets the the on action event handler for the option button.
	 * If pressed, {@link #setSelected(boolean)} gets set to true for this button and 
	 * to false for the previously selected option button on the {@link Settings page}.
	 * In addition, the {@link InformationSegment} of the page gets updated to display 
	 * the content of the {@link ExperimentElement experiment element} connected to this button via 
	 * {@link ExperimentElement#getGui()} (if {@link ExperimentElement#getBuiltGui()} returns false, 
	 * {@link ExperimentElement#buildGui(double)} will be called before)
	 * and {@link InformationSegment#setSaveAddReference(ExperimentElement, OptionButton, Settings)} gets called.
	 * @param reference The {@link ExperimentElement experiment element} of which the content should be displayed if pressed.
	 * @param page The {@link Settings page} of which the button's should be updated and carried on to 
	 * {@link InformationSegment#setSaveAddReference(ExperimentElement, OptionButton, Settings)}.
	 * @param infSegment The {@link InformationSegment} of which the content and button's action handler should be updated.
	 */
	public void setOnActionW(ExperimentElement reference, Settings page, InformationSegment infSegment) {
		this.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("Options Button: " + l.getText() + " got pressed!");
				
				infSegment.setSaveAddReference(reference, (OptionButton) t.getSource(), page);
				
				for (int i = 0; i < page.vbOptButtons.getChildren().size(); i++) {
					((OptionButton) page.vbOptButtons.getChildren().get(i)).setSelected(false);
				}
				((OptionButton) t.getSource()).setSelected(true);
				
				if (!reference.getBuiltGui()) {
					reference.buildGui(infSegment.getContent().getPrefWidth());
				} 	
				infSegment.getContent().getChildren().add(reference.getGui());
				infSegment.setHeading("Information | " + reference.getName());
				page.updateHeight();
	        }
	    });
	}
	
	
	/**
	 * Sets the {@link #currentMode current mode} of the option button. Modifies the look of the button.
	 * @param mode The new mode of the button.
	 */
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
	
	/**
	 * Sets the border of the to {@link Main#boSelected} if selected or {@link Main#boNormal} if not.
	 * Gets called from {@link #setOnActionW(ExperimentElement, Settings, InformationSegment)}.
	 * @param selected Defines whether the button is now selected or not.
	 */
	public void setSelected(boolean selected) {
		if (selected) {
			this.setBorder(Main.boSelected);
		} else {
			this.setBorder(Main.boNormal);
		}
	}
	
	
	/**
	 * Returns the {@link #currentMode} for {@link #setOnActionW(ExperimentElement, Settings, InformationSegment)}, 
	 * which previously has been set via {@link #setMode(byte)}.
	 * @return Returns {@link #currentMode}.
	 */
	public byte getMode() {return currentMode;}
}
