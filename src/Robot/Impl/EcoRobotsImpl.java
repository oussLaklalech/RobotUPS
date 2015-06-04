package Robot.Impl;

import java.awt.Color;
import java.util.ArrayList;

import Robot.EcoBoxes.Box;
import Robot.EcoRobots;
import Robot.interfaces.IBrain;
import Robot.interfaces.ICreateRobot;
import Robot.interfaces.IEye;
import Robot.interfaces.IFootHand;
import datatype.Position;

public class EcoRobotsImpl extends EcoRobots {
	
	private ArrayList<Robot.Component> listRobots = new ArrayList<Robot.Component>();

	@Override
	protected void start() {
		System.out.println("Start de ECOROBOT");
		//Robot.Component r1 = this.make_create().createStandaloneRobot(1, Color.BLACK, new Position(5, 2));
		//Robot.Component r2 = this.make_create().createStandaloneRobot(2, Color.GREEN, new Position(10, 1));
		//listRobots.add(r1);
		//listRobots.add(r2);
		
	}
	
	@Override
	protected Robot make_Robot(final int id, final Color color, final Position position) {
		return new RobotImpl(id, color, position);
	}

	@Override
	protected ICreateRobot make_create() {
		return new ICreateRobot() {
			@Override
			public Robot.Component createStandaloneRobot(int id, Color color, Position position) {
				return newRobot(id, color, position);
			}
		};
	}

}
