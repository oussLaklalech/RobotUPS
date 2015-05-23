package Environnement.Impl;

import environnement.Environnement;
import grid.Grid;
import grid.Impl.GridImpl;
import gridgui.GridGui;
import gui.Impl.GridGuiImpl;



public class EnvironnementImpl extends Environnement{

	@Override
	protected Grid make_grid() {
		
		return new GridImpl();
	}

	@Override
	protected GridGui make_gui() {
		// TODO Auto-generated method stub
		return new GridGuiImpl();
	}

}
