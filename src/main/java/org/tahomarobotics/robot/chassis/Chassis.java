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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahomarobotics.robot.util.CalibrationData;

public class Chassis extends SubsystemBase {

    private static final Logger logger = LoggerFactory.getLogger(Chassis.class);
    private static Chassis INSTANCE = new Chassis();

    public static Chassis getInstance() {
        return INSTANCE;
    }

    private final CalibrationData<Double[]> swerveCalibration;

    private Chassis() {
        // Read the calibration data.
        swerveCalibration = new CalibrationData<>("SwerveCalibration", new Double[]{0d, 0d, 0d, 0d});

        // TODO
    }

    public Chassis initialize() {
        SmartDashboard.putData("Align Swerves", new AlignSwerveCommand());

        return this;
    }

    /**
     * Finalizes calibration for all modules, then updates the swerve calibration.
     */
    public void finalizeCalibration() {
        // TODO
    }

    /**
     * Zeroes all offsets, so they may be re-calibrated.
     */
    public void initializeCalibration() {
        // TODO
    }

    /**
     * Reverts offsets to the previous.
     */
    public void cancelCalibration() {
        // TODO
    }

    /**
     * Updates the angles of swerve modules on SmartDashboard.
     */
    public void displayAbsolutePositions() {
        // TODO
    }
}