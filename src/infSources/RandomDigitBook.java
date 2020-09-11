package infSources;

import environment.ExperimentElement;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * @author Wolkenfarmer
 */
public class RandomDigitBook implements ExperimentElement {
	private static String name = "Random digit book";
	private static String protocol = "byte[]?";
	private static byte index = 2;
	private static byte type = 0;
	private static Pane root;
	public static boolean builtGui;
	private static Label mock;
	
	
	public void buildGui(Pane parent) {
		root = parent;
		
		mock = new Label();
		mock.setText("Random-digit-book-Gui has been loaded!");
		mock.setFont(environment.Main.fNormalText);
		mock.setTextFill(Color.WHITESMOKE);
		mock.setPrefWidth(root.getPrefWidth());
		mock.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(mock);
		
	}


	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().addAll(mock);
	}
	
	
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
	public boolean getBuiltGui() {return builtGui;}
	public String getName() {return name;}
	public String getProtocol() {return protocol;}
	public byte getIndex() {return index;}
	public byte getType() {return type;}
}
