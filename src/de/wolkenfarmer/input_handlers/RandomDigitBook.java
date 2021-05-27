package de.wolkenfarmer.input_handlers;

import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.UniDataType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * For now just a mock class for {@link de.wolkenfarmer.environment.pages.InputHandler}. 
 * Will later be extended to a full version of the random digit book input handler.
 * @author Wolkenfarmer
 */
public class RandomDigitBook implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Random digit book";
	/** Defines the type of this input handler. This variable has for input handlers currently no use-case.*/
	private static byte type = 0;
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton
	 * #setOnActionW(ExperimentElement, de.wolkenfarmer.environment.pages.Settings, 
	 * de.wolkenfarmer.environment.pages.gui_elements.InformationSegment)}).
	 * Its content ({@link #mock}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	/** Mock label for testing the layout-loading. It will be directly attached to {@link #root}.*/
	private static Label mock;
	
	
	/** @see de.wolkenfarmer.environment.ExperimentElement#doJob(byte, UniDataType)*/
	public UniDataType doJob(byte task, UniDataType data) {return data;}
	
	
	/** @see de.wolkenfarmer.environment.ExperimentElement#buildGui(double)*/
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		mock = new Label();
		mock.setText("Currently in development...");
		mock.setFont(de.wolkenfarmer.environment.Main.fNormalTextItalic);
		mock.setTextFill(de.wolkenfarmer.environment.Main.cNormal);
		mock.setPrefWidth(root.getPrefWidth());
		mock.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(mock);
		
	}
	
	
	/** @see de.wolkenfarmer.environment.ExperimentElement#save()*/
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
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
