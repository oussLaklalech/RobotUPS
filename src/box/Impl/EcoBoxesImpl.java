package box.Impl;

import java.awt.Color;
import java.util.ArrayList;

import Robot.EcoBoxes;
import box.Interface.IBoxesInfo;
import box.Interface.ICreateBox;
import datatype.Position;

public class EcoBoxesImpl extends EcoBoxes{

	private ArrayList<Box.Component> listBoxes = new ArrayList<Box.Component>();

	@Override
	protected void start() {
		System.out.println("Start de ECOROBOT");
		Box.Component b1 = this.make_create().createStandaloneBox(1, Color.BLUE, new Position(5, 2));
		Box.Component b2 = this.make_create().createStandaloneBox(2, Color.GREEN, new Position(0, 1));
		listBoxes.add(b1);
		listBoxes.add(b2);
	}
	
	@Override
	protected Box make_Box(final int id, final Color color, final Position position) {
		return new Box(){
			private Color myColor;
			private Position myPosition;
			private int myId;
			
			@Override
			protected void start() {
				System.out.println("Init Box num : "+id);
				System.out.println("Ma couleur est : "+color.toString());
				System.out.println("Ma position initiale est : X = "+position.getPosX()+" et Y = "+position.getPosY());
				myColor = color;
				myPosition = position;
				myId = id;
			}
		};
		
	}
	@Override
	protected ICreateBox make_create() {
		// TODO Auto-generated method stub
		return new ICreateBox() {
			
			@Override
			public Box.Component createStandaloneBox(int id, Color color, Position position) {
				System.out.println("*****************BOITE N = "+id+" CREE ****************");
				return newBox(id, color, position);
			}
		};
	}

	@Override
	protected IBoxesInfo make_informationAboutBoxesGive() {
		// TODO Auto-generated method stub
		return new IBoxesInfo() {
			
			@Override
			public Position getBoxPosition(int idBox) {
				// TODO Auto-generated method stub
				return new Position(2, 1);
			}
		};
	}

}
