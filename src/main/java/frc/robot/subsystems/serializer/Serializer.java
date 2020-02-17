/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems.serializer;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.serializer.*;
import frc.robot.Robot;
import frc.robot.RobotConstants2020;

/**
 * Add your docs here.
 */
public class Serializer extends SubsystemBase implements ISerializer{

  private CANSparkMax turnTable;
  private CANSparkMax ejector;
  private Robot.RobotType type;

  public enum Speed {
    fast, slow
  }

  private CANPIDController m_pidController;
  private CANEncoder m_encoder;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM;
  private double setPoint;
  private final double FAST_SPEED = 1500;
  private final double SLOW_SPEED = 500;

  public Serializer(Robot.RobotType robotType) {
    type = robotType;

    if (type == Robot.RobotType.raft) {
      return ;
      // turnTable = new CANSparkMax(RobotConstantsRaft.TURN_TABLE_SPARKMAX, MotorType.kBrushless);
    }
    if (type == Robot.RobotType.chaos2019) {
      // turnTable = new CANSparkMax(RobotConstants2019.TURN_TABLE_SPARKMAX,
      // CANSparkMax.MotorType.kBrushless);
      return;
    }
    if (type == Robot.RobotType.chaos2020) {
      turnTable = new CANSparkMax(RobotConstants2020.TURN_TABLE_SPARKMAX, CANSparkMax.MotorType.kBrushless);
      turnTable.setInverted(true);
      m_encoder = turnTable.getEncoder();
      m_encoder.setInverted(true);
      ejector = new CANSparkMax(RobotConstants2020.EJECTER_SPARKMAX, MotorType.kBrushless);
      ejector.setInverted(true);
    }
    /**
     * The RestoreFactoryDefaults method can be used to reset the configuration
     * parameters in the SPARK MAX to their factory default state. If no argument is
     * passed, these parameters will not persist between power cycles
     */
    turnTable.restoreFactoryDefaults();

    /**
     * In order to use PID functionality for a controller, a CANPIDController object
     * is constructed by calling the getPIDController() method on an existing
     * CANSparkMax object
     */
    m_pidController = turnTable.getPIDController();
    // Encoder object created to display position values

    // PID coefficients
    kP = 0.00005;
    kI = 0.000001;
    kD = 0;
    kIz = 0;
    kFF = 0;
    kMaxOutput = 0.25;
    kMinOutput = -0.25;
    maxRPM = 1500;

    // set PID coefficients
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);
    m_pidController.setIZone(kIz);
    m_pidController.setFF(kFF);
    m_pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain Serializer", kP);
    SmartDashboard.putNumber("I Gain Serializer", kI);
    SmartDashboard.putNumber("D Gain Serializer", kD);
    SmartDashboard.putNumber("I Zone Serializer", kIz);
    SmartDashboard.putNumber("Feed Forward Serializer", kFF);
    SmartDashboard.putNumber("Max Output Serializer", kMaxOutput);
    SmartDashboard.putNumber("Min Output Serializer", kMinOutput);
  }

  @Override
  public void periodic() {
  }

  private double turnTableDegrees(CANSparkMax input) {
    double gearRatio = (double) 10 / 1; // ratio of the axel the turntable lies on to the axel the encoder reads
    int ticksPerRev = 42; // amount of ticks in one revolution of the encoder axel
    double counts = input.getEncoder().getPosition();
    return (counts * gearRatio) / ticksPerRev;
  }

  public void driveTurnTable(SerializerSpeed speed, boolean ejectorOn) {
    if (type != Robot.RobotType.chaos2020) {
      return;
    }
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain Serializer", 0);
    double i = SmartDashboard.getNumber("I Gain Serializer", 0);
    double d = SmartDashboard.getNumber("D Gain Serializer", 0);
    double iz = SmartDashboard.getNumber("I Zone Serializer", 0);
    double ff = SmartDashboard.getNumber("Feed Forward Serializer", 0);
    double max = SmartDashboard.getNumber("Max Output Serializer", 0);
    double min = SmartDashboard.getNumber("Min Output Serializer", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to
    // controller
    if ((p != kP)) {
      m_pidController.setP(p);
      kP = p;
    }
    if ((i != kI)) {
      m_pidController.setI(i);
      kI = i;
    }
    if ((d != kD)) {
      m_pidController.setD(d);
      kD = d;
    }
    if ((iz != kIz)) {
      m_pidController.setIZone(iz);
      kIz = iz;
    }
    if ((ff != kFF)) {
      m_pidController.setFF(ff);
      kFF = ff;
    }
    if ((max != kMaxOutput) || (min != kMinOutput)) {
      m_pidController.setOutputRange(min, max);
      kMinOutput = min;
      kMaxOutput = max;
    }

    if (speed == SerializerSpeed.fast) {
      setPoint = FAST_SPEED;
    }else {
      setPoint = SLOW_SPEED;
    }

    if (ejectorOn){
      ejector.set(1);
    }else {
      ejector.set(0);
    }


    /**
     * PIDController objects are commanded to a set point using the SetReference()
     * method.
     * 
     * The first parameter is the value of the set point, whose units vary depending
     * on the control type set in the second parameter.
     * 
     * The second parameter is the control type can be set to one of four
     * parameters: com.revrobotics.ControlType.kDutyCycle
     * com.revrobotics.ControlType.kPosition com.revrobotics.ControlType.kVelocity
     * com.revrobotics.ControlType.kVoltage
     */
    m_pidController.setReference(setPoint, ControlType.kVelocity);

    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("ProcessVariable", m_encoder.getVelocity());
  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
}