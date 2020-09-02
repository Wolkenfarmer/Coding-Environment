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
 * Seperate class to build the buttons and relations of the model in settings in {@link environment.Homepage}.
 * @author Wolkenfarmer
 * @see environment.Homepage#pSetModel
 */
public class ModelFactory {
	/** The width of a zone in {@link environment.Homepage#pSetModel}. 
	 * This gets calculated in {@link #ModelFactory(double)} and used in {@link #buildButton(float, float, String, boolean)}.*/
	static double modelZoneWidth;
	/** The width of a button in {@link environment.Homepage#pSetModel}. 
	 * This gets calculated in {@link #ModelFactory(double)} and used in {@link #buildButton(float, float, String, boolean)}.*/
	static double modelButtonWidth;
	static double modelZoneHeight = 50;
	
	/** The button which is to be build. Contains {@link #vbContent} as it's labeling / content.*/
	Button b;
	/** Layout container for the buttons labeling / content. 
	 * Contains {@link #hbConName}, {@link #lConDiffer} and either {@link #lConSelectedItemHead} and {@link #lConSelectedItem} or {@link #lConSub}
	 * and is part of {@link #b}.*/
	VBox vbContent;
		/** Layout container for the buttons heading.
		 * This is needed in order to align the heading the center of the button even when the heading is just one line. 
		 * Contains {@link #lConName} and is part of {@link #vbContent}.*/
		HBox hbConName;
			/** Label which displays the heading of {@link #b}. It's part of {@link #hbConName}.*/
			Label lConName;
		/** A line to differ between {@link #lConName} and the rest of {@link #b}'s content. It's part of {@link #vbContent}.*/
		Line lConDiffer;
		/** Label which displays "Selected item:" on {@link #b}. Either this and {@link #lConSelectedItem} get displayed or {@link #lConSub}.
		 * It's part of {@link #vbContent}.*/
		Label lConSelectedItemHead;
		/** Label which displays the selected item on {@link #b}. Either this and {@link #lConSelectedItemHead} get displayed or {@link #lConSub}.
		 * It's part of {@link #vbContent}.*/
		Label lConSelectedItem;
		/** Label which displays "compare in- and output" on {@link #b}. Either this gets displayed 
		 * or {@link #lConSelectedItemHead} and {@link #lConSelectedItem}. It's part of {@link #vbContent}.*/
		Label lConSub;

	Arrow r;
		
	/**
	 * Constructor of the class which devides the given contentWidth into different zones for the obejcts.
	 * @param contentWidth Gives the width of the parent object.
	 */
	public ModelFactory(double contentWidth) {
		modelZoneWidth = contentWidth / 13;
		modelButtonWidth = modelZoneWidth * 2;
	}
	
	/**
	 * Builds the buttons for {@link environment.Homepage#pSetModel}. 
	 * It uses the {@link environment.Main#calcHeight(Region)} and {@link environment.Main#calcHeightLabel(Label, double)} methods 
	 * for some of it's calculations.
	 * @param layoutZoneX Defines the layoutX multiplied by {@link #modelZoneWidth}.
	 * @param layoutZoneY Defines the layoutY multiplied by 50.
	 * @param conName Sets the heading of the button.
	 * @param selectableItems Defines whether the button should be able to present selected items like {@link environment.Homepage#bSetModSource} or
	 * not like {@link environment.Homepage#bSetModDestination}.
	 * @return Returns the finished button.
	 */
	public Button buildButton(float layoutZoneX, float layoutZoneY, String conName, boolean selectableItems) {
		b = new Button();
		b.setLayoutX(layoutZoneX * modelZoneWidth);
		b.setLayoutY(layoutZoneY * modelZoneHeight);
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
					lConName.setPrefHeight(Main.calcHeightLabel(lConName, modelButtonWidth));
				hbConName.getChildren().add(lConName);
				hbConName.setAlignment(Pos.CENTER);
				
				lConDiffer = new Line();
				lConDiffer.setStroke(Color.WHITESMOKE);
				lConDiffer.setEndX(modelButtonWidth - 40);
				lConDiffer.setTranslateX(8);
				
				if (selectableItems) {
					lConSelectedItemHead = new Label();
					lConSelectedItemHead.setText("selected item:");
					lConSelectedItemHead.setTextFill(Color.WHITESMOKE);
					lConSelectedItemHead.setFont(Main.fSmallText);
					lConSelectedItemHead.setWrapText(true);
					lConSelectedItemHead.setPrefHeight(Main.calcHeightLabel(lConSelectedItemHead, modelButtonWidth));
					
					lConSelectedItem = new Label();
					lConSelectedItem.setText("nothing selected");
					lConSelectedItem.setTextFill(Color.INDIANRED);
					lConSelectedItem.setFont(Main.fSmallTextItalic);
					lConSelectedItem.setWrapText(true);
					lConSelectedItem.setPadding(new Insets(-5, 0, 0, 10));
					lConSelectedItem.setPrefHeight(Main.calcHeightLabel(lConSelectedItem, modelButtonWidth));
					
					vbContent.getChildren().addAll(hbConName, lConDiffer, lConSelectedItemHead, lConSelectedItem);
				} else {
					lConSub = new Label();
					lConSub.setText("compare in- & output");
					lConSub.setTextFill(Color.WHITESMOKE);
					lConSub.setFont(Main.fSmallText);
					lConSub.setWrapText(true);
					lConSub.setPrefHeight(Main.calcHeightLabel(lConSub, modelButtonWidth));
					
					vbContent.getChildren().addAll(hbConName, lConDiffer, lConSub);
				}
				
				
		b.setGraphic(vbContent);
		b.setPrefHeight(Main.calcHeight(((Region) vbContent)) + 20);
		
		return b;
	}
	
	
	public Arrow buildRelation(float layoutZoneX, float layoutZoneY, short lenght, boolean vertical, String name) {
		double startX, startY, endX, endY;
		if (vertical) {
			startX = layoutZoneX * modelZoneWidth;
			startY = layoutZoneY * modelZoneHeight - 5;
			endX = startX;
			endY = layoutZoneY * modelZoneHeight - lenght * modelZoneHeight +5;
		} else {
			startX = layoutZoneX * modelZoneWidth + 5;
			startY = layoutZoneY * modelZoneHeight;
			endX = layoutZoneX * modelZoneWidth + lenght * modelZoneWidth -5;
			endY = startY;
		}
		
		r = new Arrow(startX, startY, endX, endY, 10, 10, vertical, name);
		return r.getArrow();
	}
}
