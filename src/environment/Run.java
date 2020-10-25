package environment;

/**
 * Runs the communication experiment with the selected {@link ExperimentElement experiment elements}.
 * @author Wolkenfarmer
 * @see #run(ExperimentElement, ExperimentElement, ExperimentElement, ExperimentElement) See run() for more information.
 */
public class Run {
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
		System.out.println("_runs infSource");
		data = infSource.doJob((byte) 0, data);
		System.out.println("_runs Encoder: ");
		data = prePost.doJob((byte) 0, data);
		data = enDecoder.doJob((byte) 0, data);
		System.out.println("_runs noiSource: ");
		data = noiSource.doJob((byte) 0, data);
		System.out.println("_runs Decoder: ");
		data = enDecoder.doJob((byte) 1, data);
		data = prePost.doJob((byte) 1, data);
		System.out.println("_Decoder result: " + data.getStringBinary());
		
		if (data.getStringUnicode().length() < 100) {
			System.out.println("Communication experiment result: " + data.getStringUnicode());
		}
	}
}
