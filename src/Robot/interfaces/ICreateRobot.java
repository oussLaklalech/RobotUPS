package Robot.interfaces;

import java.awt.Color;

import datatype.Position;
import Robot.EcoRobots.Robot;
import Robot.EcoRobots;

public interface ICreateRobot {
	public Robot createStandaloneRobot(Color color, Position position);
}
