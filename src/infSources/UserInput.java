package infSources;

import environment.ExperimentElement;
import environment.Main;
import environment.UniDataType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;

/**
 * For now just a mock class for {@link environment.pages.InfSourcePage}. 
 * Will later be extended to a full version of the user input information source.
 * @author Wolkenfarmer
 */
public class UserInput implements ExperimentElement {
	/** Name of this experiment element.*/
	private static String name = "User input";
	/** Defines the type of this information source. This variable has for information sources currently no use-case.*/
	private static byte type = 0;
	/** Layout container representing the given root from {@link environment.pages.guiElements.InformationSegment} to attach the GUI-elements to 
	 * (gets added via {@link environment.pages.guiElements.OptionButton#setOnActionW(ExperimentElement, environment.pages.SettingsPage, 
	 * environment.pages.guiElements.InformationSegment)}).
	 * It's content ({@link #taUserText}) gets build in {@link #buildGui(Pane)}.
	 * When loading another page, it's content gets first removed and then the layout container will be given to the other class.
	 * When reloading the page {@link #reloadGui(Pane)} will be used to re-attach the content to the root.*/
	private static Pane root;
	/** Shows whether the UI has yet to be build ({@link #buildGui}) or is already build and has only to be re-attached ({@link #reloadGui(Pane)}).*/
	private static boolean builtGui;
	
	/** Contains the input which will be used for the communication experiment if {@link #doJob(byte, UniDataType)} gets called.
	 * Gets overwritten in {@link #save()}.*/
	private static String input;
	/** Saves whether {@link #bChecked}'s background already got set to unchecked / brown. 
	 * Should improve the performance by not setting the background of the button every time a character gets changed in {@link #taUserText}.*/
	private static boolean boUnchecked;
	/** Saves whether {@link #bSaved}'s background already got set to unsaved / red.
	 * Should improve the performance by not setting the background of the button every time a character gets changed in {@link #taUserText}.*/
	private static boolean boSavedRed;
	
