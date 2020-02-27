/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.auto.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.auto.ParseCommand;

public class AimTurret extends BaseAutoCommand {

  public static final String COMMAND_NAME = "aimTurret";
  double limelightXAngle, limelightYAngle;
  private double panTrim = -2.5;

  public AimTurret(ParseCommand parsedCommand) {
    super(parsedCommand);
    addRequirements(Robot.turret);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    super.initialize();
    Robot.turret.setPanTarget(Robot.turret.getPanAngle());
    Robot.turret.setTiltTargetAngle(Robot.turret.getTiltAngle());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {

    if (Robot.camera.hasTarget()) {
      limelightYAngle = Robot.camera.getYAngle();

      var targetData = Robot.flywheelTable.getIdealTarget(limelightYAngle);
      Robot.turret.setTiltTargetAngle(targetData.getAngle());

      panTrim = SmartDashboard.getNumber("Pan Trim", panTrim);

      limelightXAngle = Robot.camera.getXAngle();
      Robot.turret.setPanTarget(limelightXAngle + Robot.turret.getPanAngle() + panTrim);

      Robot.flywheel.setTarget(targetData.getSpeed());

    }
    Robot.turret.PIDDrive(true);
  }
}
