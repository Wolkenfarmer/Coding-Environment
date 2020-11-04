/**
 * Contains some style-sheets for the GUI of the environment. 
 * In JavaFX the appearance of all objects can be specified by CSS-style-sheets, 
 * which can either be added to the individual objects or directly to the scene.
 * Often there are more options then there is access vie the java syntax (like with TableView).
 * <p>
 * tableView.css: 
 * Defines the appearance of tables. Gets used for {@link environment.pages.Homepage#tvResTable}. 
 * It's added to {@link environment.Main#scene}.
 * This style-sheet was mostly taken from the first link in @ see.
 * <p>
 * scrollbar.css: 
 * Defines the appearance of scroll bars. Gets used for nested scroll bars (like {@link environment.pages.Homepage#tvResTable})
 * and {@link environment.Main#scrollbar scroll bar}. 
 * It's added to {@link environment.Main#scene}.
 * <p>
 * textArea.css: 
 * Defines the appearance of text areas like in {@link infSources.UserInput#taUserText}
 * It's added to {@link environment.Main#scene}.
 * @author Wolkenfarmer
 * @see <a href="https://stackoverflow.com/questions/33938811/styling-a-tableview-in-css-javafx">tableView.css - Chris</a>
 */
package environment.pages.css;