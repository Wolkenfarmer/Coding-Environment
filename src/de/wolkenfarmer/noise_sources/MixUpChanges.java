package de.wolkenfarmer.noise_sources;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.UniDataType;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * For now just a mock class for {@link de.wolkenfarmer.environment.pages.NoiseSource}. 
 * Will later be extended to a full version of the mix-up changes noise source.
 * @author Wolkenfarmer
 */
public class MixUpChanges implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Mix-up changes";
	/** Layout container which will be attached to {@link de.wolkenfarmer.environment.pages.gui_elements.InformationSegment}
	 * (gets added via {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton#setOnActionW(ExperimentElement)}).
	 * Its content ({@link #mock}) gets build in {@link #buildGui(double)}.
	 * When loading another page, it will be removed from the InformationSegment.
	 * When loading the page {@link #getGui()} will be used to get the built GUI of the experiment element.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be attached ({@link #getGui()}).*/
	public static boolean builtGui;
	/** Mock label for testing the layout-loading. It will be directly attached to {@link #root}.*/
	private static Label mock;
	
	
	public UniDataType doJob(byte task, UniDataType data) {return data;}

	
	public void buildGui(double parentWidth) {
		root = new Pane();
		root.setPrefWidth(parentWidth);
		
		mock = new Label();
		mock.setText("Currently in development...");
		mock.setFont(Constants.F_NORMAL_ITALIC);
		mock.setTextFill(Constants.C_NORMAL);
		mock.setPrefWidth(root.getPrefWidth());
		mock.setWrapText(true);
        
        builtGui = true;
        root.getChildren().addAll(mock);
	}
	
	
	public void save() {
		System.out.println(name + " saved!");
	}
	
	
	public Pane getGui() {return root;}
	/** @return {@link #builtGui}*/
	public boolean getBuiltGui() {return builtGui;}
	/** @return {@link #name}*/
	public String getName(boolean optionButton) {return name;}
}
