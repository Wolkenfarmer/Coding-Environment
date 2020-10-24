package enDecoder;

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
 * there must have been an odd number of changed bits in this unit. In this case, 
 * it just assumes that here must have been one change and counts it as detected error.
 * However, if cross parity check is enabled, it TODO
 * @author Wolkenfarmer
 */
public class ParityCheck implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "Parity Check";
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
	
	
	/** @see environment.ExperimentElement#doJob(byte, UniDataType)*/
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
				// TODO
			}
			
			data.setStringBinaryArray(stringBA);
		} else {
			int changes = 0;
			int numChar = 0;
			
			for (int i = 0; i < stringBA.length; i++) {
				int ones = 0;
				for (int k = 0; k < stringBA[i].length(); k++) {
					if (stringBA[i].charAt(k) == '1') ones++;
				}
				if (ones % 2 == 1) {
					changes++;
				}
				numChar++;
				
				stringBA[i] = stringBA[i].substring(0, 8);
			}
			System.out.println("num characters: " + numChar);
			System.out.println("found changes: " + changes);
			
			if (boCrossPC) {
				// TODO
			}
			
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
