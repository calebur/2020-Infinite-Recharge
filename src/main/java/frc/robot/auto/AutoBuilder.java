/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.auto.commands.*;

/**
 * 
 */
public class AutoBuilder {

    Preferences prefs = Preferences.getInstance();
    CommandGroup commandList = new CommandGroup(); // sequence of commands to run

    public void init() {

        int i = 1;
        // if there is an autoX in robot preferences, keep running!!!!!
        while(prefs.containsKey("auto" + i)) {

            System.out.println(prefs.getString("auto" + i, null));
            // assigns argumnents from autoX (where x = i in iterator)
            String args = prefs.getString("auto" + i, null);
            // runs ParseCommand which separates the arguments apart (all the ? and & stuff)
            ParseCommand parsedCommand = new ParseCommand(args);
            // System.out.println(argParse.toString());
            i++;

            Command command = this.getCommand(parsedCommand); // assigns current command to command
            commandList.addSequential(command); // add current command to queue of next commands
        }

    }

    // makes commandList public for use outside AutoBuilder
    public Command getAutoCommand() {
        return commandList;
    }

    // depending on the arguments, creates new object
    private Command getCommand(ParseCommand parsedCommand) {
        switch(parsedCommand.commandName){
            case "drive":
                return new AutoDrive(parsedCommand);
            case "stop":
                return new DoNothing(parsedCommand);
            default:
                System.out.println("Auto command not recognized: " + parsedCommand);
                return new NullCommand();
        

        }
    }

}
