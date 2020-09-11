package environment;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @author Wolkenfarmer
 */
public class OverviewButton extends Button {
	private double width;
	/** Layout container for the content of the button. 
	 * Contains {@link #lOveModEnDeHeading} and is part of {@link #bOveModEnDecoder}.*/
	private VBox vbContent;
		/** Label displaying the button's heading. It's part of {@link #vbOveModEnDeHeading}.*/
		private Label lHeading;
		/** Label displaying the button's selected item. It's part of {@link #vbOveModEnDeHeading}.*/
		private Label lSelectedItem;
		
	public OverviewButton(double width, String heading, String selectedItem) {
		this.setPrefWidth(width);
		this.setBackground(Main.baNormalButton);
		this.setBorder(Main.boNormal);
			vbContent = new VBox();
			vbContent.setSpacing(5);
				lHeading = new Label();
				lHeading.setText(heading);
				lHeading.setTextFill(Color.WHITESMOKE);
				lHeading.setFont(Main.fNormalText);
				lHeading.setWrapText(true);
				lHeading.setTextAlignment(TextAlignment.CENTER);
				lHeading.setAlignment(Pos.CENTER);
				
				lSelectedItem = new Label();
				lSelectedItem.setText(selectedItem);
				lSelectedItem.setTextFill(Color.INDIANRED);
				lSelectedItem.setFont(Main.fSmallText);
				lSelectedItem.setWrapText(true);
				lSelectedItem.setTextAlignment(TextAlignment.CENTER);
				lSelectedItem.setAlignment(Pos.CENTER);
			this.width = width - 10;
			vbContent.setPrefHeight(Main.calcHeightLabel(lHeading, this.width) + Main.calcHeightLabel(lSelectedItem, this.width) + vbContent.getSpacing() + 8);
			vbContent.getChildren().addAll(lHeading, lSelectedItem);
			vbContent.setAlignment(Pos.CENTER);
		this.setPrefHeight(vbContent.getPrefHeight() + 2);
		this.setGraphic(vbContent);
	}
	
	public double getHeightW() {
		return this.getPrefHeight();
	}
	
	public void setSelectedItem(String selectedItem) {
		lSelectedItem.setText(selectedItem);
		vbContent.setPrefHeight(Main.calcHeightLabel(lHeading, width) + Main.calcHeightLabel(lSelectedItem, width) + vbContent.getSpacing() + 8);
		vbContent.getChildren().addAll(lHeading, lSelectedItem);
		this.setPrefHeight(vbContent.getPrefHeight() + 2);
		this.setGraphic(vbContent);
	}
	
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
