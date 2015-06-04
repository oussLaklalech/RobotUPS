package Nest.Interface;

import java.awt.Color;
import java.util.ArrayList;

import Robot.EcoNest.Nest;

public interface INestsInfo {
		ArrayList<Nest.Component> getAllNests();
		Nest.Component getNestWithColor(Color c);
}
