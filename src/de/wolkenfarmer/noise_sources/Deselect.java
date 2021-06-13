package de.wolkenfarmer.noise_sources;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Run;
import de.wolkenfarmer.environment.UniDataType;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Represents the option to completely disable the noise source in {@link de.wolkenfarmer.environment.pages.NoiseSource} by setting it to this. 
 * @author Wolkenfarmer
 */
public class Deselect implements ExperimentElement {
	/** Name of this experiment element.*/
	private String name = "nothing selected";
	/** Defines the type of this input handler. This variable has for noise sources currently no use-case.*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement)}).
	 * Its content ({@link #l}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	/** Label which explains the function of this element. It will be directly attached to {@link #root}.*/
	private static Label l;
	
	
	/** Sets the necessary message-versions in {@link de.wolkenfarmer.environment.Run} for a flawless data analysis.*/
	public UniDataType doJob(byte task, UniDataType data) {
		Run.originalCode = data.getStringBinary();
		Run.changedCode = data.getStringBinary();
		return data;
	}

	
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		l = new Label();
		l.setText("\"Save & add\" this option in order to disable the noise source for the communication experiment.");
		l.setFont(Constants.F_NORMAL);
		l.setTextFill(Constants.C_NORMAL);
		l.setPrefWidth(root.getPrefWidth());
		l.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(l);
	}
	

	public void save() {}
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return Returns either {@link #name} or if option button is true "Deselect".*/
	public String getName(boolean optionButton) {
		if (optionButton) {
			return "Deselect";
		} else {
			return name;
		}
	}
	/** @return {@link #type}*/
	public byte getType() {return type;}
}
