package de.wolkenfarmer.transcoder;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.UniDataType;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * Represents the option to disable the pre- / post-transcoder in {@link de.wolkenfarmer.environment.pages.Transcoder} by setting it to this. 
 * @author Wolkenfarmer
 */
public class DeselectPrePost implements ExperimentElement {
	/** Name of this experiment element.*/
	private String name = "Deselect pre- / post-transcoder";
	/** Defines whether this is an transcoder or a pre- / post-transcoder. This experiment element is only an transcoder.
	 * 0: transcoder
	 * 1: pre- / post-*/
	private static byte type = 1;
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement)}).
	 * Its content ({@link #l}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	/** Label which explains the function of this element. It will be directly attached to {@link #root}.*/
	private static Label l;

	
	/** @see de.wolkenfarmer.environment.ExperimentElement#doJob(byte, UniDataType)*/
	public UniDataType doJob(byte task, UniDataType data) {return data;}
	
	
	/** @see de.wolkenfarmer.environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		l = new Label();
		l.setText("\"Save & add\" this option in order to disable the pre- / post-transcoder for the communication experiment.");
		l.setFont(Constants.F_NORMAL);
		l.setTextFill(Constants.C_NORMAL);
		l.setPrefWidth(root.getPrefWidth());
		l.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(l);
	}
	

	/** @see de.wolkenfarmer.environment.ExperimentElement#save()*/
	public void save() {}
	/** @see de.wolkenfarmer.environment.ExperimentElement#getGui()*/
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getBuiltGui()*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getName()*/
	public String getName() {return name;}
	/** @return {@link #type}
	 * @see de.wolkenfarmer.environment.ExperimentElement#getType()*/
	public byte getType() {return type;}
}
