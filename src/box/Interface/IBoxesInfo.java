package box.Interface;

import Robot.EcoBoxes.Box;
import datatype.Position;

public interface IBoxesInfo {
       Box.Component getBoxInPosition(Position positionBox);
       int nombreBox();
}
