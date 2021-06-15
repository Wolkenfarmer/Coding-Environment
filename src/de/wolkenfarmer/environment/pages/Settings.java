package de.wolkenfarmer.environment.pages;

import de.wolkenfarmer.Constants;
import de.wolkenfarmer.environment.ExperimentElement;
import de.wolkenfarmer.environment.Main;
import de.wolkenfarmer.environment.pages.gui_elements.InformationSegment;
import de.wolkenfarmer.environment.pages.gui_elements.OptionButton;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * Sub-page of the @ {@link de.wolkenfarmer.environment.pages.Home home page}, where one can choose the 
 * {@link de.wolkenfarmer.environment.ExperimentElement experiment elements}. <br>
 * It is the base for the {@link de.wolkenfarmer.environment.pages.InputHandler input handler page},
 * the {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page} and 
 * the {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}. 
 * This base layout stays loaded all the time and only the settings pages specific elements (such as the second half of the headline) 
 * get exchanged depending on the requested page.
 * It also gets used for {@link OptionButton} and {@link InformationSegment} in order to have a unified access to the pages / 
 * implement a unified system for handling the pages.
 * @author Wolkenfarmer
 * @since 0.2
 */
public class Settings {
	/** Saves, which page is currently loaded. <br>
	 * 0 =^ Input handler page<br>
	 * 1 =^ Transcoder page<br>
	 * 2 =^ Noise source page<br>
	 * Default is 7, which means no page is loaded.*/
	private static byte loadedPage = 7;
	/** Layout container containing all elements of the settings pages, which gets added to the {@link Main#root main root} 
	 * when {@link #loadedPage} gets called. <br>
	 * Its content ({@link #tfHeading}, {@link #pOverview}, {@link #pOptions}, {@link #pInformation}) 
	 * mainly is built in the {@link Settings#Settings() constructor}, 
	 * but the settings-pages specific content (such es the contents of the {@link #pOveModel overview model}) 
	 * get added by the pages classes itself.*/
	private static Group root;
	/** Saves the width of an segment in {@link #pOveModel} which gets calculated in the corresponding settings pages classes..*/
	protected static double segmentWidth;
	/** Layout container for the heading segment. <br>
	 * Its content ({@link #lHeaHome} and {@link #lHeaHere}) gets added in the {@link Settings#Settings() constructor}. 
	 * It gets attached to {@link #root}.*/
	private static TextFlow tfHeading;
		/** Label which displays the first, general half of the heading "CE \  " (bold). <br>
		 * It's part of {@link #tfHeading}.*/
		private static Label lHeaHome;
		/** Label which displays the second, page-specific half of the heading. <br>
		 * This gets assigned in the dedicated settings pages classes. 
		 * It's part of {@link #tfHeading}.*/
		protected static Label lHeaHere;
	/** Layout container for the overview segment. <br>
	 * Its content ({@link #lOveHeading} and {@link #pOveModel}) gets added in the {@link Settings#Settings() constructor}. 
	 * It gets attached to {@link #root}.*/
	protected static Pane pOverview;
		/** Label which displays the subheading "Overview". <br>
		 * It's part of {@link #pOverview}.*/
		private static Label lOveHeading;
		/** Layout container for the overview model. <br>
		 * Its parts are page-specific and therefore they get added in the corresponding settings pages classes. 
		 * It's part of {@link #pOverview}.*/
		protected static Pane pOveModel;
	/** Layout container for the options segment. <br>
	 * Its content ({@link #lOptHeading} and {@link #vbOptButtons}) gets added in the {@link Settings#Settings() constructor}. 
	 * It gets attached to {@link #root}.*/
	protected static Pane pOptions;
		/** Label which displays the subheading "Options". <br>
		 * It's part of {@link #pOptions}.*/
		private static Label lOptHeading;
		/** Layout container for the option buttons. <br>
		 * Its parts are page-specific and therefore they get added in the corresponding settings pages classes. 
		 * It's part of {@link #pOptions}.*/
		public static VBox vbOptButtons;
	/** Layout container for the information segment. <br>
	 * Displays the information of the selected {@link ExperimentElement experiment element} in {@link #pOptions}.
	 * It gets attached to {@link #root}.
	 * @see InformationSegment*/
	public static InformationSegment pInformation;
	
	
	/** Reference to the {@link InputHandler input handler page} in order for {@link #loadedPage} to know 
	 * whether the pages elements got already instantiated or not. <br>*/
	private static InputHandler inputHandlerPage;
	/** Reference to the {@link Transcoder transcoder page} in order for {@link #loadedPage} to know 
	 * whether the pages elements got already instantiated or not. <br>*/
	private static Transcoder transcoderPage;
	/** Reference to the {@link NoiseSource noise source page} in order for {@link #loadedPage} to know 
	 * whether the pages elements got already instantiated or not. <br>*/
	private static NoiseSource noiseSourcePage;
	
	
	// TODO: Convert also Transcoder
	// TODO: Make sure, that updating the overviews etc works fine
	
	
	/**
	 * The constructor which builds the base settings page. <br>
	 * It builds all layout elements which are needed across every settings page and adds them to the {@link #root}. 
	 * Elements whose content is yet undefined such as {@link #pOveModel} are kept empty. 
	 * This method gets called in {@link Main#start(javafx.stage.Stage)}.
	 * @since 0.2
	 */
	public Settings() {
		root = new Group();
		
		tfHeading = new TextFlow();
		tfHeading.setLayoutX(Main.pos1);
		tfHeading.setLayoutY(Main.pos1 / 3);
		tfHeading.setPrefWidth(Main.contentWidth);
			lHeaHome = new Label();
			lHeaHome.setText("CE \\  ");
			lHeaHome.setTextFill(Constants.C_NORMAL);
			lHeaHome.setFont(Constants.F_HEADING_BOLD);
			lHeaHome.setAlignment(Pos.CENTER_LEFT);
				
			lHeaHere = new Label();
			lHeaHere.setTextFill(Constants.C_NORMAL);
			lHeaHere.setFont(Constants.F_HEADING);
			lHeaHere.setAlignment(Pos.CENTER_LEFT);
		tfHeading.getChildren().addAll(lHeaHome, lHeaHere);
		
		
		pOverview = new Pane();
		pOverview.setLayoutX(Main.pos1);
		pOverview.setLayoutY(tfHeading.getLayoutY() + Constants.I_DISTANCE_HEADING);
		pOverview.setPrefWidth(Main.contentWidth);
			lOveHeading = new Label();
			lOveHeading.setText("Overview");
			lOveHeading.setTextFill(Constants.C_NORMAL);
			lOveHeading.setFont(Constants.F_SUBHEADING);
			
			pOveModel = new Pane();
			pOveModel.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
		pOverview.getChildren().addAll(lOveHeading, pOveModel);
		
		
		pOptions = new Pane();
		pOptions.setLayoutX(Main.pos1);
		pOptions.setPrefWidth(Main.stageWidth / 8 * 1.5);
			lOptHeading = new Label();
			lOptHeading.setText("Option");
			lOptHeading.setTextFill(Constants.C_NORMAL);
			lOptHeading.setFont(Constants.F_SUBHEADING);			
			
			vbOptButtons = new VBox();
			vbOptButtons.setPrefWidth(pOptions.getPrefWidth());
			vbOptButtons.setLayoutY(Constants.I_DISTANCE_SUBHEADING);
			vbOptButtons.setSpacing(20);
	    pOptions.getChildren().addAll(lOptHeading, vbOptButtons);
	    	    
	    
	    pInformation = new InformationSegment(Main.calcHeight(pOptions));
	    pInformation.setLayoutX(Main.pos1 * 3);

		root.getChildren().addAll(tfHeading, pOverview, pOptions, pInformation);
	}
	
	
	/**
	 * Loads the page-specific content and displays the finished page afterwards. <br>
	 * If the requested settings page is already loaded, it simply reloads it, but if not, 
	 * {@link #pOveModel}s and {@link #vbOptButtons}s children get set to unselected, cleared 
	 * and replaced by the elements of the requested page.
	 * This is done by either building (instantiating) the page or simply loading its already build elements.
	 * In addition, {@link #pInformation} type gets changed by calling {@link InformationSegment#setRefType(byte)} 
	 * and reseted by calling {@link InformationSegment#setContentDefault()} .
	 * Afterwards it calls {@link #recalculateDistances()} to fit the layout distances to the newly loaded elements and 
	 * {@link #updateHeight()} to update the {@link Main#scrollBar scroll bar}.
	 * Lastly, {@link Main#krlBackHome} gets attached to the scene and {@link #root} added to the {@link Main#root parent root}.
	 * @param parent The parent root to which the root of the settings page gets added to.
	 * @param page The number of the page which should get loaded. See "Also See" for further information.
	 * @see #loadedPage page number description
	 * @since 0.2
	 */
	public void loadPage(Group parent, byte page) {
		if (page != loadedPage) {
			if (loadedPage != 7) {
				pOveModel.getChildren().clear();
				for (Node i : vbOptButtons.getChildren()) ((OptionButton) i).setSelected(false);
				vbOptButtons.getChildren().clear();	
			}
			
			switch (page) {
			case 0: 
				if (inputHandlerPage != null) InputHandler.load();
				else inputHandlerPage = new InputHandler();
				break;
			case 1: 
				if (transcoderPage != null) Transcoder.load();
				else transcoderPage = new Transcoder();
				break;
			case 2: 
				if (noiseSourcePage != null) NoiseSource.load();
				else noiseSourcePage = new NoiseSource();
				break;
			default:
				System.out.println("Error: Page " + page + " doesn't exist.");
			}
			
			pInformation.setRefType(page);
			pInformation.setContentDefault();
			recalculateDistances();
			loadedPage = page;
			System.out.println("Settings page " + page + " has been loaded.");
			
		} else {
			System.out.println("Settings page " + page + " has been reloaded.");
		}
		
		updateHeight();
		Main.scene.setOnKeyReleased(Main.krlBackHome);
		parent.getChildren().add(root);
	}
	
	
	/**
	 * Updates the position of the layout elements. <br>
	 * It recalculates the layout Y of {@link #pOptions} and {@link #pInformation} according to the height of {@link #pOverview}. 
	 * This method usually gets called after {@link #loadPage(Group, byte) loading another settings page}.
	 * @since 0.2  
	 */
	private void recalculateDistances() {
		pOptions.setLayoutY(pOverview.getLayoutY() + Main.calcHeight(pOverview) + Constants.I_DISTANCE_SEGMENT);
		pInformation.setLayoutY(pOptions.getLayoutY());
		root.getChildren().add(pOverview);
	}
	
	
	
