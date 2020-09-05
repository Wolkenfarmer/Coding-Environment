/**
 * Contains the environment itself and with it the entire UI and {@link environment.Main main} class. 
 * This part of the program handles all the other packages and picks the {@link infSources information source} 
 * as well as {@link enDecoder en-/ decoder} which will be used for the communication experiment. 
 * In addition, it contains the logic of the program.
 * <p>
 * In the classes building the UI the declaration of the UI objects is written in the following scheme: 
 * (the dots in the beginning are only for layout purposes for javadoc)<br>
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
package environment;