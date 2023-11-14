package org.tahomarobotics.robot.mechanism;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A generic mechanism subsystem to provide generic functionality
 * to a mechanism with 2 motors. Rev and or CTRE motors
 */
public class Mechanism extends SubsystemBase {
    private static final Logger logger = LoggerFactory.getLogger(Mechanism.class);

    private static final Mechanism INSTANCE = new Mechanism();

    public static Mechanism getInstance() {
        return INSTANCE;
    }
}
