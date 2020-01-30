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

import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.AutoBuilder;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {

    boolean cameraPipelineSet = false;
    public static Camera camera;
    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();
    // ColorSensor detectedColor;
    AutoBuilder autoBuilder;

    public enum RobotType {
        raft, chaos2019, chaos2020, simulator
    };

    public static RobotType hardware;

    public static OI oi;
    public static DriveBase driveBase;
    public static TurnTable turnTable;
    public static ClimbTake19 climbTake;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {

        String macAddress = getMACAddress();

        if (macAddress.equals(RobotConstantsRaft.MAC_ADDRESS)){
            hardware = RobotType.raft;
        }else if (macAddress.equals(RobotConstants2019.MAC_ADDRESS)){
            hardware = RobotType.chaos2019;
        }else if (macAddress.equals(RobotConstants2020.MAC_ADDRESS)){
            hardware = RobotType.chaos2020;
        }else if (macAddress.equals(RobotConstantsSim.MAC_ADDRESS)){
            hardware = RobotType.simulator;
        }else{
            hardware = RobotType.chaos2020;
        }

        System.out.println("RobotType = " + hardware);

        camera = new Camera(34, 47, 2);
        // detectedColor = new ColorSensor();
        // detectedColor.addColorMatch();

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveBase = new DriveBase(hardware);
        // turnTable = new TurnTable(hardware);

        if (hardware == RobotType.chaos2019) {
            climbTake = new ClimbTake19();
        }

        // OI must be constructed after subsystems. If the OI creates Commands
        // (which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        HAL.report(tResourceType.kResourceType_Framework, tInstances.kFramework_RobotBuilder);

        // Add commands to Autonomous Sendable Chooser
        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Auto mode", chooser);
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
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            // autonomousCommand.start();
        }
        autoBuilder.init();
        autoBuilder.getAutoCommand().start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
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
        Scheduler.getInstance().run();
        driveBase.reportPosition();

    }

    @Override
    public void robotPeriodic() {
        camera.updateDashboard();
        camera.getDistance();

        if (!cameraPipelineSet) {
            camera.setPipeline(9);
            if (camera.getPipeline() == 9) {
                cameraPipelineSet = true;
            }
        }
        // System.out.println(camera.getPipeline());

        // detectedColor.updateColorDashboard();
    }
}
