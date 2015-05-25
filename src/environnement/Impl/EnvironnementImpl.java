package environnement.Impl;

import java.awt.Color;
import java.util.ArrayList;

import datatype.Position;
import environnement.Interface.IPlay;
import Robot.EcoRobots;
import Robot.Environnement;
import Robot.EcoRobots.Robot;
import Robot.Impl.EcoRobotsImpl;
import Robot.interfaces.IInfo;

public class EnvironnementImpl extends Environnement{
	
	private ArrayList<Robot> listRobots = new ArrayList<>();

	@Override
	protected IInfo make_informationGive() {
		// TODO Auto-generated method stub
		return new IInfo() {
			
			@Override
			public Position getMyPosition(int idRobot) {
				// TODO Auto-generated method stub
				System.out.println("GET MY POSITION");
				return null;
			}
		};
	}

	@Override
	protected EcoRobots make_robots() {
		// TODO Auto-generated method stub
		return new EcoRobotsImpl();
	}

	@Override
	protected IPlay make_init() {
		// TODO Auto-generated method stub
		return new IPlay() {
			
			@Override
			public void initApp() {
				// TODO Auto-generated method stub
				System.out.println("Init Application");
				EcoRobots.Component ecoRobot = new EcoRobotsImpl()._newComponent(null, true);
				Robot r1 = ecoRobot.create().createStandaloneRobot(1, Color.BLACK, new Position(0, 1));
				Robot r2 = ecoRobot.create().createStandaloneRobot(2, Color.RED, new Position(8, 1));
				
				listRobots.add(r1);
				listRobots.add(r2);
				
				r1._newComponent(null, true).decider().fakir();
				r2._newComponent(null, true).decider().fakir();
				
				//r2._newComponent(null, true).
				//System.out.println(listRobots.get(0).getInfoRobot().getPosition());
			}
		};
	}

}
