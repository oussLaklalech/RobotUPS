package environnement.Impl;

import java.awt.Color;
import java.util.ArrayList;

import box.Impl.EcoBoxesImpl;
import datatype.Position;
import environnement.Interface.IPlay;
import Robot.EcoBoxes;
import Robot.EcoRobots;
import Robot.Environnement;
import Robot.EcoRobots.Robot;
import Robot.Impl.EcoRobotsImpl;
import Robot.interfaces.IInfo;

public class EnvironnementImpl extends Environnement{	

	@Override
	protected IInfo make_informationGive() {
		// TODO Auto-generated method stub
		return new IInfo() {
			
			@Override
			public Position getMyPosition(int idRobot) {
				// TODO Auto-generated method stub
				System.out.println("GET POSITION OF ROBOT : "+idRobot);
				return null;
			}
		};
	}
	
	@Override
	protected EcoRobots make_robots() {
		return new EcoRobotsImpl();
	}

	@Override
	protected EcoBoxes make_boxes() {
		// TODO Auto-generated method stub
		return new EcoBoxesImpl();
	}
}
