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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.auto.AutoBuilder;
import frc.robot.auto.commands.NullCommand;
import frc.robot.commands.drive.DriveSquare;
import frc.robot.subsystems.drivebase.*;
import frc.robot.subsystems.camera.*;
import frc.robot.subsystems.climbtake.*;
import frc.robot.subsystems.colorsensor.*;
import frc.robot.subsystems.flywheel.*;
import frc.robot.subsystems.intake.*;
import frc.robot.subsystems.navx.*;
import frc.robot.subsystems.serializer.*;
import frc.robot.subsystems.throat.*;
import frc.robot.subsystems.turret.*;
import frc.robot.subsystems.unjammer.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    boolean cameraPipelineSet = false;
    public static ICamera camera;
    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();
    AutoBuilder autoBuilder;

    private double trackWidth;

    public enum RobotType {
        raft, chaos2019, chaos2020, simulator
    };

    public static INavX navx;

    public static RobotType hardware;

    public static OI oi;
    public static DriveBase driveBase;
    public static IClimbTake2019 climbTake19;
    public static IClimbTake2020 climbTake;
    public static IFlywheel flywheel;
    public static ITurret turret;
    public static IColorSensor colorSensor;
    public static ISerializer serializer;
    public static IThroat throat;
    public static IUnjammer unjammer;
    public static IIntake intake;

    public static FlywheelTable flywheelTable;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        String macAddress = getMACAddress();

        if (macAddress.equals(RobotConstantsRaft.MAC_ADDRESS)) {
            hardware = RobotType.raft;
        } else if (macAddress.equals(RobotConstants2019.MAC_ADDRESS)) {
            hardware = RobotType.chaos2019;
        } else if (macAddress.equals(RobotConstants2020.MAC_ADDRESS)) {
            hardware = RobotType.chaos2020;
        } else if (macAddress.equals(RobotConstantsSim.MAC_ADDRESS)) {
            hardware = RobotType.simulator;
        } else {
            hardware = RobotType.chaos2020;
        }

        // hardware = RobotType.chaos2020;

        System.out.println("RobotType = " + hardware);

        // detectedColor.addColorMatch();
        autoBuilder = new AutoBuilder();

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        if (hardware == RobotType.chaos2020) {
            turret = new Turret();
            climbTake = new ClimbTake2020();
            climbTake19 = new DummyClimbTake();
            flywheel = new Flywheel();
            navx = new DummyNavX(); // testing
            colorSensor = new DummyColorSensor();
            camera = new Camera(34, 47, 2);
            serializer = new Serializer();
            driveBase = new DriveBase2020();
            throat = new Throat();
            unjammer = new Unjammer();
            intake = new Intake();
        }

        if (hardware == RobotType.chaos2019) {
            turret = new DummyTurret();
            climbTake19 = new ClimbTake2019();
            climbTake = new DummyClimbTake();
            flywheel = new DummyFlywheel();
            navx = new DummyNavX();
            colorSensor = new DummyColorSensor();
            camera = new Camera(34, 47, 2);
            serializer = new DummySerializer();
            driveBase = new DriveBase2019();
            throat = new DummyThroat();
            unjammer = new DummyUnjammer();
            intake = new DummyIntake();

        }

        if (hardware == RobotType.raft) {
            trackWidth = 26;
            flywheel = new DummyFlywheel();
            turret = new DummyTurret();
            navx = new DummyNavX();
            climbTake = new DummyClimbTake();
            climbTake19 = new DummyClimbTake();
            colorSensor = new DummyColorSensor();
            camera = new DummyCamera();
            serializer = new DummySerializer();
            driveBase = new DriveBaseRaft();
            throat = new DummyThroat();
            unjammer = new DummyUnjammer();
            intake = new DummyIntake();

        }

        flywheelTable = new FlywheelTable();

        new DifferentialDriveKinematics(Units.inchesToMeters(trackWidth));

        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);

    }

    /**
     * This function is called when the disabled button is hit. You can use it to
     * reset subsystems before shutting down.
     */
    @Override
    public void disabledInit() {
    }

    public String getMACAddress() {
        String address = "";
        InetAddress ip;
        try {

            NetworkInterface network = NetworkInterface.getByName("eth0");

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());

            address = sb.toString();

        } catch (SocketException e) {

            e.printStackTrace();

        }
        return address;
    }

    @Override
    public void disabledPeriodic() {
        CommandScheduler.getInstance().run();
        turret.smartDashboardConstants();
    }

    @Override
    public void autonomousInit() {
        autoBuilder.init();
        autoBuilder.getAutoCommand().schedule();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null)
            autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        CommandScheduler.getInstance().run();
//        driveBase.reportPosition();

    }

    @Override
    public void robotPeriodic() {
        camera.updateDashboard();
        camera.getDistance();
        navx.updateNavDashboard();
        turret.addTurretSmartDashboard();
        turret.smartDashboardConstants();

        if (!cameraPipelineSet) {
            camera.setPipeline(9);
            if (camera.getPipeline() == 9) {
                cameraPipelineSet = true;
            }
        }
        // System.out.println(camera.getPipeline());

        // detectedColor.updateColorDashboard();
        SmartDashboard.putNumber("Battery (v)", RobotController.getBatteryVoltage());
    }
}
