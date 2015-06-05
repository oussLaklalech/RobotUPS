package Robot.interfaces;

import Robot.EcoRobots.Robot;

public interface IPlay {
	void resume();
	void pause();
	void init(Robot.Component r);
}
