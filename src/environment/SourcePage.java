package environment;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * @author Wolkenfarmer
 */
public class SourcePage {
	private static Group root;
	private static EventHandler<KeyEvent> keyReleasedListener;
	private static TextFlow tfHeading;
		private static Label lHeaHome;
		private static Label lHeaHere;
	private static Pane pOverview;
		private static Label lOveHeading;
		private static Pane pOveModel;
			private static Button bOveModSource;
				private static HBox hbOveModSouContent;
					private static VBox vbOveModSouConHeading;
						private static Label lOveModSouConHeading;
					private static Line lOveModSouConDiffer;
					private static VBox vbOveModSouSelectedItem;
						private static Label lConSelectedItemHead;
						private static Label lConSelectedItem;
			private static Button bOveModDecoder;
				private static VBox vbOveModDecHeading;
					private static Label lOveModDecHeading;
			private static Arrow a;
				

	public SourcePage(Group parent) {
		root = parent;
		
		tfHeading = new TextFlow();
		tfHeading.setLayoutX(Main.pos1);
		tfHeading.setLayoutY(Main.pos1 / 3);
		tfHeading.setPrefWidth(Main.contentWidth);
			lHeaHome = new Label();
			lHeaHome.setText("SCCE \\  ");
			lHeaHome.setTextFill(Color.WHITESMOKE);
			lHeaHome.setFont(Main.fHeadline);
			lHeaHome.setAlignment(Pos.CENTER_LEFT);
				
			lHeaHere = new Label();
			lHeaHere.setText("Information Source");
			lHeaHere.setTextFill(Color.WHITESMOKE);
			lHeaHere.setFont(Main.fHeading);
			lHeaHere.setAlignment(Pos.CENTER_LEFT);
		tfHeading.getChildren().addAll(lHeaHome, lHeaHere);
		
		
		pOverview = new Pane();
		pOverview.setLayoutX(Main.pos1);
		pOverview.setLayoutY(tfHeading.getLayoutY() + Main.distanceToHeading);
		pOverview.setPrefWidth(Main.contentWidth);
			lOveHeading = new Label();
			lOveHeading.setText("Overview");
			lOveHeading.setTextFill(Color.WHITESMOKE);
			lOveHeading.setFont(Main.fSubheading);
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Main.distanceToSubheading);
				bOveModSource = new Button();
				bOveModSource.setBackground(Main.baNormalButton);
				bOveModSource.setBorder(Main.boNormalWhite);
				bOveModSource.setMaxWidth(Main.stageWidth / 2);
					hbOveModSouContent = new HBox();
					hbOveModSouContent.setSpacing(20);
					hbOveModSouContent.setPadding(new Insets(5));
						vbOveModSouConHeading = new VBox();
						vbOveModSouConHeading.setMaxWidth(Main.stageWidth / 2 / 3);
							lOveModSouConHeading = new Label();
							lOveModSouConHeading.setText("Information Source");
							lOveModSouConHeading.setTextFill(Color.WHITESMOKE);
							lOveModSouConHeading.setFont(Main.fNormalText);
							lOveModSouConHeading.setWrapText(true);
							lOveModSouConHeading.setTextAlignment(TextAlignment.CENTER);
							lOveModSouConHeading.setPrefHeight(Main.calcHeightLabel(lOveModSouConHeading, vbOveModSouConHeading.getMaxWidth()));
							lOveModSouConHeading.setPrefWidth(Main.calcWidthLabel(lOveModSouConHeading) + 20);
						vbOveModSouConHeading.getChildren().add(lOveModSouConHeading);
						vbOveModSouConHeading.setAlignment(Pos.CENTER_LEFT);
						
						lOveModSouConDiffer = new Line();
						lOveModSouConDiffer.setStroke(Color.WHITESMOKE);
						
						vbOveModSouSelectedItem = new VBox();
						vbOveModSouSelectedItem.setMaxWidth(bOveModSource.getMaxWidth() / 3 * 2 - 
								(hbOveModSouContent.getSpacing() + hbOveModSouContent.getPadding().getLeft() * 2));
							lConSelectedItemHead = new Label();
							lConSelectedItemHead.setText("selected item:");
							lConSelectedItemHead.setTextFill(Color.WHITESMOKE);
							lConSelectedItemHead.setFont(Main.fSmallText);
							lConSelectedItemHead.setWrapText(true);
							lConSelectedItemHead.setPrefHeight(Main.calcHeightLabel(lConSelectedItemHead, 
									Main.stageWidth / 2 - (hbOveModSouContent.getSpacing() + hbOveModSouContent.getPadding().getLeft() * 2)
									- vbOveModSouSelectedItem.getPrefWidth()));
							
