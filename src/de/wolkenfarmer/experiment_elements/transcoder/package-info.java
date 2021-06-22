/**
 * Contains the transcoder of the experiment elements. <br>
 * At the moment the CE is only focused on channel coding. Consequently, all codes are error detection and / or correction codes (ECCs). <br>
 * The transcoder are responsible for encoding the input in a way, that flaws can be detected or even corrected during the decoding, 
 * and decoding it afterwards. Ultimately, the capabilities of the different transcoder / codings get tested in the environment.
 * <p>
 * They can be picked on the {@link de.wolkenfarmer.environment.pages.Transcoder transcoder page}, 
 * will be displayed by the {@link de.wolkenfarmer.environment.pages.Home#pSetModel model} on the home page 
 * and used for the communication experiment in {@link de.wolkenfarmer.environment.logic.Run run} 
 * when the {@link de.wolkenfarmer.environment.pages.Home#bConButRun run button} on the home page gets pressed.
 * <p>
 * For further information see the nested class explanations.
 * @author Wolkenfarmer
 */
package de.wolkenfarmer.experiment_elements.transcoder;