	/**
	 * Passes the update {@link #pOveModel overview's model} call on to the settings pages. <br>
	 * @param page The number of the page whose model has to be updated.
	 * @see #loadedPage page number description
	 * @since 0.2
	 */
	public static void updateOveModel(byte page) {
		switch (page) {
		case 0: 
			InputHandler.updateOveModel();
			break;
		case 1: 
			Transcoder.updateOveModel();
			break;
		case 2: 
			NoiseSource.updateOveModel();
			break;
		default:
			System.out.println("Error: Page " + page + " doesn't exist.");
		}
	}
	
	
	/**
	 * Calculates the height of the currently loaded page and calls {@link Main#updateScrollbar(javafx.scene.layout.Region)} with it. <br>
	 * Therefore it updates the {@link Main#scrollBar scroll bar}.
	 * Gets called when either the {@link de.wolkenfarmer.environment.pages.gui_elements.OptionButton
	 * #setOnActionW(ExperimentElement) information segment's content changed} or another  
	 * {@link #loadPage(Group, byte) settings page got loaded}. 
	 * @since 0.1
	 */
	public static void updateHeight() {
		if (Main.calcHeight(pOptions) >= Main.calcHeight(pInformation)) {
			Main.updateScrollbar(pOptions);
		} else {
			Main.updateScrollbar(pInformation);
		}
		root.getChildren().addAll(pOptions, pInformation);
	}
}
