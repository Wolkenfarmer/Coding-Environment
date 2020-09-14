package environment.pages.guiElements;

import environment.ExperimentElement;
import environment.Main;
import environment.pages.EnDecoderPage;
import environment.pages.Homepage;
import environment.pages.InfSourcePage;
import environment.pages.SettingsPage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

/**
 * The information segment for the {@link SettingsPage settings pages}.
 * The segment gets build in {@link #InformationSegment(byte, double, double, double) the constructor}.
 * In addition, the segment gets updated to display always the currently selected option {@link OptionButton} 
 * in {@link SettingsPage#vbOptButtons} using {@link #getContent()}.
 * @author Wolkenfarmer
 */
public class InformationSegment extends Pane {
	/** Saves the type of the segment in order for the {@link #bHeaSav button's} action handler to know what communication element 
	 * should be changed / updated. The individual types belong to the {@link SettingsPage settings page's}.<br>
	 * 0: {@link InfSourcePage}<br>
	 * 1: {@link EnDecoderPage}<br>
	 * 2: TODO*/
	private byte refType;
		/** Label which displays the sub-heading "Information" by default, but gets updated to fit the currently picked 
		 * {@link ExperimentElement experiment element}. 
		 * It gets directly attached to the pane.*/
		private Label lHeading;
		/** The "save and add" button. If pressed, the currently selected {@link OptionButton} gets added to the communication experiment.
		 * For this, either {@link Main#selectedInfSource} or {@link Main#selectedEnDecoder} or {@link Main#selectedPrePost} or
		 * {@link Main#selectedNoiSource} gets updated, as well as the {@link OverviewButton} in the corresponding {@link SettingsPage},
		 * the {@link OptionButton}'s and {@link Homepage#pSetModel}.
		 * Additionally, the {@link ExperimentElement experiment element} gets saved beforehand.
		 * Contains {@link #hbHeaSavContent} and gets directly attached to the pane.*/
		private Button bHeaSav;
			/** Layout container for the content of the button. Contains {@link #lHeaSavContent}.*/
			private HBox hbHeaSavContent;
				/** Label displaying the button's heading. It's part of {@link #hbHeaSavContent}.*/
				private Label lHeaSavContent;
	/** Layout container for the information content elements. Contains {@link #lInfConDefault} by default,
	 * but gets updated by the currently picked en- / decoder, and gets directly attached to the pane.*/
	private Pane pInfContent;
	/** Label displaying "No option picked" if there is yet no option picked. It's part of {@link #hbHeaSavContent}.*/
		private Label lInfConDefault;

	/**
	 * Builds the information source page of the application.
	 * For building it's content and updating the environment accordingly to the picked options {@link OverviewButton}, {@link OptionButton} and
	 * {@link InformationSegment} get used.
	 * The information source page gets scaled accordingly to {@link Main#stageHeight} and {@link Main#stageWidth}.
	 * Normally, the height of {@link #pInformation} gets calculated in order to not exceed the screen's size, 
	 * but if the screen is too small to even fit {@link #pOptions} on it, 
	 * the options height will be the minimum height of the information segment and {@link Main#scrollbar scroll bar} will be displayed.
	 * @param parent Layout container to attach it's layout parts to.
	 */

