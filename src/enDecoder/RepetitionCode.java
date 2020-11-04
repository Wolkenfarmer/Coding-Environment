package enDecoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import environment.ExperimentElement;
import environment.UniDataType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * The {@link enDecoder en- / decoder} "Repetition Code" which is selectable on the {@link environment.pages.EnDecoderPage en- / decoder page}.
 * This encoder repeats every given character {@link #repNumber} times.
 * Therefore, the decoder can check whether there is a character out of place in a repetition-segment 
 * and consequently correct or flag the character.
 * @author Wolkenfarmer
 * @see #doJob(byte, UniDataType) doJob() for further information
 */
public class RepetitionCode implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Repetition Code";
	/** Defines whether this is an en- / decoder or a pre-en- / post-decoder. This experiment element is only an en- / decoder.
	 * 0: en- / decoder
	 * 1: pre- / post-*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link environment.pages.guiElements.InformationSegment}
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * Its content ({@link #lDescription}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	
	/** Saves how often each character should be repeated during encoding. Gets used in {@link #doJob(byte, UniDataType)}. E.g. with 3:<br>
	 * 1011 would be 111000111111<br> 
	 * Currently this variable can only be set manually.*/
	private static int repNumber = 5;
	
	/** Label displaying the description for this experiment element. It gets directly attached to {@link #root}.*/
	private static Label lDescription;
	
	
	/** 
	 * Does the en- and decoding of the message with error detection and correction.<br>
	 * During encoding (task 0) each bit of the message will be repeated {@link #repNumber} times. 
	 * Therefore, the different repetitions can be compared against each other and error detection and correction can occur.<br>
	 * In addition, a copy of the data with no corrected or flagged units will be decoded 
	 * and set as {@link environment.Run#changedMessage} via {@link #decodeSimple(String[])}, as well as a version of the decoded data with only corrected 
	 * and not flagged units as {@link environment.Run#changedMessage} and the decoded data with flagged and corrected units as
	 * {@link environment.Run#correctedFlaggedMessage} for later comparison in the end.<br><br>
	 * 
	 * <dl>
	 * <dt><span class="strong">Encoding</span></dt><dd>
	 * Each character of the data gets read and then written {@link #repNumber} times in a String[](binary), which gets returned.
	 * See {@link #repNumber here} for a example of this.</dd>
	 * 
	 * <dt><span class="strong">Decoding:</span></dt><dd>
	 * Following will be done for each original character, which is now referred to as repetition block:<br>
	 * Firstly, it iterates through the repetition block and writes every new character in a list and a one in another list.
	 * If it detects a character, which already is in the character list, it gets its position in the character list 
	 * and increments this position in the integer list. 
	 * This works, because the indices of both lists are synchronized due to being handled similar.<br>
	 * Then it gets the index of the integer with the highest value and 
	 * prints the character found in the character list with this index in the decoded message String[](binary).
	 * This way, always the character which is represented most in each repetition block will make up corrected character in the result.<br>
	 * However, it could still be, that there were originally multiple equal highest numbers in the integer list, 
	 * meaning that multiple characters were represented equally. 
	 * For this case, the integer list gets sorted afterwards and the two highest numbers get compared. 
	 * If they are equal, the whole block - a unit of binary characters - will be flagged / replaced by 
	 * {@link environment.Run#flagSignBinary}. 
	 * The just corrected message will always show the character sorted highest during the integer list sorting.</dd>
	 * 
	 * <dt><span class="strong">Note:</span></dt><dd>
	 * The method is designed to also be able to handle non-binary data, but it's currently only implemented for binary input.</dd>
	 * </dl>
	 * @param task Defines whether the input (data) should be encoded (task = 0) or decoded (task = 1).
	 * @param data The String[](binary) which will be modified.
	 * @return Returns the modified data with corrected and flagged characters.
	 * @see <a href="https://en.wikipedia.org/wiki/Repetition_code">Wikipedia about Repetition Code</a>
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		if (task == 0) {
			String[] message = data.getStringBinaryArray();
			String[] code = new String[message.length];
			
			for (int i = 0; i < message.length; i++) {
				StringBuilder sb = new StringBuilder();
				
				for (int k = 0; k < message[i].length(); k++) {
					for (int j = 0; j < repNumber; j++) {
						sb.append(message[i].charAt(k));
					}
				}
				code[i] = sb.toString();
			}
			
			data.setStringBinaryArray(code);
			
		} else {
			
			String[] codeCo = data.getStringBinaryArray();
			String[] codeCf = codeCo.clone();
			
			UniDataType changedMessage = new UniDataType();
			UniDataType correctedMessage = new UniDataType();
			UniDataType correctedFlaggedMessage = new UniDataType();
			changedMessage.setStringBinaryArray(decodeSimple(codeCf.clone()));
			environment.Run.changedMessage = changedMessage.getStringUnicode();
			
			
			for (int i = 0; i < codeCf.length; i++) {
				StringBuilder sb = new StringBuilder();
				boolean flag = false;
				
				for (int k = 0; k < codeCf[i].length(); k += repNumber) {
					List<Character> characters = new ArrayList<Character>();
					List<Integer> numFound = new ArrayList<Integer>();
					
					for (int j = 0; j < repNumber; j++) {
						if (characters.contains(codeCf[i].charAt(j + k))) {
							numFound.set(characters.indexOf(codeCf[i].charAt(j + k)), numFound.get(characters.indexOf(codeCf[i].charAt(j + k))) + 1);
						} else {
							characters.add(codeCf[i].charAt(j + k));
							numFound.add(1);
						}
					}
					
					int index = numFound.indexOf(Collections.max(numFound));
					sb.append(characters.get(index));
					
					Collections.sort(numFound);
					if (numFound.size() > 1 && numFound.get(0) == numFound.get(1)) {
						flag = true;
					} 
				}
				
				codeCo[i] = sb.toString();
				if (flag) {
					codeCf[i] = environment.Run.flagSignBinary;
				} else {
					codeCf[i] = sb.toString();
				}
			}
			
			correctedMessage.setStringBinaryArray(codeCo);
			correctedFlaggedMessage.setStringBinaryArray(codeCf);
			environment.Run.correctedMessage = correctedMessage.getStringUnicode();
			environment.Run.correctedFlaggedMessage = correctedFlaggedMessage.getStringUnicode();
			data.setStringBinaryArray(codeCf);
		}
		
		return data;
	}
	
	
	/**
	 * Reverses the encoding made by {@link #doJob(byte, UniDataType)} (task 0) with neither error detection nor correction.<br>
	 * @param stringBA The encoded String[] to be decoded.
	 * @return Returns the decoded input without adding any new corrections or flags.
	 */
	public String[] decodeSimple(String[] stringBA) {
		String[] stringBANew = new String[stringBA.length];
		
		for (int i = 0; i < stringBA.length; i++) {
			StringBuilder sb = new StringBuilder();
			
			for (int k = 0; k < stringBA[i].length(); k += repNumber) {
				sb.append(stringBA[i].charAt(k));
			}
			stringBANew[i] = sb.toString();
		}
		
		return stringBANew;
	}
	

	/** @see environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		lDescription = new Label();
		lDescription.setText("This en-/decoder repeats each bit of the input multiple times.\n"
				+ "The number of repetitions can currently only be set via code.");
		lDescription.setFont(environment.Main.fNormalText);
		lDescription.setTextFill(environment.Main.cNormal);
		lDescription.setPrefWidth(root.getPrefWidth());
		lDescription.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(lDescription);
	}
	
	
	/** @see environment.ExperimentElement#save()*/
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
	/** @see environment.ExperimentElement#getGui()*/
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}
	 * @see environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #type}
	 * @see environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
