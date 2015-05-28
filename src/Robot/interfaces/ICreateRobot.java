package Robot.interfaces;

import java.awt.Color;

import datatype.Position;
import Robot.EcoRobots;
import Robot.EcoRobots.Robot;

public interface ICreateRobot {
	public EcoRobots.Robot.Component createStandaloneRobot(int id, Color color, Position position);
}
