package Nest.Interface;
import java.awt.Color;

import Robot.EcoNest.Nest;
import datatype.Position;

public interface ICreateNest {
	public Nest.Component createStandaloneNest(int id, Color color, Position position);
	
}
