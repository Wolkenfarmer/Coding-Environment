package infSources;

import environment.ExperimentElement;
import javafx.scene.layout.Pane;

/**
 * The information source if none is selected. 
 * @author Wolkenfarmer
 */
public class Deselect implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "nothing selected";
	/** The protocol / data type / structure that this experiment element gives.*/
	private static String protocol = "-";
	/** Defines the type of this information source. This variable has for information sources currently no use-case.*/
	private static byte type = 0;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already built and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	private static boolean builtGui;

	
	
	/** @see environment.ExperimentElement#buildGui(Pane)*/
	public void buildGui(Pane parent) {}


	/** @see environment.ExperimentElement#reloadGui(Pane)*/
	public void reloadGui(Pane parent) {}
	
	
	/** @see environment.ExperimentElement#save()*/
	public void save() {}


	/** @return {@link #builtGui}
	 * @see environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #protocol}
	 * @see environment.ExperimentElement#getProtocol()*/
	public String getProtocol() {return protocol;}
	/** @return {@link #type}
	 * @see environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
