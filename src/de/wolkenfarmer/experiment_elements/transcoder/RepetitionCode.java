package de.wolkenfarmer.experiment_elements.transcoder;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.logic.Main;
import de.wolkenfarmer.environment.logic.Run;
import de.wolkenfarmer.environment.logic.UniDataType;
import de.wolkenfarmer.experiment_elements.ExperimentElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * The {@link de.wolkenfarmer.experiment_elements.transcoder transcoder} "Repetition Code" which is selectable on the 
 * {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page}.
 * This encoder repeats every given character {@link #repNumber} times.
 * Therefore, the decoder can check whether there is a character out of place in a repetition-segment 
 * and consequently correct or flag the character.
 * @author Wolkenfarmer
 * @see #doJob(byte, UniDataType) doJob() for further information
 */
public class RepetitionCode implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Repetition Code";
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.gui_elements.OptionButton#setOnActionW(ExperimentElement)}).
	 * Its content ({@link #lDescription}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	
	/** Saves how often each character should be repeated during encoding. Gets used in {@link #doJob(byte, UniDataType)}. E.g. with 3:<br>
	 * 1011 would be 111000111111<br> 
	 * This variable gets set by {@link #tfRepeat} in {@link #save()}.*/
	private static int repNumber;
	
	/** Label displaying the description for this experiment element. It gets directly attached to {@link #root}.*/
	private static Label lDescription;
	/** The text field where the user can enter the desired amount of {@link #repNumber repetitions} for the next communication experiment. 
	 * Every character which is not a digit will automatically be replaced by "". It gets directly attached to {@link #root}.*/
	private static TextField tfRepeat;
	/** Label displaying the different exceptions for wrong {@link #tfRepeat} input. 
	 * It gets updated to fit the current input in {@link #save()}. It gets directly attached to {@link #root}.*/
	private static Label lException;
	
	
	/** 
	 * Does the en- and decoding of the message with error detection and correction.<br>
	 * During encoding (task 0) each bit of the message will be repeated {@link #repNumber} times. 
	 * Therefore, the different repetitions can be compared against each other and error detection and correction can occur.<br>
	 * In addition, a copy of the data with no corrected or flagged units will be decoded 
	 * and set as {@link Run#changedMessage} via {@link #decodeSimple(String[])}, 
	 * as well as a version of the decoded data with only corrected 
	 * and not flagged units as {@link Run#changedMessage} and the decoded data with flagged and corrected units as
	 * {@link Run#correctedFlaggedMessage} for later comparison in the end.<br><br>
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
	 * However, it's still possible that there were originally multiple equal highest numbers in the integer list, 
	 * meaning that multiple characters were represented equally. 
	 * For this case, the integer list gets sorted afterwards and the two highest numbers get compared. 
	 * If they are equal, the whole block - a unit of binary characters - will be flagged / replaced by 
	 * {@link Run#flagSignBinary}. 
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
			Run.changedMessage = changedMessage.getStringUnicode();
			
			
			for (int i = 0; i < codeCf.length; i++) {
				StringBuilder sb = new StringBuilder();
				boolean flag = false;
				
				for (int k = 0; k < codeCf[i].length(); k += repNumber) {
					List<Character> characters = new ArrayList<Character>();
					List<Integer> numFound = new ArrayList<Integer>();
					
					for (int j = 0; j < repNumber; j++) {
						if (characters.contains(codeCf[i].charAt(j + k))) {
							numFound.set(characters.indexOf(codeCf[i].charAt(j + k)), 
									numFound.get(characters.indexOf(codeCf[i].charAt(j + k))) + 1);
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
					codeCf[i] = Run.flagSignBinary;
				} else {
					codeCf[i] = sb.toString();
				}
			}
			
			correctedMessage.setStringBinaryArray(codeCo);
			correctedFlaggedMessage.setStringBinaryArray(codeCf);
			Run.correctedMessage = correctedMessage.getStringUnicode();
			Run.correctedFlaggedMessage = correctedFlaggedMessage.getStringUnicode();
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
	

	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		lDescription = new Label();
		lDescription.setText("This transcoder repeats each bit of the input multiple times.\n"
				+ "The number of repetitions can be entered below. "
				+ "The number of repetitions has to be between 1 and 20 (inclusively). "
				+ "Please note that higher numbers can cause a considerably longer computing time.");
		lDescription.setFont(Constants.F_NORMAL);
		lDescription.setTextFill(Constants.C_NORMAL);
		lDescription.setPrefWidth(root.getPrefWidth());
		lDescription.setWrapText(true);
		
		tfRepeat = new TextField();
        tfRepeat.setFont(Constants.F_NORMAL);
        tfRepeat.setPromptText("Repetitions");
        tfRepeat.setStyle("-fx-text-inner-color: WHITESMOKE;");
        tfRepeat.setBackground(new Background(new BackgroundFill(Color.grayRgb(90), new CornerRadii(5),  null)));
        tfRepeat.setFocusTraversable(false);
        tfRepeat.setPrefHeight(30);
        tfRepeat.setPrefWidth(130);
        tfRepeat.setMaxWidth(root.getPrefWidth());
        tfRepeat.setLayoutY(Main.calcHeightLabel(lDescription, parentWidth + 10));
        tfRepeat.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	tfRepeat.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
        
        lException = new Label();
        lException.setText("Exception: The number of repetitions has to be between 1 and 20 (inclusively).\n"
        		+ "2 got set instead of \"" + tfRepeat.getText() + "\".");
        lException.setFont(Constants.F_NORMAL_ITALIC);
        lException.setTextFill(Constants.C_PINK);
        lException.setPrefWidth(root.getPrefWidth());
        lException.setLayoutY(tfRepeat.getLayoutY() + tfRepeat.getPrefHeight() + 20);
        lException.setWrapText(true);
        lException.setVisible(false);
        
        builtGui = true;
        root.getChildren().addAll(lDescription, tfRepeat, lException);
	}
	
	
	/** 
	 * Checks whether the given number in {@link #tfRepeat} is correct and if not, it updates {@link #lException} accordingly.*/
	public void save() {
		if (!tfRepeat.getText().equals("")) {
			int newRepNumber = Integer.parseInt(tfRepeat.getText());
			if (Integer.parseInt(tfRepeat.getText()) > 20 || Integer.parseInt(tfRepeat.getText()) < 1) {
				repNumber = 3;
				lException.setText("Exception: The number of repetitions has to be between 1 and 20 (inclusively).\n"
        		+ "3 got set instead of \"" + tfRepeat.getText() + "\".");
				lException.setVisible(true);
			} else {
				repNumber = newRepNumber;
				lException.setVisible(false);
			}
		} else {
			repNumber = 3;
			lException.setText("Warning: Please enter a repetition number. 3 got set instead of nothing.");
			lException.setVisible(true);
		}
		
		System.out.println(name + " saved!");
	}
	
	
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}*/
	public String getName(boolean optionButton) {return name;}
}
