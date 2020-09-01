package environment;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

/**
 * @author Wolkenfarmer
 */
public class ModelFactory {
	static double modelZoneWidth;
	static double modelButtonWidth;
	Button b;
	VBox vbContent;
		HBox hbConName;
			Label lConName;
		Line lConDiffer;
		Label lConSelectedItemHead;
		Label lConSelectedItem;

	public ModelFactory(double contentWidth) {
		modelZoneWidth = contentWidth / 13;
		modelButtonWidth = modelZoneWidth * 2;
	}
	
	public Button buildButton(float layoutZoneX, float layoutZoneY, String conName) {
		b = new Button();
		b.setLayoutX(layoutZoneX * modelZoneWidth);
		b.setLayoutY(layoutZoneY * 50);
		b.setPrefWidth(modelButtonWidth);
		b.setBackground(Main.baNormalButton);
		b.setBorder(Main.boNormalWhite);
			vbContent = new VBox();
			vbContent.setSpacing(10);
				hbConName = new HBox();
					lConName = new Label();
					lConName.setText(conName);
					lConName.setTextFill(Color.WHITESMOKE);
					lConName.setFont(Main.fNormalText);
					lConName.setWrapText(true);
					lConName.setTextAlignment(TextAlignment.CENTER);
					lConName.setPrefHeight(calcHeightLabel(lConName, modelButtonWidth));
				hbConName.getChildren().add(lConName);
				hbConName.setAlignment(Pos.CENTER);
				
				lConDiffer = new Line();
				lConDiffer.setStroke(Color.WHITESMOKE);
				lConDiffer.setEndX(modelButtonWidth - 40);
				lConDiffer.setTranslateX(8);
				
				lConSelectedItemHead = new Label();
				lConSelectedItemHead.setText("selected item:");
				lConSelectedItemHead.setTextFill(Color.WHITESMOKE);
				lConSelectedItemHead.setFont(Main.fSmallText);
				lConSelectedItemHead.setWrapText(true);
				lConSelectedItemHead.setPrefHeight(calcHeightLabel(lConSelectedItemHead, modelButtonWidth));
				
				lConSelectedItem = new Label();
				lConSelectedItem.setText("nothing selected");
				lConSelectedItem.setTextFill(Color.INDIANRED);
				lConSelectedItem.setFont(Main.fSmallTextItalic);
				lConSelectedItem.setWrapText(true);
				lConSelectedItem.setPadding(new Insets(-5, 0, 0, 10));
				lConSelectedItem.setPrefHeight(calcHeightLabel(lConSelectedItem, modelButtonWidth));
			vbContent.getChildren().addAll(hbConName, lConDiffer, 
					lConSelectedItemHead, lConSelectedItem);
		b.setGraphic(vbContent);
		b.setPrefHeight(calcHeight(vbContent) + 20);
		
		return b;
	}
	
	public double calcHeight(Region r) {
		Main.dummyRoot.getChildren().add(r);
		Main.dummyRoot.applyCss();
		Main.dummyRoot.layout();
		Main.dummyRoot.getChildren().remove(r);
		return r.getHeight();
	}
	
	public double calcHeightLabel(Label l, double parentWidth) {
		Main.dummyRoot.getChildren().add(l);
		Main.dummyRoot.applyCss();
		Main.dummyRoot.layout();
		double lines = Math.ceil(l.getWidth() / (parentWidth - 20));
		double height = (l.getHeight() * lines) + ((l.getLineSpacing() + (l.getFont().getSize() * 1.2)) * (lines - 1));
		Main.dummyRoot.getChildren().remove(l);
		return height;
	}
}
