/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * Add your docs here.
 */
public interface ITurret extends Subsystem {
  public double getXPosition();

  public void setXTarget(double target);

  public void setXSpeedUnsafe(double speed);

  public void setYSpeedUnsafe(double speed);

  public void setHoodSpeed(double speed);

  public double getHoodAngle();

  public void setHoodTargetAngle(double angle);

  public void PIDDriveY();

  public void addTurretSmartDashboard();

  public void PIDDrive();
}
