package enDecoder;

import java.util.ArrayList;

import environment.ExperimentElement;
import environment.Main;
import environment.UniDataType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

/**
 * The {@link enDecoder en- / decoder} "Parity Check" which is selectable on the {@link environment.pages.EnDecoderPage en- / decoder page}.
 * This encoder adds after every unit either a 0 or 1 in a way that every unit has a even number of 1s afterwards.
 * Therefore, the decoder can check whether there is still a even number of 1s and if not, 
 * there must have been an odd number of changed bits in this unit. 
 * If {@link #boCrossPC} = true, there will also parity units be attached and used for error detection and correction.
 * @author Wolkenfarmer
 * @see #doJob(byte, UniDataType) doJob() for further information
 */
public class ParityCheck implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Binary Parity Check";
	/** Defines whether this is an en- / decoder or a pre-en- / post-decoder. This experiment element is only an en- / decoder.
	 * 0: en- / decoder
	 * 1: pre- / post-*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link environment.pages.guiElements.InformationSegment}
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * Its content ({@link #lDescription}, {@link #rbParSimple}, {@link #rbParCross}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	
	/** Saves whether the simple (false) or the cross (true) parity check should be used on the next 
	 * {@link environment.Run#run(ExperimentElement, ExperimentElement, ExperimentElement, ExperimentElement) run} of the communication experiment.
	 * It gets set by {@link #rbParSimple} and {@link #rbParCross} and its default is false.*/
	private static boolean boCrossPC;
	/** Saves how many units (most likely bytes) will be between each parity check unit.
	 * Gets used in {@link #doJob(byte, UniDataType)}. E.g. with 4:<br>
	 * _011000011 ((encoded) Byte 1)<br>
	 * _011000101 ((encoded) Byte 2)<br>
	 * _011000110 ((encoded) Byte 3)<br>
	 * _011001001 ((encoded) Byte 4)<br>
	 * _000001001 (Correction unit)<br>
	 * _011001001 ((encoded) Byte 1)<br><br>
	 * Currently this variable can only be set manually.*/
	private static short crossPCDistance = 8;

	/** Label displaying the description for this experiment element. It gets directly attached to {@link #root}.*/
	private static Label lDescription;
	/** The toggle group to toggle between the simple parity check ({@link #boCrossPC = false}) and the cross parity check 
	 * ({@link #boCrossPC = true}). Connects {@link #rbParSimple} and {@link #rbParCross}.*/
	private static ToggleGroup tgChangeRate;
	/** The radio button of {@link #tgChangeRate} which represents the simple parity check. 
	 * It sets {@link #boCrossPC} to false and is directly attached to {@link #root}.*/
	private static RadioButton rbParSimple;
	/** The radio button of {@link #tgChangeRate} which represents the cross parity check.
	 * It sets {@link #boCrossPC} to true and is directly attached to {@link #root}.*/
	private static RadioButton rbParCross;
	
	
	/** 
	 * Does the en- and decoding of the message with error detection and correction.<br>
	 * While encoding (task = 0) it attaches parity bits or whole parity units to each unit of the input (divided by '-') 
	 * and while decoding (task = 1) checks whether the parity bits and parity units still sum up correctly 
	 * and otherwise detects or even corrects the changes made and ultimately reverses them while encoding.
	 * It either does a simple binary parity check or a cross binary parity check depending on {@link #boCrossPC}.<br>
	 * In addition, a copy of the data with no corrected or flagged units will be decoded 
	 * and set as {@link environment.Run#changedMessage} via {@link #decodeSimple(String[])}, as well as a version of the decoded data with only corrected 
	 * and not flagged units as {@link environment.Run#changedMessage} and the decoded data with flagged and corrected units as
	 * {@link environment.Run#correctedFlaggedMessage} for later comparison in the end.<br><br>
	 * 
	 * <dl>
	 * <dt><span class="strong">Encoding</span></dt><dd>
	 * It counts the ones of every unit (separated by '-') and attaches a parity bit at the end so that there is an even number of ones in every unit.
	 * In the case of the cross-parity check, after every {@link #crossPCDistance} units a parity unit gets attached 
	 * so that the number of ones in the first digit of every unit is even in every segment ({@link #crossPCDistance} units + parity unit).
	 * If the  number of initial units can't be divided by {@link #crossPCDistance}, the rest will just get parity bits attached.</dd>
	 * 
	 * <dt><span class="strong">Decoding:</span></dt><dd>
	 * <strong>Simple:</strong> Checks whether there is still an even number of ones in every unit and if not, flags the character. 
	 * In addition, it reverses the changes made while encoding (not via {@link #decodeSimple(String[])} for better performance).<br>
	 * <strong>Cross:</strong> Checks whether there is still an even number of ones in every column of the segment.
	 * If it detects exactly one changed row (unit with non-fitting parity bit) and one changed column, it will assume there was one change
	 * and reverses the bit of the changed column in the changed row. However, if multiple changes get detected, 
	 * it just flags the changed rows and not the whole segment. Ultimately, {@link #decodeSimple(String[])} gets called.</dd>
	 * 
	 * <dt><span class="strong">Note:</span></dt><dd>
	 * The method assumes that the length of every unit is equally long but is not specified to UTF8 
	 * (except for distinct {@link environment.Run#flagSignBinary flag-sign}) 
	 * and the in-method reverse-encoding of the simple parity check decoding.</dd>
	 * </dl>
	 * @param task Defines whether the input (data) should be encoded (task = 0) or decoded (task = 1).
	 * @param data The String[](binary) which will be modified.
	 * @return Returns the modified data with corrected and flagged characters.
	 * @see <a href="https://en.wikipedia.org/wiki/Parity_bit">Wikipedia about a simple binary parity check</a>
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		if (task == 0) {
			String[] message = data.getStringBinaryArray();
			
			for (int i = 0; i < message.length; i++) {
				int ones = 0;
				for (int k = 0; k < message[i].length(); k++) {
					if (message[i].charAt(k) == '1') ones++;
				}
				if (ones % 2 == 1) {
					message[i] += 1;
				} else {
					message[i] += 0;
				}
			}
			
			if (boCrossPC) {
				int numNewUnits = message.length / crossPCDistance;
				int addedNewUnits = 0;
				String[] code = new String[message.length + numNewUnits];
				StringBuilder sb;
				
				// adding the new units to the String[](binary)
				for (int i = 0; i < message.length; i++) {
					if (i % (crossPCDistance) == 0 && i != 0) {
						addedNewUnits++;
					} 
					code[i + addedNewUnits] = message[i];
				}
				
				// filling the new units
				for (int i = 0; i < numNewUnits; i++) {
					int[] ones = new int[code[0].length()];
					for (int k = 0; k < crossPCDistance; k++) {
						for (int j = 0; j < code[0].length(); j++) {
							if (code[k + (i * crossPCDistance) + i].charAt(j) == '1') {
								ones[j]++;
							}
						}
					}
					
					sb = new StringBuilder();
					for (int k = 0; k < code[0].length(); k++) {
						if (ones[k] % 2 == 1) {
							sb.append('1');
						} else {
							sb.append('0');
						}
					}
					code[crossPCDistance * (i + 1) + i] = sb.toString();
				}
				data.setStringBinaryArray(code);
			} else {
				data.setStringBinaryArray(message);
			}
			
		} else {
			String[] messageCF = data.getStringBinaryArray();
			String[] messageC = messageCF.clone();
			
			UniDataType changedMessage = new UniDataType();
			UniDataType correctedMessage = new UniDataType();
			UniDataType correctedFlaggedMessage = new UniDataType();
			changedMessage.setStringBinaryArray(decodeSimple(messageCF.clone()));
			environment.Run.changedMessage = changedMessage.getStringUnicode();
	
			
			if (!boCrossPC) {
				for (int i = 0; i < messageCF.length; i++) {
					int ones = 0;
					for (int k = 0; k < messageCF[i].length(); k++) {
						if (messageCF[i].charAt(k) == '1') ones++;
					}
					
					messageCF[i] = messageCF[i].substring(0, 8);
					messageC[i] = messageC[i].substring(0, 8);
					
					if (ones % 2 == 1) {
						messageCF[i] = environment.Run.flagSignBinary;
					}
				}
				
			} else {
				
				int numSegments = messageCF.length / (crossPCDistance + 1);
				
				// error correction and detection units
				for (int i = 0; i < numSegments; i++) {
					ArrayList<Integer> incorrectColumns = new ArrayList<Integer>();
					ArrayList<Integer> incorrectRows = new ArrayList<Integer>();
					int[] onesColumn = new int[messageCF[i * (crossPCDistance + 1)].length()];
					
					// counting the incorrect spots
					for (int k = 0; k < (crossPCDistance + 1); k++) {
						int onesRow = 0;
						for (int j = 0; j < messageCF[i * (crossPCDistance + 1)].length(); j++) {
							if (messageCF[k + (i * (crossPCDistance + 1))].charAt(j) == '1') {
								onesColumn[j]++;
								onesRow++;
							}
						}
						if (onesRow % 2 != 0) {
							incorrectRows.add(k);
						}
					}
					for (int k = 0; k < messageCF[i * (crossPCDistance + 1)].length(); k++) {
						if (onesColumn[k] % 2 != 0) {
							incorrectColumns.add(k);
						}
					}
					
					// correcting and flagging
					if (incorrectColumns.size() == 1 && incorrectRows.size() == 1) {
						char[] correctedRow = new char[messageCF[incorrectRows.get(0) + (i * (crossPCDistance + 1))].length()];
						
						for (int k = 0; k < messageCF[incorrectRows.get(0) + (i * (crossPCDistance + 1))].length(); k++) {
							correctedRow[k] = messageCF[incorrectRows.get(0) + (i * (crossPCDistance + 1))].charAt(k);
						}
						if (correctedRow[incorrectColumns.get(0)] == '1') {
							correctedRow[incorrectColumns.get(0)] = '0';
						} else {
							correctedRow[incorrectColumns.get(0)] = '1';
						}
						
						messageCF[incorrectRows.get(0) + (i * (crossPCDistance + 1))] = new String(correctedRow);
						messageC[incorrectRows.get(0) + (i * (crossPCDistance + 1))] = new String(correctedRow);
						
						// Flagging if more than one change got detected
					} else if (incorrectColumns.size() > 0 || incorrectRows.size() > 0) {
						for (int k = 0; k < incorrectRows.size(); k++) {
							messageCF[incorrectRows.get(k) + (i * (crossPCDistance + 1))] = environment.Run.flagSignBinary;
						}
					}
					
				}
				
				// error detection after last unit
				for (int i = numSegments * (crossPCDistance + 1); i < messageCF.length; i++) {
					int ones = 0;
					for (int k = 0; k < messageCF[i].length(); k++) {
						if (messageCF[i].charAt(k) == '1') ones++;
					}
					if (ones % 2 == 1) {
						messageCF[i] = environment.Run.flagSignBinary;
					}
				}
				
				
				messageCF = decodeSimple(messageCF);
				messageC = decodeSimple(messageC);
			}
			
			correctedMessage.setStringBinaryArray(messageC);
			correctedFlaggedMessage.setStringBinaryArray(messageCF);
			environment.Run.correctedMessage = correctedMessage.getStringUnicode();
			environment.Run.correctedFlaggedMessage = correctedFlaggedMessage.getStringUnicode();
			data.setStringBinaryArray(messageCF);
		}
		
		return data;
	}
	
	
	/**
	 * Reverses the encoding made by {@link #doJob(byte, UniDataType)} (task 0) with neither error detection nor correction.<br>
	 * Checks whether {@link #boCrossPC parity cross check} was enabled during encoding and therefore either just trims every unit to 8 bits or
	 * additionally removes the added parity units beforehand.
	 * 
	 * <dl>
	 * <dt><span class="strong">Note:</span></dt><dd>
	 * This decoding will only work if the native length of the units is 8 bits long as in UTF8.</dd>
	 * </dl>
	 * @param stringBA The encoded String[](binary) to be decoded.
	 * @return Returns the decoded input without adding any new corrections or flags.
	 */
	public String[] decodeSimple(String[] stringBA) {
		if (boCrossPC) {
			int numNewUnits = stringBA.length / (crossPCDistance + 1);
			int removedNewUnits = 0;
			String[] stringBANew = new String[stringBA.length - numNewUnits];
			for (int i = 0; i < stringBANew.length; i++) {
				if (i % (crossPCDistance) == 0 && i != 0) {
					removedNewUnits++;
				} 
				stringBANew[i] = stringBA[i + removedNewUnits].substring(0, 8);
			}
			return stringBANew;
			
		} else {
			for (int i = 0; i < stringBA.length; i++) {
				stringBA[i] = stringBA[i].substring(0, 8);
			}
			return stringBA;
		}
	}
	

	/** @see environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		lDescription = new Label();
		lDescription.setText("This en- / decoder attaches parity bits.\n"
				+ "Choose either the simple or the cross parity check.\n"
				+ "The number of units for each parity unit in case of the cross parity check can currently only be set via code.");
		lDescription.setFont(Main.fNormalText);
		lDescription.setTextFill(Main.cNormal);
		lDescription.setAlignment(Pos.TOP_LEFT);
		lDescription.setWrapText(true);
		lDescription.setPrefWidth(root.getPrefWidth());
		lDescription.setPrefHeight(Main.calcHeightLabel(lDescription, parentWidth + 10));
		
		tgChangeRate = new ToggleGroup();
			rbParSimple = new RadioButton("Simple parity check");
			rbParSimple.setLayoutY(lDescription.getPrefHeight() + 30);
		    rbParSimple.setToggleGroup(tgChangeRate);
		    rbParSimple.setFont(Main.fNormalText);
		    rbParSimple.setTextFill(Main.cNormal);
		    rbParSimple.setPrefWidth(parentWidth);
		    rbParSimple.setPrefHeight(Main.calcHeight(rbParSimple));
		    rbParSimple.setSelected(true);
		    rbParSimple.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {boCrossPC = false;}
		    });
         
	        rbParCross = new RadioButton("Cross parity check");
	        rbParCross.setLayoutY(rbParSimple.getLayoutY() + rbParSimple.getPrefHeight() + 15);
	        rbParCross.setToggleGroup(tgChangeRate);
	        rbParCross.setFont(Main.fNormalText);
	        rbParCross.setTextFill(Main.cNormal);
	        rbParCross.setPrefWidth(parentWidth);
	        rbParCross.setPrefHeight(Main.calcHeight(rbParCross));
	        rbParCross.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {boCrossPC = true;}
		    });
        
        root.getChildren().addAll(lDescription, rbParSimple, rbParCross);
        builtGui = true;
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
