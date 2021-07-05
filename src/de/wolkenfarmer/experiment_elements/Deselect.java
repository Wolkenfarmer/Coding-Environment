package de.wolkenfarmer.experiment_elements;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.logic.UniDataType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Child class of {@link de.wolkenfarmer.experiment_elements.ExperimentElement ExperimentElement} which defines the default setup 
 * for all deselect options in the communication experiment. <br>
 * This class can be inherited as base for other experiment elements. On its own however, it has no functionality.
 * @author Wolkenfarmer
 * @since 0.2
 */
public abstract class Deselect extends ExperimentElement {
	/** Label which explains the function of this element. It will be directly attached to {@link #root}.*/
	protected Label l;
	

	public abstract UniDataType doJob(byte task, UniDataType data);

	
	/**
	 * @since 0.2
	 */
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		l = new Label();
		l.setText("\"Save & add\" this option in order to fall back to the default input \"Hello World!\".");
		l.setFont(Constants.F_NORMAL);
		l.setTextFill(Constants.C_NORMAL);
		l.setPrefWidth(root.getPrefWidth());
		l.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(l);
	}

	
	/** 
	 * @return Returns either {@link #name} or if optionButton is true "Deselect".
	 * @since 0.2
	 */
	@Override
	public String getName(boolean optionButton) {
		if (optionButton) return "Deselect";
		else return name;
	}
}
