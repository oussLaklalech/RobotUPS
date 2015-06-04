package Robot.Impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import Robot.EcoBoxes.Box;
import Robot.EcoNest.Nest;
import Robot.EcoRobots;
import Robot.interfaces.IBrain;
import Robot.interfaces.IConfigureEcoRobots;
import Robot.interfaces.ICreateRobot;
import Robot.interfaces.IEye;
import Robot.interfaces.IFootHand;
import datatype.Position;

public class EcoRobotsImpl extends EcoRobots {

	private ArrayList<Robot.Component> listRobots = new ArrayList<Robot.Component>();
	static int nombreRobots = 0;
	private int tailleGrille;
	private AtomicInteger vitesseSyst = new AtomicInteger(1);
	private static ArrayList<Nest.Component> listNests = new ArrayList<Nest.Component>();

	@Override
	protected void start() {
		System.out.println("Start de ECOROBOT");

	}

	@Override
	protected Robot make_Robot(final Color color, final Position position) {

		return new Robot() {

			private Color myColor;
			private Position myPosition;
			private int myId;
			private int myEnergy;
			private Box.Component myBox;

			private void updateEnergy() {
				if (myEnergy > 3) {
					myEnergy = myEnergy - 1;
					System.out.println(myEnergy);
				} else {
					// Cr�ation d'un nouveau Robot
					eco_provides().create().createStandaloneRobot(myColor, myPosition);
					System.out.println("Un Robot vient de cr�er un autre robot");
					// TODO : Suicide
				}
			}

			@Override
			protected void start() {
				myColor = color;
				myPosition = position;
				myId = ++nombreRobots;
				myEnergy = 100; // TODO : Configurable plus tard
				System.out.println("Init Robot num : " + myId);
				System.out.println("Ma couleur est : " + color.toString());
				System.out.println("Ma position initiale est : X = "
						+ position.getPosX() + " et Y = " + position.getPosY());

			}

			@Override
			protected IEye make_percevoir() {
				return new IEye() {

					@Override
					public Box.Component lookAtMyPosition() {
						System.out.println("je regarde s'il y a une bo�te !!");
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
			protected IBrain make_decider() {
				return new IBrain() {

					@Override
					public void reflechir() {
						System.out.println("Je suis entrain de reflechir");
						// ********** PERCEVOIR **********
						Box.Component boxTemp = provides().percevoir().lookAtMyPosition();
						// ********* DECIDER **********
						// Si le robot retrouve une bo�te dans la position, il soul�ve la bo�te
						if(boxTemp != null && myBox == null){
							// ********** AGIR *********
							provides().agir().raiseBox(boxTemp);
							System.out.println("$$$$$$$$$$$$$$$$$$$$$$ box Soulev� $$$$$$$$$$$$$$$$$$$");
							
							// chercher un nid avec la meme couleur que la box
							if(searchNestWithColor(boxTemp.getInfoBox().getColor())==1){
								provides().agir().depositBox();
							}
							
						}else{
							// ******** AGIR ********
							provides().agir().moveRandomly();	
						}
					}

					private int searchNestWithColor(Color color) {
						// Chercher le nid avec la meme couleur dans listNests
						System.out.println(":::::::::::::::::::::searching Nest With Color ... :::::::::::::::::");
						boolean nonTrouve = true;
						
						while(nonTrouve){
							if(myPosition.getPosX() < myBox.getInfoBox().getPosition().getPosX()){
								provides().agir().goStraight();
							}
							if(myPosition.getPosX() > myBox.getInfoBox().getPosition().getPosX()){
								provides().agir().goBack();
							}
							if(myPosition.getPosY() > myBox.getInfoBox().getPosition().getPosY()){
								provides().agir().turnLeft();
							}
							if(myPosition.getPosY() > myBox.getInfoBox().getPosition().getPosY()){
								provides().agir().turnRight();
							}
							if(myPosition.equals(myBox.getInfoBox().getPosition())){
								nonTrouve = true;
								System.out.println("������������ NID TROUVE �����������");
								return 1;
							}
						}
						return -1;
						
					}
				};
			}

			@Override
			protected IFootHand make_agir() {
				return new IFootHand() {

					@Override
					public void turnLeft() {
						Position lastPos = new Position(myPosition.getPosX(),
								myPosition.getPosY());
						myPosition.setPosY(myPosition.getPosY() - 1);
						System.out.println(myPosition);
						System.out.println("je tourne � gauche");
					
						updateEnergy();
						eco_requires().robotManageGui()
								.RobotMoveNotification(
								lastPos,
								new Position(myPosition.getPosX(), myPosition.getPosY()), 
								myColor,
								myBox!=null
								);
					}

					@Override
					public void turnRight() {
						Position lastPos = new Position(myPosition.getPosX(),
								myPosition.getPosY());
						myPosition.setPosY(myPosition.getPosY() + 1);
						System.out.println(myPosition);
						System.out.println("je tourne � droite");
						eco_requires().robotManageGui().RobotMoveNotification(
								lastPos,
								new Position(myPosition.getPosX(), myPosition
										.getPosY()), myColor, myBox!=null);

						updateEnergy();
					}

				
					@Override
					public void goStraight() {
						// TODO : Aller tout droit d�pend du sens du Robot (X+1
						// ou X-1)
						Position lastPos = new Position(myPosition.getPosX(),
								myPosition.getPosY());

						myPosition.setPosX(myPosition.getPosX() + 1);
						System.out.println(myPosition);
						System.out.println("je vais tout droit");

						eco_requires().robotManageGui().RobotMoveNotification(
								lastPos,
								new Position(myPosition.getPosX(), myPosition
										.getPosY()), myColor,myBox!=null);

						updateEnergy();
					}

					@Override
					public void goBack() {
						// TODO Auto-generated method stub
						Position lastPos = new Position(myPosition.getPosX(),
								myPosition.getPosY());
						myPosition.setPosX(myPosition.getPosX() - 1);
						System.out.println(myPosition);
						System.out.println("je recule");

						eco_requires().robotManageGui().RobotMoveNotification(
								lastPos,
								new Position(myPosition.getPosX(), myPosition
										.getPosY()), myColor,myBox!=null);
						updateEnergy();
					}

					@Override
					public void raiseBox(Box.Component box) {
						// TODO : notifier la GUI pour la box
						// TODO : supprimer la box de la listBoxes
						myBox = box;

					}

					@Override
					public void moveRandomly() {
						Random rand = new Random();
						try {
							Thread.sleep(2000 / vitesseSyst.get());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// nextInt is normally exclusive of the top value,
						// so add 1 to make it inclusive
						int randomNum = rand.nextInt(4) + 1;
						switch (randomNum) {
						case 1:
							if (myPosition.getPosX() - 1 > 0)
								goBack();
							break;
						case 2:
							if (myPosition.getPosX() + 1 < tailleGrille - 1)
								goStraight();
							break;
						case 3:
							if (myPosition.getPosY() - 1 > 0)
								turnLeft();
							break;
						case 4:
							if (myPosition.getPosY() + 1 < tailleGrille - 1)
								turnRight();
							break;
						default:
							break;
						}
					}

					@Override
					public void depositBox() {
						// TODO : notifier la GUI pour d�poser la box
						// TODO : MAJ ENERGIE
						System.out.println("++++++ BOX d�pos� ++++");
						myBox = null;
					}

				};
			}

		};
	}

	@Override
	protected ICreateRobot make_create() {
		return new ICreateRobot() {
			@Override
			public Robot.Component createStandaloneRobot(Color color,
					Position position) {
				Robot.Component r = newRobot(color, position);
				listRobots.add(r);
				return r;
			}

			@Override
			public boolean createRobots(int nbreRobotsToCreate) {
				try {
					// fair un random sur les element coulour et position en
					// respectant la taille de la gui
					Color color;
					Random rand = new Random();
					for (int i = 0; i < nbreRobotsToCreate; i++) {
					
						int randomForColor = rand.nextInt(3) + 1;
						switch (randomForColor) {
						case 1:
							color = new Color(15, 134, 36);
							break;
						case 2:
							color = Color.RED;
							break;
						case 3:
							color = Color.BLUE;
							break;

						default:
							color = Color.GREEN;
							break;
						}
						Position posRobot=new Position(rand.nextInt(tailleGrille), rand.nextInt(tailleGrille));
						Robot.Component r1 = createStandaloneRobot(color,posRobot);
						requires().robotManageGui().RobotCreateNotification(posRobot, color);
					}
					for (final Robot.Component r : listRobots) {
						Thread t = new Thread() {
							public void run() {
								for (;;)
									r.decider().reflechir();
							}
						};
						t.start();

					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return false;
				}
				return true;
			}
		};
	}

	@Override
	protected IConfigureEcoRobots make_setConfiguration() {
		// TODO Auto-generated method stub
		return new IConfigureEcoRobots() {

			@Override
			public void setTailleGrille(int n) {
				// TODO Auto-generated method stub
				tailleGrille = n;
				System.out
						.println("*****La taille de la grille est configur� � "
								+ n + "X" + n + " pour l'ecosyst�me Robot*****");
				// On r�cup�re les nids avec leurs couleurs & positions
				listNests = requires().informationAboutNestsNeed().getAllNests();
			}

			@Override
			public void setVitesse(int n) {
				vitesseSyst.set(n);
				System.out.println("Vitesse du syst�me MAJ : " + n);
			}
		};
	}

}
