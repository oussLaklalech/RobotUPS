import java.awt.Color;

import datatype.Position;
import Robot.Environnement;
import Robot.Environnement.Robot;
import Robot.Impl.EnvironnementImpl;


public class testClass {

	public static void main(String[] args) {
			Environnement.Component environnement = new EnvironnementImpl().newComponent();
			Robot.Component r1 = environnement.create().createStandaloneRobot(Color.BLACK, new Position(0, 1));
			Robot.Component r2 = environnement.create().createStandaloneRobot(Color.RED, new Position(8, 1));
			
			r1.decider().fakir();
			r2.decider().fakir();
	}

}
