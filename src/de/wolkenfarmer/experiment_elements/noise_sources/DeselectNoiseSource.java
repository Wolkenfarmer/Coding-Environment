package de.wolkenfarmer.experiment_elements.noise_sources;

import de.wolkenfarmer.environment.logic.Run;
import de.wolkenfarmer.environment.logic.UniDataType;
import de.wolkenfarmer.experiment_elements.Deselect;

/**
 * The standard noise source. <br>
 * Represents the option to disable the noise source in {@link de.wolkenfarmer.environment.pages.NoiseSource} by setting it to this. 
 * @author Wolkenfarmer
 */
public class DeselectNoiseSource extends Deselect {
	/**
	 * Sets the {@link #name name} of the experiment element.
	 * @since 0.2
	 */
	public DeselectNoiseSource() {name = "nothing selected";}
	
	
	/** 
	 * Sets the necessary message-versions in {@link Run} for a flawless data analysis.
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		Run.originalCode = data.getStringBinary();
		Run.changedCode = data.getStringBinary();
		return data;
	}

	
	/**
	 * @since 0.2
	 */
	@Override
	public void buildGui(double parentWidth) {
		super.buildGui(parentWidth);
		l.setText("\"Save & add\" this option in order to disable the noise source for the communication experiment.");
	}
}
