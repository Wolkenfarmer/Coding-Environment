package de.wolkenfarmer.environment.pages.gui_elements;

import de.wolkenfarmer.environment.Main;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

/**
 * A template class to create arrows.
 * They can either be created horizontal (from left to right) or vertical (from bottom to top or top to bottom).
 * The arrows have a non-solid head and can be labeled (if horizontal).
 * The arrows get used in {@link de.wolkenfarmer.environment.pages.gui_elements.ModelFactory#buildRelation(float, float, short, boolean, String)} 
 * to be shown in {@link de.wolkenfarmer.environment.pages.Home#pSetModel} and used in the different {@link de.wolkenfarmer.environment.pages.Settings settings pages}
 * constructors and {@link de.wolkenfarmer.environment.pages.Settings#updateOveModel(byte)}.
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
     * @param lenghtHead Defines the length of the head.
     * @param widthHead Defines the width of the head.
     * @param vertical Defines whether the arrow should be displayed horizontal (from left to right) or vertical (from bottom to top).
     * @param name The description of the arrow which will be displayed above it (only if horizontal).
     * @param labelWidth Only used for vertical arrows to determine the space for {@link #description}.
     * @return Returns the finished arrow.
     */
    public Arrow getArrow(double startX, double startY, double endX, double endY, double lenghtHead, double widthHead, 
    		boolean vertical, String name, double labelWidth) {
    	line = new Line();
    	line.setStroke(Main.cNormal);
    	headL = new Line();
    	headL.setStroke(Main.cNormal);
    	headR = new Line();
    	headR.setStroke(Main.cNormal);
    	    	
    	hbDescription = new HBox();
    	description = new Label();
    	description.setFont(Main.fSmallText);
    	description.setTextFill(Main.cNormal);
    	description.setText(name);
    	
    	
       	if (vertical) {
       		line.setStartX(startX);
        	line.setStartY(startY - 5);
        	line.setEndX(endX);
        	line.setEndY(endY + 5);
        	
        	hbDescription.setAlignment(Pos.CENTER_LEFT);
        	hbDescription.setPrefWidth(labelWidth - 10);
        	hbDescription.setPrefHeight(endY - startY);
    		hbDescription.setLayoutX(endX + 10);
    		hbDescription.setLayoutY(startY);
    		description.setWrapText(true);
        	
        	if (startY < endY) {
        		headL.setEndY(endY - lenghtHead);
        		headL.setEndX(endX + (widthHead / 2));
        		headR.setEndY(endY - lenghtHead);
        		headR.setEndX(endX - (widthHead / 2));
        	} else {
        		headL.setEndY(endY + lenghtHead);
        		headL.setEndX(endX + (widthHead / 2));
        		headR.setEndY(endY + lenghtHead);
        		headR.setEndX(endX - (widthHead / 2));
        	}
    	} else {
    		line.setStartX(startX + 5);
        	line.setStartY(startY);
        	line.setEndX(endX - 5);
        	line.setEndY(endY);
        	
    		headL.setEndX(endX - lenghtHead);
    		headL.setEndY(endY + (widthHead / 2));
    		headR.setEndX(endX - lenghtHead);
    		headR.setEndY(endY - (widthHead / 2));
    		
    		hbDescription.setAlignment(Pos.CENTER);
    		hbDescription.setPrefWidth(endX - startX);
    		hbDescription.setLayoutX(startX);
    		hbDescription.setLayoutY(startY - 25);
    	}
    	
    	headL.setStartX(line.getEndX());
    	headL.setStartY(line.getEndY());
    	headR.setStartX(line.getEndX());
    	headR.setStartY(line.getEndY());
        
    	hbDescription.getChildren().add(description);
        this.getChildren().addAll(line, headL, headR, hbDescription);
        
        return this;
    }
}