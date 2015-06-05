package Robot.Impl;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import logging.Impl.LoggerImpl;
import logging.Interface.ILogger;
import Robot.EcoBoxes.Box;
import Robot.EcoNest.Nest;
import Robot.EcoRobots;
import Robot.interfaces.IBrain;
import Robot.interfaces.IConfigureEcoRobots;
import Robot.interfaces.ICreateRobot;
import Robot.interfaces.IEye;
import Robot.interfaces.IFootHand;
import Robot.interfaces.IGettersRobot;
import Robot.interfaces.ILife;
import Robot.interfaces.IPlay;
import datatype.Position;

public class EcoRobotsImpl extends EcoRobots {

	private ArrayList<Robot.Component> listRobots = new ArrayList<Robot.Component>();
	static int nombreRobots = 0;
	private int tailleGrille;
	private AtomicInteger vitesseSyst = new AtomicInteger(1);
	private AtomicInteger timeToSleep = new AtomicInteger(2000);
	private static ArrayList<Nest.Component> listNests = new ArrayList<Nest.Component>();
	File logEcoRobot;

	@Override
	protected void start() {
		String l1="Start de ECOROBOT";
		System.out.println(l1);
		logEcoRobot=provides().log().newRobotFile("EcoRobot");
		provides().log().addLine(logEcoRobot, l1);
	}

