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
package org.tahomarobotics.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class OI {
    private static final Logger logger = LoggerFactory.getLogger(OI.class);
    private static final OI INSTANCE = new OI();
    private static final double ROTATIONAL_SENSITIVITY = 2;
    private static final double FORWARD_SENSITIVITY = 1.1;
    private static final double ELEVATOR_SENSITIVITY = 2.5;
    private static final double DEAD_ZONE = 0.09;


    private OI() {

    }

    public static OI getInstance() {
        return INSTANCE;
    }

    private static double deadband(double value) {
        if (Math.abs(value) > OI.DEAD_ZONE) {
            if (value > 0.0) {
                return (value - OI.DEAD_ZONE) / (1.0 - OI.DEAD_ZONE);
            } else {
                return (value + OI.DEAD_ZONE) / (1.0 - OI.DEAD_ZONE);
            }
        } else {
            return 0.0;
        }
    }

    /**
     * Reduces the sensitivity around the zero point to make the Robot more
     * controllable.
     *
     * @param value - raw input
     * @param power - 1.0 indicates linear (full sensitivity) - larger number
     *              reduces small values
     * @return 0 to +/- 100%
     */
    private double desensitizePowerBased(String name, double value, double power) {
        value = deadband(value);
        value *= Math.pow(Math.abs(value), power - 1);
        return value;
    }

}
