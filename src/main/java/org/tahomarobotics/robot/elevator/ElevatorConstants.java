package org.tahomarobotics.robot.elevator;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import edu.wpi.first.math.system.plant.DCMotor;
import org.tahomarobotics.robot.util.TalonFXConfig;

public class ElevatorConstants {
    public static final double GEAR_REDUCTION = 12d/60d * 22d/56d * 36d/36d;
    private static final double MAIN_PULLEY_CIRCUMFERENCE = 0.1764; // Meters
    public static final double SENSOR_COEFFICIENT = GEAR_REDUCTION / 2048 * MAIN_PULLEY_CIRCUMFERENCE; // Encoder units to linear meters
    private static final double REFERENCE_VOLTAGE = 12;
    private static final double VELOCITY_CURRENT_LIMIT = 80; // amps
    private static final double ACCEL_CURRENT_LIMIT = 60; // amps

    public static final double ELEVATOR_MAX = 1.7; // Meters
    public static final double ELEVATOR_MAX_VELOCITY = 1; // Meters / sec
    public static final double ELEVATOR_MAX_ACCELERATION = 2; // Meters / sec^2

    // Feed Forward
    public static final double S_VOLTS = 0; // Static Gain
    public static final double COS_VOLTS = 0; // Gravity Gain
    public static final double V_VOLT_SECOND_PER_RAD = 1d / DCMotor.getFalcon500(2).KvRadPerSecPerVolt / 59.26; // Velocity gain;
    public static final double A_VOLT_SECOND_SQUARED_PER_RAD = 0.1; // Acceleration Gain

}
