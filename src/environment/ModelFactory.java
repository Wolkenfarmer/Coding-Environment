package environment;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
 * Separate class to build the buttons and relations of the model in settings in {@link environment.Homepage}.
 * @author Wolkenfarmer
 * @see environment.Homepage#pSetModel
 */
public class ModelFactory {
	/** The width of a zone in {@link environment.Homepage#pSetModel}. 
	 * This gets calculated in {@link #ModelFactory(double)} and used in {@link #buildButton(float, float, String, boolean)}.*/
	private static double modelZoneWidth;
	/** The width of a button in {@link environment.Homepage#pSetModel}. 
	 * This gets calculated in {@link #ModelFactory(double)} and 
	 * used in {@link #buildButton(float, float, String, boolean)} and {@link #buildRelation(float, float, short, boolean, String)}.*/
	private static double modelButtonWidth;
	/** The height of a zone in {@link environment.Homepage#pSetModel}. 
	 * This gets used in {@link #buildButton(float, float, String, boolean)} and {@link #buildRelation(float, float, short, boolean, String)}.*/
	private static double modelZoneHeight = 50;
	
	/** The button which is to be build. Contains {@link #vbContent} as it's labeling / content.*/
	private Button b;
	/** Layout container for the buttons labeling / content. 
	 * Contains {@link #hbConName}, {@link #liConDiffer} and either {@link #lConSelectedItemHead} and lConSelectedItem 
	 * (gets declared in {@link #buildButton(float, float, String, boolean)} because otherwise it wouldn't be possible to change the selected Item
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
		/** Label which displays "compare in- and output" on {@link #b}. Either this gets displayed 
		 * or {@link #lConSelectedItemHead} and lConSelectedItem (see {@link #vbContent} for more information). It's part of {@link #vbContent}.*/
		private Label lConSub;

		
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
		/** Label which displays the selected item on {@link #b}. Either this and {@link #lConSelectedItemHead} get displayed or {@link #lConSub}.
		 * It needs to be dined inside the method in order to change it's text from {@link environment.SourcePage} via {@link environment.Homepage}.
		 * It's part of {@link #vbContent}.*/
		Label lConSelectedItem;
		
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
					lConName.setPrefWidth(modelButtonWidth - 10);
				hbConName.getChildren().add(lConName);
				hbConName.setAlignment(Pos.CENTER);
				
				liConDiffer = new Line();
				liConDiffer.setStroke(Color.WHITESMOKE);
				liConDiffer.setEndX(modelButtonWidth - 40);
				liConDiffer.setTranslateX(8);
				
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
					
					b.textProperty().addListener(new ChangeListener<String>() {
						public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
							lConSelectedItem.setText(newValue);
							b.setText("");
						}
					});
					
					vbContent.getChildren().addAll(hbConName, liConDiffer, lConSelectedItemHead, lConSelectedItem);
				} else {
					lConSub = new Label();
					lConSub.setText("compare in- & output");
					lConSub.setTextFill(Color.WHITESMOKE);
					lConSub.setFont(Main.fSmallText);
					lConSub.setWrapText(true);
					lConSub.setPrefHeight(Main.calcHeightLabel(lConSub, modelButtonWidth));
					
					vbContent.getChildren().addAll(hbConName, liConDiffer, lConSub);
				}
				
		b.setGraphic(vbContent);
		b.setPrefHeight(Main.calcHeight(vbContent) + 20);
		
		return b;
	}
	
	/**
	 * Builds the buttons for {@link environment.Homepage#pSetModel}. 
	 * It uses {@link environment.Arrow#getArrow(double, double, double, double, double, double, boolean, String)} to create the arrow.
	 * This method calculates the values to create the arrow via the class and returns it.
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
		
		Arrow a = new Arrow();
		return a.getArrow(startX, startY, endX, endY, 10, 10, vertical, name);
	}
}
