/**
 * Contains some style sheets for the GUI of the environment. <br>
 * In JavaFX the appearance of all objects can be specified by CSS style sheets, 
 * which can either be added to the individual objects or directly to the scene.
 * Often there are more options then there is access vie the java syntax (like with TableView).
 * <p>
 * tableView.css: 
 * Defines the appearance of tables.<br>
 * Gets used for {@link de.wolkenfarmer.environment.pages.Home#tvResTable result table} on the home page. 
 * It's added to the {@link de.wolkenfarmer.environment.Main#scene main scene}.
 * This style sheet was mostly taken from the link in "See Also".
 * <p>
 * scrollBar.css: 
 * Defines the appearance of scroll bars.<br>
 * Gets used for nested scroll bars (like in the {@link de.wolkenfarmer.environment.pages.Home#tvResTable result table} on the home page)
 * and the main {@link de.wolkenfarmer.environment.Main#scrollBar scroll bar}. 
 * It's added to the {@link de.wolkenfarmer.environment.Main#scene main scene}.
 * <p>
 * textArea.css: 
 * Defines the appearance of text areas like in the {@link de.wolkenfarmer.input_handlers.UserInput#taUserText text area} 
 * of the input handler "User Input".<br>
 * It's added to the {@link de.wolkenfarmer.environment.Main#scene main scene}.
 * @author Wolkenfarmer
 * @see <a href="https://stackoverflow.com/questions/33938811/styling-a-tableview-in-css-javafx">tableView.css - Chris</a>
 */
package de.wolkenfarmer.environment.pages.css;