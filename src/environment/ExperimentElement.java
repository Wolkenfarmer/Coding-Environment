package environment;

import javafx.scene.layout.Pane;

/**
 * @author Wolkenfarmer
 */
public interface ExperimentElement {	
	public void buildGui(Pane parent);
	public void reloadGui(Pane parent);
	public boolean getBuiltGui();
}
