package org.tahomarobotics.robot.arm;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;

public final class ArmConstants {
    public static final double GEAR_REDUCTION = 9d/64d * 18d/74d * 18d/60d;
    public static final double SENSOR_COEFFICIENT = GEAR_REDUCTION / 2048 * (Math.PI * 2);
    public static final double ARM_MAX_VELOCITY = 1;

    //TODO: Taken from CAD, verify irl
    public static final double MIN_ANGLE = -1.6; // Radians
    public static final double MAX_ANGLE = 2; // Radians

    //Trapezoid Constraints
    public static final double MAX_VELOCITY_RAD_PER_SECOND = Units.degreesToRadians(10) * GEAR_REDUCTION;
    public static final double MAX_ACCELERATION_RAD_PER_SEC_SQUARED = Units.degreesToRadians(25) * GEAR_REDUCTION;

    //Feed Forward
    public static final double S_VOLTS = 0; // Static gain
    public static final double COS_VOLTS = 0; // Gravity gain
    public static final double V_VOLT_SECOND_PER_RAD = 1d / DCMotor.getFalcon500(1).KvRadPerSecPerVolt / 59.26; // Velocity gain
    public static final double A_VOLT_SECOND_SQUARED_PER_RAD = 0.1; // Acceleration gain
}
