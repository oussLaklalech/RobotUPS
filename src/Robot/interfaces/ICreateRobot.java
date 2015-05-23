package Robot.interfaces;

import java.awt.Color;

import datatype.Position;
import Robot.EcoRobots.Robot;

public interface ICreateRobot {
	public Robot createStandaloneRobot(Color color, Position position);
}
