package environment;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

/**
 * The homepage of the application with access to every part of the program. 
 * @author Wolkenfarmer
 */
public class Homepage {
	Scene dummyScene;
	Group dummyRoot;
	HBox hbHeadline;
		Label lHeadline;
		Region rHeadlineSpacer;
		VBox vbHeadline;
			Label lHeadlineVersion;
			Label lHeadlineBy;
	Pane pSettings;
		Label lSetHeadline;
		Pane pSetModel;
			Button bSetModSource;
				VBox vbSetModSouContent;
					Label lSetModSouConName;
					Line lSetModSouConDiffer;
					Label lSetModSouConSelectedItemHead;
					Label lSetModSouConSelectedItem;
			Button bSetModEncoder;
			Button bSetModNoise;
			Button bSetModDecoder;
			Button bSetModDestination;
	Group gResults;
	Group gButtons;
	
	/**
	 * Builds the homepage of the application.
	 * @param root A group to attach it's layout parts to.
	 */
	public Homepage(Group root) {
		double pos1 = Main.stageWidth / 8;
		double pos7 = Main.stageWidth / 8 * 7;
		double contentWidth = pos7 - pos1;
		dummyScene = new Scene(dummyRoot = new Group());
		
		hbHeadline = new HBox();
		hbHeadline.setLayoutX(pos1);
		hbHeadline.setLayoutY(pos1 / 3);
		hbHeadline.setPrefWidth(contentWidth);
			lHeadline = new Label();
			lHeadline.setText("Source / Channel Coding Environment");
			lHeadline.setTextFill(Color.WHITESMOKE);
			lHeadline.setFont(Main.fHeadline);
			lHeadline.setAlignment(Pos.CENTER_LEFT);
			
			rHeadlineSpacer = new Region();
			HBox.setHgrow(rHeadlineSpacer, Priority.ALWAYS);
			
			vbHeadline = new VBox();
			vbHeadline.setAlignment(Pos.CENTER_RIGHT);
				lHeadlineVersion = new Label();
				lHeadlineVersion.setText("Version 1.0");
				lHeadlineVersion.setTextFill(Color.WHITESMOKE);
				lHeadlineVersion.setFont(Main.fNormalText);
				lHeadlineVersion.setAlignment(Pos.TOP_RIGHT);
				
				lHeadlineBy = new Label();
				lHeadlineBy.setText("by Wolkenfarmer");
				lHeadlineBy.setTextFill(Color.WHITESMOKE);
				lHeadlineBy.setFont(Main.fSmallText);
				lHeadlineBy.setAlignment(Pos.BOTTOM_RIGHT);
			vbHeadline.getChildren().addAll(lHeadlineVersion, lHeadlineBy);
		hbHeadline.getChildren().addAll(lHeadline, rHeadlineSpacer, vbHeadline);
		
		
		pSettings = new Pane();
		pSettings.setLayoutX(pos1);
		pSettings.setLayoutY(hbHeadline.getLayoutY() + 120);
		pSettings.setPrefWidth(contentWidth);
			lSetHeadline = new Label();
			lSetHeadline.setText("Settings");
			lSetHeadline.setTextFill(Color.WHITESMOKE);
			lSetHeadline.setFont(Main.fSubheadline);
			
			pSetModel = new Pane();
			pSetModel.setLayoutY(60);
				double modelRelationWidth = contentWidth / 13;
				double modelButtonWidth = modelRelationWidth * 2;
				
				bSetModSource = new Button();
				bSetModSource.setLayoutX(0);
				bSetModSource.setPrefWidth(modelButtonWidth);
				bSetModSource.setBackground(Main.baNormalButton);
				bSetModSource.setBorder(Main.boNormalWhite);
					vbSetModSouContent = new VBox();
					vbSetModSouContent.setSpacing(10);
						lSetModSouConName = new Label();
						lSetModSouConName.setText("information source");
						lSetModSouConName.setTextFill(Color.WHITESMOKE);
						lSetModSouConName.setFont(Main.fNormalText);
						lSetModSouConName.setWrapText(true);
						lSetModSouConName.setTextAlignment(TextAlignment.CENTER);
						lSetModSouConName.setPrefHeight(calcHeightLabel(lSetModSouConName, modelButtonWidth));
						
						lSetModSouConDiffer = new Line();
						lSetModSouConDiffer.setStroke(Color.WHITESMOKE);
						lSetModSouConDiffer.setEndX(modelButtonWidth - 40);
						lSetModSouConDiffer.setTranslateX(8);
						
						lSetModSouConSelectedItemHead = new Label();
						lSetModSouConSelectedItemHead.setText("selected item:");
						lSetModSouConSelectedItemHead.setTextFill(Color.WHITESMOKE);
						lSetModSouConSelectedItemHead.setFont(Main.fSmallText);
						lSetModSouConSelectedItemHead.setWrapText(true);
						lSetModSouConSelectedItemHead.setPrefHeight(calcHeightLabel(lSetModSouConSelectedItemHead, modelButtonWidth));
						
						lSetModSouConSelectedItem = new Label();
						lSetModSouConSelectedItem.setText("nothing selected");
						lSetModSouConSelectedItem.setTextFill(Color.INDIANRED);
						lSetModSouConSelectedItem.setFont(Main.fSmallTextItalic);
						lSetModSouConSelectedItem.setWrapText(true);
						lSetModSouConSelectedItem.setPadding(new Insets(-5, 0, 0, 10));
						lSetModSouConSelectedItem.setPrefHeight(calcHeightLabel(lSetModSouConSelectedItem, modelButtonWidth));
					vbSetModSouContent.getChildren().addAll(lSetModSouConName, lSetModSouConDiffer, 
							lSetModSouConSelectedItemHead, lSetModSouConSelectedItem);
				bSetModSource.setGraphic(vbSetModSouContent);
				bSetModSource.setPrefHeight(calcHeight(vbSetModSouContent) + 20);
				System.out.println(calcHeight(vbSetModSouContent));
			pSetModel.getChildren().addAll(bSetModSource);
		pSettings.getChildren().addAll(lSetHeadline, pSetModel);
		
		
		root.getChildren().addAll(hbHeadline, pSettings);
		
		Main.contentHeight = lHeadline.getLayoutY() + pos1 / 3;
		Main.scrollbar.setMax(Main.contentHeight - Main.scene.getHeight());
		Main.scrollbar.setBlockIncrement(Main.contentHeight);
        if (Main.scene.getHeight() >= Main.contentHeight) {Main.scrollbar.setVisible(false);}
	}
	
	public double calcHeight(Region r) {
		dummyRoot.getChildren().add(r);
		dummyRoot.applyCss();
		dummyRoot.layout();
		dummyRoot.getChildren().remove(r);
		return r.getHeight();
	}
	
	public double calcHeightLabel(Label l, double parentWidth) {
		dummyRoot.getChildren().add(l);
		dummyRoot.applyCss();
		dummyRoot.layout();
		double lines = Math.ceil(l.getWidth() / (parentWidth - 10));
		System.out.println(l.getFont().getSize() / 2);
		double height = (l.getHeight() * lines) + ((l.getLineSpacing() + (l.getFont().getSize() / 2)) * (lines - 1));
		dummyRoot.getChildren().remove(l);
		return height;
	}

}
