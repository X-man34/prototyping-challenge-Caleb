/**
 * Copyright 2023 Tahoma Robotics - http://tahomarobotics.org - Bear Metal 2046 FRC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */
package org.tahomarobotics.robot.chassis;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.tahomarobotics.robot.util.SwerveRateLimiter;

import java.util.function.DoubleSupplier;

/**
 * Drives the chassis based on two-joystick input from the driver.
 */
public class TeleopDriveCommand extends CommandBase {

    private static  final Chassis chassis  = Chassis.getInstance();

    private final DoubleSupplier xSup, ySup, rotSup;

    private final double maxVelocity;

    private final double maxRotation;

    private ChassisSpeeds velocityInput = new ChassisSpeeds();

    private SwerveRateLimiter rateLimiter;


    public TeleopDriveCommand(DoubleSupplier xSupplier,DoubleSupplier ySupplier,DoubleSupplier rotaitonSupplier) {
         xSup = xSupplier;
         ySup = ySupplier;
         rotSup = rotaitonSupplier;

         MK4iChassisConstants constant
         maxVelocity = MK4iChassisConstants.MAX_VELOCITY_MPS;
         maxRotation = MK4iChassisConstants.MAX_ANGULAR_VELOCITY_RPS;

         addRequirements(chassis);
         rateLimiter = new SwerveRateLimiter(MK4iChassisConstants.TRANSLATION_LIMIT, MK4iChassisConstants.ROTATION_LIMIT);

    }

    @Override
    public void execute() {
        double direction = (DriverStation.getAlliance() == DriverStation.Alliance.Blue)?(1):(-1);
        velocityInput.vxMetersPerSecond = xSup.getAsDouble() * maxVelocity * direction;
        velocityInput.vyMetersPerSecond = ySup.getAsDouble() * maxVelocity * direction;
        velocityInput.omegaRadiansPerSecond = rotSup.getAsDouble() * maxRotation;

        ChassisSpeeds velcityOutput = rateLimiter.calculate(velocityInput);

        chassis.drive(velcityOutput);
    }

    @Override
    public void end(boolean interrupted) {
        chassis.drive(new ChassisSpeeds());
    }
}
