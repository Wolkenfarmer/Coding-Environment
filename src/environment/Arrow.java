/**
 * 
 */
package environment;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * A template class to create arrows.
 * They can either be created horizontal (from left to right) or vertical (from bottom to top).
 * The arrows have a non-solid head and can be labeled (if horizontal).
 * The data for the arrows get prepared in {@link environment.ModelFactory#buildRelation(float, float, short, boolean, String)}
 * to be shown as relation in {@link environment.Homepage#pSetModel}.
 * @author Wolkenfarmer
 */
public class Arrow extends Group {
	/** The line connecting the start and end point.*/
    private Line line;
    /** The left arrow-head line (from the perspective of the line).*/
    private Line headL;
    /** The right arrow-head line (from the perspective of the line).*/
    private Line headR;
    /** The layout group for the description of the arrow which will be printed centered above the arrow. Contains {@link #description}.*/
    private HBox hbDescription;
    /** Label which displays the description of the arrow. It's part of {@link #hbDescription}.*/
    private Label description;

    /**
     * Creates and gives the arrow specified by it's arguments.
     * @param startX Defines the x-Coordinate of the start.
     * @param startY Defines the x-Coordinate of the start.
     * @param endX Defines the x-Coordinate of the end.
     * @param endY Defines the y-Coordinate of the end.
     * @param lenghtHead Defines the lenght of the head.
     * @param widthHead Defines the width of the head.
     * @param vertical Defines whether the arrow should be displayed horizontal (from left to right) or vertical (from bottom to top).
     * @param name The discription of the arrow which will be displayed above it (only if horizontal).
     * @return Returns the finished arrow.
     */
    public Arrow getArrow(double startX, double startY, double endX, double endY, double lenghtHead, double widthHead, boolean vertical, String name) {
    	line = new Line();
    	line.setStartX(startX);
    	line.setStartY(startY);
    	line.setEndX(endX);
    	line.setEndY(endY);
    	line.setStroke(Color.WHITESMOKE);
    	    	
    	headL = new Line();
    	headL.setStartX(endX);
    	headL.setStartY(endY);
    	headL.setStroke(Color.WHITESMOKE);
    	
    	headR = new Line();
    	headR.setStartX(endX);
    	headR.setStartY(endY);
    	headR.setStroke(Color.WHITESMOKE);
    	
    	
    	if (vertical) {
    		headL.setEndY(endY + lenghtHead);
    		headL.setEndX(endX + (widthHead / 2));
    		headR.setEndY(endY + lenghtHead);
    		headR.setEndX(endX - (widthHead / 2));
    	} else {
    		headL.setEndX(endX - lenghtHead);
    		headL.setEndY(endY + (widthHead / 2));
    		headR.setEndX(endX - lenghtHead);
    		headR.setEndY(endY - (widthHead / 2));
    		
    		hbDescription = new HBox();
    		hbDescription.setPrefWidth(endX - startX - lenghtHead);
    		hbDescription.setLayoutX(startX);
    		hbDescription.setLayoutY(startY - 25);
    		description = new Label(name);
    		description.setFont(Main.fSmallText);
    		description.setTextFill(Color.WHITESMOKE);
    		hbDescription.getChildren().add(description);
    		hbDescription.setAlignment(Pos.CENTER);
    		this.getChildren().add(hbDescription);
    	}
        
        this.getChildren().addAll(line, headL, headR);
        return this;
    }
}
