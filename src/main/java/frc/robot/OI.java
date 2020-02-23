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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot.RobotType;
import frc.robot.commands.ManualThroat;
import frc.robot.commands.ManualThroatZero;
import frc.robot.commands.climbtake.SetExtensionPosition;
import frc.robot.commands.drive.ResetOdometry;
import frc.robot.commands.drive.TankDrive;
import frc.robot.commands.serializer.*;
import frc.robot.commands.turret.AimTurret;
import frc.robot.commands.turret.FlywheelZero;
import frc.robot.commands.turret.ManualTurret;
import frc.robot.commands.turret.PrepareFlywheel;
import frc.robot.commands.util.Deadline;
import frc.robot.commands.util.DoneCommand;
import frc.robot.commands.util.RamseteFactory;
import frc.robot.subsystems.flywheel.Flywheel;
import frc.robot.subsystems.serializer.SerializerSpeed;

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

    public OI() {
        driver = new LogitechF310(0);
        operator = new LogitechF310(1);
        // var ramseteFactory = new RamseteFactory();
        
        // if (Robot.hardware == RobotType.chaos2020) {
        //     aim = new AimTurret();
        //     flywheel = new PrepareFlywheel();
        // }
        
        driver.leftTrigger.whileHeld(new ManualThroat());
        
        driver.leftBumper.whileHeld(new ManualThroatZero());

        

        driver.aButton.whileHeld(Deadline.createDeadline(new AimTurret(), new PrepareFlywheel()));
        
        /*

        // driver left - climbarm left
        driver.dPadLeft.whenPressed(new MoveClimbtake(-0.5));

        // driver right - climbarm right
        driver.dPadRight.whenPressed(new MoveClimbtake(0.5));

        // driver leftBump - NavX mode
        driver.leftBumper.whileHeld(new NavXTurnRobot());

        driver.selectButton.whileHeld(new ResetNavX(driver.startButton));

        */

        // driver.xButton.whileHeld(ramseteFactory.getCircleCommand());

        // TEST: Parallel Group test, delete after
        // TODO: commented out because DriveRunAfterReady removed
        //driver.xButton.whenPressed(new ParallelCommandGroup(new Wait(2000), new DriveRunAfterReady()));

        // driver.yButton.whenPressed(new DriveDistancePID(12));
        // driver.bButton.whenPressed(new TurnAnglePID(90));
        // driver.aButton.whenPressed(new DriveDistancePID(-12));
        // driver.xButton.whenPressed(new TurnAnglePID(-90));
        // driver.startButton.whileHeld(new DriveSquare(12));
        // driver.selectButton.whileHeld(new AimClimbtake());
        // driver.leftBumper.whenPressed(new TurnToTarget());
        // driver.leftTrigger.whenPressed(new SetPipeline());
        // driver.rightBumper.whileHeld(new SerializerFeed());

        /* TODO: remove this comment

        // operator up - move arm up to climb + extend
        operator.dPadUp.whenPressed(new SetClimber());

        // operator down - move arm up to climb + retract
        operator.dPadDown.whenPressed(new SetClimber());

        // operator left - move arm to intake
        operator.dPadLeft.whenPressed(new SetClimber());

        // op right - move arm to colorwheel
        operator.dPadRight.whenPressed(new SetClimber());

        // op a - forward intake
        operator.aButton.whenPressed(new SetIntake(0.5));

        // op b - reverse intake
        operator.bButton.whenPressed(new SetIntake(-0.5));

        // op y - turret to 2pt pos
        operator.yButton.whenPressed(new SetTurretTilt(75.0));

        // op leftBump - turn on auto turret (hold) "aim"
        operator.leftBumper.whileHeld(new AimTurret()); //TODO: move flywheel when aiming turret (experiment)

        // op leftTrig - shoot (override: doesn't need to be aimed)
        operator.leftTrigger.whenPressed(new Shoot(false));

        // op rightBump - shoot once (needs to be aimed)
        operator.rightBumper.whenPressed(new Shoot(true));

        // op rightTrig - shoot (hold)
        operator.rightTrigger.whileHeld(new Shoot(true));
        
        // op select - colorwheel spin to amt
        operator.selectButton.whenPressed(new MoveColorWheelAmt());

        // op start - colorwheel spin to color
        operator.startButton.whenPressed(new MoveColorWheelToColor()); 

        TODO: and this one */

        // operator.leftTrigger.whileHeld(new ManualThroat());
        
        // operator.leftBumper.whileHeld(new ManualThroatZero());

        // operator.xButton.whileHeld(new SetExtensionPosition());

        // operator.yButton.whileHeld(new SerializerFeed());

        // operator.aButton.whileHeld(new SerializerDefault());

        operator.rightTrigger.whileHeld(() -> Robot.serializer.driveTurnTable(SerializerSpeed.fast, false), Robot.serializer);

        operator.rightBumper.whileHeld(() -> Robot.throat.ejectorSpeed(true), Robot.throat);

        operator.bButton.whileHeld(() -> Robot.flywheel.setFlywheelTargetDashboard(), Robot.flywheel);

        // operator.leftBumper.whileHeld(() -> Robot.turret.setTurretAngleDashboard(), Robot.turret);

        // operator.leftBumper.whileHeld(new NavXTurnRobot());
        // operator.xButton.whenPressed(new ResetNavX());

        // SmartDashboard Buttons
        //SmartDashboard.putData("Reset Odometry", new ResetOdometry());

        // Robot.driveBase.setDefaultCommand(new TankDrive());
        // Robot.turret.setDefaultCommand(new ManualTurret());
        Robot.flywheel.setDefaultCommand(new FlywheelZero());
        Robot.serializer.setDefaultCommand(new SerializerStop()); // TODO: change to default
    }

    public double getRobotTargetAngle() {
        return driver.getRightJoystickAngle();
    }

    
    public double getSerializerTarget() {
        return driver.getRightY();
    }
    
    public double getThroatTarget() {
        return driver.getLeftY();
    }



    public double getTurretPanTarget() {
        return operator.getLeftX();
    }

    public double getTurretHoodTarget() {
        return operator.getLeftY();
    }

    public double getFlywheelTarget() {
        return operator.getRightY();
    }

    public double getExtensionTarget() {
        return operator.getRightY();
    }
    
    public double getPivotTarget() {
        return operator.getLeftY();
    }

    public boolean shouldUseRobotTargetAngle() {
        return (Math.abs(Robot.oi.driver.getRightX()) > 0.1) || (Math.abs(Robot.oi.driver.getRightY()) > 0.1);
    }

    public double getLeftSpeed() {
        return driver.getLeftY();
    }

    public double getRightSpeed() {
        return driver.getRightY();
    }

    public double getSpeedDuringNavX(){
        return driver.getLeftY();
    }


    public double getTankDriveSpeedScale() {
        if (driver.rightBumper.get()){
            return 0.25; // slow speed
        }else if (driver.rightTrigger.get()){
            return 1; // fast speed
        }else{
            return 0.5; // normal speed
        }
    }
}
