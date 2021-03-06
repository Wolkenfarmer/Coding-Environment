package de.wolkenfarmer.environment.logic;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import de.wolkenfarmer.experiment_elements.ExperimentElement;

/**
 * Universal data type to be handed over from one method to another. <br>
 * This class saves the given input and gives it back in the required format {@link #converter(String) converting} it in the process if needed.
 * In addition, it works as an universal data type for the interface {@link ExperimentElement experiment element}
 * in order to be able to get an unknown type from an experiment element.
 * @author Wolkenfarmer (+ mkyong, Vincent)
 * @see #converter(String) converter() for further information
 */
public class UniDataType {
	/** The String(Unicode) option as data type. Example: "Hello!"*/
	private String stringUnicode;
	/** The String(binary) option as data type. Example: "1001000-1100101-1101100-1101100-1101111-"*/
	private String stringBinary;
	/** The String[](binary) option as data type. Example: "1001000", "1100101", "1101100", "1101100", "1101111"*/
	private String[] stringBinaryArray;
	/** The char[](binary) option as data type. Example: '1', '0', '0', '1', '0', '0', '0', '-'*/
	private char[] charBinary;
	
	/**
	 * String builder for "String(Unicode) to String(binary)" and "String[](binary) to String(binary)".
	 * @see #converter(String)
	 */
	StringBuilder sb;
	
	
	/**
	 * Searches for an already set variable and converts it's content into the requested output format. <br>
	 * The previously set variable gets set null again in order for the {@link ExperimentElement experiment elements} 
	 * to have to use the most recently modified data.<br><br>
	 * 
	 * <dl>
	 * <dt><span class="strong">String(Unicode) to ...</span></dt><dd>
	 * ... String(binary): Translates the String char after char into it's binary representation. 
	 * The char-representations get divided by a '-'. Uses the 4th code example of the first @ see in a slightly modified version. 
	 * UTF8 is used for this conversion.<br>
	 * ... String[](binary): Uses "String(Unicode) to String(binary)" and "String(binary) to String[](binary)".<br>
	 * ... char[](binary): Uses "String(Unicode) to String(binary)" plus .toCharArray().</dd>
	 * 
	 * <dt><span class="strong">String(binary) to ...</span></dt><dd>
	 * ... String(Unicode): Uses the 5th code example of the first @ see as it's base. 
	 * Got further enhanced with the help of Vincent (see comments under article) and then further improved for the use in this program 
	 * (various try-catches for example for noisy Unicode interpretation. UTF8 is used for this conversion.<br>
	 * ... String[](binary): Uses String.split("-").<br>
	 * ... char[](binary): Uses String.toCharArray().</dd>
	 * 
	 * <dt><span class="strong">String[](binary) to ...</span></dt><dd>
	 * ... String(Unicode): Uses "String[](binary) to String(binary)" and "String(binary) to String(Unicode)".<br>
	 * ... String(binary): Appends each String[](binary)-element and puts '-' in between.<br>
	 * ... char[](binary): Uses "String[](binary) to String(binary)" and "String(binary) to char[](binary)".</dd>
	 * 
	 * <dt><span class="strong">char[](binary) to ...</span></dt><dd>
	 * ... String(Unicode): Uses "char[](binary) to String(binary)" and "String(binary) to String(Unicode)".<br>
	 * ... String(binary): Uses new String(char[]).<br>
	 * ... String[](binary): Uses "char[](binary) to String(binary)" and "String(binary) to String[](binary)".</dd>
	 * 
	 * <dt><span class="strong">apiNote:</span></dt><dd>
	 * If you want to ensure that the content is always available in only one variable, 
	 * use the corresponding get-method before the set-method.</dd>
	 * </dl>
	 * 
	 * @param output The requested output.
	 * @see <a href="https://mkyong.com/java/java-convert-string-to-binary/">mkyong</a>
	 */
	private void converter(String output) {		
		if (stringUnicode != null) {
			//System.out.println("__UniDataType_converter: input type found: String(Unicode) -> \"" + output + "\"");
			switch (output) {
			case "String(binary)":
				sb = new StringBuilder();
				byte[] bInput = stringUnicode.getBytes(StandardCharsets.UTF_8);
				for (byte b : bInput) {
		            int val = b;
		            for (int i = 0; i < 8; i++) {
		                sb.append((val & 128) == 0 ? 0 : 1);
		                val <<= 1;
		            }
		            sb.append('-');
		        }
		        stringBinary = sb.toString();
		        stringUnicode = null;
				break;
				
			case "String[](binary)":
				converter("String(binary)");
				converter("String[](binary)");
				break;
				
			case "char[](binary)":
				converter("String(binary)");
				charBinary = stringBinary.toCharArray();
				stringBinary = null;
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"String(Unicode)\" -> \"" + output + "\"");
			}
			
			
		} else if (stringBinary != null) {
			//System.out.println("__UniDataType_converter: input type found: String(binary) -> \"" + output + "\"");
			switch (output) {
			case "String(Unicode)":
				String[] stringBA = stringBinary.split("-");
				String stringBConvert = new String();
				
				if (!stringBinary.equals("")) {
					for (int i = 0; i < stringBA.length; i++) {
						try {
							if (stringBA[i].startsWith("110")) {
								stringBConvert = stringBA[i] + stringBA[i + 1];
								i++;
							} else if (stringBA[i].startsWith("1110")) {
								stringBConvert = stringBA[i] + stringBA[i + 1] + stringBA[i + 2];
								i += 2;
							} else if (stringBA[i].startsWith("11110")) {
								stringBConvert = stringBA[i] + stringBA[i + 1] + stringBA[i + 2] + stringBA[i + 3];
								i += 3;
							} else {
								stringBConvert = stringBA[i];
							}
						} catch (ArrayIndexOutOfBoundsException e) {
							stringBConvert = "00111111";
						}
						
						try {
							byte[] array = ByteBuffer.allocate(4).putInt(Integer.parseInt(stringBConvert, 2)).array();
							if (stringUnicode != null) {
								stringUnicode = stringUnicode + 
										new String(array, StandardCharsets.UTF_8).substring(4 - stringBConvert.length() / 8);
							} else {
								stringUnicode = new String(array, StandardCharsets.UTF_8).substring(4 - stringBConvert.length() / 8);
							}
						} catch (NumberFormatException e) {
							if (stringBConvert.length() >= 32) {
								if (stringUnicode != null) {
									stringUnicode = stringUnicode + Run.flagSignUnicode + Run.flagSignUnicode + Run.flagSignUnicode 
											+ Run.flagSignUnicode;
								} else {
									stringUnicode = "" + Run.flagSignUnicode + Run.flagSignUnicode + Run.flagSignUnicode + Run.flagSignUnicode;
								}
							} else {
								throw e;
							}
						}
					}
				} else {
					stringUnicode = "";
				}
				stringBinary = null;
				break;
				
			case "String[](binary)":
				stringBinaryArray = stringBinary.split("-");
				stringBinary = null;
				break;
				
			case "char[](binary)":
				charBinary = stringBinary.toCharArray();
				stringBinary = null;
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"String(binary)\" -> \"" + output + "\"");
			}
			
		
		} else if (stringBinaryArray != null) {
			//System.out.println("__UniDataType_converter: input type found: String[](binary) -> \"" + output + "\"");
			switch (output) {
			case "String(Unicode)":
				converter("String(binary)");
				converter("String(Unicode)");
				break;
				
			case "String(binary)":
				sb = new StringBuilder();
				for (int i = 0; i < stringBinaryArray.length; i++) {
					sb.append(stringBinaryArray[i]);
					sb.append('-');
				}
				stringBinary = sb.toString();
				stringBinaryArray = null;
				break;
				
			case "char[](binary)":
				converter("String(binary)");
				converter("char[](binary)");
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"String[](binary)\" -> \"" + output + "\"");
			}
			
			
		} else if (charBinary != null) {
			//System.out.println("__UniDataType_converter: input type found: char[](binary) -> \"" + output + "\"");
			switch (output) {
			case "String(Unicode)":
				converter("String(binary)");
				charBinary = null;
				converter("String(Unicode)");
				stringBinary = null;
				break;
				
			case "String(binary)":
				stringBinary = new String(charBinary);
				charBinary = null;
				break;
				
			case "String[](binary)":
				converter("String(binary)");
				converter("String[](binary)");
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"char[](binary)\" -> \"" + output + "\"");
			}
			
			
		} else {
			System.out.println("__UniDataType_converter: no input detected while converting to \"" + output + "\"");
		}
	}
	
	
	/** Sets {@link #stringUnicode} to v.
	 * @param v New value for {@link #stringUnicode}.*/
	public void setStringUnicode(String v) {
		stringUnicode = v;
	}
	/** Returns {@link #stringUnicode} and calls {@link #converter(String)} beforehand if {@link #stringUnicode} was null.
	 * @return Returns {@link #stringUnicode}.*/
	public String getStringUnicode() {
		if (stringUnicode == null) {
			converter("String(Unicode)");
		}
		return stringUnicode;
	}
	
	/** Sets {@link #stringBinary} to v.
	 * @param v New value for {@link #stringBinary}.*/
	public void setStringBinary(String v) {
		stringBinary = v;
	}
	/** Returns {@link #stringBinary} and calls {@link #converter(String)} beforehand if {@link #stringBinary} was null.
	 * @return Returns {@link #stringBinary}.*/
	public String getStringBinary() {
		if (stringBinary == null) {
			converter("String(binary)");
		}
		return stringBinary;
	}
	
	/** Sets {@link #stringBinaryArray} to v.
	 * @param v New value for {@link #stringBinaryArray}.*/
	public void setStringBinaryArray(String[] v) {
		stringBinaryArray = v;
	}
	/** Returns {@link #stringBinaryArray} and calls {@link #converter(String)} beforehand if {@link #stringBinaryArray} was null.
	 * @return Returns {@link #stringBinaryArray}.*/
	public String[] getStringBinaryArray() {
		if (stringBinaryArray == null) {
			converter("String[](binary)");
		}
		return stringBinaryArray;
	}
	
	/** Sets {@link #charBinary} to v.
	 * @param v New value for {@link #charBinary}.*/
	public void setCharBinary(char[] v) {
		charBinary = v;
	}
	/** Returns {@link #charBinary} and calls {@link #converter(String)} beforehand if {@link #charBinary} was null.
	 * @return Returns {@link #charBinary}.*/
	public char[] getCharBinary() {
		if (charBinary == null) {
			converter("char[](binary)");
		}
		return charBinary;
	}
}