							lConSelectedItem = new Label();
							lConSelectedItem.setText("nothing selected");
							lConSelectedItem.setTextFill(Color.INDIANRED);
							lConSelectedItem.setFont(Main.fSmallTextItalic);
							lConSelectedItem.setWrapText(true);
							lConSelectedItem.setPadding(new Insets(0, 0, 0, 10));
							lConSelectedItem.setPrefHeight(Main.calcHeightLabel(lConSelectedItem, 
									Main.stageWidth / 2 - (hbOveModSouContent.getSpacing() + hbOveModSouContent.getPadding().getLeft() * 2)
									- vbOveModSouSelectedItem.getPrefWidth()));
						vbOveModSouSelectedItem.getChildren().addAll(lConSelectedItemHead, lConSelectedItem);
						vbOveModSouSelectedItem.setAlignment(Pos.CENTER_LEFT);
						
						bOveModSource.setPrefWidth(bOveModSource.getMaxWidth() 
								- ((vbOveModSouConHeading.getMaxWidth() - Main.calcWidth(vbOveModSouConHeading))
								+ (vbOveModSouSelectedItem.getMaxWidth() - Main.calcWidth(vbOveModSouSelectedItem))));
					hbOveModSouContent.getChildren().addAll(vbOveModSouConHeading, lOveModSouConDiffer, vbOveModSouSelectedItem);	
					double sourceButtonContentHeight = Main.calcHeight(hbOveModSouContent);
					lOveModSouConDiffer.setEndY(sourceButtonContentHeight - 10);
				bOveModSource.setGraphic(hbOveModSouContent);
			
				
				bOveModDecoder = new Button();
				bOveModDecoder.setBackground(Main.baNormalButton);
				bOveModDecoder.setBorder(Main.boNormalWhite);
				bOveModDecoder.setMaxWidth(Main.stageWidth - Main.calcWidth(bOveModSource) - Main.stageWidth / 16);
					vbOveModDecHeading = new VBox();
						lOveModDecHeading = new Label();
						lOveModDecHeading.setText("Decoder");
						lOveModDecHeading.setTextFill(Color.WHITESMOKE);
						lOveModDecHeading.setFont(Main.fNormalText);
						lOveModDecHeading.setWrapText(true);
						lOveModDecHeading.setTextAlignment(TextAlignment.CENTER);
						lOveModDecHeading.setPrefHeight(Main.calcHeightLabel(lOveModDecHeading, bOveModDecoder.getMaxWidth()));
					vbOveModDecHeading.getChildren().add(lOveModDecHeading);
					vbOveModDecHeading.setAlignment(Pos.CENTER_LEFT);
				bOveModDecoder.setPrefHeight(Main.calcHeight(bOveModSource) + 1);
				bOveModDecoder.setGraphic(vbOveModDecHeading);
				bOveModDecoder.setLayoutX(Main.pos1 * 6 - Main.calcWidth(bOveModDecoder));
				
				a = new Arrow();
				double yArrow = Main.calcHeight(bOveModSource) / 2;
				a = a.getArrow(Main.calcWidth(bOveModSource), yArrow, bOveModDecoder.getLayoutX(), yArrow, 20, 15, false, "message");
			pOveModel.getChildren().addAll(bOveModSource, a, bOveModDecoder);
		pOverview.getChildren().addAll(lOveHeading, pOveModel);
		
		
		Main.scene.setOnKeyReleased(keyReleasedListener = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (Main.input.contains("ESCAPE")) {
                	root.getChildren().removeAll(tfHeading, pOverview);
                	Main.homepage.reload(root);
                }
            }
        });
		
		root.getChildren().addAll(tfHeading, pOverview);
	}
	
	void reload(Group parent) {
		root = parent;
		root.getChildren().addAll(tfHeading, pOverview);
		Main.scene.setOnKeyReleased(keyReleasedListener);
	}
}
