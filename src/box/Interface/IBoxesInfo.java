package box.Interface;

import Robot.EcoBoxes.Box;
import datatype.Position;

public interface IBoxesInfo {
	// retourne l'id de la box s'il exist, sinon retourne -1
       Box.Component getBoxInPosition(Position positionBox);
}
