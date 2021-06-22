/**
 * Contains the environment itself which only uses {@link de.wolkenfarmer.experiment_elements experiment elements} 
 * for the communication experiment and can otherwise work as a stand alone application. <br>
 * ... except for the {@link de.wolkenfarmer.Constants constants file}, which provides a set of application wide standard values.
 * <p>
 * The parts, which handle the communication experiment itself and the basic program structure, are inside the logic package.<br>
 * The GUI however is split into the pages and the GUI elements package. 
 * The pages utilize the GUI elements of the dedicated package.
 * <p>
 * For further information see the dedicated package descriptions below "See Also".
 * 
 * @author Wolkenfarmer
 * @see de.wolkenfarmer.environment.logic logic
 * @see de.wolkenfarmer.environment.pages pages
 * @see de.wolkenfarmer.environment.gui_elements GUI elements
 */
package de.wolkenfarmer.environment;