	@Override
	protected Robot make_Robot(final Color color, final Position position) {

		return new Robot() {
            private File mylogFile;
			private Color myColor;
			private Position myPosition;
			private int myId;
			private int myEnergy;
			private Box.Component myBox;
			protected boolean suspended = false;
			private MyThread thread;

			private void updateEnergy() {
				if (myEnergy > 3) {
					myEnergy = myEnergy - 1;
					System.out.println(myEnergy);
				} else {
					// Création d'un nouveau Robot
					eco_provides().create().createStandaloneRobot(myColor, myPosition);
					System.out.println("Un Robot vient de créer un autre robot");
					// Se Suicider 
					provides().seSuicider().adieu(myId);
				}
			}

			@Override
			protected void start() {
			
				myColor = color;
				myPosition = position;
				myId = ++nombreRobots;
				myEnergy = 100;

				String l1="Init Robot num : " + myId;
				String l2="Ma couleur est : " + color.toString();
				String l3="Ma position initiale est : X = "
						+ position.getPosX() + " et Y = " + position.getPosY();
				System.out.println(l1);
				System.out.println(l2);
				System.out.println(l3);
				
				
				mylogFile=eco_provides().log().newRobotFile(myId+"");
               eco_provides().log().addLine(mylogFile, l1);
               eco_provides().log().addLine(mylogFile, l2);
               eco_provides().log().addLine(mylogFile, l3);

			}

			@Override
			protected IEye make_percevoir() {
				return new IEye() {

					@Override
					public Box.Component lookAtMyPosition() {
						System.out.println("je regarde s'il y a une boîte !!");
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
						try {							   
							Thread.sleep(timeToSleep.get() / vitesseSyst.get());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Je suis entrain de reflechir");
						// ********** PERCEVOIR **********
						Box.Component boxTemp = provides().percevoir().lookAtMyPosition();
						// ********* DECIDER **********
						// Si le robot retrouve une boîte dans la position, il soulève la boîte
						if(boxTemp != null && myBox == null){
							// ********** AGIR *********
							provides().agir().raiseBox(boxTemp);
							System.out.println("$$$$$$$$$$$$$$$$$$$$$$ box Soulevé $$$$$$$$$$$$$$$$$$$");
							
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
						Nest.Component nestTemp = eco_requires().informationAboutNestsNeed().getNestWithColor(color);
						if(nestTemp != null){
	
							while(nonTrouve){
								try {
									Thread.sleep(timeToSleep.get() / vitesseSyst.get());
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								
								if(myPosition.getPosX() < nestTemp.getInfoNest().getPosition().getPosX()){
									provides().agir().goStraight();
								}
								if(myPosition.getPosX() > nestTemp.getInfoNest().getPosition().getPosX()){
									provides().agir().goBack();
								}
								if(myPosition.getPosY() > nestTemp.getInfoNest().getPosition().getPosY()){
									provides().agir().turnLeft();
								}
								if(myPosition.getPosY() < nestTemp.getInfoNest().getPosition().getPosY()){
									provides().agir().turnRight();
								}
								if(myPosition.equals(nestTemp.getInfoNest().getPosition())){
									nonTrouve = true;
									System.out.println("ùùùùùùùùùùùù NID TROUVE ùùùùùùùùùùù");
									return 1;
								}
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
						System.out.println("je tourne à gauche");
					
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
						System.out.println("je tourne à droite");
						eco_requires().robotManageGui().RobotMoveNotification(
								lastPos,
								new Position(myPosition.getPosX(), myPosition
										.getPosY()), myColor, myBox!=null);

						updateEnergy();
					}

				
					@Override
					public void goStraight() {
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
						// On notifie la GUI qu'une boite a été soulevée
						eco_requires().robotManageGui().BoxPriseNotification(box.getInfoBox().getPosition());	
						myBox = box;
						// On supprime la boîte de la liste
						eco_requires().manageBoxesNeed().removeBoxFromList(box.getInfoBox().getId());
					}

					@Override
					public void moveRandomly() {
						Random rand = new Random();
						// nextInt is normally exclusive of the top value,
						// so add 1 to make it inclusive
						int randomNum = rand.nextInt(4) + 1;
						switch (randomNum) {
						case 1:
							if (myPosition.getPosX() - 1 >= 0)
								goBack();
							break;
						case 2:
							if (myPosition.getPosX() + 1 <= tailleGrille - 1)
								goStraight();
							break;
						case 3:
							if (myPosition.getPosY() - 1 >= 0)
								turnLeft();
							break;
						case 4:
							if (myPosition.getPosY() + 1 <= tailleGrille - 1)
								turnRight();
							break;
						default:
							break;
						}
					}

					@Override
					public void depositBox() {
						// TODO : notifier la GUI pour déposer la box
						System.out.println("++++++ BOX déposé +++++");
						// On mets à jour l'energie du robot selon la couleur déposé
						if(myBox.getInfoBox().getColor().equals(myColor)){
							myEnergy = myEnergy+30;
						}else{
							myEnergy = myEnergy+10;
						}
						System.out.println("!!!!!!!!! ROBOT : ma nouvelle Energie est : "+myEnergy);
						myBox = null;
					}

				};
			}

			@Override
			protected IPlay make_play() {
				return new IPlay() {
					
					@Override
					public synchronized void resume() {
						thread.resume();
					}

					@Override
					public synchronized void pause() {
						thread.suspend();
					}

					@Override
					public void init(Robot.Component r) {
						thread = new MyThread("MyThread", r);				
					}
				};
			}

			@Override
			protected ILife make_seSuicider() {
				return new ILife() {
					
					@Override
					public void adieu(int idRobot) {
						for(int i=0;i<listRobots.size();i++){
							if(listRobots.get(i).getInfoRobot().getId() == idRobot){
								listRobots.remove(i);
								// TODO : notifier la GUI
							}
						}
					}
				};
			}

			@Override
			protected IGettersRobot make_getInfoRobot() {
				
				return new IGettersRobot() {
					
					@Override
					public Position getPosition() {
						return myPosition;
					}
					
					@Override
					public int getId() {
						return myId;
					}
					
					@Override
					public Color getColor() {
						return myColor;
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
						r.play().init(r);					
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
		return new IConfigureEcoRobots() {

			@Override
			public void setTailleGrille(int n) {
				tailleGrille = n;
				System.out
						.println("*****La taille de la grille est configuré à "
								+ n + "X" + n + " pour l'ecosystéme Robot*****");
				// On récupère les nids avec leurs couleurs & positions
				listNests = requires().informationAboutNestsNeed().getAllNests();
			}

			@Override
			public void setVitesse(int n) {
				vitesseSyst.set(n);
				System.out.println("Vitesse du système MAJ : " + n);
			}

			@Override
			public void setPause() {
				//suspended=true;
				System.out.println("*******************Système en Pause********************* ");
				for(int i=0;i<listRobots.size();i++){
					 listRobots.get(i).play().pause();
				 }
				
			}
			 
			@Override
			public void setPlay() {
				System.out.println("*******************Reprise du Système********************* ");
				for(int i=0;i<listRobots.size();i++){
					 listRobots.get(i).play().resume();
				 }
			}
		};
	}
	
	class MyThread implements Runnable {
		  Thread thrd;
		  boolean suspended;
		  boolean stopped;
		  Robot.Component robot;

		  MyThread(String name, Robot.Component r) {
		    thrd = new Thread(this, name);
		    suspended = false;
		    stopped = false;
		    robot = r;
		    thrd.start();
		  }
		  
		  public void run() {
		    try {
		    	for (;;){
					//Thread.sleep(50);
		    		robot.decider().reflechir();
					synchronized (this) {
				          while (suspended)
				            wait();
				          if (stopped)
				            break;
				        }
				}
		 
		    } catch (InterruptedException exc) {
		      System.out.println(thrd.getName() + " interrupted.");
		    }
		    System.out.println("\n" + thrd.getName() + " exiting.");
		  }

		  synchronized void stop() {
		    stopped = true;
		    suspended = false;
		    notify();
		  }

		  synchronized void suspend() {
			  System.out.println("SUSPENDED----------------------------");
		    suspended = true;
		  }

		  synchronized void resume() {
			  System.out.println("RESUME----------------------------");
		    suspended = false;
		    notify();
		  }
		}


	@Override
	protected ILogger make_log() {
		// TODO Auto-generated method stub
		return new LoggerImpl(new File("Robots"));
	}

	

}
