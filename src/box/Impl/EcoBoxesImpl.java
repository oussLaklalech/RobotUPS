package box.Impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import Robot.EcoBoxes;
import box.Interface.IBoxesInfo;
import box.Interface.IConfigureEcoBox;
import box.Interface.ICreateBox;
import box.Interface.IGettersBox;
import box.Interface.IManageBoxes;
import datatype.Position;

public class EcoBoxesImpl extends EcoBoxes{

	private static ArrayList<Box.Component> listBoxes = new ArrayList<Box.Component>();
    private int tailleGrille;
    static int nombreBoxes = 0;
	@Override
	protected void start() {
		System.out.println("Start de ECOBOX");
	}
	
	@Override
	protected Box make_Box(final Color color, final Position position) {
		return new Box(){
			private Color myColor;
			private Position myPosition;
			private int myId;
			
			@Override
			protected void start() {
				
				myColor = color;
				myPosition = position;
				myId  = ++nombreBoxes;
				System.out.println("*****************BOITE N = "+myId+" CREE ****************");
				System.out.println("Init Box num : "+myId);
				System.out.println("Ma couleur est : "+color.toString());
				System.out.println("Ma position initiale est : X = "+position.getPosX()+" et Y = "+position.getPosY());
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
			public Box.Component createStandaloneBox( Color color, Position position) {
				Box.Component boxTemp = newBox( color, position);
				listBoxes.add(boxTemp);
				return boxTemp;
			}

			@Override
			public boolean createBoxes(int nbreBoxToCreate) {
				// fair un random sur les element coulour et position en
				// respectant la taille de la gui
				Color color;
				Random rand = new Random();
				for (int i = 0; i < nbreBoxToCreate; i++) {
				
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
					Position p =new Position(rand.nextInt(tailleGrille), rand.nextInt(tailleGrille));
					Box.Component r1 = createStandaloneBox(color,
							p);
				
					requires().boxManageGui().BoxCreateNotification(p, color);
			}
				return true;}
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
				System.out.println("nombre de boxes : "+listBoxes.size());
				System.out.println("position Box recherch� : "+positionBox);
				for(int i=0;i<listBoxes.size();i++){
					System.out.println("position BOX : "+listBoxes.get(i).getInfoBox().getPosition());
					if(listBoxes.get(i).getInfoBox().getPosition().equals(positionBox)){
						return listBoxes.get(i);
					}
				}
				return null;
			}

			@Override
			public int nombreBox() {
				// TODO Auto-generated method stub
				return listBoxes.size();
			}
			
			
		};
	}

	@Override
	protected IConfigureEcoBox make_setConfiguration() {
		// TODO Auto-generated method stub
		return new IConfigureEcoBox() {
			
			@Override
			public void setTailleGrille(int n) {
				// TODO Auto-generated method stub
				tailleGrille=n;
				System.out.println("*****La taille de la grille est configur� � "+n+"X"+n+" pour l'ecosyst�me Boxes*****");
				
			}
		};
	}

	@Override
	protected IManageBoxes make_manageBoxesGive() {
		return new IManageBoxes() {
			
			@Override
			public void removeBoxFromList(int idBox) {
				for(int i=0;i<listBoxes.size();i++){
					if(listBoxes.get(i).getInfoBox().getId() == idBox){
						try{
							listBoxes.remove(idBox);
						}catch(Exception e){
							
						}
						
					}
				}				
			}
		};
	}

}
