package enDecoder;

import environment.ExperimentElement;
import environment.UniDataType;
import javafx.scene.layout.Pane;

/**
 * The en- / decoder if none is selected. 
 * @author Wolkenfarmer
 */
public class Deselect implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "nothing selected";
	/** Defines the type of this en- / decoder.*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link environment.pages.guiElements.InformationSegment}
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * Its content (-) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already built and has only to be attached ({@link #getGui()}).*/
	private static boolean builtGui;

	
	/** Sets the necessary message-versions in {@link environment.Run} for a flawless data analysis.
	 * @see environment.ExperimentElement#doJob(byte, UniDataType)*/
	public UniDataType doJob(byte task, UniDataType data) {
		if (task != 0) {
			environment.Run.changedMessage = data.getStringUnicode();
			environment.Run.correctedMessage = data.getStringUnicode();
			environment.Run.correctedFlaggedMessage = data.getStringUnicode();
		} 
		return data;
	}
	/** @see environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {}
	/** @see environment.ExperimentElement#save()*/
	public void save() {}
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
