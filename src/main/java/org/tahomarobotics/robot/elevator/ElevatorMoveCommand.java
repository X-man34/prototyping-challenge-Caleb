package org.tahomarobotics.robot.elevator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tahomarobotics.robot.motion.MotionProfile;
import org.tahomarobotics.robot.motion.MotionState;
import org.tahomarobotics.robot.motion.SCurveMotionProfile;

/**
 * Moves the elevator to a desired location with a motion profile.
 * (Decide whether to move arm and elevator separate, or move them both in this command)
 */
public class ElevatorMoveCommand extends CommandBase {
    private static final Logger logger = LoggerFactory.getLogger(ElevatorMoveCommand.class);

    public ElevatorMoveCommand() {
        // TODO
    }

    @Override
    public void initialize() {
        // TODO
    }

    @Override
    public void execute() {
        // TODO
    }

    @Override
    public boolean isFinished() {
        // TODO
        return true;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO
    }
}
