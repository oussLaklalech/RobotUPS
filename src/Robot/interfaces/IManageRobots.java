package Robot.interfaces;

import java.awt.Color;

import datatype.Position;

public interface IManageRobots {
	public void RobotMoveNotification(Position lastPos, Position newPos,Color color);
}
