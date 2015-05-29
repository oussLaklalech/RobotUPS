package box.Impl;

import java.awt.Color;
import java.util.ArrayList;

import Robot.EcoBoxes;
import box.Interface.IBoxesInfo;
import box.Interface.ICreateBox;
import box.Interface.IGettersBox;
import datatype.Position;

public class EcoBoxesImpl extends EcoBoxes{

	private static ArrayList<Box.Component> listBoxes = new ArrayList<Box.Component>();

	@Override
	protected void start() {
		System.out.println("Start de ECOBOX");
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

			@Override
			protected IGettersBox make_getInfoBox() {
				// TODO Auto-generated method stub
				return new IGettersBox() {
					
					@Override
					public Position getPosition() {
						// TODO Auto-generated method stub
						return myPosition;
					}
					
					@Override
					public int getId() {
						// TODO Auto-generated method stub
						return myId;
					}
					
					@Override
					public Color getColor() {
						// TODO Auto-generated method stub
						return myColor;
					}
				};
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
		
		return new IBoxesInfo() {

			@Override
			public Box.Component getBoxInPosition(Position positionBox) {
				// TODO : chercher dans la liste des boxes par position
				// retourne l'id de la box (ou le composant Box) si existe
				// retourne -1 sinon (ou null)
				System.out.println("*** getBoxInPosition ****");
				for(int i=0;i<listBoxes.size();i++){
					System.out.println(listBoxes.get(i).getInfoBox().getPosition());
					System.out.println(positionBox);
					if(listBoxes.get(i).getInfoBox().getPosition().equals(positionBox)){
						return listBoxes.get(i);
					}
				}
				return null;
			}
			
			
		};
	}

}
