package environment;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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

	public SourcePage(Group parent) {
		System.out.println("hello there");
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
		
		Main.scene.setOnKeyReleased(keyReleasedListener = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                if (Main.input.contains("ESCAPE")) {
                	root.getChildren().removeAll(tfHeading);
                	Main.homepage.reload(root);
                }
            }
        });
		
		root.getChildren().addAll(tfHeading);
	}
	
	void reload(Group parent) {
		System.out.println("hello again");
		root = parent;
		root.getChildren().addAll(tfHeading);
		Main.scene.setOnKeyReleased(keyReleasedListener);
	}
}
