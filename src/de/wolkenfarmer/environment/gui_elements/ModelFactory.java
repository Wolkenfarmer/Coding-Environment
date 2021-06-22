package de.wolkenfarmer.environment.gui_elements;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.logic.Main;
import de.wolkenfarmer.environment.pages.Home;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

/**
 * Separate class to build the buttons and relations of the model in settings in {@link de.wolkenfarmer.environment.pages.Home}.
 * @author Wolkenfarmer
 * @see de.wolkenfarmer.environment.pages.Home#pSetModel
 */
public class ModelFactory {
	/** The width of a zone in {@link de.wolkenfarmer.environment.pages.Home#pSetModel}. 
	 * This gets calculated in {@link #ModelFactory(double)} and 
	 * used in {@link #buildButton(float, float, byte)} and {@link #buildRelation(float, float, short, boolean, String)}.*/
	private static double modelZoneWidth;
	/** The width of a button in {@link de.wolkenfarmer.environment.pages.Home#pSetModel}. 
	 * This gets calculated in {@link #ModelFactory(double)} and 
	 * used in {@link #buildButton(float, float, byte)}.*/
	private static double modelButtonWidth;
	/** The height of a zone in {@link de.wolkenfarmer.environment.pages.Home#pSetModel}. 
	 * This gets used in {@link #buildButton(float, float, byte)} and {@link #buildRelation(float, float, short, boolean, String)}.*/
	private static double modelZoneHeight = 50;
	
	/** The button which is to be build. Contains {@link #vbContent} as it's labeling / content.*/
	private Button b;
	/** Layout container for the buttons labeling / content. 
	 * Contains {@link #hbConName}, {@link #liConDiffer} and either {@link #lConSelectedItemHead} and lConSelectedItem 
	 * (gets declared in {@link #buildButton(float, float, byte)} because otherwise it wouldn't be possible to change the selected Item
	 * via the button's listener) or {@link #lConSub} and is part of {@link #b}.*/
	private VBox vbContent;
		/** Layout container for the buttons heading.
		 * This is needed in order to align the heading the center of the button even when the heading is just one line. 
		 * Contains {@link #lConName} and is part of {@link #vbContent}.*/
		private HBox hbConName;
			/** Label which displays the heading of {@link #b}. It's part of {@link #hbConName}.*/
			private Label lConName;
		/** A line to differ between {@link #lConName} and the rest of {@link #b}'s content. It's part of {@link #vbContent}.*/
		private Line liConDiffer;
		/** Label which displays "Selected item:" on {@link #b}. Either this and lConSelectedItem (see {@link #vbContent} for more information)
		 * get displayed or {@link #lConSub}. It's part of {@link #vbContent}.*/
		private Label lConSelectedItemHead;
		/** Label which displays the selected item on {@link #b}. Either this and {@link #lConSelectedItemHead} get displayed or {@link #lConSub}.
		 * It's part of {@link #vbContent}.*/
		private Label lConSelectedItem;
		/** Label which displays "compare in- and output" on {@link #b}. Either this gets displayed 
		 * or {@link #lConSelectedItemHead} and lConSelectedItem (see {@link #vbContent} for more information). It's part of {@link #vbContent}.*/
		private Label lConSub;

		
	/**
	 * Constructor of the class which divides the given contentWidth into different zones for the objects.
	 * @param contentWidth Gives the width of the parent object.
	 */
	public ModelFactory(double contentWidth) {
		modelZoneWidth = contentWidth / 13;
		modelButtonWidth = modelZoneWidth * 2;
	}
	
