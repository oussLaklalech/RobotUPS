package box.Interface;
import java.awt.Color;

import Robot.EcoBoxes.Box;
import datatype.Position;


public interface ICreateBox {
	public Box.Component createStandaloneBox(Color color, Position position);
	public boolean createBoxes(int nbreBoxToCreate);
}
