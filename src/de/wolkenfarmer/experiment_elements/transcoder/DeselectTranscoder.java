package de.wolkenfarmer.experiment_elements.transcoder;

import de.wolkenfarmer.environment.logic.Run;
import de.wolkenfarmer.environment.logic.UniDataType;
import de.wolkenfarmer.experiment_elements.Deselect;

/**
 * The standard transcoder. <br>
 * Represents the option to disable the transcoder by setting it to this. 
 * Therefore, the changed, corrected and flagged message version will be exactly the same.
 * @author Wolkenfarmer
 */
public class DeselectTranscoder extends Deselect {
	/**
	 * Sets the {@link #name name} of the experiment element.
	 * @since 0.2
	 */
	public DeselectTranscoder() {name = "nothing selected";}

	
	/** 
	 * Sets the necessary message-versions in {@link Run} for a flawless data analysis.
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		if (task != 0) {
			Run.changedMessage = data.getStringUnicode();
			Run.correctedMessage = data.getStringUnicode();
			Run.correctedFlaggedMessage = data.getStringUnicode();
		} 
		return data;
	}
	
	
	/**
	 * @since 0.2
	 */
	@Override
	public void buildGui(double parentWidth) {
		super.buildGui(parentWidth);
		l.setText("\"Save & add\" this option in order to disable the transcoder for the communication experiment.");
	}
}
