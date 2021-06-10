package de.wolkenfarmer.input_handlers;

import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.UniDataType;
import javafx.scene.layout.Pane;

/**
 * The input handler if none is selected. 
 * @author Wolkenfarmer
 */
public class Deselect implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "nothing selected";
	/** Defines the type of this input handler. This variable has for input handlers currently no use-case.*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement)}).
	 * Its content (-) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already built and has only to be attached ({@link #getGui()}).*/
	private static boolean builtGui;

	
	/** Sets the necessary message-version in {@link de.wolkenfarmer.environment.Run} for a flawless data analysis 
	 * and sets the {@link de.wolkenfarmer.environment.Run#standardUnicodeMessage} as input.
	 * @see de.wolkenfarmer.environment.ExperimentElement#doJob(byte, UniDataType)*/
	public UniDataType doJob(byte task, UniDataType data) {
		data.setStringUnicode(de.wolkenfarmer.environment.Run.standardUnicodeMessage);
		de.wolkenfarmer.environment.Run.originalMessage = de.wolkenfarmer.environment.Run.standardUnicodeMessage;
		return data;
	}
	/** @see de.wolkenfarmer.environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {}
	/** @see de.wolkenfarmer.environment.ExperimentElement#save()*/
	public void save() {}
	/** @see de.wolkenfarmer.environment.ExperimentElement#getGui()*/
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #type}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
