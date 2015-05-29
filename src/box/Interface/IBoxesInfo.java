package box.Interface;

import datatype.Position;

public interface IBoxesInfo {
	// retourne l'id de la box s'il exist, sinon retourne -1
       int getBoxInPosition(Position positionBox);
}
