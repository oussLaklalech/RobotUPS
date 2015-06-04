package Robot.Impl;

import java.awt.Color;

import datatype.Position;
import Robot.EcoBoxes.Box;
import Robot.EcoRobots.Robot;
import Robot.interfaces.IBrain;
import Robot.interfaces.IEye;
import Robot.interfaces.IFootHand;

public class RobotImpl extends Robot{

	private Color myColor;
	private Position myPosition;
	private int myId;
	private int myEnergy;
	private Box boxRaised;
	
	public RobotImpl(int id, Color color, Position position) {
		System.out.println("Init Robot num : "+id);
		System.out.println("Ma couleur est : "+color.toString());
		System.out.println("Ma position initiale est : X = "+position.getPosX()+" et Y = "+position.getPosY());
		myColor = color;
		myPosition = position;
		myId = id;
	}

	private void updateEnergy(){
		if(myEnergy > 3){
			myEnergy = myEnergy-1;
		}
		else{
			// Création d'un nouveau Robot avec les mêmes attributs
			eco_provides().create().createStandaloneRobot(myId, myColor, myPosition);
			
			// TODO : Suicide
		}	
	}
	
	@Override
	protected void start() {
		myEnergy = 100; // TODO : Configurable plus tard
		this.make_decider().reflechir();
	}
	
	@Override
	protected IEye make_percevoir() {
		return new IEye() {

			@Override
			public Box.Component lookAtMyPosition() {
				System.out.println("je regarde autour de moi !!");
				Box.Component box = eco_requires().informationAboutBoxesNeed().getBoxInPosition(myPosition);
				if(box != null){
					System.out.println("PERCEPTION : "+box.getInfoBox().getColor());
					return box;
				}
				else{
					System.out.println("pas de box dans l'emplacement");
					return null;
				}
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
				
				// PERCEVOIR
				if(provides().percevoir().lookAtMyPosition() != null){
					provides().agir().raiseBox();
					// TODO : algorithme pour aller au plus proche nid
				}else{
					// Agir
					// TODO : Algorithme de recherche de boite
					provides().agir().turnLeft();
					provides().agir().goStraight();
				}
			}
		};
	}

	@Override
	protected IFootHand make_agir() {
		return new IFootHand() {

			@Override
			public void turnLeft() {
				myPosition.setPosY(myPosition.getPosY()-1);
				System.out.println(myPosition);
				System.out.println("je tourne à gauche");
				
				updateEnergy();
					
			}

			@Override
			public void turnRight() {
				myPosition.setPosY(myPosition.getPosY()+1);
				System.out.println(myPosition);
				System.out.println("je tourne à droite");
				
				updateEnergy();
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
				
				updateEnergy();
			}

			@Override
			public void raiseBox() {
				// TODO : Soulever la boîte
				
			}
			
		};
	}
	
}
