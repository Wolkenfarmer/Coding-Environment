package environment;

/**
 * Runs the communication experiment with the selected {@link ExperimentElement experiment elements}.
 * @author Wolkenfarmer
 * @see #run(ExperimentElement, ExperimentElement, ExperimentElement, ExperimentElement) See run() for more information.
 */
public class Run {
	/** Saves the number of times the communication experiment should be repeated before evaluation. 
	 * This is how often the experiment will be run if {@link environment.pages.Homepage#bConButRun run} gets pressed.
	 * Currently this variable can only be set manually.*/
	public static int repeat = 10000;
	public static int repeated = 0;
	/** Defines the interpretation rule for message-version-comparison in {@link Result}. 
	 * If set to true, no check-position corrections will be made. 
	 * This is only recommended if just basic characters got used in the example of Unicode text 
	 * and might improve the change-counting-accuracy a bit. Currently this variable can only be set manually.*/
	public static boolean oneUnitPerChar = true;
	/** The character '_' in Unicode (binary) which will replace changed but not correctable units in {@link #correctedFlaggedMessage}.
	 * Currently this variable can only be set manually.*/
	public static String flagSignBinary = "01011111";
	/** The character '_' which will replace changed but not correctable units in {@link #correctedFlaggedMessage}.
	 * Currently this variable can only be set manually.*/
	public static char flagSignUnicode = '_';
	/** The standard Unicode message, if no {@link infSources information source} got selected or it was left empty.
	 * Currently this variable can only be set manually.*/
	public static String standardUnicodeMessage = "Hello World!";
	
	/** The original Message, which neither got en- / decoded or changed by a noise source. 
	 * It gets set by the {@link infSources information source}. */
	public static String originalMessage;
	/** Saves the already encoded but not yet by the {@link noiSources noise source} changed message from the {@link noiSources noise source}.*/
	public static String originalCode;
	/** Saves the {@link enDecoder encoded} and by the {@link noiSources noise source} changed message from the {@link noiSources noise source}.*/
	public static String changedCode;
	/** Saves the by the {@link noiSources noise source} changed message from the {@link enDecoder encoder}.*/
	public static String changedMessage;
	/** Saves the by the en- / decoder corrected message from the {@link enDecoder encoder}.*/
	public static String correctedMessage;
	/** Saves the by the en- / decoder corrected message with flagged characters from the {@link enDecoder encoder}.*/
	public static String correctedFlaggedMessage;
	
	
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
				
		for (repeated = 0; repeated < repeat; repeated++) {
			data = infSource.doJob((byte) 0, data);
			//data = prePost.doJob((byte) 0, data);
			data = enDecoder.doJob((byte) 0, data);
			data = noiSource.doJob((byte) 0, data);
			data = enDecoder.doJob((byte) 1, data);
			//data = prePost.doJob((byte) 1, data);
			
			Result.addResult(originalMessage, originalCode, changedCode, changedMessage, correctedMessage, correctedFlaggedMessage);
			
			if (data.getStringUnicode().length() < 100) {
				Result.SysoResult(originalMessage, originalCode, changedCode, changedMessage, correctedMessage, correctedFlaggedMessage);
			}
		}
		
		Result.updateResult();		
		
	}
}
