package Robot.Impl;

import java.awt.Color;

import Robot.Environnement;
import Robot.Environnement.Robot.Component;
import Robot.interfaces.IBrain;
import Robot.interfaces.ICreateRobot;
import Robot.interfaces.IEye;
import Robot.interfaces.IFoot;
import datatype.Position;

public class EnvironnementImpl extends Environnement{

	@Override
	protected Robot make_Robot(final Color color, final Position position) {
		// TODO Auto-generated method stub
		return new Robot(){

			private Color myColor;
			private Position myPosition;
			
			@Override
			protected void start() {
				myColor = color;
				myPosition = position;
			}
			
			@Override
			protected IEye make_percevoir() {
				// TODO Auto-generated method stub
				return new IEye() {

					@Override
					public void regarderAutour() {
						System.out.println("je regarde autour de moi !!");
					}
				};
			}

			@Override
			protected IBrain make_decider() {
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
						myPosition.setPosX(myPosition.getPosX()+1);
						System.out.println("je tourne à gauche");
						System.out.println("voici les coordonnées : X = "+myPosition.getPosX());
						System.out.println("voici les coordonnées : Y = "+myPosition.getPosY());
					}

					@Override
					public void turnRight() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void stop() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void goStraight() {
						// TODO Auto-generated method stub
						
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
			public Robot.Component createStandaloneRobot(Color color, Position position) {
				// TODO Auto-generated method stub
				return newRobot(color, position);
			}
		};
	}


}
