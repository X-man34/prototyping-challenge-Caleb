/**
 * Copyright 2023 Tahoma Robotics - http://tahomarobotics.org - Bear Metal 2046 FRC Team
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */
package org.tahomarobotics.robot.chassis;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahomarobotics.robot.RobotMap;

public class MK4iSwerveModule {

    private static final Logger logger = LoggerFactory.getLogger(MK4iSwerveModule.class);

    public void displayPosition() {
        SmartDashboard.putNumber(name + " Swerve Module Position", getSteerAngle());
    }

    private final String name;
    private final TalonFX driveMotor;
    private final TalonFX steerMotor;

    private int restInteation = MK4iChassisConstants.ENCODER_RESET_ITERATIONS;
    private final PIDController pidController = new PIDController(0,0,0);

    private final SimpleMotorFeedforward simpleMotorFeedforward = new SimpleMotorFeedforward(0, MK4iChassisConstants.kV_DRIVE);
    private SwerveModuleState state = new SwerveModuleState();
    private final CANCoder steerABSEncoder;
    private double angularOffset;

    private final Translation2d positionOffset;

    public MK4iSwerveModule(String name, RobotMap.SwerveModulePorts ports, Translation2d positionOffset, double augularOffset) {
        this.name = name;
        this.positionOffset = positionOffset;
        this.angularOffset = augularOffset;

        // TODO: Assign motors and encoder properly using "ports"
        driveMotor = new TalonFX(-1);
        steerMotor = new TalonFX(-1);
        steerABSEncoder = new CANCoder(-1);
    }

    /**
     * Resets the angular offset to the one currently stored.
     */
    public void cancelCalibration() {
        // TODO
    }

    /**
     * Sets the new angular offset to negative the current absolute angle, then
     * re-enables break mode.
     * @return New Angular Offset
     */
    public double finalizeCalibration() {
        // TODO
        return 0.0;
    }

    /**
     * Applies an angular offset of 0.0 and enables coast mode.
     */
    public void initializeCalibration() {
        // TODO
    }

    /**
     * Sets the angular offset on the absolute encoder.
     * @param offset New offset of the absolute encoder in radians.
     */
    private void applyAngularOffset(double offset) {
        if (steerABSEncoder.configMagnetOffset(Units.radiansToDegrees(offset)) != ErrorCode.OK) {
            logger.error("Failed to apply angular offset to " + offset);
        }
    }

    /**
     * @return The positional offset of the module relative to the center of the chassis.
     */
    public Translation2d getPositionOffset() {
        return positionOffset;
    }

    /**
     * @return Absolute angle in radians; [0, 2PI]
     */
    public double getAbsoluteAngle() {
        double angle = steerABSEncoder.getAbsolutePosition();
        angle %= 2.0 * Math.PI;
        if (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }

        return angle;
    }

    /**
     * @return Relative angle in radians.
     */
    public double getSteerAngle() {
        return steerMotor.getSelectedSensorPosition() * MK4iChassisConstants.STEER_POSITION_COEFFICIENT;
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(driveMotor.getSelectedSensorPosition(), new Rotation2d(getSteerAngle()));
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        double steerAngle = getSteerAngle();
        //set the state depending on if we should go one way or another
        state = SwerveModuleState.optimize(desiredState, new Rotation2d(steerAngle));

        //calculate output velocityes
        final double driveOutput = pidController.calculate(getVelocity(), state.speedMetersPerSecond);
        final double driveFeedForeward = this.simpleMotorFeedforward.calculate(state.speedMetersPerSecond);

        setDriveVoltage(driveOutput + driveFeedForeward);

        setReferenceAngle(state.angle.getRadians());

    }

    private void setReferenceAngle(double referenceAngle) {

        double currentAngle = getSteerAngle();

        if (Math.abs(getSteerVelocity()) < MK4iChassisConstants.ENCODER_RESET_MAX_ANGULAR_VELOCITY) {
            if (++restInteation >= MK4iChassisConstants.ENCODER_RESET_ITERATIONS) {
                restInteation = 0;
                double absAngle = getAbsoluteAngle();
                steerMotor.setSelectedSensorPosition(absAngle);
                currentAngle = absAngle;
            }
        }else {
             restInteation = 0;
        }

        double currentAngleMod = currentAngle % (2 * Math.PI);
        if (currentAngleMod < 0) {
            currentAngleMod += 2 * Math.PI;
        }

        double adjustedReferenceAngle = referenceAngle + currentAngle - currentAngleMod;
        if (referenceAngle - currentAngleMod > Math.PI) {
            adjustedReferenceAngle -= 2.0 * Math.PI;
        } else if (referenceAngle - currentAngleMod < -Math.PI) {
            adjustedReferenceAngle += 2.0 * Math.PI;
        }

        //pidController.set
    }


    private void setDriveVoltage(double voltage) {
        driveMotor.set(TalonFXControlMode.PercentOutput, voltage / RobotController.getBatteryVoltage());
    }
    private double getVelocity() {
        return driveMotor.getSelectedSensorVelocity();
    }

    private double getSteerVelocity() {
        return steerMotor.getSelectedSensorVelocity();
    }




}
