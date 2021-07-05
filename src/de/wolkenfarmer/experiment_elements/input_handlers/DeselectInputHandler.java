package de.wolkenfarmer.experiment_elements.input_handlers;

import de.wolkenfarmer.environment.logic.Run;
import de.wolkenfarmer.environment.logic.UniDataType;
import de.wolkenfarmer.experiment_elements.Deselect;

/**
 * The standard input handler. <br>
 * If this input handler is ticked for the communication experiment, 
 * the {@link de.wolkenfarmer.environment.logic.Run#standardUnicodeMessage standard input message} will be used.
 * @author Wolkenfarmer
 */
public class DeselectInputHandler extends Deselect {
	/**
	 * Sets the {@link #name name} of the experiment element.
	 * @since 0.2
	 */
	public DeselectInputHandler() {name = "nothing selected";}

	
	/** 
	 * Sets the {@link Run#standardUnicodeMessage} as input.
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		data.setStringUnicode(Run.standardUnicodeMessage); 
		Run.originalMessage = Run.standardUnicodeMessage;
		return data;
	}
	
	
	/**
	 * @since 0.2
	 */
	@Override
	public void buildGui(double parentWidth) {
		super.buildGui(parentWidth);
		l.setText("\"Save & add\" this option in order to fall back to the default input \"" + Run.standardUnicodeMessage + "\".");
	}
}
