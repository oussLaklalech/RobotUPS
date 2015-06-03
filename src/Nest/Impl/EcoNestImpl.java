package Nest.Impl;

import java.awt.Color;
import java.util.ArrayList;

import Nest.Interface.ICreateNest;
import Nest.Interface.IGettersNest;
import Nest.Interface.INestsInfo;
import Robot.EcoNest;
import datatype.Position;

public class EcoNestImpl extends EcoNest{

	private static ArrayList<Nest.Component> listNests = new ArrayList<Nest.Component>();
	
	@Override
	protected void start() {
		System.out.println("Start de ECONEST");
		Nest.Component n1 = this.make_create().createStandaloneNest(1, Color.YELLOW, new Position(5, 2));
		Nest.Component n2 = this.make_create().createStandaloneNest(2, Color.GREEN, new Position(0, 1));
		listNests.add(n1);
		listNests.add(n2);
	}
	
	@Override
	protected INestsInfo make_informationAboutNestsGive() {
		return new INestsInfo() {

			@Override
			public ArrayList<Nest.Component> getAllNests() {
				return listNests;
			}

			
		};
	}

	@Override
	protected Nest make_Nest(int id, Color color, Position position) {
		return new Nest(){

			private Color myColor;
			private Position myPosition;
			private int myId;
			
			@Override
			protected IGettersNest make_getInfoNest() {
				// TODO Auto-generated method stub
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
			public Nest.Component createStandaloneNest(int id, Color color, Position position) {
				System.out.println("*****************Nid Numero "+id+" a été CREE ****************");
				return newNest(id, color, position);
			}

			@Override
			public boolean createNests(int nbreNestToCreate) {
/************************************************************************************************/
				/************************************************************************************************/
				/************************************************************************************************/
				/************************************************************************************************/
				/************************************************************************************************/

				return false;
			}
		};
	}

}
