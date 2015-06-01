package environnement.Impl;

import Nest.Impl.EcoNestImpl;
import Robot.EcoBoxes;
import Robot.EcoNest;
import Robot.EcoRobots;
import Robot.Environnement;
import Robot.Impl.EcoRobotsImpl;
import Robot.interfaces.IInfo;
import box.Impl.EcoBoxesImpl;
import datatype.Position;

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
		return new EcoBoxesImpl();
	}

	@Override
	protected EcoNest make_nests() {
		return new EcoNestImpl();
	}
}
