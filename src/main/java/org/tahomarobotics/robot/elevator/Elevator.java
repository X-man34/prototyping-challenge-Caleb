package org.tahomarobotics.robot.elevator;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Elevator extends SubsystemBase {
    private static final Logger logger = LoggerFactory.getLogger(Elevator.class);

    private static final Elevator INSTANCE = new Elevator();

    public static Elevator getInstance() {
        return INSTANCE;
    }

    private Elevator() {
        // TODO
    }
}
