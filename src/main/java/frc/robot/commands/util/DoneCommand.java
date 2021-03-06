/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.util;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Specific type of Command.
 * If a RunAfterReady command is ready early, keeps running command.
 * 
 * Combined w. ParallelGroup, allows all commands to keep running until all are ready.
 */
public abstract class DoneCommand extends CommandBase {

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  public abstract boolean isDone();

}
