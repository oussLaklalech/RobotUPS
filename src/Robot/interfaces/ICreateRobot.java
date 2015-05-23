package Robot.interfaces;

import java.awt.Color;

import datatype.Position;
import Robot.Environnement;

public interface ICreateRobot {
	public Environnement.Robot.Component createStandaloneRobot(Color color, Position position);
}
