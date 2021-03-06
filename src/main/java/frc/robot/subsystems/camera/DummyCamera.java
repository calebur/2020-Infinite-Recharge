/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.camera;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */

public class DummyCamera extends SubsystemBase implements ICamera{

    public DummyCamera() {
        SmartDashboard.putNumber("y angle camera", 0);
    }

    public double getXAngle() {
        return 0;
    }

    public double getYAngle() {
        return SmartDashboard.getNumber("y angle camera", 0);
    }

    public boolean hasTarget() {
        return true;
    }

    public void setPipeline(double pipeline) {
        SmartDashboard.putNumber("Pipeline", pipeline);
    }

    public double getPipeline() {
        return 0;
    }

    // public void turnRobot(double angle) {

    // }

    public void updateDashboard() {
    }

    // Calculates angle
    // 2-degree offset hardcoded for raft testing
    public double getDistance() {
        return 0;
    }

}
