/**
 * Contains some stylesheets for the environment. 
 * In JavaFX the appearance of all objects can be specified by CSS-stylesheets, 
 * which can either be added to the individual objects or directly to the scene.
 * Often there are more options then there is access vie the java syntax (like with TableView).
 * <p>
 * <p>
 * tableView.css: 
 * Defines the appearance of tables. Gets used for {@link environment.Homepage#tvResTable}. 
 * It's added to {@link environment.Main#scene}.
 * This stylesheet was mostly taken from the first link in @ see.
 * <p>
 * scrollbar.css: 
 * Defines the appearance of scrollbars. Gets used for {@link environment.Homepage#tvResTable} (has a nested scrollbar) 
 * and {@link environment.Main#scrollbar}. 
 * It's added to {@link environment.Main#scene}.
 * @author Wolkenfarmer
 * @see <a href="https://stackoverflow.com/questions/33938811/styling-a-tableview-in-css-javafx">tableView.css - Chris</a>
 */
package css;