package de.wolkenfarmer.transcoder;

import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.UniDataType;
import javafx.scene.layout.Pane;

/**
 * The transcoder if none is selected. 
 * @author Wolkenfarmer
 */
public class Deselect implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "nothing selected";
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement)}).
	 * Its content (-) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already built and has only to be attached ({@link #getGui()}).*/
	private static boolean builtGui;

	
	/** Sets the necessary message-versions in {@link de.wolkenfarmer.environment.Run} for a flawless data analysis.*/
	public UniDataType doJob(byte task, UniDataType data) {
		if (task != 0) {
			de.wolkenfarmer.environment.Run.changedMessage = data.getStringUnicode();
			de.wolkenfarmer.environment.Run.correctedMessage = data.getStringUnicode();
			de.wolkenfarmer.environment.Run.correctedFlaggedMessage = data.getStringUnicode();
		} 
		return data;
	}
	public void buildGui(double parentWidth) {}
	public void save() {}
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return Returns either {@link #name} or if option button is true "Deselect".*/
	public String getName(boolean optionButton) {
		if (optionButton) return "Deselect";
		else return name;
	}
}
