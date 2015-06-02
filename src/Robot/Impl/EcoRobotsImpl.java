package Robot.Impl;

import java.awt.Color;
import java.util.ArrayList;

import Robot.EcoBoxes.Box;
import Robot.EcoRobots;
import Robot.interfaces.IBrain;
import Robot.interfaces.ICreateRobot;
import Robot.interfaces.IEye;
import Robot.interfaces.IFootHand;
import datatype.Position;

public class EcoRobotsImpl extends EcoRobots{
	
	private ArrayList<Robot.Component> listRobots = new ArrayList<Robot.Component>();

	@Override
	protected void start() {
		System.out.println("Start de ECOROBOT");
		Robot.Component r1 = this.make_create().createStandaloneRobot(1, Color.BLACK, new Position(5, 2));
		Robot.Component r2 = this.make_create().createStandaloneRobot(2, Color.GREEN, new Position(10, 1));
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
			 try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				System.out.println("Init Robot num : "+id);
				System.out.println("Ma couleur est : "+color.toString());
				System.out.println("Ma position initiale est : X = "+position.getPosX()+" et Y = "+position.getPosY());
				myColor = color;
				myPosition = position;
				myId = id;
				this.make_decider().reflechir();
			}
			
			@Override
			protected IEye make_percevoir() {
				return new IEye() {

					@Override
					public void regarderAutour() {
						System.out.println("je regarde autour de moi !!");
						Box.Component box = eco_requires().informationAboutBoxesNeed().getBoxInPosition(myPosition);
						if(box != null)
							System.out.println("PERCEPTION : "+box.getInfoBox().getColor());
						else
							System.out.println("pas de box dans l'emplacement");
					}
				};
			}

			@Override
			protected IBrain make_decider()  {
				return new IBrain() {

					@Override
					public void reflechir() {
						// TODO : Algorithme de décision
						System.out.println("Je suis entrain de reflechir");
						provides().percevoir().regarderAutour();
						provides().agir().turnLeft();
						provides().agir().turnRight();
						provides().percevoir().regarderAutour();
					}
				};
			}

			@Override
			protected IFootHand make_agir() {
				return new IFootHand() {

					@Override
					public void turnLeft() {
						Position lastPos=new Position(myPosition.getPosX(),myPosition.getPosY());
						myPosition.setPosY(myPosition.getPosY()-1);
						System.out.println(myPosition);
						System.out.println("je tourne à gauche");
						//System.out.println("voici les coordonnées : X = "+myPosition.getPosX());
						//System.out.println("voici les coordonnées : Y = "+myPosition.getPosY());
						eco_requires().robotManageGui().RobotMoveNotification(lastPos, new Position(myPosition.getPosX(),myPosition.getPosY()), myColor);
					}

					@Override
					public void turnRight() {
						Position lastPos=new Position(myPosition.getPosX(),myPosition.getPosY());
						myPosition.setPosY(myPosition.getPosY()+1);
						System.out.println(myPosition);
						System.out.println("je tourne à droite");
						eco_requires().robotManageGui().RobotMoveNotification(lastPos, new Position(myPosition.getPosX(),myPosition.getPosY()), myColor);
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void goStraight() {
						// TODO : Aller tout droit dépend du sens du Robot (X+1 ou X-1)
						myPosition.setPosX(myPosition.getPosX()+1);
						System.out.println(myPosition);
						System.out.println("je vais tout droit");
					}

					@Override
					public void raiseBox() {
						// TODO : Soulever la boîte
						
					}
					
				};
			}
			
		};
	}

	@Override
	protected ICreateRobot make_create() {
		return new ICreateRobot() {
			@Override
			public Robot.Component createStandaloneRobot(int id, Color color, Position position) {
				return newRobot(id, color, position);
			}
		};
	}

	
	
}
