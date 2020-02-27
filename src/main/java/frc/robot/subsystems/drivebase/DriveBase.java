// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.subsystems.drivebase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import frc.robot.commands.drive.*;
import frc.robot.Robot;
import frc.robot.RobotConstants2019;
import frc.robot.RobotConstants2020;
import frc.robot.RobotConstantsRaft;
import frc.robot.Robot.RobotType;

/**
 *
 */
public abstract class DriveBase extends SubsystemBase {


    protected SpeedControllerGroup leftDrive;
    protected SpeedControllerGroup rightDrive;
    public DifferentialDrive differentialDrive1;
    public DifferentialDriveOdometry odometer;
    protected PIDController PIDRight;
    protected PIDController PIDLeft;
    protected double setpointLeft, setpointRight;

    public DriveBase() {
        // setup();

    }

    protected void setup() {
        leftDrive = getLeftDrive();
        rightDrive = getRightDrive();
        
        addChild("LeftDrive", leftDrive);
        addChild("RightDrive", rightDrive);
        differentialDrive1 = new DifferentialDrive(leftDrive, rightDrive);
        addChild("Differential Drive 1", differentialDrive1);
        differentialDrive1.setSafetyEnabled(true);
        differentialDrive1.setExpiration(0.1);
        differentialDrive1.setMaxOutput(1.0);
        double navxAngle = Robot.navx.getNavYaw();
        odometer = new DifferentialDriveOdometry(Rotation2d.fromDegrees(navxAngle));
    }
    
    public abstract SpeedControllerGroup getLeftDrive();

    public abstract SpeedControllerGroup getRightDrive();

    protected abstract double encoderInches(WPI_TalonSRX driveInput);

    public abstract double encoderInches(CANSparkMax driveInput);

    public abstract double speedToVolts(double speed);

    public abstract void tankDriveVolts(double leftSpeed, double rightSpeed);

    public abstract double angleToDist(double angle);


    public abstract void reportPosition();

    public abstract double getRightPosition();

    public abstract double getLeftPosition();

    public abstract void PIDDrive();

    public abstract void resetPosition();

    
    public abstract PIDController getPIDLeft();

    public abstract PIDController getPIDRight();

    public abstract void setTarget(double left, double right);

    public abstract void setTargetAngle(double targetAngle);

    public abstract boolean isAtTarget();

    public abstract boolean isAtRightTarget();

    public abstract boolean isAtLeftTarget();

    public abstract void resetOdometry();

    public abstract Pose2d getPose();

    public abstract DifferentialDriveWheelSpeeds getWheelSpeeds();

    @Override
    public void periodic() {
        //SmartDashboard.putNumber("Right Encoder", right4.getSensorCollection().getQuadraturePosition());
        //SmartDashboard.putNumber("Left Encoder", -left4.getSensorCollection().getQuadraturePosition());
        // Put code here to be run every loop
        double rightInches = getRightPosition();
        double leftInches = getLeftPosition();
        double navxAngle = Robot.navx.getNavYaw();
        // converts raw encoder readout to inches
        // odometer.update(Rotation2d.fromDegrees(navxAngle), leftInches, rightInches);
        SmartDashboard.putNumber("Right Position", rightInches);
        SmartDashboard.putNumber("Left Position", leftInches);

        // Translation2d translation = odometer.getPoseMeters().getTranslation();

        // SmartDashboard.putNumber("Odometer x", translation.getX());
        // SmartDashboard.putNumber("Odometer y", translation.getY());
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
