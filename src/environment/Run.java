package environment;

/**
 * Runs the communication experiment with the selected {@link ExperimentElement experiment elements}.
 * @author Wolkenfarmer
 * @see #run(ExperimentElement, ExperimentElement, ExperimentElement, ExperimentElement) See run() for more information.
 */
public class Run {
	/** The character '_' which will replace changed but not correctable units in {@link #correctedFlaggedMessage} 
	 * in the case of Unicode input format.*/
	public static String flagSign = "01011111";
	/** Saves the original input from the {@link infSources information source}.*/
	public static String originalMessage = new String();
	/** Saves the already encoded but not yet by the {@link noiSources noise source} changed message from the {@link noiSources noise source}.*/
	public static String originalCode = new String();
	/** Saves the {@link enDecoder encoded} and by the {@link noiSources noise source} changed message from the {@link noiSources noise source}.*/
	public static String changedCode = new String();
	/** Saves the by the {@link noiSources noise source} changed message from the {@link enDecoder encoder}.*/
	public static String changedMessage = new String();
	/** Saves the by the en- / decoder corrected message from the {@link enDecoder encoder}.*/
	public static String correctedMessage = new String();
	/** Saves the by the en- / decoder corrected message with flagged characters from the {@link enDecoder encoder}.*/
	public static String correctedFlaggedMessage = new String();
	
	
	/**
	 * Runs the communication experiment with the given {@link ExperimentElement experiment elements}. 
	 * In order to cover as much information sources and en- / decoder and noise sources as possible, 
	 * the data from one experiment element will be transferred via {@link UniDataType} to another 
	 * converting the given data type to the requested one.
	 * 
	 * @param infSource The {@link infSources information source} which provides the data for this experiment
	 * @param prePost The {@link enDecoder pre-en- / post-decoder} which prepares the data for the en- / decoder. 
	 * @param enDecoder The {@link enDecoder en- / decoder} which will encode then given data from infSource for the channel 
	 * and afterwards decode it again for the destination. 
	 * During the decoding most changes through the noise source should hopefully be detected or even corrected.
	 * @param noiSource The {@link noiSources noise source} which will alter the data between en- and decoder in the channel a bit.
	 */
	public static void run(ExperimentElement infSource, ExperimentElement prePost, ExperimentElement enDecoder, ExperimentElement noiSource) {
		UniDataType data = new UniDataType();
		data.setStringUnicode("Hello world!");
		
		
		data = infSource.doJob((byte) 0, data);
		//data = prePost.doJob((byte) 0, data);
		data = enDecoder.doJob((byte) 0, data);
		data = noiSource.doJob((byte) 0, data);
		data = enDecoder.doJob((byte) 1, data);
		//data = prePost.doJob((byte) 1, data);
		
		if (data.getStringUnicode().length() < 100) {
			System.out.println("Communication experiment result: original message:              " + originalMessage);
			System.out.println("Communication experiment result: original encoded code:         " + originalCode);
			System.out.println("Communication experiment result: changed encoded code:          " + changedCode);
			System.out.println("Communication experiment result: changed message:               " + changedMessage);
			System.out.println("Communication experiment result: corrected message:             " + correctedMessage);
			System.out.println("Communication experiment result: corrected and flagged message: " + correctedFlaggedMessage);
		}
	}
}
