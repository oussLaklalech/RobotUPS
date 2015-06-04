package gui.Interface;

import java.awt.Color;

import datatype.Position;

public interface IManageCell {
	public void RobotArrive(Color color,boolean hasBox);
	public void RobotQuit();
	public void BoxArrive(Color color);
	public void BoxQuit();
	public void NestArrive(Color color);
}
