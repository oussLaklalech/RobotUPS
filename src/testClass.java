import java.awt.Color;

import datatype.Position;
import environnement.Impl.EnvironnementImpl;
import Robot.EcoRobots;
import Robot.Environnement;
import Robot.EcoRobots.Robot;
import Robot.Impl.EcoRobotsImpl;


public class testClass {

	public static void main(String[] args) {
		Environnement.Component systeme = new EnvironnementImpl().newComponent();
		systeme.init().initApp();
	}

}
