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
	 * Gets used in {@link #doJob(byte, UniDataType)}. E.g. with 4:
	 * _011000011 ((encoded) Byte 1)<br>
	 * _011000101 ((encoded) Byte 2)<br>
	 * _011000110 ((encoded) Byte 3)<br>
	 * _011001001 ((encoded) Byte 4)<br>
	 * _000001001 (Correction unit)<br>
	 * _011001001 ((encoded) Byte 1)*/
	private static short crossPCDistance = 4;

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
	 * While encoding (task = 0) attaches parity bits or whole parity units to the input and while decoding (task = 1) 
	 * checks whether the parity bits and parity units still sum up correctly and otherwise detects or even corrects the made changes 
	 * and ultimately reverses the changes made while encoding. 
	 * Either does a simple binary parity check or a cross binary parity check depending on {@link #boCrossPC}.<br><br>
	 * 
	 * <dl>
	 * <dt><span class="strong">Encoding</span></dt><dd>
	 * It counts the ones of every unit (divided by '-') and attaches a parity bit at the end so that there is a even number of ones in every unit.
	 * In the case of the cross parity check, after every {@link #crossPCDistance} units a parity unit gets attached 
	 * so that the number of ones in the first digit of every unit is even in every segment ({@link #crossPCDistance} units + parity unit).
	 * If the  number of initial units can't be divided by {@link #crossPCDistance}, the rest will just get parity bits attached.</dd>
	 * 
	 * <dt><span class="strong">Decoding:</span></dt><dd>
	 * <strong>Simple:</strong> Checks whether there is still an even number of ones in every unit and if not, flags the character.<br>
	 * <strong>Cross:</strong> Checks whether there is still an even number of ones in every column of the segment.
	 * If it detect exactly one changed row (unit with non-fitting parity bit) and one changed column, it will assume there was exactly change
	 * and reverses the bit of the changed column in the changed row. However, if multiple changes get detected, it just flags the characters.<br>
	 * <strong>Both:</strong> Reverse the changes made while encoding (removes parity bits and units).</dd>
	 * 
	 * <dt><span class="strong">apiNote:</span></dt><dd>
	 * The method assumes that the length of every unit is equally long.</dd>
	 * </dl>
	 * @param task Defines whether the input (data) should be encoded (task = 0) or decoded (task = 1).
	 * @param data The String[](binary) which will be modified.
	 * @return Returns the modified data.
	 * @see <a href="https://en.wikipedia.org/wiki/Parity_bit">Wikipedia about a simple binary parity check</a>
	 */
	public UniDataType doJob(byte task, UniDataType data) {
		String[] stringBA = data.getStringBinaryArray();
		
		if (task == 0) {
			for (int i = 0; i < stringBA.length; i++) {
				int ones = 0;
				for (int k = 0; k < stringBA[i].length(); k++) {
					if (stringBA[i].charAt(k) == '1') ones++;
				}
				if (ones % 2 == 1) {
					stringBA[i] += 1;
				} else {
					stringBA[i] += 0;
				}
			}
			
			if (boCrossPC) {
				int numNewUnits = stringBA.length / crossPCDistance;
				int addedNewUnits = 0;
				String[] stringBATemp = new String[stringBA.length + numNewUnits];
				StringBuilder sb;
				
				// adding the new units to the String[](binary)
				for (int i = 0; i < stringBA.length; i++) {
					if (i % (crossPCDistance) == 0 && i != 0) {
						addedNewUnits++;
					} 
					stringBATemp[i + addedNewUnits] = stringBA[i];
				}
				stringBA = stringBATemp;
				
				// filling the new units
				for (int i = 0; i < numNewUnits; i++) {
					int[] ones = new int[stringBA[0].length()];
					for (int k = 0; k < crossPCDistance; k++) {
						for (int j = 0; j < stringBA[0].length(); j++) {
							if (stringBA[k + (i * crossPCDistance) + i].charAt(j) == '1') {
								ones[j]++;
							}
						}
					}
					
					sb = new StringBuilder();
					for (int k = 0; k < stringBA[0].length(); k++) {
						if (ones[k] % 2 == 1) {
							sb.append('1');
						} else {
							sb.append('0');
						}
					}
					stringBA[crossPCDistance * (i + 1) + i] = sb.toString();
				}
			}
			data.setStringBinaryArray(stringBA);
			
		} else {
			int correctedChanges = 0;
			int detectedChanges = 0;
			int numUnits = 0;
			int numChar = 0;
			
			
			if (!boCrossPC) {
				for (int i = 0; i < stringBA.length; i++) {
					int ones = 0;
					for (int k = 0; k < stringBA[i].length(); k++) {
						if (stringBA[i].charAt(k) == '1') ones++;
					}
					if (ones % 2 == 1) {
						detectedChanges++;
					}
					numChar++;
					
					stringBA[i] = stringBA[i].substring(0, 8);
				}
			} else {
				
				numUnits = stringBA.length / (crossPCDistance + 1);
				
				// error correction and detection units
				for (int i = 0; i < numUnits; i++) {
					ArrayList<Integer> incorrectColumns = new ArrayList<Integer>();
					ArrayList<Integer> incorrectRows = new ArrayList<Integer>();
					int[] onesColumn = new int[stringBA[0].length()];
					
					// counting the incorrect spots
					for (int k = 0; k < (crossPCDistance + 1); k++) {
						int onesRow = 0;
						for (int j = 0; j < stringBA[0].length(); j++) {
							if (stringBA[k + (i * (crossPCDistance + 1))].charAt(j) == '1') {
								onesColumn[j]++;
								onesRow++;
							}
						}
						if (onesRow % 2 != 0) {
							incorrectRows.add(k);
						}
					}
					for (int k = 0; k < stringBA[0].length(); k++) {
						if (onesColumn[k] % 2 != 0) {
							incorrectColumns.add(k);
						}
					}
					
					// correcting
					if (incorrectColumns.size() == 1 && incorrectRows.size() == 1) {
						char[] correctedRow = new char[stringBA[0].length()];
						
						for (int k = 0; k < stringBA[0].length(); k++) {
							correctedRow[k] = stringBA[incorrectRows.get(0) + (i * (crossPCDistance + 1))].charAt(k);
						}
						if (correctedRow[incorrectColumns.get(0)] == '1') {
							correctedRow[incorrectColumns.get(0)] = '0';
						} else {
							correctedRow[incorrectColumns.get(0)] = '1';
						}
						
						stringBA[incorrectRows.get(0) + (i * (crossPCDistance + 1))] = new String(correctedRow);
						correctedChanges++;
					} else if (incorrectColumns.size() > 0 || incorrectRows.size() > 0) {
						detectedChanges += incorrectColumns.size() + incorrectRows.size();
					}
					
				}
				
				// error detection after last unit
				for (int i = numUnits * (crossPCDistance + 1); i < stringBA.length; i++) {
					int ones = 0;
					for (int k = 0; k < stringBA[i].length(); k++) {
						if (stringBA[i].charAt(k) == '1') ones++;
					}
					if (ones % 2 == 1) {
						detectedChanges++;
					}
				}
				
				
				// decoding unit
				int numNewUnits = stringBA.length / (crossPCDistance + 1);
				int removedNewUnits = 0;
				String[] stringBATemp = new String[stringBA.length - numNewUnits];
				for (int i = 0; i < stringBATemp.length; i++) {
					if (i % (crossPCDistance) == 0 && i != 0) {
						removedNewUnits++;
					} 
					stringBATemp[i] = stringBA[i + removedNewUnits];
				}
				stringBA = stringBATemp;
				
				
				// decoding simple
				for (int i = 0; i < stringBA.length; i++) {
					stringBA[i] = stringBA[i].substring(0, 8);
				}
			}
			
			System.out.println("corrected changes: " + correctedChanges);
			System.out.println("detected changes: " + detectedChanges);
			System.out.println("num units: " + numUnits);
			System.out.println("num characters: " + numChar);
			data.setStringBinaryArray(stringBA);
		}
		
		return data;
	}
	

	/** @see environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		lDescription = new Label();
		lDescription.setText("This en- / decoder attaches parity bits. Choose either the simple or cross parity check, "
				+ "which are both described below");
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
