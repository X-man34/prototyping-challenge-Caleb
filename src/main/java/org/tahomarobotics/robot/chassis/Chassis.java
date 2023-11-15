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

import com.ctre.phoenix.sensors.Pigeon2;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahomarobotics.robot.RobotMap;
import org.tahomarobotics.robot.util.CalibrationData;

import java.util.List;

public class Chassis extends SubsystemBase {

    private static final Logger logger = LoggerFactory.getLogger(Chassis.class);
    private static Chassis INSTANCE = new Chassis();

    private boolean isFieldOriented = true;

    private final List<MK4iSwerveModule> swerveModules;


    public static Chassis getInstance() {
        return INSTANCE;
    }

    private final CalibrationData<Double[]> swerveCalibration;

    private final Pigeon2 pigeon2 = new Pigeon2(RobotMap.PIGEON);

    private final SwerveDriveKinematics swerveDriveKinematics;

    private Chassis() {
        // Read the calibration data.
        swerveCalibration = new CalibrationData<>("SwerveCalibration", new Double[]{0d, 0d, 0d, 0d});

        // TODO
        swerveModules = MK4iChassisConstants.createSwerveModules(swerveCalibration.get());

        swerveDriveKinematics = new SwerveDriveKinematics(
                swerveModules.stream()
                        .map(MK4iSwerveModule::getPositionOffset)
                        .toArray(Translation2d[]::new)
        );



    }

    public Chassis initialize() {
        SmartDashboard.putData("Align Swerves", new AlignSwerveCommand());
        zeroGyro();
        Rotation2d gyro = getGyroRotation();
        var modules = getSwerveModulesPositions();

        return this;
    }

    /**
     * Finalizes calibration for all modules, then updates the swerve calibration.
     */
    public void finalizeCalibration() {
        swerveCalibration.set(swerveModules.stream()
                .map(MK4iSwerveModule::finalizeCalibration)
                .toArray(Double[]::new)
        );
    }

    /**
     * Zeroes all offsets, so they may be re-calibrated.
     */
    public void initializeCalibration() {
        swerveModules.forEach(MK4iSwerveModule::initializeCalibration);
    }

    /**
     * Reverts offsets to the previous.
     */
    public void cancelCalibration() {
        swerveModules.forEach(MK4iSwerveModule::cancelCalibration);
    }

    /**
     * Updates the angles of swerve modules on SmartDashboard.
     */
    public void displayAbsolutePositions() {
        swerveModules.forEach(MK4iSwerveModule::displayPosition);
    }

    public void drive(ChassisSpeeds velocityInput) {
        if (!isFieldOriented && (DriverStation.getAlliance() == DriverStation.Alliance.Red)) {
            velocityInput = new ChassisSpeeds(-velocityInput.vxMetersPerSecond, -velocityInput.vyMetersPerSecond, velocityInput.omegaRadiansPerSecond);

        }
        drive(velocityInput, isFieldOriented);
    }

    private void drive(ChassisSpeeds input, boolean isFieldRelative) {
        if (isFieldRelative) {
            input = ChassisSpeeds.fromFieldRelativeSpeeds(input, Rotation2d.fromDegrees(pigeon2.getYaw()));
        }

        final double dT = .02;

// Correct for rotation while moving. Code taken from 254.
        // https://www.chiefdelphi.com/t/whitepaper-swerve-drive-skew-and-second-order-kinematics/416964/5
        Pose2d velocityPose = new Pose2d(
                input.vxMetersPerSecond * dT,
                input.vyMetersPerSecond * dT,
                Rotation2d.fromRadians(input.omegaRadiansPerSecond * dT)
        );

        Twist2d velocityTwist = new Pose2d().log(velocityPose);
        ChassisSpeeds correctedVelocities = new ChassisSpeeds(
                velocityTwist.dx / dT,
                velocityTwist.dy / dT,
                velocityTwist.dtheta / dT
        );

        var swerveModuleStates = swerveDriveKinematics.toSwerveModuleStates(correctedVelocities);
        SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates,MK4iChassisConstants.MAX_VELOCITY_MPS);
        setSwerveStates(swerveModuleStates);
    }

    private void setSwerveStates(SwerveModuleState[] swerveModuleStates) {
        for (int i = 0; i < swerveModuleStates.length; i++) {
swerveModules.get(i).setDesiredState(swerveModuleStates[i]);
        }
    }


    private void zeroGyro() {
        pigeon2.setYaw(0);
    }

    private Rotation2d getGyroRotation() {
        return Rotation2d.fromDegrees(pigeon2.getYaw());
    }

    private SwerveModulePosition[] getSwerveModulesPositions() {
        return swerveModules.stream().map(MK4iSwerveModule::getPosition).toArray(SwerveModulePosition[]::new);
    }

}