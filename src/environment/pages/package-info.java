/**
 * Contains the different pages of the environment.
 * Its {@link environment.pages.Homepage home page} links up to the different sub-pages 
 * {@link environment.pages.InfSourcePage information source page},
 * {@link environment.pages.EnDecoderPage en- / decoder page},
 * {@link environment.pages.NoiSourcePage noise source page} and soon the
 * destination page, of which the first three extend {@link environment.pages.SettingsPage}.<br>
 * In addition, it contains the packages {@link environment.pages.guiElements}, 
 * which helps the pages to build and process / update it's GUI accordingly,
 * and {@link environment.pages.css}, which contains CSS-style-sheets which get added to the scene 
 * and specify the look of the program in some aspects.
 * <p>
 * The GUI objects are mostly declared named after the following scheme: 
 * (the dots in the beginning are only for layout purposes for Javadoc and represent tabulator spaces)<br>
 * . 1a_Container (contains node 2A, 2C and container 2B)<br>
 * . . . 2a_Node (is contained by / part of container 1) <br>
 * . . . 2b_Container (is contained by / part of container 1 and contains node 3)<br>
 * . . . . . 3_Node (is contained by / part of container 2B)<br>
 * . . . 2c_Node (is contained by / part of container 1)<br>
 * . 1b_Container ... 
 * <p>
 * ... and the objects would be named:<br>
 * . 1aContainer<br>
 * . . . 1a2aNode<br>
 * . . . 1a2bContainer<br>
 * . . . . . 1a2b3Node<br>
 * . . . 1a2cNode<br>
 * . 1bContainer ... 
 * @author Wolkenfarmer
 */
package environment.pages;