	/** The text field where the user can write his/her input into. It will be directly attached to {@link #root}.*/
	private static TextArea taUserText;
	/** Layout container for the controls and indicators regarding {@link #taUserText}.
	 * Contains {@link #bCheckInput}, {@link #bChecked} and {@link #bSaved} and will be directly attached to {@link #root}.*/
	private static HBox hbControls;
		/** Button to check whether the input in {@link #taUserText} meets the given requirements in {@link #lDescription}.
		 * It contains {@link #hbCheckInput} and is part of {@link #hbControls}.*/
		private static Button bCheckInput;
			/** Layout container for the content of the button. Contains {@link #lCheckInput} and is part of {@link #bCheckInput}.*/
			private static HBox hbCheckInput;
				/** Label displaying the button's heading "Check input". It's part of {@link #hbCheckInput}.*/
				private static Label lCheckInput;
		/** A spacer for the right-hand-side-layout of {@link #bChecked} and {@link #bSaved}. It's part of {@link #hbControls}.*/
		private static Region rSpacer;
		/** Button which displays whether the current input in {@link #taUserText} meets the requirements or was not yet checked 
		 * by changing it's background. It's used as a better rectangle. It contains {@link #hbChecked} and is part of {@link #hbControls}.*/
		private static Button bChecked;
			/** Layout container for the content of the button. Contains {@link #lChecked} and is part of {@link #bChecked}.*/
			private static HBox hbChecked;
				/** Label displaying the button's heading "Checked". It's part of {@link #hbChecked}.*/
				private static Label lChecked;
				/** Button which displays whether the current input in {@link #taUserText} already got saved 
				 * and therefore is equal to {@link #input} or not by changing it's background. It's used as a better rectangle. 
				 * It contains {@link #hbSaved} and is part of {@link #hbControls}.*/
		private static Button bSaved;
			/** Layout container for the content of the button. Contains {@link #lSaved} and is part of {@link #bSaved}.*/
			private static HBox hbSaved;
				/** Label displaying the button's heading "Saved". It's part of {@link #hbSaved}.*/
				private static Label lSaved;
	/** Label displaying the description for this experiment element. It will be directly attached to {@link #root}.*/
	private static Label lDescription;
	
	
	/** @see environment.ExperimentElement#doJob(byte, UniDataType)
	 * @return Returns {@link #input}.*/
	public UniDataType doJob(byte task, UniDataType data) {
		data.setStringAscii(input);
		return data;
	}
	
	
	/** @see environment.ExperimentElement#buildGui(Pane)*/
	public void buildGui(Pane parent) {
		root = parent;
		
		taUserText = new TextArea();
        taUserText.setFont(environment.Main.fNormalText);
        taUserText.setPromptText("User input");
        taUserText.setStyle("-fx-text-inner-color: WHITESMOKE;");
        taUserText.setPrefHeight(200);
        taUserText.setPrefWidth(root.getPrefWidth());
        taUserText.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	if (boSavedRed == false) {
		    		bSaved.setBackground(Main.baRedButton);
		    		boSavedRed = true;
		    	}
		    	if (boUnchecked == false) {
		    		bChecked.setBackground(Main.baBrownButton);
		    		boUnchecked = true;
		    	}
		    }
		});
        
        
        hbControls = new HBox();
        hbControls.setLayoutY(taUserText.getPrefHeight() + 20);
        hbControls.setSpacing(20);
        hbControls.setPrefWidth(root.getPrefWidth());
	        double w, w1, w2;
	        w1 = root.getPrefWidth() / 4;
	        
	        bCheckInput = new Button();
	        bCheckInput.setBackground(Main.baNormalButton);
	        bCheckInput.setBorder(Main.boNormal);
				hbCheckInput = new HBox();
					lCheckInput = new Label();
					lCheckInput.setText("Check input");
					lCheckInput.setTextFill(Main.cNormal);
					lCheckInput.setFont(Main.fNormalText);
					lCheckInput.setWrapText(false);
					lCheckInput.setTextAlignment(TextAlignment.CENTER);
					lCheckInput.setAlignment(Pos.CENTER);
				hbCheckInput.getChildren().addAll(lCheckInput);
				hbCheckInput.setAlignment(Pos.CENTER_LEFT);
			w2 = Main.calcWidth(hbCheckInput);
			if (w1 <= w2) {w = w1;} else {w = w2;} 
			bCheckInput.setPrefWidth(w + 20);
			bCheckInput.setPrefHeight(40);
			bCheckInput.setGraphic(hbCheckInput);
			bCheckInput.setOnMouseEntered(Main.evButEntered);
			bCheckInput.setOnMouseExited(Main.evButExited);
			bCheckInput.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent t) {
					if (taUserText.getText().matches("\\A\\p{ASCII}*\\z")) {
						bChecked.setBackground(Main.baGreenButton);
					} else {
						bChecked.setBackground(Main.baRedButton);
					}
					boUnchecked = false;
		        }
		    });
			
			rSpacer = new Region();
			HBox.setHgrow(rSpacer, Priority.ALWAYS);
			
			bChecked = new Button();
			bChecked.setBackground(Main.baBrownButton);
			bChecked.setBorder(Main.boNotInteractive);
				hbChecked = new HBox();
					lChecked = new Label();
					lChecked.setText("Checked");
					lChecked.setTextFill(Main.cLightGray);
					lChecked.setFont(Main.fNormalTextItalic);
					lChecked.setWrapText(false);
					lChecked.setTextAlignment(TextAlignment.CENTER);
					lChecked.setAlignment(Pos.CENTER);
				hbChecked.getChildren().addAll(lChecked);
				hbChecked.setAlignment(Pos.CENTER_LEFT);
			w2 = Main.calcWidth(hbChecked);
			if (w1 <= w2) {w = w1;} else {w = w2;} 
			bChecked.setPrefWidth(w + 20);
			bChecked.setPrefHeight(40);
			bChecked.setGraphic(hbChecked);
			
			bSaved = new Button();
			bSaved.setBackground(Main.baRedButton);
			bSaved.setBorder(Main.boNotInteractive);
				hbSaved = new HBox();
					lSaved = new Label();
					lSaved.setText("Saved");
					lSaved.setTextFill(Main.cLightGray);
					lSaved.setFont(Main.fNormalTextItalic);
					lSaved.setWrapText(false);
					lSaved.setTextAlignment(TextAlignment.CENTER);
					lSaved.setAlignment(Pos.CENTER);
				hbSaved.getChildren().addAll(lSaved);
				hbSaved.setAlignment(Pos.CENTER_LEFT);
			w2 = Main.calcWidth(hbSaved);
			if (w1 <= w2) {w = w1;} else {w = w2;} 
			bSaved.setPrefWidth(w + 20);
			bSaved.setPrefHeight(40);
			bSaved.setGraphic(hbSaved);
		hbControls.getChildren().addAll(bCheckInput, rSpacer, bChecked, bSaved);
        
		
        lDescription = new Label();
        lDescription.setText("Enter your text above.\n"
        		+ "Note that only up to  (2'147'483'647 (2^30 - 1)) ASCII characters are supported.\n\n"
        		+ "To check whether the given text meets the requierements you can press the \"Check input\" button above. "
        		+ "\"Checked\" is green, if the given text got recently checked and it was okay, it's brown if the text was not checked yet"
        		+ "and it's red if it got checked and non-ASCII characters were found.\n"
        		+ "\"Saved\" is green if the text was recently saved and red if the current text was not saved yet.");
        lDescription.setFont(environment.Main.fNormalText);
        lDescription.setTextFill(environment.Main.cNormal);
        lDescription.setLayoutY(hbControls.getLayoutY() + Main.calcHeight(hbControls) + 30);
        lDescription.setPrefWidth(root.getPrefWidth());
        lDescription.setPrefHeight(Main.calcHeightLabel(lDescription, lDescription.getPrefWidth()));
        lDescription.setWrapText(true);
        lDescription.setAlignment(Pos.TOP_LEFT);
        
        builtGui = true;
        root.getChildren().addAll(taUserText, hbControls, lDescription);
	}
	
	
	/** @see environment.ExperimentElement#reloadGui(Pane)*/
	public void reloadGui(Pane parent) {
		root = parent;
		root.getChildren().addAll(taUserText, hbControls, lDescription);
	}
	
	
	/** @see environment.ExperimentElement#save()*/
	public void save() {
		if (taUserText.getText().matches("\\A\\p{ASCII}*\\z")) {
			bChecked.setBackground(Main.baGreenButton);
		} else {
			bChecked.setBackground(Main.baRedButton);
		}
		input = taUserText.getText();
		bSaved.setBackground(Main.baGreenButton);
		boSavedRed = false;
		boUnchecked = false;
		System.out.println(name + " saved!");
	}


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
