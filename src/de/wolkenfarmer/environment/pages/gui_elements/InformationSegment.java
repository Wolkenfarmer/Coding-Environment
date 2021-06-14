package de.wolkenfarmer.environment.pages.gui_elements;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.Home;
import de.wolkenfarmer.environment.pages.Settings;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

/**
 * The information segment for the {@link Settings settings pages}.
 * The segment gets build in {@link #InformationSegment(double) the constructor}.
 * In addition, the segment gets updated to display always the currently selected option {@link OptionButton} 
 * in {@link Settings#vbOptButtons} using {@link #getContent()}.
 * @author Wolkenfarmer
 */
public class InformationSegment extends Pane {
	/** Saves the type of the segment in order for the {@link #bHeaSav button's} action handler to know what communication element 
	 * should be changed / updated. The individual types belong to the {@link Settings settings page}.<br>
	 * 0: {@link de.wolkenfarmer.environment.pages.InputHandler}<br>
	 * 1: {@link de.wolkenfarmer.environment.pages.Transcoder}<br>
	 * 2: {@link de.wolkenfarmer.environment.pages.NoiseSource} */
	private byte refType;
		/** Label which displays the subheading "Information" by default, but gets updated to fit the currently picked 
		 * {@link ExperimentElement experiment element}. 
		 * It gets directly attached to the pane.*/
		private Label lHeading;
		/** The "save and add" button. If pressed, the currently selected {@link OptionButton} gets added to the communication experiment.
		 * For this, either {@link Main#selectedInputHandler} or {@link Main#selectedTranscoder} or
		 * {@link Main#selectedNoiSource} gets updated, as well as the {@link OverviewButton} in the corresponding {@link Settings},
		 * the {@link OptionButton}'s and {@link Home#pSetModel}.
		 * Additionally, the {@link ExperimentElement experiment element} gets saved beforehand.
		 * Contains {@link #hbHeaSavContent} and gets directly attached to the pane.*/
		private Button bHeaSav;
			/** Layout container for the content of the button. Contains {@link #lHeaSavContent}.*/
			private HBox hbHeaSavContent;
				/** Label displaying the button's heading. It's part of {@link #hbHeaSavContent}.*/
				private Label lHeaSavContent;
	/** Layout container for the information content elements. Contains {@link #lInfConDefault} by default,
	 * but gets updated by the currently picked transcoder, and gets directly attached to the pane.*/
	private Pane pInfContent;
		/** Label displaying "No option picked" if there is yet no option picked. It's part of {@link #hbHeaSavContent}.*/
		private Label lInfConDefault;
		

