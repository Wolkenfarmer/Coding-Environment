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
 * @author Wolkenfarmer
 */
public class Arrow extends Group {
    private Line line;
    private Line headL;
    private Line headR;
    private Label description;
    private HBox descriptionSpace;

    public Arrow(double startX, double startY, double endX, double endY, double lenghtHead, double widthHead, boolean vertical, String name) {
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
    		
    		descriptionSpace = new HBox();
    		descriptionSpace.setPrefWidth(endX - startX - lenghtHead);
    		descriptionSpace.setLayoutX(startX);
    		descriptionSpace.setLayoutY(startY - 25);
    		description = new Label(name);
    		description.setFont(Main.fSmallText);
    		description.setTextFill(Color.WHITESMOKE);
    		descriptionSpace.getChildren().add(description);
    		descriptionSpace.setAlignment(Pos.CENTER);
    		this.getChildren().add(descriptionSpace);
    	}
        
        this.getChildren().addAll(line, headL, headR);
    }
    
    public Arrow getArrow() {
    	return this;
    }

}
