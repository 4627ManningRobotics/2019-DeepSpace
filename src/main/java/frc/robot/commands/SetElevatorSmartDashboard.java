package frc.robot.commands;

import edu.wpi.first.wpilibj.command.StartCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetElevatorSmartDashboard extends StartCommand {

    public SetElevatorSmartDashboard(){
        super(new SetElevator(SmartDashboard.getNumber("set point" , 10)));
    }

}