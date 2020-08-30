package environment;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

/**
 * The homepage of the application with access to every part of the program. 
 * @author Wolkenfarmer
 */
public class Homepage {	
	
	/**
	 * Builds the homepage of the application.
	 * @param root A group to attach it's layout parts to.
	 */
	public Homepage(Group root) {
		Label label = new Label();
		label.setText("w\nu\nf\nf");
		label.setTextFill(Color.WHITESMOKE);
		label.setFont(Main.fHeadline);
		root.getChildren().add(label);
		
		Main.contentHeight = label.getLayoutY() + 50;
		Main.scrollbar.setMax(Main.contentHeight - Main.scene.getHeight());
		Main.scrollbar.setBlockIncrement(Main.contentHeight);
        if (Main.scene.getHeight() >= Main.contentHeight) {Main.scrollbar.setVisible(false);}
	}

}
