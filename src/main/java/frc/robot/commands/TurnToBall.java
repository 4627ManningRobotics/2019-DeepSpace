package frc.robot.commands;

import edu.wpi.first.wpilibj.command.StartCommand;
import frc.robot.subsystems.Sensors;

public class TurnToBall extends StartCommand {

    public TurnToBall(){
        super(new TurnToAngle(Sensors.ballReqester.getAngle()));
    }

}