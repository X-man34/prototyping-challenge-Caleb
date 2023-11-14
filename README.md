# Elevator Chassis Template (Returning Member)
Provided are partial skeletons for the subsystems:
 - Chassis
 - Elevator
 - Arm (Optional)
 - Mechanism (Optional)

Complete the implementation of each subsystem based on javadoc comments
and prior experience with Swerve, Elevator, etc.

**Documentation References:**\
[CTRE Phoenix 5](https://v5.docs.ctr-electronics.com/en/stable/)\
[WPILib](https://docs.wpilib.org/en/stable)\
[SDS Serve Library](https://github.com/SwerveDriveSpecialties/Do-not-use-swerve-lib-2022-unmaintained/tree/develop/src/main/java/com/swervedrivespecialties/swervelib)\
[REVLib:](https://codedocs.revrobotics.com/java/com/revrobotics/package-summary.html) For Mechanism Motors


### [Controls](./ControllerMap.md)

## Subsystem Requirements
### Chassis
Chassis must be able to:
- Drive in response to user input.

### Elevator
Elevator must be able to:
- Manual control with by manipulator.
- Go to set scoring positions (takes precedence over manual control).
- Zero the encoder at the bottom using an effective Zero Command

### Arm
Arm must be able to:
- Articulate from [0, 3.6] radians in response to user input as well as scoring positions.
- Zero encoder at hard-stop using an effective Zero Command (Assume front hard-stop is 0)

### Mechanism
Mechansim must be able to:
- Drive two Spark Maxes separately.
- Have toggleable pneumatics
