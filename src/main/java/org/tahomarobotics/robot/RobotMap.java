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
package org.tahomarobotics.robot;

/**
 * Contains Port Mappings for the Robot.
 */
public final class RobotMap {
    public static final int PIGEON = 0;
    public record SwerveModulePorts(int drive, int steer, int encoder) {}

    // Swerve Modules
    public final static SwerveModulePorts FRONT_LEFT_MOD = new SwerveModulePorts(1, 11, 21);
    public final static SwerveModulePorts FRONT_RIGHT_MOD = new SwerveModulePorts(2, 12, 22);
    public final static SwerveModulePorts BACK_LEFT_MOD = new SwerveModulePorts(3, 13, 23);
    public final static SwerveModulePorts BACK_RIGHT_MOD = new SwerveModulePorts(4, 14, 24);

    // Elevator
    public static final int ELEVATOR_LEFT_MOTOR = 5;
    public static final int ELEVATOR_RIGHT_MOTOR = 6;
    public static final int ARM_MOTOR = 7;

    // Mechanism
    public static final int MECHANISM_MOTOR_ONE = 8;
    public static final int MECHANISM_MOTOR_TWO = 9;
}