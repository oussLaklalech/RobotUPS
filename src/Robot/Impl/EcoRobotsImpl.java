package Robot.Impl;

import java.awt.Color;

import Robot.EcoRobots;
import Robot.interfaces.IBrain;
import Robot.interfaces.ICreateRobot;
import Robot.interfaces.IEye;
import Robot.interfaces.IFoot;
import datatype.Position;

public class EcoRobotsImpl extends EcoRobots{

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
			}
			
			@Override
			protected IEye make_percevoir() {
				// TODO Auto-generated method stub
				return new IEye() {

					@Override
					public void regarderAutour() {
						System.out.println("je regarde autour de moi !!");
						// Demander à l'environnement s il y a presence d'une boite à la position actuelle
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
						provides().agir().turnLeft();
						provides().agir().turnLeft();
					}
				};
			}

			@Override
			protected IFoot make_agir() {
				return new IFoot() {

					@Override
					public void turnLeft() {
						System.out.println(myPosition);
						myPosition.setPosY(myPosition.getPosY()-1);
						System.out.println(myPosition);
						System.out.println("je tourne à gauche");
						//System.out.println("voici les coordonnées : X = "+myPosition.getPosX());
						//System.out.println("voici les coordonnées : Y = "+myPosition.getPosY());
					}

					@Override
					public void turnRight() {
						myPosition.setPosY(myPosition.getPosY()+1);
						System.out.println("je tourne à droite");
						System.out.println("voici les coordonnées : X = "+myPosition.getPosX());
						System.out.println("voici les coordonnées : Y = "+myPosition.getPosY());
						
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void goStraight() {
						myPosition.setPosX(myPosition.getPosX()+1);
						// TODO : Aller tout droit dépend du sens du Robot (X+1 ou X-1)
						System.out.println("je vais tout droit");
						System.out.println("voici les coordonnées : X = "+myPosition.getPosX());
						System.out.println("voici les coordonnées : Y = "+myPosition.getPosY());
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
			public Robot createStandaloneRobot(int id, Color color, Position position) {
				// TODO Auto-generated method stub
				return make_Robot(id, color, position);
			}
		};
	}
}