	/**
	 * Builds the information segment for the {@link SettingsPage settings pages}.
	 * For building it's content layoutX and layoutY needs to be set, because the segment gets directly added to 
	 * the {@link SettingsPage#root settings pages' root}.
	 * Normally, the segment gets scaled in order to fit the screen's size perfectly, but if the screen is too small to even fit the
	 * {@link SettingsPage#pOptions option's segment} on it, this will be minimum height of the information segment.
	 * @param refType Overwrites {@link #refType}.
	 * @param layoutX The layoutX for the segment.
	 * @param layoutY The layoutY for the segment.
	 * @param minHeight The minimum height of the segment.
	 */
	public InformationSegment(byte refType, double layoutX, double layoutY, double minHeight) {
		this.refType = refType;
		this.setLayoutX(layoutX);
		this.setLayoutY(layoutY);
		this.setPrefWidth(Main.stageWidth / 2);
			bHeaSav = new Button();
			bHeaSav.setBorder(Main.boNormal);
			bHeaSav.setBackground(Main.baGreenButton);
			bHeaSav.setOnMouseEntered(Main.evButGreEntered);
			bHeaSav.setOnMouseExited(Main.evButGreExited);
				hbHeaSavContent = new HBox();
					lHeaSavContent = new Label();
					lHeaSavContent.setText("save & add");
					lHeaSavContent.setTextFill(Main.cNormal);
					lHeaSavContent.setFont(Main.fNormalText);
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
			lHeading.setTextFill(Main.cNormal);
			lHeading.setFont(Main.fSubheading);
			lHeading.setPrefWidth(bHeaSav.getLayoutX());
			
			bHeaSav.setPrefHeight(Main.calcHeightLabel(lHeading, Double.MAX_VALUE));
			
			pInfContent = new Pane();
			pInfContent.setLayoutY(Main.distanceToSubheading);
			pInfContent.setPrefHeight(Main.stageHeight - this.getLayoutY() - pInfContent.getLayoutY() - Main.pos1 / 3);
			pInfContent.setPrefWidth(this.getPrefWidth());
			pInfContent.setMinHeight(minHeight - pInfContent.getLayoutY());
				lInfConDefault = new Label();
				lInfConDefault.setText("No option picked");
				lInfConDefault.setTextFill(Main.cPink);
				lInfConDefault.setFont(Main.fNormallTextItalic);
			pInfContent.getChildren().add(lInfConDefault);
		this.getChildren().addAll(bHeaSav, lHeading, pInfContent);
	}
	
	
	/**
	 * Re-sets the heading of the information segment to fit the currently selected {@link ExperimentElement}.
	 * Gets called from {@link OptionButton#setOnActionW(ExperimentElement, SettingsPage, InformationSegment)}.
	 * @param newName The new name of the segment.
	 */
	public void setHeading(String newName) {
		lHeading.setText(newName);
	}
	
	
	/**
	 * Updates the on action event handler of {@link #bHeaSav} to fit the currently selected {@link ExperimentElement experiment element}.
	 * This method gets called by {@link OptionButton#setOnActionW(ExperimentElement, SettingsPage, InformationSegment)}.
	 * Depending on the {@link #refType} the corresponding selection for the communication experiment in {@link Main} gets changed.
	 * In addition, the mode of the {@link OptionButton}'s gets changed as well as the {@link Homepage#pSetModel home page's settings model} on
	 * it's next build by setting {@link Main#boUpdateSettingsModelHomepage} to true and the corresponding page's
	 * {@link SettingsPage#pOverview overview model}. 
	 * Moreover {@link ExperimentElement#save()} gets called beforehand.
	 * @param reference The currently selected {@link ExperimentElement experiment element} to be added to the communication experiment.
	 * @param optButton The {@link OptionButton option button} which called this method and represents the experiment element.
	 * @param page The {@link SettingsPage settings page} where the corresponding other option buttons are and 
	 * the overview model has to be {@link SettingsPage#updateOveModel(byte) updated}.
	 */
	public void setSaveAddReference(ExperimentElement reference, OptionButton optButton, SettingsPage page) {
		bHeaSav.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.out.println("\"Save & add\" got pressed!");
				reference.save();
				
				
				switch (refType) {
				case 0: // information source
					Main.selectedInfSource = reference.getIndex();
					page.updateOveModel((byte) 0);
					
					for (int i = 0; i < page.vbOptButtons.getChildren().size(); i++) {
						if (((OptionButton) page.vbOptButtons.getChildren().get(i)).getMode() == 1) {
							((OptionButton) page.vbOptButtons.getChildren().get(i)).setMode((byte) 0);
						}
					}
					optButton.setMode((byte) 1);
					break;
				case 1: // EnDecoder	
					if (reference.getType() == 0) {
						Main.selectedEnDecoder = reference.getIndex();
						
						if (optButton.getMode() == 2) {
							Main.selectedPrePost = 0;
							page.updateOveModel((byte) 3);
						} else {
							page.updateOveModel((byte) 0);
						}
						
						for (int i = 0; i < page.vbOptButtons.getChildren().size(); i++) {
							if (((OptionButton) page.vbOptButtons.getChildren().get(i)).getMode() == 1) {
								((OptionButton) page.vbOptButtons.getChildren().get(i)).setMode((byte) 0);
							}
						}
						optButton.setMode((byte) 1);
						
						Main.noiSourcePage.updateOveModEnDeProtocol();
					} else {
						Main.selectedPrePost = reference.getIndex();
						
						if (optButton.getMode() == 1) {
							Main.selectedEnDecoder = 0;
							page.updateOveModel((byte) 0);
							
							Main.noiSourcePage.updateOveModEnDeProtocol();
						}
						
						if (!EnDecoderPage.ovePrePostDisplaying) {
							page.updateOveModel((byte) 2);
						} else {
							page.updateOveModel((byte) 1);
						}
						
						for (int i = 0; i < page.vbOptButtons.getChildren().size(); i++) {
							if (((OptionButton) page.vbOptButtons.getChildren().get(i)).getMode() == 2) {
								((OptionButton) page.vbOptButtons.getChildren().get(i)).setMode((byte) 0);
							}
						}
						optButton.setMode((byte) 2);
					}
					break;
				case 2: // noise source
					Main.selectedNoiSource = reference.getIndex();
					page.updateOveModel((byte) 0);
					
					for (int i = 0; i < page.vbOptButtons.getChildren().size(); i++) {
						if (((OptionButton) page.vbOptButtons.getChildren().get(i)).getMode() == 1) {
							((OptionButton) page.vbOptButtons.getChildren().get(i)).setMode((byte) 0);
						}
					}
					optButton.setMode((byte) 1);
					break;
				default:
					System.out.println("Type not found");
				}
								
				Main.boUpdateSettingsModelHomepage = true;
	        }
	    });
	}
	
	
	/**
	 * Gives the content if the segment to be updated when another {@link OptionButton} gets selected.
	 * @return Return {@link #pInfContent}.
	 */
	public Pane getContent() {return pInfContent;}
}
