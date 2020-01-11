/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class drive12inches extends Command {
  public drive12inches() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    PIDRight = new PIDController(0.75, 0, 0);
    SmartDashboard.putData("PIDRight",PIDRight);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    target=Robot.driveBase.getRightPosition()+12;
    PIDRight.setSetpoint(target);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double right=PIDRight.calculate(Robot.driveBase.getRightPosition());
    Robot.driveBase.differentialDrive1.tankDrive(0, right);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }

  private PIDController PIDRight;
  
  double target;

  
}