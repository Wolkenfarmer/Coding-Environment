package de.wolkenfarmer.environment.logic;

import de.wolkenfarmer.experiment_elements.ExperimentElement;

/**
 * Runs the communication experiment with the selected {@link ExperimentElement experiment elements}.
 * @author Wolkenfarmer
 * @see #run(ExperimentElement, ExperimentElement, ExperimentElement) See run() for more information.
 */
public class Run {
	/** Saves the number of times the communication experiment should be repeated before evaluation. 
	 * This is how often the experiment will be run if {@link de.wolkenfarmer.environment.pages.Home#bConButRun run} gets pressed.
	 * Currently this variable can only be set manually.*/
	public static int repeat = 1000;
	/** Saves the number of times the communication experiment was already repeated.
	 * Gets incremented in {@link #run(ExperimentElement, ExperimentElement, ExperimentElement)} and 
	 * - if an evaluation had a caught error - decremented in 
	 * {@link de.wolkenfarmer.environment.logic.Result#addResult(String, String, String, String, String, String)}.*/
	public static int repeated = 0;
	/** Defines the interpretation rule for message-version-comparison in {@link Result}. 
	 * If set to true, no check-position corrections will be made. 
	 * This is only recommended if just basic characters got used in the example of Unicode text 
	 * and might improve the change-counting-accuracy a bit. Currently this variable can only be set manually.*/
	public static boolean oneUnitPerChar = false;
	/** The character '_' in Unicode (binary) which will replace changed but not correctable units in {@link #correctedFlaggedMessage}.
	 * Currently this variable can only be set manually.*/
	public static String flagSignBinary = "01011111";
	/** The character '_' which will replace changed but not correctable units in {@link #correctedFlaggedMessage}.
	 * Currently this variable can only be set manually.*/
	public static char flagSignUnicode = '_';
	/** The standard Unicode message, if no {@link de.wolkenfarmer.experiment_elements.input_handlers input handler} 
	 * got selected or it was left empty. Currently this variable can only be set manually.*/
	public static String standardUnicodeMessage = "Hello World!";
	
	/** The original Message, which neither got en- / decoded or changed by a noise source. 
	 * It gets set by the {@link de.wolkenfarmer.experiment_elements.input_handlers input handler}. */
	public static String originalMessage;
	/** Saves the already encoded but not yet by the {@link de.wolkenfarmer.experiment_elements.noise_sources noise source} 
	 * changed message from the {@link de.wolkenfarmer.experiment_elements.noise_sources noise source}.*/
	public static String originalCode;
	/** Saves the {@link de.wolkenfarmer.experiment_elements.transcoder encoded} and by the 
	 * {@link de.wolkenfarmer.experiment_elements.noise_sources noise source} 
	 * changed message from the {@link de.wolkenfarmer.experiment_elements.noise_sources noise source}.*/
	public static String changedCode;
	/** Saves the by the {@link de.wolkenfarmer.experiment_elements.noise_sources noise source} changed message from the 
	 * {@link de.wolkenfarmer.experiment_elements.transcoder encoder}.*/
	public static String changedMessage;
	/** Saves the by the transcoder corrected message from the {@link de.wolkenfarmer.experiment_elements.transcoder encoder}.*/
	public static String correctedMessage;
	/** Saves the by the transcoder corrected message with flagged characters from the 
	 * {@link de.wolkenfarmer.experiment_elements.transcoder encoder}.*/
	public static String correctedFlaggedMessage;
	
	
	/**
	 * Runs the communication experiment with the given {@link ExperimentElement experiment elements}. <br>
	 * In order to cover as much input handlers and transcoder and noise sources as possible, 
	 * the data from one experiment element will be transferred via {@link UniDataType} to another 
	 * converting the given data type to the requested one.
	 * 
	 * @param inputHandler The {@link de.wolkenfarmer.experiment_elements.input_handlers input handler} 
	 * which provides the data for this experiment
	 * @param transcoder The {@link de.wolkenfarmer.experiment_elements.transcoder transcoder} which will encode then given data 
	 * from inputHandler for the channel and afterwards decode it again for the destination. 
	 * During the decoding most changes through the noise source should hopefully be detected or even corrected.
	 * @param noiseSource The {@link de.wolkenfarmer.experiment_elements.noise_sources noise source} 
	 * which will alter the data between en- and decoder in the channel a bit.
	 */
	public static void run(ExperimentElement inputHandler, ExperimentElement transcoder, ExperimentElement noiseSource) {
		UniDataType data = new UniDataType();
				
		for (repeated = 0; repeated < repeat; repeated++) {
			data = inputHandler.doJob((byte) 0, data);
			data = transcoder.doJob((byte) 0, data);
			data = noiseSource.doJob((byte) 0, data);
			data = transcoder.doJob((byte) 1, data);
			
			Result.addResult(originalMessage, originalCode, changedCode, changedMessage, correctedMessage, correctedFlaggedMessage);
			
			if (data.getStringUnicode().length() < 100) {
				Result.SysoResult(originalMessage, originalCode, changedCode, changedMessage, correctedMessage, correctedFlaggedMessage);
			}
		}
		
		Result.updateResult();		
		
	}
}
