/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.serializer;

import frc.robot.Robot;
import frc.robot.commands.util.DoneCommand;
import frc.robot.util.LogUtils;

public class SerializerStop extends DoneCommand {
  public SerializerStop() {
    addRequirements(Robot.serializer);
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    // Robot.serializer.driveTurnTable(SerializerSpeed.stop, false);
    Robot.serializer.manualSpeed(false);
    // Robot.throat.ejectorSpeed(false);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true or when interrupted
  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isDone() {
    // TODO: check that the serializer actually stops
    LogUtils.log("SerializerStop is done");
    return true;
  }
}
