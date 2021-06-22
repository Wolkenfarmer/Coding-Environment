/**
 * Contains the noise sources of the experiment elements. <br>
 * The noise sources are responsible for altering the encoded input stream 
 * in order to test the error detection and correction capabilities of the coding / decoder.
 * <p>
 * They can be picked on the {@link de.wolkenfarmer.environment.pages.NoiseSource noise source page}, 
 * will be displayed by the {@link de.wolkenfarmer.environment.pages.Home#pSetModel model} on the home page 
 * and used for the communication experiment in {@link de.wolkenfarmer.environment.logic.Run run} 
 * when the {@link de.wolkenfarmer.environment.pages.Home#bConButRun run button} on the home page gets pressed.
 * <p>
 * For further information see the nested class explanations.
 * @author Wolkenfarmer
 */
package de.wolkenfarmer.experiment_elements.noise_sources;