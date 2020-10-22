package environment;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Universal data type to be handed over from one method to another.
 * This class saves the given input and gives it back in the required format translating it in the process if needed.
 * In addition, it works as an universal data type for the {@link ExperimentElement interface experiment element}
 * in order to be able to get an unknown type from an experiment element.
 * @author Wolkenfarmer
 */
public class UniDataType {
	/** The String(Unicode) option as data type. Example: "Hello!"*/
	private String stringUnicode;
	/** The String(binary) option as data type. Example: "1001000-1100101-1101100-1101100-1101111-"*/
	private String stringBinary;
	/** The char[](binary) option as data type. Example: '1', '0', '0', '1', '0', '0', '0', '-'*/
	private char[] charBinary;
	
	
	/**
	 * Searches for an already set variable and converts it's content into the requested output format. 
	 * The previously set variable gets set null again in order for the {@link ExperimentElement experiment elements} 
	 * to have to use the most recently modified data.<br><br>
	 * 
	 * String(Unicode) to String(binary): Translates the String char after char into it's binary representation. 
	 * The char-representations get divided by a '-'. Uses the 4th code example of the first @ see in a slightly modified version. 
	 * UTF8 is used for this conversion.<br>
	 * String(binary) to String(Unicode): Uses the 5th code example of the first @ see as it's base. 
	 * Got further enhanced with the help of Vincent (see comments under article).UTF8 is used for this conversion.<br><br>
	 * 
	 * String(binary) to char[](binary): Uses String.toCharArray().<br>
	 * char[](binary) to String(binary): Uses new String(char[]).<br><br>
	 * 
	 * String(Unicode) to char[](binary): Uses "String(Unicode) to String(binary)" plus .toCharArray().<br>
	 * char[](binary) to String(Unicode): Uses "char[](binary) to String(binary)" and "String(binary) to String(Unicode)".
	 * 
	 * @param output The requested output.
	 * @see <a href="https://mkyong.com/java/java-convert-string-to-binary/">mkyong</a>
	 */
	private void converter(String output) {		
		if (stringUnicode != null) {
			System.out.println("__UniDataType_converter: input type found: String(Unicode) -> \"" + output + "\"");
			switch (output) {
			case "String(binary)":
				StringBuilder sb = new StringBuilder();
				byte[] bInput = stringUnicode.getBytes(StandardCharsets.UTF_8);
				for (byte b : bInput) {
		            int val = b;
		            for (int i = 0; i < 8; i++) {
		                sb.append((val & 128) == 0 ? 0 : 1);      // 128 = 1000 0000
		                val <<= 1;
		            }
		            sb.append('-');
		        }
		        stringBinary = sb.toString();
		        stringUnicode = null;
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
			System.out.println("__UniDataType_converter: input type found: String(binary) \"" + output + "\"");
			switch (output) {
			case "String(Unicode)":
				String[] stringBA = stringBinary.split("-");
				String stringBConvert = new String();
				
				for (int i = 0; i < stringBA.length; i++) {
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
					byte[] array = ByteBuffer.allocate(4).putInt(Integer.parseInt(stringBConvert, 2)).array();
					
					if (stringUnicode != null) {
						stringUnicode = stringUnicode + new String(array, StandardCharsets.UTF_8).substring(4 - stringBConvert.length() / 8);
					} else {
						stringUnicode = new String(array, StandardCharsets.UTF_8).substring(4 - stringBConvert.length() / 8);
					}
				}
				stringBinary = null;
				break;
				
			case "char[](binary)":
				charBinary = stringBinary.toCharArray();
				stringBinary = null;
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"String(binary)\" -> \"" + output + "\"");
			}
			
			
		} else if (charBinary != null) {
			System.out.println("__UniDataType_converter: input type found: char[](binary) \"" + output + "\"");
			switch (output) {
			case "String(binary)":
				stringBinary = new String(charBinary);
				charBinary = null;
				break;
			
			case "String(Unicode)":
				converter("String(binary)");
				charBinary = null;
				converter("String(Unicode)");
				stringBinary = null;
				
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
