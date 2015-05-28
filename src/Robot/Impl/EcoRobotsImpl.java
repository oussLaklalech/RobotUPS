package Robot.Impl;

import java.awt.Color;
import java.util.ArrayList;

import Robot.EcoRobots;
import Robot.interfaces.IBrain;
import Robot.interfaces.ICreateRobot;
import Robot.interfaces.IEye;
import Robot.interfaces.IFoot;
import Robot.interfaces.IGettersRobot;
import datatype.Position;
import environnement.Impl.EnvironnementImpl;

public class EcoRobotsImpl extends EcoRobots{
	
	private ArrayList<Robot.Component> listRobots = new ArrayList<Robot.Component>();

	@Override
	protected void start() {
		System.out.println("Start de ECOROBOT");
		Robot.Component r1 = this.make_create().createStandaloneRobot(1, Color.BLACK, new Position(5, 9));
		Robot.Component r2 = this.make_create().createStandaloneRobot(1, Color.GREEN, new Position(0, 1));
		listRobots.add(r1);
		listRobots.add(r2);
	}
	
	@Override
	protected Robot make_Robot(final int id, final Color color, final Position position) {
		
		return new Robot(){

			private Color myColor;
			private Position myPosition;
			private int myId;
			
			@Override
			protected void start() {
				System.out.println("Init Robot num : "+id);
				System.out.println("Ma couleur est : "+color.toString());
				System.out.println("Ma position initiale est : X = "+position.getPosX()+" et Y = "+position.getPosY());
				myColor = color;
				myPosition = position;
				myId = id;
				this.make_decider().fakir();
			}
			
			@Override
			protected IEye make_percevoir() {
				// TODO Auto-generated method stub
				return new IEye() {

					@Override
					public void regarderAutour() {
						System.out.println("je regarde autour de moi !!");
						//TODO: Demander � l'environnement s il y a presence d'une boite � la position actuelle
						System.out.println(eco_requires().informationNeed().getMyPosition(myId));
					}
				};
			}

			@Override
			protected IBrain make_decider()  {
				return new IBrain() {

					@Override
					public void fakir() {
						System.out.println("Je suis entrain de reflechir");
						provides().agir().turnLeft();
						provides().agir().turnRight();
						provides().percevoir().regarderAutour();
					}
				};
			}

			@Override
			protected IFoot make_agir() {
				return new IFoot() {

					@Override
					public void turnLeft() {
						myPosition.setPosY(myPosition.getPosY()-1);
						System.out.println(myPosition);
						System.out.println("je tourne � gauche");
						//System.out.println("voici les coordonn�es : X = "+myPosition.getPosX());
						//System.out.println("voici les coordonn�es : Y = "+myPosition.getPosY());
					}

					@Override
					public void turnRight() {
						myPosition.setPosY(myPosition.getPosY()+1);
						System.out.println(myPosition);
						System.out.println("je tourne � droite");
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void goStraight() {
						// TODO : Aller tout droit d�pend du sens du Robot (X+1 ou X-1)
						myPosition.setPosX(myPosition.getPosX()+1);
						System.out.println(myPosition);
						System.out.println("je vais tout droit");
					}
					
				};
			}

			@Override
			protected IGettersRobot make_getInfoRobot() {
				// TODO Auto-generated method stub
				return new IGettersRobot() {
					
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
	protected ICreateRobot make_create() {
		// TODO Auto-generated method stub
		return new ICreateRobot() {
			@Override
			public EcoRobots.Robot.Component createStandaloneRobot(int id, Color color, Position position) {
				// TODO Auto-generated method stub
			EcoRobots.Robot.Component tempRobot =  newRobot(id, color, position);
				
				//listRobots.add(tempRobot);
				return tempRobot;
			}
		};
	}
}
