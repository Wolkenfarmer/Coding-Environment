/**
 * Contains the different pages / Handles the GUI of the environment. <br>
 * The pages utilize the GUI elements of the {@link de.wolkenfarmer.environment.gui_elements dedicated package}. 
 * Moreover, the pages represent the view and the controller at the same time.
 * Consequently, the user can set up the program as well as view the results here.
 * <p>
 * Its {@link de.wolkenfarmer.environment.pages.Home home page} links up to the different sub-pages 
 * {@link de.wolkenfarmer.environment.pages.InputHandler input handler page},
 * {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page} and
 * {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}, 
 * which extend from the {@link de.wolkenfarmer.environment.pages.Settings settings page}.
 * <p>&nbsp;</p>
 * 
 * The GUI objects are mostly declared named after the following scheme 
 * (the dots in the beginning are only for layout purposes for Javadoc and represent tabulator spaces): <br> 
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
 * 
 * @author Wolkenfarmer
 */
package de.wolkenfarmer.environment.pages;