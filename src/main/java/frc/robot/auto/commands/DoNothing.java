/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.commands;

import frc.robot.auto.ParseCommand;

/**
 * Does nothing, but requires the parameter of type ParseCommand
 */
public class DoNothing extends BaseAutoCommand {

    public DoNothing(ParseCommand parsedCommand) {
        super(parsedCommand);
    }

}