package org.tahomarobotics.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

public class TalonFXConfig {
    public TalonFXConfiguration config = new TalonFXConfiguration();
    public boolean enableVoltageCompensation = false;
    public boolean sensorPhase = false;
    public NeutralMode neutralMode = NeutralMode.Brake;
    public TalonFXInvertType motorInverted = TalonFXInvertType.Clockwise;

}