	/**
	 * Builds the information segment for the {@link Settings settings pages}. <br>
	 * @param minHeight The minimum height of the segment.
	 * @since 0.2
	 */
	public InformationSegment(double minHeight) {
		this.setPrefWidth(Main.stageWidth / 2);
			bHeaSav = new Button();
			bHeaSav.setBorder(Constants.B_NORMAL);
			bHeaSav.setBackground(Constants.BG_GREEN);
			bHeaSav.setMaxHeight(50);
			bHeaSav.setOnMouseEntered(Constants.EH_BUTTON_GREEN_ENTERED);
			bHeaSav.setOnMouseExited(Constants.EH_BUTTON_GREEN_EXITED);
				hbHeaSavContent = new HBox();
					lHeaSavContent = new Label();
					lHeaSavContent.setText("save & add");
					lHeaSavContent.setTextFill(Constants.C_NORMAL);
					lHeaSavContent.setFont(Constants.F_NORMAL);
					lHeaSavContent.setWrapText(true);
					lHeaSavContent.setTextAlignment(TextAlignment.CENTER);
					lHeaSavContent.setAlignment(Pos.CENTER);
					bHeaSav.setMinWidth(Main.calcWidthLabel(lHeaSavContent));
				hbHeaSavContent.getChildren().addAll(lHeaSavContent);
				hbHeaSavContent.setAlignment(Pos.CENTER);
			bHeaSav.setGraphic(hbHeaSavContent);
			bHeaSav.setLayoutX(this.getPrefWidth() - Main.calcWidth(bHeaSav));
			
			lHeading = new Label();
			lHeading.setText("Information");
			lHeading.setTextFill(Constants.C_NORMAL);
			lHeading.setFont(Constants.F_SUBHEADING);
			lHeading.setPrefWidth(bHeaSav.getLayoutX());
			
			
			pInfContent = new Pane();
			pInfContent.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
			pInfContent.setPrefWidth(this.getPrefWidth());
			pInfContent.setMinHeight(minHeight - pInfContent.getLayoutY());
				lInfConDefault = new Label();
				lInfConDefault.setText("No option picked");
				lInfConDefault.setTextFill(Constants.C_PINK);
				lInfConDefault.setFont(Constants.F_NORMAL_ITALIC);
			pInfContent.getChildren().add(lInfConDefault);
		this.getChildren().addAll(bHeaSav, lHeading, pInfContent);
	}
	
	
	/**
	 * Resets the information segment to default. <br>
	 * Sets the {@link #pInfContent content container} to only have {@link #lInfConDefault} as its content and resets {@link #lHeading}.
	 * @since 0.2
	 */
	public void setContentDefault() {
		lHeading.setText("Information");
		pInfContent.getChildren().clear();
		pInfContent.getChildren().add(lInfConDefault);
	}
	
	
	/**
	 * Resets the heading of the information segment to fit the currently selected {@link ExperimentElement}. <br>
	 * Gets called from {@link OptionButton#setOnActionW(ExperimentElement)}.
	 * @param newName The new name of the segment.
	 */
	public void setHeading(String newName) {
		lHeading.setText(newName);
	}
	
	
	/**
	 * Sets the {@link #refType reference type} for the information segment. <br>
	 * @param newRefType The new reference type.
	 * @since 0.2
	 */
	public void setRefType(byte newRefType) {
		refType = newRefType;
	}
	
	
	/**
	 * Updates the on action event handler of {@link #bHeaSav} to fit the currently selected {@link ExperimentElement experiment element}. <br>
	 * This method gets called by {@link OptionButton#setOnActionW(ExperimentElement)}.
	 * Depending on the {@link #refType} the corresponding selection for the communication experiment in {@link Main} gets changed.
	 * In addition, the mode of the {@link OptionButton}s gets changed as well as the {@link Home#pSetModel home pages settings model} on
	 * its next build by setting {@link Main#boUpdateSettingsModelHomepage} to true and the corresponding pages
	 * {@link Settings#pOverview overview model}. 
	 * Moreover {@link ExperimentElement#save()} gets called beforehand.
	 * @param reference The currently selected {@link ExperimentElement experiment element} to be added to the communication experiment.
	 * @param optButton The {@link OptionButton option button} which called this method and represents the experiment element.
	 * @since 0.2
	 */
	public void setSaveAddReference(ExperimentElement reference, OptionButton optButton) {
		bHeaSav.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("\"Save & add\" got pressed!");
				reference.save();
				
				
				switch (refType) {
				case 0: // input handler
					Main.selectedInputHandler = reference;
					Settings.updateOveModel((byte) 0);
					break;
				case 1: // Transcoder	
					Main.selectedTranscoder = reference;
					Settings.updateOveModel((byte) 1);
					break;
				case 2: // noise source
					Main.selectedNoiSource = reference;
					Settings.updateOveModel((byte) 2);
					break;
				default:
					System.out.println("Type not found");
				}
				
				for (int i = 0; i < Settings.vbOptButtons.getChildren().size(); i++) {
					if (((OptionButton) Settings.vbOptButtons.getChildren().get(i)).getMode() == 1) {
						((OptionButton) Settings.vbOptButtons.getChildren().get(i)).setMode((byte) 0);
					}
				}
				optButton.setMode((byte) 1);
								
				Main.boUpdateSettingsModelHomepage = true;
	        }
	    });
	}
	
	
	/**
	 * Removes all elements from the {@link #pInfContent content layout container} and returns the empty container. <br>
	 * Usually gets called by {@link OptionButton#setOnActionW(ExperimentElement)}.
	 * @return Return {@link #pInfContent}.
	 */
	public Pane getContent() {
		pInfContent.getChildren().clear();
		return pInfContent;
	}
}
