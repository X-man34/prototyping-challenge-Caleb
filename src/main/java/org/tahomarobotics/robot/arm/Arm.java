package org.tahomarobotics.robot.arm;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A pass-through Arm subsystem, moves from [min, max].
 */
public class Arm extends SubsystemBase {
    private static final Logger logger = LoggerFactory.getLogger(Arm.class);

    private static final Arm INSTANCE = new Arm();

    public static Arm getInstance() {
        return INSTANCE;
    }

    private Arm() {
        //TODO
    }
}
