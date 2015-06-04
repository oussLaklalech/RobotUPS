package environnement.Impl;

import gui.GridGuiImpl;
import gui.Interface.IManageGui;
import Nest.Impl.EcoNestImpl;
import Robot.EcoBoxes;
import Robot.EcoNest;
import Robot.EcoRobots;
import Robot.Environnement;
import Robot.GridGui;
import Robot.GridGui.CellGui;
import Robot.Impl.EcoRobotsImpl;
import Robot.interfaces.IInfo;
import box.Impl.EcoBoxesImpl;
import datatype.Position;

public class EnvironnementImpl extends Environnement{	
	
	@Override
	protected EcoRobots make_robots() {
		return new EcoRobotsImpl();
	}

	@Override
	protected EcoBoxes make_boxes() {
		// TODO Auto-generated method stub
		return new EcoBoxesImpl();
	}

	@Override
	protected GridGui make_gridGui() {
		// TODO Auto-generated method stub
		return new GridGuiImpl();
	}

	@Override
	protected EcoNest make_nests() {
		return new EcoNestImpl();
	}
}
