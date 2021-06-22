/**
 * Contains the logic of the environment and therefore also the {@link de.wolkenfarmer.environment.logic.Main#main(String[]) main method}. <br>
 * The {@link de.wolkenfarmer.environment.logic.Run run class} handles the communication experiment itself utilizing the 
 * {@link de.wolkenfarmer.environment.logic.UniDataType universal data type class} for flawless data transfers 
 * from one {@link de.wolkenfarmer.experiment_elements experiment element} to another and the
 * {@link de.wolkenfarmer.environment.logic.Result result class} collects the results of the experiment as well as analyzes them.<br>
 * The {@link de.wolkenfarmer.environment.logic.Main main class} holds the main method and therefore starts up the application 
 * as well as its window with some basic setup. In addition, 
 * the class holds some relevant variables which have to be accessible in the whole environment.
 * 
 * @author Wolkenfarmer
 */
package de.wolkenfarmer.environment.logic;