package environment;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * The homepage of the application with access to every part of the program. 
 * @author Wolkenfarmer
 */
public class Homepage {
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
					HBox hbSetModSouConName;
						Label lSetModSouConName;
					Line lSetModSouConDiffer;
					Label lSetModSouConSelectedItemHead;
					Label lSetModSouConSelectedItem;
			Button bSetModEncoder;
				VBox vbSetModEncContent;
					HBox hbSetModEncConName;
						Label lSetModEncConName;
					Line lSetModEncConDiffer;
					Label lSetModEncConSelectedItemHead;
					Label lSetModEncConSelectedItem;
			Button bSetModNoise;
				VBox vbSetModNoiContent;
					HBox hbSetModNoiConName;
						Label lSetModNoiConName;
					Line lSetModNoiConDiffer;
					Label lSetModNoiConSelectedItemHead;
					Label lSetModNoiConSelectedItem;
			Button bSetModDecoder;
				VBox vbSetModDecContent;
					HBox hbSetModDecConName;
						Label lSetModDecConName;
					Line lSetModDecConDiffer;
					Label lSetModDecConSelectedItemHead;
					Label lSetModDecConSelectedItem;
			Button bSetModDestination;
				VBox vbSetModDesContent;
					HBox hbSetModDesConName;
						Label lSetModDesConName;
					Line lSetModDesConDiffer;
					Label lSetModDesConSelectedItemHead;
					Label lSetModDesConSelectedItem;
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
				ModelFactory cSetModelFactory = new ModelFactory(contentWidth); 
				bSetModSource = cSetModelFactory.buildButton(0, 0, "information source");
				bSetModEncoder = cSetModelFactory.buildButton(3, 0, "encoder");
				bSetModNoise = cSetModelFactory.buildButton(5.5f, 2, "noise source");
				bSetModDecoder = cSetModelFactory.buildButton(8, 0, "decoder");
				bSetModDestination = cSetModelFactory.buildButton(11, 0, "destination");
			pSetModel.getChildren().addAll(bSetModSource, bSetModEncoder, bSetModNoise, bSetModDecoder, bSetModDestination);
		pSettings.getChildren().addAll(lSetHeadline, pSetModel);
		
		
		root.getChildren().addAll(hbHeadline, pSettings);
		
		Main.contentHeight = lHeadline.getLayoutY() + pos1 / 3;
		Main.scrollbar.setMax(Main.contentHeight - Main.scene.getHeight());
		Main.scrollbar.setBlockIncrement(Main.contentHeight);
        if (Main.scene.getHeight() >= Main.contentHeight) {Main.scrollbar.setVisible(false);}
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
		double lines = Math.ceil(l.getWidth() / (parentWidth - 10));
		double height = (l.getHeight() * lines) + ((l.getLineSpacing() + (l.getFont().getSize() * 1.2)) * (lines - 1));
		Main.dummyRoot.getChildren().remove(l);
		return height;
	}
}
