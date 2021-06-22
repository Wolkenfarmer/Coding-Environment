package de.wolkenfarmer.environment.gui_elements;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.logic.Main;
import de.wolkenfarmer.environment.pages.Settings;
import de.wolkenfarmer.experiment_elements.ExperimentElement;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * The overview button for the overview models in the {@link Settings#pOverview settings pages}.
 * It's mostly used as a better rectangle and without it's action listeners.
 * It's able to display a {@link #lHeading heading} and a {@link #lSelectedItem selected item}. 
 * The second one can be set with {@link #setSelectedItem(String)}.
 * It's height gets correctly calculated to fit the given text and gets updated if needed. 
 * The button can also be {@link #rebuild(double, String, String) rebuild}.
 * @author Wolkenfarmer
 */
public class OverviewButton extends Button {
	/** The width of the button. Gets set in {@link #OverviewButton(double, String, String)} and {@link #rebuild(double, String, String)}.*/
	private double width;
	/** Layout container for the content of the button. 
	 * Contains {@link #lHeading} and {@link #lSelectedItem} and gets added as Graphic to the button.*/
	private VBox vbContent;
		/** Label displaying the button's heading. It's part of {@link #vbContent}.*/
		private Label lHeading;
		/** Label displaying the button's selected item. It's part of {@link #vbContent}.*/
		private Label lSelectedItem;
		
	
	/**
	 * Builds the overview button accordingly to the width and with the provided texts. 
	 * The overview button's height gets scaled in order to fit the included texts needed height. 
	 * The parameters can be overwritten in {@link #rebuild(double, String, String)}. 
	 * @param width The {@link #width width} of the button.
	 * @param heading The text for the {@link #lHeading heading} of the button.
	 * @param selectedItem The text for the {@link #lSelectedItem selected item} (a {@link ExperimentElement}) which should be displayed.
	 * This parameter can additionally be re-set in {@link #setSelectedItem(String)}.
	 */
	public OverviewButton(double width, String heading, String selectedItem) {
		this.setPrefWidth(width);
		this.setBackground(Constants.BG_GRAY);
		this.setBorder(Constants.B_NORMAL);
			vbContent = new VBox();
			vbContent.setSpacing(5);
				lHeading = new Label();
				lHeading.setText(heading);
				lHeading.setTextFill(Constants.C_NORMAL);
				lHeading.setFont(Constants.F_NORMAL);
				lHeading.setWrapText(true);
				lHeading.setTextAlignment(TextAlignment.CENTER);
				lHeading.setAlignment(Pos.CENTER);
				
				lSelectedItem = new Label();
				lSelectedItem.setText(selectedItem);
				lSelectedItem.setTextFill(Constants.C_PINK);
				lSelectedItem.setFont(Constants.F_SMALL);
				lSelectedItem.setWrapText(true);
				lSelectedItem.setTextAlignment(TextAlignment.CENTER);
				lSelectedItem.setAlignment(Pos.CENTER);
			this.width = width - 10;
			vbContent.setPrefHeight(Main.calcHeightLabel(lHeading, this.width) + Main.calcHeightLabel(lSelectedItem, this.width) + 
					vbContent.getSpacing() + 8);
			vbContent.getChildren().addAll(lHeading, lSelectedItem);
			vbContent.setAlignment(Pos.CENTER);
		this.setPrefHeight(vbContent.getPrefHeight() + 2);
		this.setGraphic(vbContent);
	}
	
	
	/**
	 * Returns the height of the button. Gets used for building the {@link Settings#vbOptButtons}.
	 * @return Returns the preferred height of the button. 
	 */
	public double getHeightW() {
		return this.getPrefHeight();
	}
	
	
	/**
	 * Re-sets the {@link #lSelectedItem selected item} and rescales the height of the button accordingly. 
	 * Gets called when changing the selected item in {@link Settings#vbOptButtons} with {@link Settings#updateOveModel(byte)}.
	 * @param selectedItem The text of the new selected item.
	 */
	public void setSelectedItem(String selectedItem) {
		lSelectedItem.setText(selectedItem);
		vbContent.setPrefHeight(Main.calcHeightLabel(lHeading, width) + Main.calcHeightLabel(lSelectedItem, width) + vbContent.getSpacing() + 8);
		vbContent.getChildren().addAll(lHeading, lSelectedItem);
		this.setPrefHeight(vbContent.getPrefHeight() + 2);
		this.setGraphic(vbContent);
	}
	
	
	/**
	 * Re-builds the button. Gets usually called when the {@link #width width} needs to be re-set. 
	 * See {@link #OverviewButton(double, String, String)} for more information.
	 * @param width The new {@link #width width} of the button.
	 * @param heading The new text for the {@link #lHeading heading} of the button.
	 * @param selectedItem The new text for the {@link #lSelectedItem selected item} (a {@link ExperimentElement}) which should be displayed.
	 * This parameter can additionally be re-set in {@link #setSelectedItem(String)}.
	 */
	public void rebuild(double width, String heading, String selectedItem) {
		this.setPrefWidth(width);
		lHeading.setText(heading);
		lSelectedItem.setText(selectedItem);
		this.width = width - 10;
		vbContent.setPrefHeight(Main.calcHeightLabel(lHeading, this.width) + Main.calcHeightLabel(lSelectedItem, this.width) + vbContent.getSpacing() + 8);
		vbContent.getChildren().addAll(lHeading, lSelectedItem);
		this.setPrefHeight(vbContent.getPrefHeight() + 2);
		this.setGraphic(vbContent);
	}
}
