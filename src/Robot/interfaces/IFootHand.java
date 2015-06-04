package Robot.interfaces;

import Robot.EcoBoxes.Box;

public interface IFootHand {
	public void turnLeft();
	public void turnRight();
	public void goStraight();
	public void goBack();
	public void raiseBox(Box.Component b);
	public void depositBox();
	public void moveRandomly();
}
