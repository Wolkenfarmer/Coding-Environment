/**
 * 
 */
package infSources;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * @author Wolkenfarmer
 *
 */
public class UserInput {
	private static Pane root;
	private static TextField tfUserText;
	
	
	public void showUI(Pane parent) {
		System.out.println("Wohooo");
		root = parent;
		
		tfUserText = new TextField();
        tfUserText.setFont(environment.Main.fNormalText);
        tfUserText.setPromptText("User input");
        tfUserText.setStyle("-fx-text-inner-color: WHITESMOKE;");
        tfUserText.setBackground(environment.Main.baNormalButton);
        tfUserText.setPrefWidth(root.getPrefWidth());
        root.getChildren().add(tfUserText);
		
		
	}
}
