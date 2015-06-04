package gui.Interface;

import java.awt.Color;

import datatype.Position;

public interface IManageGui {
	public void RobotCreateNotification(Position pos,Color color);
	public void RobotMoveNotification(Position lastPos, Position newPos,Color color,boolean hasbox);
	public void NestCreateNotification( Position pos,Color color);
	public void BoxCreateNotification( Position pos,Color color);
	public void BoxPriseNotification( Position pos,Color color);
	
}
