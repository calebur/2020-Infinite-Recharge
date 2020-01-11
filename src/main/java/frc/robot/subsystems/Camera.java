/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */

public class Camera {

    NetworkTableEntry tx, ty, ta, ts, tl, tshort, tlong, thor, tvert, getpipe, camtran;
    double robotHeight, totalHeight, offsetAngle;

    public Camera(double robotHeight, double totalHeight, double offsetAngle) {

        NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        ts = table.getEntry("ts"); // not helpful. Measures rotation of target
        tshort = table.getEntry("tshort");
        tlong = table.getEntry("tlong");
        thor = table.getEntry("thor");
        tvert = table.getEntry("tvert");
        camtran = table.getEntry("camtran");

        this.robotHeight = robotHeight;
        this.totalHeight = totalHeight;
        this.offsetAngle = offsetAngle;

    }

    public double getYAngle() {
        return ty.getDouble(0.0);
    }

    public void updateDashboard() {

        // post to smart dashboard periodically
        SmartDashboard.putNumber("LimelightX", tx.getDouble(0.0));
        SmartDashboard.putNumber("LimelightY", ty.getDouble(0.0));
        SmartDashboard.putNumber("LimelightArea", ta.getDouble(0.0));
        SmartDashboard.putNumber("LimelightSkew", ts.getDouble(0.0)); // not useful

        SmartDashboard.putNumber("LimelightShort", tshort.getDouble(0.0));
        SmartDashboard.putNumber("LimelightLong", tlong.getDouble(0.0));
        SmartDashboard.putNumber("LimelightHorizSidelen", thor.getDouble(0.0));
        SmartDashboard.putNumber("LimelightVerticalSidelen", tvert.getDouble(0.0));
        SmartDashboard.putNumber("LimelightCamtran", camtran.getDouble(0.0));

    }

    // Calculates angle
    // 2-degree offset hardcoded for raft testing
    public double getDistance() {
        double distance = (totalHeight - robotHeight) / Math.tan(Math.toRadians(getYAngle() + offsetAngle));
        SmartDashboard.putNumber("distance", distance);
        return distance;
    }

}
