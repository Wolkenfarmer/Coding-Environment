/**
 * Contains all experiment elements which can be utilized by the {@link de.wolkenfarmer.environment environment} 
 * for the communication experiment. <br>
 * The {@link de.wolkenfarmer.experiment_elements.ExperimentElement experiment element} class provides a base for every experiment element 
 * regardless of its type for easier access inside the environment.<br>
 * There are three types of experiment elements, which all perform different tasks and are in their dedicated packages 
 * while having the same structure.
 * <p>
 * Currently the experiment elements do not get automatically mapped by the environment. 
 * Therefore, new elements have to be manually added to the environment via code.
 * <p>
 * For further information see the dedicated package descriptions below "See Also" as well as the nested class explanation.
 * 
 * @author Wolkenfarmer
 * @see de.wolkenfarmer.experiment_elements.input_handlers input handlers
 * @see de.wolkenfarmer.experiment_elements.noise_sources noise sources
 * @see de.wolkenfarmer.experiment_elements.transcoder transcoder
 */
package de.wolkenfarmer.experiment_elements;