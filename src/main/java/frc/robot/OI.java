// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot;

import com.chaos131.LogitechF310;

import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.commands.climbtake.*;
import frc.robot.commands.drive.DriveDistancePID;
import frc.robot.commands.drive.DriveDistancePIDDashboard;
import frc.robot.commands.drive.TankDrive;
import frc.robot.commands.drive.TurnAnglePID;
import frc.robot.commands.inputs.SetPipeline;
import frc.robot.commands.serializer.SerializerDefault;
import frc.robot.commands.serializer.SerializerStop;
import frc.robot.commands.turret.AimAndShoot;
import frc.robot.commands.turret.AimTurret;
import frc.robot.commands.turret.AimTurretDashboard;
import frc.robot.commands.serializer.Unjam;
import frc.robot.commands.turret.BumperShotAim;
import frc.robot.commands.turret.ManualTurret;
import frc.robot.commands.turret.PrepareFlywheel;
import frc.robot.commands.turret.Shoot;
import frc.robot.commands.turret.TiltSafe;
import frc.robot.commands.turret.TurretDefault;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

    public LogitechF310 driver;
    public LogitechF310 operator;
    public LogitechF310 tester;

    public boolean testMode = true;

    public OI() {
        driver = new LogitechF310(0);
        operator = new LogitechF310(1);
        tester = null;

        // Driver
        driver.rightBumper.whileHeld(new TankDrive(0.5));
        driver.rightTrigger.whileHeld(new SetIntake(0.75).alongWith(
                new SetClimbTakePosition(RobotConstants2020.INTAKE_POSITION, RobotConstants2020.EXTENDER_ZERO)));

        // TODO: remove once tested and happy
        driver.aButton.whileActiveOnce(new DriveDistancePID(12));
        driver.yButton.whileActiveOnce(new DriveDistancePID(-12));
        driver.xButton.whileActiveOnce(new TurnAnglePID(90));
        driver.bButton.whileActiveOnce(new TurnAnglePID(-90));
        driver.selectButton.whileActiveOnce(new DriveDistancePIDDashboard());

        driver.startButton.whenPressed(() -> Robot.driveBase.resetPosition(), Robot.driveBase);

        //driver.leftTrigger.whileHeld(() -> {
        //    Robot.climbTake.setExtenderSpeed(Math.abs(operator.getRightY()));
        //}, Robot.climbTake);

        /*
         * Operator Testing operator.rightTrigger.whileHeld(() ->
         * Robot.serializer.driveTurnTable(SerializerSpeed.fast), Robot.serializer);
         * 
         * operator.rightBumper.whileHeld(() -> Robot.throat.ejectorSpeed(true),
         * Robot.throat);
         * 
         * operator.bButton.whileHeld(() -> Robot.flywheel.setFlywheelTargetDashboard(),
         * Robot.flywheel);
         */

        // Operator
        operator.aButton.whileHeld(new SetIntake(0.75).alongWith(
                new SetClimbTakePosition(RobotConstants2020.INTAKE_POSITION, RobotConstants2020.EXTENDER_ZERO)));
        operator.bButton.whileHeld(new SetIntake(-0.75));
        operator.xButton.whileHeld(new Unjam());
        operator.yButton.whileHeld(new AimTurret().alongWith(new SetPipeline(8)));

        operator.dPadUp.whileHeld(
                new SetClimbTakePosition(RobotConstants2020.CLIMB_POSITION, RobotConstants2020.EXTENDER_OUT));
        operator.dPadDown.whileHeld(
                new SetClimbTakePosition(RobotConstants2020.INTAKE_POSITION, RobotConstants2020.EXTENDER_ZERO));
        operator.dPadLeft.whileHeld(
                new SetClimbTakePosition(RobotConstants2020.PIVOT_THRESHOLD, RobotConstants2020.EXTENDER_ZERO));
        operator.dPadRight
                .whileHeld(new SetClimbTakePosition(RobotConstants2020.CLEAR_OF_BALLS, RobotConstants2020.EXTENDER_IN));

        operator.selectButton.whileHeld(new TiltSafe());
        operator.startButton.whileHeld(new AimAndShoot());

        operator.leftTrigger.whileHeld(new BumperShotAim());
        operator.leftBumper.whileHeld(new AimTurret().alongWith(new SetPipeline(9)));

        operator.rightBumper.and(operator.rightTrigger.negate())
                .whileActiveContinuous(new PrepareFlywheel().alongWith(new SerializerStop()));
        // operator.rightBumper.whileHeld(new PrepareFlywheel());
        operator.rightTrigger.whileHeld(new Shoot());

        // Tester
        if (testMode) {
            tester = new LogitechF310(2);

            tester.dPadUp.whileHeld(() -> Robot.climbTake.setExtenderSpeed(0.10), Robot.climbTake);
            tester.dPadDown.whileHeld(() -> Robot.climbTake.setExtenderSpeed(-0.10), Robot.climbTake);
            tester.dPadLeft.whileHeld(() -> Robot.climbTake.setPivotSpeed(-0.10), Robot.climbTake);
            tester.dPadRight.whileHeld(() -> Robot.climbTake.setPivotSpeed(0.10), Robot.climbTake);

            // tester.aButton.whileHeld(() -> Robot.unjammer.spin(false), Robot.unjammer);
            tester.bButton.whileHeld(() -> Robot.unjammer.spin(true), Robot.unjammer);

            tester.yButton.whileHeld(() -> Robot.throat.ejectorSpeed(true), Robot.throat);

            tester.xButton.whileHeld(() -> Robot.serializer.manualSpeed(true), Robot.serializer);

            tester.rightBumper.and(tester.rightTrigger.negate()).whileActiveContinuous(new PrepareFlywheel());

            tester.leftBumper.whileHeld(new AimTurretDashboard());

            tester.leftTrigger.whileHeld(new ManualTurret());

        }

        // Framework for deadline commandGroup
        // operator.leftBumper.whileHeld(Deadline.createDeadline(new AimTurret(), new
        // PrepareFlywheel()));

        // Default Commands
        Robot.driveBase.setDefaultCommand(new TankDrive(1));
        Robot.intake.setDefaultCommand(new SetIntake(0));
        Robot.camera.setDefaultCommand(new SetPipeline(7));
        // Robot.flywheel.setDefaultCommand(new FlywheelZero());
        Robot.flywheel.setDefaultCommand(new RunCommand(() -> Robot.flywheel.coastFlywheel(), Robot.flywheel));
        Robot.serializer.setDefaultCommand(new SerializerDefault());
        Robot.throat.setDefaultCommand(new RunCommand(() -> Robot.throat.ejectorSpeed(false), Robot.throat));
        Robot.unjammer.setDefaultCommand(new RunCommand(() -> Robot.unjammer.spin(true), Robot.unjammer));
        Robot.turret.setDefaultCommand(new TurretDefault());
        Robot.climbTake.setDefaultCommand(new ManualClimbtake());

    }

    public double testTurretPanTarget() {
        return tester.getLeftX();
    }

    public double testTurretTiltTarget() {
        return tester.getLeftY();
    }

    public double getTurretPanTarget() {
        return operator.getLeftX();
    }
    
    public double getLeftSpeed() {
        return driver.getLeftY();
    }

    public double getRightSpeed() {
        return driver.getRightY();
    }

    public boolean getEnableExtend() {
        return driver.leftTrigger.get();
    }

    public double manualExtend()
    {
        return operator.getRightY();
    }

    public double manualTestExtend()
    {
        if (tester == null) {
            return 0;
        }
        return Math.abs(tester.getRightY());
    }    
}