	/**
	 * Builds the buttons for {@link de.wolkenfarmer.environment.pages.Home#pSetModel}. 
	 * It uses the {@link de.wolkenfarmer.environment.logic.Main#calcHeight(Region)} and {@link de.wolkenfarmer.environment.logic.Main#calcHeightLabel(Label, double)} methods 
	 * for some of it's calculations.<br>
	 * For setting the text of {@link #lConSelectedItem} {@link Main#selectedInputHandler}, {@link Main#selectedTranscoder} and
	 * {@link Main#selectedNoiSource} get used depending on the type.
	 * @param layoutZoneX Defines the layoutX multiplied by {@link #modelZoneWidth}.
	 * @param layoutZoneY Defines the layoutY multiplied by 50.
	 * @param type Specifies which button will be build, because each button of {@link Home#pSetModel} needs it's own {@link #lConName} text
	 * and {@link #lConSelectedItem} text source (or even no at all).
	 * @return Returns the finished button.
	 */
	public Button buildButton(float layoutZoneX, float layoutZoneY, byte type) {
		b = new Button();
		b.setLayoutX(layoutZoneX * modelZoneWidth);
		b.setLayoutY(layoutZoneY * modelZoneHeight);
		b.setPrefWidth(modelButtonWidth);
		b.setBackground(Constants.BG_GRAY);
		b.setBorder(Constants.B_NORMAL);
			vbContent = new VBox();
			vbContent.setSpacing(10);
				hbConName = new HBox();
					lConName = new Label();
					switch (type) {
					case 0:
						lConName.setText("input handler");
						break;
					case 1:
						lConName.setText("encoder");
						break;
					case 2:
						lConName.setText("noise source");
						break;
					case 3:
						lConName.setText("decoder");
						break;
					case 4:
						lConName.setText("destination");
						break;
					default:
						lConName.setText("button type not found");
					}
					lConName.setTextFill(Constants.C_NORMAL);
					lConName.setFont(Constants.F_NORMAL);
					lConName.setWrapText(true);
					lConName.setTextAlignment(TextAlignment.CENTER);
					lConName.setPrefHeight(Main.calcHeightLabel(lConName, modelButtonWidth));
					lConName.setPrefWidth(modelButtonWidth - 10);
					lConName.setAlignment(Pos.CENTER);
				hbConName.getChildren().add(lConName);
				
				liConDiffer = new Line();
				liConDiffer.setStroke(Constants.C_NORMAL);
				liConDiffer.setEndX(modelButtonWidth - 40);
				liConDiffer.setTranslateX(8);
				
				if (type != 4) {
					lConSelectedItemHead = new Label();
					lConSelectedItemHead.setText("selected item:");
					lConSelectedItemHead.setTextFill(Constants.C_NORMAL);
					lConSelectedItemHead.setFont(Constants.F_SMALL);
					lConSelectedItemHead.setWrapText(true);
					lConSelectedItemHead.setPrefHeight(Main.calcHeightLabel(lConSelectedItemHead, modelButtonWidth));
					
					lConSelectedItem = new Label();
					lConSelectedItem.setText("nothing selected");
					switch (type) {
					case 0:	
						lConSelectedItem.setText(Main.selectedInputHandler.getName(false));
						break;
					case 1:
					case 3:
						lConSelectedItem.setText(Main.selectedTranscoder.getName(false));
						break;
					case 2:
						lConSelectedItem.setText(Main.selectedNoiSource.getName(false));
						break;
					default:
						lConSelectedItem.setText("button type not found");
					}
					lConSelectedItem.setTextFill(Constants.C_PINK);
					lConSelectedItem.setFont(Constants.F_SMALL_ITALIC);
					lConSelectedItem.setWrapText(true);
					lConSelectedItem.setPrefHeight(Main.calcHeightLabel(lConSelectedItem, modelButtonWidth));
					
					vbContent.getChildren().addAll(hbConName, liConDiffer, lConSelectedItemHead, lConSelectedItem);
				} else {
					lConSub = new Label();
					lConSub.setText("in development");
					lConSub.setTextFill(Constants.C_NORMAL);
					lConSub.setFont(Constants.F_SMALL_ITALIC);
					lConSub.setWrapText(true);
					lConSub.setPrefHeight(Main.calcHeightLabel(lConSub, modelButtonWidth));
					
					vbContent.getChildren().addAll(hbConName, liConDiffer, lConSub);
				}
				
		b.setGraphic(vbContent);
		b.setPrefHeight(Main.calcHeight(vbContent) + 20);
		
		return b;
	}
	
	/**
	 * Builds the buttons for {@link de.wolkenfarmer.environment.pages.Home#pSetModel}. 
	 * It uses {@link Arrow#Arrow(double, double, double, double, double, double, boolean, String, double) Arrow()} 
	 * to create the arrow. This method calculates the values to create the arrow via the class and returns it.
	 * @param layoutZoneX Defines the x coordinate of the start of the arrow multiplied by {@link #modelZoneWidth}.
	 * @param layoutZoneY Defines the y coordinate of the start of the arrow multiplied by {@link #modelZoneHeight}.
	 * @param lenght Defines the length of the arrow multiplied by either {@link #modelZoneWidth} or {@link #modelZoneHeight}.
	 * @param vertical Defines whether the arrow should be displayed horizontal (from left to right) or vertical (from bottom to top).
	 * @param name The description of the arrow which will be displayed above it (only if horizontal).
	 * @return Returns the finished relation.
	 */
	public Arrow buildRelation(float layoutZoneX, float layoutZoneY, short lenght, boolean vertical, String name) {
		double startX, startY, endX, endY;
		if (vertical) {
			startX = layoutZoneX * modelZoneWidth;
			startY = layoutZoneY * modelZoneHeight;
			endX = startX;
			endY = layoutZoneY * modelZoneHeight - lenght * modelZoneHeight;
		} else {
			startX = layoutZoneX * modelZoneWidth;
			startY = layoutZoneY * modelZoneHeight;
			endX = layoutZoneX * modelZoneWidth + lenght * modelZoneWidth;
			endY = startY;
		}
		
		return new Arrow(startX, startY, endX, endY, 10, 10, vertical, name, 0);
	}
}
