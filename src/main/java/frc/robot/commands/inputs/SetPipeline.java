/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.inputs;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetPipeline extends CommandBase {

  double pipeline;

  public SetPipeline(int pipeline) {
    addRequirements(Robot.camera);
    this.pipeline = pipeline;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    if (Robot.camera.getPipeline() != pipeline) {
      Robot.camera.setPipeline(pipeline);
    }
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
}
