import java.awt.Color;

import datatype.Position;
import Robot.EcoRobots;
import Robot.EcoRobots.Robot;
import Robot.Impl.EnvironnementImpl;


public class testClass {

	public static void main(String[] args) {
		EcoRobots.Component environnement = new EnvironnementImpl().newComponent();
			Robot r1 = environnement.create().createStandaloneRobot(Color.BLACK, new Position(0, 1));
			Robot r2 = environnement.create().createStandaloneRobot(Color.RED, new Position(8, 1));
			
			r1._newComponent(null, true).decider().fakir();
			//r1.decider().fakir();
			//r2.decider().fakir();
	}

}
