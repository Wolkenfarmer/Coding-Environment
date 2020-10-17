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
	/** The String(cp125X) option as data type. Example: "Hello!"*/
	private String stringCp125X;
	/** The String(binary) option as data type. Example: "1001000-1100101-1101100-1101100-1101111-"*/
	private String stringBinary;
	/** The char[](binary) option as data type. Example: '1', '0', '0', '1', '0', '0', '0', '-'*/
	private char[] charBinary;
	
	
	/**
	 * Searches for an already set variable and converts it's content into the requested output format. 
	 * The previously set variable gets set null again in order for the {@link ExperimentElement experiment elements} 
	 * to have to use the most recently modified data.<br><br>
	 * 
	 * __ String(cp125X) to String(binary): Translates the String char after char into it's binary representation. 
	 * The char-representations get divided by a '-'. This conversion got taken from the 1. @ see with some slight modifications.<br>
	 * __ String(binary) to String(cp125X): Splits the given binary String at every [-] and translates each part 
	 * into the corresponding character. However, only characters translated into 8 bits or less can get converted back correctly, 
	 * which is why usually only cp125X will work depending on your operating system. 
	 * This conversion got taken from the 2. @ see with some slight modification.<br><br>
	 * 
	 * __ String(binary) to char[](binary): Uses String.toCharArray().<br>
	 * __ char[](binary) to String(binary): Uses new String(char[]).<br><br>
	 * 
	 * __ String(cp125X) to char[](binary): Connects "String(cp125X) to String(binary)" with "String(binary) to char[](binary)".<br>
	 * __ char[](binary) to String(binary): Connects "char[](binary) to String(binary)" with "String(binary) to String(cp125X)".
	 * 
	 * @param output The requested output.
	 * @see <a href="https://mkyong.com/java/java-convert-string-to-binary/">String(cp125X) to String(binary) - mkyong</a>
	 * @see <a href="https://stackoverflow.com/questions/4211705/binary-to-text-in-java">String(binary) to String(cp125X) - Leon Kasko</a>
	 */
	private void converter(String output) {		
		if (stringCp125X != null) {
			System.out.println("__UniDataType_converter: input type found: String(cp125X) -> \"" + output + "\"");
			switch (output) {
			case "String(binary)":
				StringBuilder resultStrStr = new StringBuilder();
		        char[] charsStrStr = stringCp125X.toCharArray();
		        for (char aChar : charsStrStr) {
		        	resultStrStr.append(String.format(Integer.toBinaryString(aChar)));
		        	resultStrStr.append('-');
		        }
		        stringBinary = resultStrStr.toString();
		        stringCp125X = null;
				break;
				
			case "char[](binary)":
				StringBuilder resultStrCha = new StringBuilder();
		        char[] charsStrCha = stringCp125X.toCharArray();
		        for (char aChar : charsStrCha) {
		        	resultStrCha.append(String.format(Integer.toBinaryString(aChar)));
		        	resultStrCha.append('-');
		        }
		        charBinary = resultStrCha.toString().toCharArray();
		        stringCp125X = null;
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"String(cp125X)\" -> \"" + output + "\"");
			}
			
			
		} else if (stringBinary != null) {
			System.out.println("__UniDataType_converter: input type found: String(binary) \"" + output + "\"");
			switch (output) {
			case "String(cp125X)":
				StringBuilder sb = new StringBuilder();
				Arrays.stream(stringBinary.split("-"))
					.forEach(s -> sb.append((char) Integer.parseInt(s, 2)) 
				);
				stringCp125X = sb.toString(); 
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
			
			case "String(cp125X)":
				String strBin = new String(charBinary);
				StringBuilder sb = new StringBuilder();
				Arrays.stream(strBin.split("-"))
					.forEach(s -> sb.append((char) Integer.parseInt(s, 2)) 
				);
				stringCp125X = sb.toString(); 
				charBinary = null;
				break;
				
			default:
				System.out.println("__UniDataType_converter: no fitting converter found for \"char[](binary)\" -> \"" + output + "\"");
			}
			
			
		} else {
			System.out.println("__UniDataType_converter: no input detected while converting to \"" + output + "\"");
		}
	}
	
	
	/** Sets {@link #stringCp125X} to v.
	 * @param v New value for {@link #stringCp125X}.*/
	public void setStringAscii(String v) {
		stringCp125X = v;
	}
	/** Returns {@link #stringCp125X} and calls {@link #converter(String)} beforehand if {@link #stringCp125X} was null.
	 * @return Returns {@link #stringCp125X}.*/
	public String getStringCp125X() {
		if (stringCp125X == null) {
			converter("String(cp125X)");
		}
		return stringCp125X;
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
