/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.util.LogUtils;

public class TurnToTarget extends CommandBase {
  public TurnToTarget() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  double targetAngle;
  double targetRight;
  double targetLeft;

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    targetAngle = Robot.camera.getXAngle();

    double delta = Robot.driveBase.angleToDist(targetAngle);
    targetLeft = Robot.driveBase.getLeftPosition() + delta;
    targetRight = Robot.driveBase.getRightPosition() - delta;
    Robot.driveBase.setTarget(targetLeft, targetRight);
    LogUtils.log("TurnAnglePID initialized, target left = " + targetLeft + " target right = " + targetRight);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    Robot.driveBase.PIDDrive();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    double error = 2;
    boolean rightFinished = (targetRight < Robot.driveBase.getRightPosition() + error) && (targetRight > Robot.driveBase.getRightPosition() - error);
    boolean leftFinished = (targetLeft < Robot.driveBase.getLeftPosition() + error) && (targetLeft > Robot.driveBase.getLeftPosition() - error);
    if (leftFinished && rightFinished) {
      return true;
    }
    return false;
  }

  // Called once after isFinished returns true or when interrupted
  @Override
  public void end(boolean interrupted) {
  }
}
