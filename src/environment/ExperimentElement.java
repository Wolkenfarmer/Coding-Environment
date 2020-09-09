package environment;

import javafx.scene.layout.Pane;

/**
 * @author Wolkenfarmer
 */
public interface ExperimentElement {	
	public void buildGui(Pane parent);
	public void reloadGui(Pane parent);
	public void save();
	public boolean getBuiltGui();
	public String getName();
	public byte getIndex();
	public byte getType();
}
