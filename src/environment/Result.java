package environment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Manages the results from {@link Run} and analyzes them.
 * Firstly, the results of the each communication experiment run get added and analyzed via 
 * {@link #addResult(String, String, String, String, String, String)}. When all communication experiment {@link Run#repeat runs / run-repeats}
 * got added, {@link #updateResult()} gets called, which calculates the average of each added run, 
 * summarizes the final results in a String[], and lastly updates {@link environment.pages.Homepage#tvResTable the results-table on the home page}.
 * This class gets only called by {@link Run}.
 * @author Wolkenfarmer
 */
public class Result {
	/** Saves the used {@link infSources information source} for the last run of the communication experiment.*/
	public static String informationSource;
	/** Saves the used {@link enDecoder en-/decoder} for the last run of the communication experiment.*/
	public static String enDecoder;
	/** Saves the used {@link noiSources noise source} for the last run of the communication experiment.*/
	public static String noiseSource;
	
	/** Saves the number of changes in the encoded version of the message made by the {@link noiSources noise source} by comparing
	 * {@link Run#originalCode} and {@link Run#changedCode}.*/
	private static double changes;
	/** Saves the number of changed characters in the {@link Run#changedMessage changed message} in comparison to the 
	 * {@link Run#originalMessage original message}.*/
	private static double changedChars;
	/** Saves the number of corrected characters in the {@link Run#correctedMessage corrected message} in comparison to the 
	 * {@link Run#originalMessage original message}.*/
	private static double correctedChars;
	/** Saves the number of mistakenly corrected characters in the {@link Run#correctedMessage corrected message} in comparison to the 
	 * {@link Run#originalMessage original message}.*/
	private static double mistakenlyCorrectedChars;
	/** Saves the number of flagged characters in the {@link Run#correctedFlaggedMessage corrected+flagged message} in comparison to the 
	 * {@link Run#originalMessage original message}.*/
	private static double flaggedChars;
	/** Saves the number of mistakenly flagged characters in the {@link Run#correctedFlaggedMessage corrected+flagged message} 
	 * in comparison to the {@link Run#originalMessage original message}.*/
	private static double mistakenlyFlaggedChars;
	/** Saves the information content of the {@link Run#originalMessage original message}, which is equivalent to it's length. 
	 * @see #addResult(String, String, String, String, String, String) addResult's Note 2 for further information on the term "information"*/
	private static int information;
	/** Saves the information content of the {@link Run#changedMessage changed message}.
	 * @see #addResult(String, String, String, String, String, String) addResult's Note 2 for further information on the term "information"*/
	private static double informationWithoutCoding;
	/** Saves the information content of the {@link Run#correctedMessage corrected message}.
	 * @see #addResult(String, String, String, String, String, String) addResult's Note 2 for further information on the term "information"*/
	private static double informationWithCodingCo;
	/** Saves the information content of the {@link Run#correctedFlaggedMessage corrected+flagged message}.
	 * @see #addResult(String, String, String, String, String, String) addResult's Note 2 for further information on the term "information"*/
	private static double informationWithCodingCf;
	
	
	/**
	 * Adds the given run to the result by analyzing it and incrementing the corresponding variables.<br>
	 * Firstly, all changes in the encoded message made by the {@link noiSources noise source} get counted for {@link #changes}.<br><br>
	 * 
	 * Then, it iterates the characters of the original message and counts {@link #information} up by doing so. 
	 * While doing this, it checks for changes in comparison to the changed Message.<br>
	 * If there is a difference, it increments {@link #changedChars} and then compares the original message again with the 
	 * corrected and the corrected+flagged message in order to check if the changes got detected (increments {@link #flaggedChars}) 
	 * or even corrected (increments {@link #correctedChars}). In addition, the individual check-positions for the different message-versions
	 * get corrected if needed (see Note 1 for more information). <br>
	 * If there is no difference, the corrected and the corrected+flagged message still get compared to the original one 
	 * in order to search for mistakenly corrected (increments {@link #mistakenlyCorrectedChars}) or 
	 * (increments {@link #mistakenlyFlaggedChars}) flagged characters.<br><br>
	 * 
	 * Lastly, the information content of the other message versions gets calculated and added to {@link #informationWithoutCoding},
	 * {@link #informationWithCodingCo} and {@link #informationWithCodingCf} (see Note 2 for further information on the term "information".
	 * 
	 * 
	 * <dl>
	 * <dt><span class="strong">Note 1:</span></dt><dd>
	 * The correction of the different check-positions is needed, because in some cases - like in Unicode - 
	 * multiple binary units describe one character 
	 * and if there was a special change, this originally one characters gets interpreted as multiple ones or the reverse case 
	 * (which the program can't handle currently). Moreover, the responsible could have been corrected, 
	 * or a at first harmless change in this regard triggers a flag-algorithm, 
	 * which is why none message-version can be directly aligned to the other one and each version has it's own check-position.
	 * They get corrected by checking where the next (or two next) character(s) of the original message can be found on the versions. 
	 * However, currently only the next 4 and 1 previous characters get double checked (2 characters have to align afterwards correctly) 
	 * (and if the distance is above 1 character even triple-checked with the next next character of the original message). 
	 * These limitations are in order to avoid wrong positioning in highly repetitive messages. 
	 * Still, this is not perfect, which is why complex Unicode characters for example should be obviated as last character in the message 
	 * or even completely avoided in case of high change-rates.<br>
	 * If it is sure that every unit will define exactly one character, {@link Run#oneUnitPerChar} can be turned to true, 
	 * which will deactivate this correction.<br>
	 * If there is still an StringIndexOutOfBoundsException, it will be caught, this particular run rerun and its current data erased.<br>
	 * Ultimately, with a higher message length the risk of having false message check-positions increases, 
	 * which is why long messages should be avoided and instead a higher {@link Run#repeat repeat}-number should be used.</dd>
	 * 
	 * <dt><span class="strong">Note 2:</span></dt><dd>
	 * Every correct character gets counted as 1 positive bit of information, every wrong character as 1 negative bit of information 
	 * and every flagged information as information-less. While not doing the formal information term justice, 
	 * this is the most practicable way of determining the information content without too much complexity.</dd>
	 * 
	 * <dt><span class="strong">Note 3:</span></dt><dd>
	 * {@link #changes}, {@link #changedChars}, {@link #correctedChars}, {@link #mistakenlyCorrectedChars}, {@link #flaggedChars} and 
	 * {@link #mistakenlyFlaggedChars} have a local version of themselves here in order for the information content calculations to have 
	 * the correct information for this particular run if it gets repeated multiple times (and not the accumulated counts).</dd>
	 * </dl>
	 * @param originalMessage Refers to {@link Run#originalMessage}.
	 * @param originalCode Refers to {@link Run#originalCode}.
	 * @param changedCode Refers to {@link Run#changedCode}.
	 * @param changedMessage Refers to {@link Run#changedMessage}.
	 * @param correctedMessage Refers to {@link Run#correctedMessage}.
	 * @param correctedFlaggedMessage Refers to {@link Run#correctedFlaggedMessage}.
	 */
	public static void addResult(String originalMessage, String originalCode, String changedCode, String changedMessage, 
			String correctedMessage, String correctedFlaggedMessage) {
		
		int changesM = 0;
		int changedCharsM = 0;
		int correctedCharsM = 0;
		int mistakenlyCorrectedCharsM = 0;
		int flaggedCharsM = 0;
		int mistakenlyFlaggedCharsM = 0;
		information = 0;
		
		int iOrM = 0;
		int iChM = 0;
		int iCoM = 0;
		int iCfM = 0;			
		
		for (int i = 0; i < originalCode.length(); i++) {
			if (originalCode.charAt(i) != changedCode.charAt(i)) changesM++;
		}
		
		
		try {
			for (int i = 0; i < originalMessage.length(); i++) {
				information++;
				if (originalMessage.charAt(iOrM) != changedMessage.charAt(iChM)) {
					changedCharsM++;
					
					if (originalMessage.charAt(iOrM) == correctedMessage.charAt(iCoM)) correctedCharsM++;
					if (correctedFlaggedMessage.charAt(iCfM) == '_' && 
							changedMessage.charAt(iChM) != '_' && correctedMessage.charAt(iCoM) != '_') flaggedCharsM++;
					
					if (!Run.oneUnitPerChar) {
						try {
							if (originalMessage.charAt(iOrM + 1) == changedMessage.charAt(iChM + 1)) {
							} else if (originalMessage.charAt(iOrM + 1) == changedMessage.charAt(iChM + 2)
									&& originalMessage.charAt(iOrM + 2) == changedMessage.charAt(iChM + 3)) {
								iChM++;
							} else if (originalMessage.charAt(iOrM + 1) == changedMessage.charAt(iChM + 3)
									&& originalMessage.charAt(iOrM + 2) == changedMessage.charAt(iChM + 4)
									&& originalMessage.charAt(iOrM + 2) == changedMessage.charAt(iChM + 4)) {
								iChM += 2;
							} else if (originalMessage.charAt(iOrM + 1) == changedMessage.charAt(iChM + 4)
									&& originalMessage.charAt(iOrM + 2) == changedMessage.charAt(iChM + 5)
									&& originalMessage.charAt(iOrM + 3) == changedMessage.charAt(iChM + 6)) {
								iChM += 3;
							} else if (originalMessage.charAt(iOrM + 2) == changedMessage.charAt(iChM + 1)
									&& originalMessage.charAt(iOrM + 3) == changedMessage.charAt(iChM + 2)) {
								iChM--;
							}
						} catch (StringIndexOutOfBoundsException e) {
							System.out.println("Result output-compare: Couldn't match the Strings "
									+ "changed message correctly to origin for further comparing");
						}
						
						try {
							if (originalMessage.charAt(iOrM + 1) == correctedMessage.charAt(iCoM + 1)) {
							} else if (originalMessage.charAt(iOrM + 1) == correctedMessage.charAt(iCoM + 2)
									&& originalMessage.charAt(iOrM + 2) == correctedMessage.charAt(iCoM + 3)) {
								iCoM++;
							} else if (originalMessage.charAt(iOrM + 1) == correctedMessage.charAt(iCoM + 3)
									&& originalMessage.charAt(iOrM + 2) == correctedMessage.charAt(iCoM + 4)
									&& originalMessage.charAt(iOrM + 2) == correctedMessage.charAt(iCoM + 4)) {
								iCoM += 2;
							} else if (originalMessage.charAt(iOrM + 1) == correctedMessage.charAt(iCoM + 4)
									&& originalMessage.charAt(iOrM + 2) == correctedMessage.charAt(iCoM + 5)
									&& originalMessage.charAt(iOrM + 3) == correctedMessage.charAt(iCoM + 6)) {
								iCoM += 3;
							} else if (originalMessage.charAt(iOrM + 2) == correctedMessage.charAt(iChM + 1)
									&& originalMessage.charAt(iOrM + 3) == correctedMessage.charAt(iChM + 2)) {
								iCoM--;
							}
						} catch (StringIndexOutOfBoundsException e) {
							System.out.println("Result output-compare: Couldn't match the Strings "
									+ "corrected message correctly to origin for further comparing");
						}
						
						try {
							if (originalMessage.charAt(iOrM + 1) == correctedFlaggedMessage.charAt(iCfM + 1)) {
							} else if (originalMessage.charAt(iOrM + 1) == correctedFlaggedMessage.charAt(iCfM + 2)
									&& originalMessage.charAt(iOrM + 2) == correctedFlaggedMessage.charAt(iCfM + 3)) {
								iCfM++;
							} else if (originalMessage.charAt(iOrM + 1) == correctedFlaggedMessage.charAt(iCfM + 3)
									&& originalMessage.charAt(iOrM + 2) == correctedFlaggedMessage.charAt(iCfM + 4)) {
								iCfM += 2;
							} else if (originalMessage.charAt(iOrM + 1) == correctedFlaggedMessage.charAt(iCfM + 4)
									&& originalMessage.charAt(iOrM + 2) == correctedFlaggedMessage.charAt(iCfM + 5)
									&& originalMessage.charAt(iOrM + 3) == correctedFlaggedMessage.charAt(iCfM + 6)) {
								iCfM += 3;
							} else if (originalMessage.charAt(iOrM + 2) == correctedFlaggedMessage.charAt(iChM + 1)
									&& originalMessage.charAt(iOrM + 3) == correctedFlaggedMessage.charAt(iChM + 2)) {
								iCfM--;
							}
						} catch (StringIndexOutOfBoundsException e) {
							System.out.println("Result output-compare: Couldn't match the Strings "
									+ "corrected flagged message correctly to origin for further comparing");
						}
					}
					
				} else {
					
					if ((correctedFlaggedMessage.charAt(iCfM) == '_') && (originalMessage.charAt(iOrM) != '_')) {
						flaggedCharsM++;
						mistakenlyFlaggedCharsM++;
					}
					
					if (correctedMessage.charAt(iCoM) != originalMessage.charAt(iOrM)) {
						correctedCharsM++;
						mistakenlyCorrectedCharsM++;
					}
				}
				
				iOrM++;
				iChM++;
				iCoM++;
				iCfM++;
			}
			
		} catch (StringIndexOutOfBoundsException e) {
			
			Run.repeated--;
			changesM = 0;
			changedCharsM = 0;
			correctedCharsM = 0;
			mistakenlyCorrectedCharsM = 0;
			flaggedCharsM = 0;
			mistakenlyFlaggedCharsM = 0;
			information = 0;
			
			
			System.out.println("Communication experiment exception - changed message shorter than original one and not alignable\n" + e);
			System.out.println("\nlog: original message:              " + originalMessage);
			System.out.println("log: original encoded code:         " + originalCode);
			System.out.println("log: changed encoded code:          " + changedCode);
			System.out.println("log: changed message:               " + changedMessage);
		}
			
		informationWithoutCoding += information - (changedCharsM * 2);
		informationWithCodingCo += information - ((changedCharsM - (correctedCharsM - mistakenlyCorrectedCharsM)) * 2);
		informationWithCodingCf += information - ((changedCharsM - (correctedCharsM - mistakenlyCorrectedCharsM)) * 2)
				+ (flaggedCharsM - mistakenlyFlaggedCharsM) - mistakenlyFlaggedCharsM;
		
		changes += changesM;
		changedChars += changedCharsM;
		correctedChars += correctedCharsM;
		mistakenlyCorrectedChars += mistakenlyCorrectedCharsM;
		flaggedChars += flaggedCharsM;
		mistakenlyFlaggedChars += mistakenlyFlaggedCharsM;
	}
	
	
	/**
	 * The results of the experiment as well as its evaluation get combined to a String[]. 
	 * If {@link Run#repeat} != 1, the evaluated values like {@link #changes} get divided 
	 * by the number of repeats before the evaluation in order to get the average.
	 * In the end, it prepares the variables, which count the changes in the code and message as well as the information numbers, 
	 * for the next communication experiment by setting them to 0. This is crucial to them due being static.<br>
	 * Following information gets included in the result:
	 * {@link infSources the used information source}, {@link enDecoder the used en- / decoder}, {@link noiSources the used noise source}, 
	 * {@link #changes}, {@link #changedChars}, {@link #correctedChars}, {@link #mistakenlyCorrectedChars}, 
	 * {@link #flaggedChars}, {@link #mistakenlyFlaggedChars}, {@link #information}, {@link #informationWithoutCoding}, 
	 * {@link #informationWithCodingCo}, {@link #informationWithCodingCf}.
	 */
	public static void updateResult() {
		String[] resultElement = new String[2];
		ObservableList<String[]> resultTableContent = FXCollections.observableArrayList();
		
		resultElement[0] = "Used information source";
		resultElement[1] = Main.selectedInfSource.getName();
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Used en- / decoder";
		resultElement[1] = Main.selectedEnDecoder.getName();
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Used noise source";
		resultElement[1] = Main.selectedNoiSource.getName();
		resultTableContent.add(resultElement.clone());
		
		resultElement[0] = "Repetitions";
		resultElement[1] = "" + Run.repeat;
		resultTableContent.add(resultElement.clone());
		
		resultElement[0] = "Changes in code";
		resultElement[1] = "" + String.format("%.2f", changes / Run.repeat);
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Changed characters";
		resultElement[1] = "" + String.format("%.2f", changedChars / Run.repeat);
		resultTableContent.add(resultElement.clone());
		
		resultElement[0] = "Corrected characters";
		resultElement[1] = "" + String.format("%.2f", correctedChars / Run.repeat);
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Mistakenly corrected characters";
		resultElement[1] = "" + String.format("%.2f", mistakenlyCorrectedChars / Run.repeat);
		resultTableContent.add(resultElement.clone());
		
		resultElement[0] = "Flagged characters";
		resultElement[1] = "" + String.format("%.2f", flaggedChars / Run.repeat);
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Mistakenly flagged characters";
		resultElement[1] = "" + String.format("%.2f", mistakenlyFlaggedChars / Run.repeat);
		resultTableContent.add(resultElement.clone());
		
		resultElement[0] = "Information original";
		resultElement[1] = "" + information;
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Inf. result without coding";
		resultElement[1] = "" + String.format("%.2f", informationWithoutCoding / Run.repeat);
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Inf. result with coding (corrected)";
		resultElement[1] = "" + String.format("%.2f", informationWithCodingCo / Run.repeat);
		resultTableContent.add(resultElement.clone());
		resultElement[0] = "Inf. result with coding (corrected & flagged)";
		resultElement[1] = "" + String.format("%.2f", informationWithCodingCf / Run.repeat);
		resultTableContent.add(resultElement.clone());
		
		
		if (Run.repeat == 1) {
			resultElement[0] = "original encoded code";
			resultElement[1] = Run.originalCode;
			resultTableContent.add(resultElement.clone());
			
			resultElement[0] = "changed encoded code";
			resultElement[1] = Run.changedCode;
			resultTableContent.add(resultElement.clone());
			
			resultElement[0] = "original message";
			resultElement[1] = Run.originalMessage;
			resultTableContent.add(resultElement.clone());
			
			resultElement[0] = "changed message";
			resultElement[1] = Run.changedMessage;
			resultTableContent.add(resultElement.clone());
			
			resultElement[0] = "corrected message";
			resultElement[1] = Run.correctedMessage;
			resultTableContent.add(resultElement.clone());
			
			resultElement[0] = "corrected and flagged message";
			resultElement[1] = Run.correctedFlaggedMessage;
			resultTableContent.add(resultElement.clone());
		}
		
		Main.homepage.updateResultTable(resultTableContent);
		
		
		changes = 0;
		changedChars = 0;
		correctedChars = 0;
		flaggedChars = 0;
		mistakenlyCorrectedChars = 0;
		mistakenlyFlaggedChars = 0;
		information = 0;
		informationWithoutCoding = 0;
		informationWithCodingCo = 0;
		informationWithCodingCf = 0;
	}
	
	
	/**
	 * Prints the results of the last communication experiment into the console. 
	 * Only the message-versions and the original as well as the changes code are included. 
	 * Note that these won't be printed in the console if the .exe-version got started.
	 * @param originalMessage Refers to {@link Run#originalMessage}.
	 * @param originalCode Refers to {@link Run#originalCode}.
	 * @param changedCode Refers to {@link Run#changedCode}.
	 * @param changedMessage Refers to {@link Run#changedMessage}.
	 * @param correctedMessage Refers to {@link Run#correctedMessage}.
	 * @param correctedFlaggedMessage Refers to {@link Run#correctedFlaggedMessage}.
	 */
	public static void SysoResult(String originalMessage, String originalCode, String changedCode, String changedMessage, 
			String correctedMessage, String correctedFlaggedMessage) {
		System.out.println("\nCommunication experiment result: original message:              " + originalMessage);
		System.out.println("Communication experiment result: original encoded code:         " + originalCode);
		System.out.println("Communication experiment result: changed encoded code:          " + changedCode);
		System.out.println("Communication experiment result: changed message:               " + changedMessage);
		System.out.println("Communication experiment result: corrected message:             " + correctedMessage);
		System.out.println("Communication experiment result: corrected and flagged message: " + correctedFlaggedMessage);
	}
}
