package Nest.Impl;

import java.awt.Color;
import java.util.ArrayList;

import Nest.Interface.IConfigureEcoNest;
import Nest.Interface.ICreateNest;
import Nest.Interface.IGettersNest;
import Nest.Interface.INestsInfo;
import Robot.EcoNest;
import datatype.Position;

public class EcoNestImpl extends EcoNest{

	private static ArrayList<Nest.Component> listNests = new ArrayList<Nest.Component>();
	private int tailleGrille;
	private static int numberNest = 0;
	
	@Override
	protected void start() {
		System.out.println("Création des 3 nids");
	}
	
	@Override
	protected INestsInfo make_informationAboutNestsGive() {
		return new INestsInfo() {

			@Override
			public ArrayList<Nest.Component> getAllNests() {
				return listNests;
			}

			@Override
			public Robot.EcoNest.Nest.Component getNestWithColor(Color c) {
				
				for(int i=0;i<listNests.size();i++){
					if(listNests.get(i).getInfoNest().getColor().equals(c)){
						return listNests.get(i);
					}
				}
				return null;
			}

			
		};
	}

	@Override
	protected Nest make_Nest( final Color color, final Position position) {
		return new Nest(){

			private Color myColor;
			private Position myPosition;
			private int myId;
			
			@Override
			protected void start() {
				myColor = color;
				myPosition = position;
				myId = ++numberNest;
			}
			
			@Override
			protected IGettersNest make_getInfoNest() {
				return new IGettersNest() {
					@Override
					public Position getPosition() {
						return myPosition;
					}

					@Override
					public Color getColor() {
						return myColor;
					}

					@Override
					public int getId() {
						return myId;
					}
				};
			}
			
		};
	}

	@Override
	protected ICreateNest make_create() {
		return new ICreateNest() {
			@Override
			public Nest.Component createStandaloneNest(Color color, Position position) {
				System.out.println("*****************Nid Numero "+numberNest+" a été CREE ****************");
				Nest.Component nestTemp = newNest(color, position);
				requires().nestManageGui().NestCreateNotification(position, color);
				listNests.add(nestTemp);
				
				return nestTemp;
			}

			
			
		};
	}

	@Override
	protected IConfigureEcoNest make_setConfiguration() {
		// TODO Auto-generated method stub
		return new IConfigureEcoNest() {
			
			@Override
			public void setTailleGrille(int n) {
				tailleGrille=n;
				System.out.println("*****La taille de la grille est configuré à "+n+"X"+n+" pour l'ecosysteme Nest*****");
				Nest.Component n1 = make_create().createStandaloneNest(Color.RED, new Position((int)tailleGrille/2, (int)tailleGrille/2));
				Nest.Component n2 = make_create().createStandaloneNest(Color.GREEN, new Position(tailleGrille-1, 0));
				Nest.Component n3 = make_create().createStandaloneNest(Color.BLUE, new Position(0, (int)tailleGrille-1));
				
			}
		};
	}

}
