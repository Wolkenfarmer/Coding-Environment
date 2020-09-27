package environment;

import java.util.Arrays;

/**
 * Universal data type to be handed over from one method to another.
 * This class saves the given input and gives it back in the required format translating it in the process if needed.
 * In addition, it works as an universal data type for the {@link ExperimentElement interface experiment element}
 * in order to be able to get an unknown type from an experiment element.
 * @author Wolkenfarmer
 */
public class UniDataType {
	/** The String[ascii] option as data type. Example: "Hello!"*/
	private String stringAscii;
	/** The String[binary] option as data type. Example: "1001000-1100101-1101100-1101100-1101111-"*/
	private String stringBinary;
	
	
	/**
	 * Searches for an already set variable and converts it's content into the requested output format.<br>
	 * 
	 * __ String[ascii] to String[binary]: Translates the String char after char into it's binary representation. 
	 * The char-representations get divided by a '-'. This conversion got taken from the 1. @ see with some slight modifications.<br>
	 * 
	 * __ String[binary] to String[ascii]: Splits the given binary String at every [-] and translates each part 
	 * into the corresponding ASCII-Character.  This conversion got taken from the 2. @ see with some slight modification
	 * 
	 * @param output The requested output.
	 * @see <a href="https://mkyong.com/java/java-convert-string-to-binary/">String[ascii] to String[binary] - mkyong</a>
	 * @see <a href="https://stackoverflow.com/questions/4211705/binary-to-text-in-java">String[binary] to String[ascii] - Leon Kasko</a>
	 */
	private void converter(String output) {		
		if (stringAscii != null) {
			System.out.println("__UniDataType_converter: input type found: String[ascii] -> \"" + output + "\"");
			switch (output) {
			case "String[binary]":
				StringBuilder result = new StringBuilder();
		        char[] chars = stringAscii.toCharArray();
		        for (char aChar : chars) {
		            result.append(String.format(Integer.toBinaryString(aChar)));
		            result.append('-');
		        }
		        stringBinary = result.toString();
		        stringAscii = null;
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"String\" -> \"" + output + "\"");
			}
			
			
		} else if (stringBinary != null) {
			System.out.println("__UniDataType_converter: input type found: String[binary] \"" + output + "\"");
			switch (output) {
			case "String[ascii]":
				StringBuilder sb = new StringBuilder();
				Arrays.stream(stringBinary.split("-"))
					.forEach(s -> sb.append((char) Integer.parseInt(s, 2)) 
				);
				stringAscii = sb.toString(); 
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"String[binary]\" -> \"" + output + "\"");
			}
			
			
		} else {
			System.out.println("__UniDataType_converter: no input detected while converting to \"" + output + "\"");
		}
	}
	
	
	/** Sets {@link #stringAscii} to v.
	 * @param v New value for {@link #stringAscii}.*/
	public void setStringAscii(String v) {
		stringAscii = v;
	}
	/** Returns {@link #stringAscii} and calls {@link #converter(String)} beforehand if {@link #stringAscii} was null.
	 * @return {@link #stringAscii}*/
	public String getStringAscii() {
		if (stringAscii == null) {
			converter("String[ascii]");
		}
		return stringAscii;
	}
	
	/** Sets {@link #stringBinary} to v.
	 * @param v New value for {@link #stringBinary}.*/
	public void setStringBinary(String v) {
		stringBinary = v;
	}
	/** Returns {@link #stringBinary} and calls {@link #converter(String)} beforehand if {@link #stringBinary} was null.
	 * @return {@link #stringBinary}*/
	public String getStringBinary() {
		if (stringBinary == null) {
			converter("String[binary]");
		}
		return stringBinary;
	}
}
