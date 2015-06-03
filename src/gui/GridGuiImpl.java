package gui;

import gui.Interface.IGetInfo;
import gui.Interface.IManageCell;
import gui.Interface.IManageGui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Robot.GridGui;
import datatype.Position;

public class GridGuiImpl extends GridGui {
	private final JFrame f = new JFrame();
	private final JPanel containerPane = new JPanel();
	Border blackline = BorderFactory.createLineBorder(Color.black, 1);
	JPanel gridpane;
	JPanel containerTop;
	JSpinner spinnerRobot;
	JSpinner spinnerBox;
	JButton buttonRobot;
	JButton buttonBox;
	
	
	private ArrayList<CellGui.Component> listCells = new ArrayList<CellGui.Component>();
	
	@Override
	protected void start() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				containerPane.setLayout(new BoxLayout(containerPane, BoxLayout.Y_AXIS));
				final JTextField textTailleGrille = new JTextField(
						"Taille de la grille (Entier)");
				textTailleGrille.setColumns(15);
				final JButton bAddAccount = new JButton("Crée");
				final JPanel panelTailleGrille = new JPanel();
				panelTailleGrille.add(textTailleGrille);
				panelTailleGrille.add(bAddAccount);
             
				bAddAccount.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						 spinnerRobot=new JSpinner();
						 spinnerBox=new JSpinner();
						 spinnerBox.addChangeListener(new ChangeListener() {
							
							@Override
							public void stateChanged(ChangeEvent e) {
								// TODO Auto-generated method stub
								buttonBox.setText("Crée "+spinnerBox.getValue()+" Boxs");
								
							}
						});
						 spinnerRobot.addChangeListener(new ChangeListener() {
							
							@Override
							public void stateChanged(ChangeEvent e) {
								// TODO Auto-generated method stub
								buttonRobot.setText("Crée "+spinnerRobot.getValue()+" Robots");
								
							}
						});
						 buttonBox=new JButton("Crée des Boxs");
						
						 buttonRobot=new JButton("Crée des Robots");
						 buttonRobot.addActionListener(new ActionListener() {
								
								@Override
								public void actionPerformed(ActionEvent e) {
									requires().createRobots().createRobots( (int)spinnerRobot.getValue());
									
								}
							});
						 containerTop=new JPanel();
						 BoxLayout b= new BoxLayout(containerTop, BoxLayout.X_AXIS);
						 containerTop.setLayout(b);
						 containerTop.add(spinnerRobot);
						 containerTop.add(buttonRobot);
						 containerTop.add(spinnerBox);
						 containerTop.add(buttonBox);
						 containerTop.setMaximumSize(new Dimension(1000,150));
						 //f.getContentPane().add(containerTop);
						 containerPane.add(containerTop);
						
						String tailleString = textTailleGrille.getText();
						try {

							int taille = Integer.parseInt(tailleString);

							panelTailleGrille.removeAll();

							panelTailleGrille.revalidate();
							panelTailleGrille.repaint();
							containerPane.remove(panelTailleGrille);
							gridpane = new JPanel(
									new GridLayout(taille, taille));
								int x=0;
								int y=0;
								
							for (int i = 0; i < taille * taille; i++) {
								 y=i%taille;
								listCells.add(newCellGui(new Position(x,y)));
								if(y==taille-1)
									x++;
							}
							gridpane.setBorder(blackline);
							containerPane.add(gridpane);
							f.pack();
							
							//f.setExtendedState(JFrame.MAXIMIZED_BOTH);
						} catch (NumberFormatException nfe) {
							JOptionPane.showMessageDialog(f,
									"Veuillez entrer un entier !", "Erreur",
									JOptionPane.WARNING_MESSAGE);
						}
					}

				});
				
				containerPane.add(panelTailleGrille);
				panelTailleGrille.setBackground(Color.ORANGE);
				f.getContentPane().add(containerPane);
				
				f.pack();
				f.setVisible(true);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

	@Override
	protected CellGui make_CellGui(final Position p) {
		return new GridGui.CellGui() {
			private JLabel cell;
			public Position position;
			@Override
			protected void start() {
				this.position=p;
				cell = new JLabel();
				//cell.setSize(20,20);
				cell.setPreferredSize(new Dimension(30,30));
				cell.setOpaque(true);
				cell.setBackground(Color.RED);
				cell.setBorder(blackline);
				//cell.setText(p.getPosX()+"x"+p.getPosY());
				gridpane.add(cell);
		

			}
			@Override
			protected IManageCell make_manageCell() {
				// TODO Auto-generated method stub
				return new IManageCell() {
					
					@Override
					public void RobotQuit() {
						cell.setBackground(Color.gray);
						
					}
					
					@Override
					public void RobotArrive(Color color) {
						cell.setBackground(color);
						
					}
				};
			}
			@Override
			protected IGetInfo make_getInfo() {
				// TODO Auto-generated method stub
				return new IGetInfo() {
					
					@Override
					public Position getPosition() {
						// TODO Auto-generated method stub
						return position;
					}
				};
			}
		};
	}

	@Override
	protected IManageGui make_manageGui() {
		// TODO Auto-generated method stub
		return new IManageGui() {
			
			@Override
			public void RobotMoveNotification(Position lastPos, Position newPos,
					Color color) {
				 CellGui.Component lastcell= getCellByPos(lastPos);
				 CellGui.Component newcell= getCellByPos(newPos);
				
				 lastcell.manageCell().RobotQuit();
				 newcell.manageCell().RobotArrive(color);
			}
			
			public CellGui.Component getCellByPos(Position pos){
				
			 for (CellGui.Component cell : listCells) {
				 Position position= cell.getInfo().getPosition();
				 if(pos.getPosX()==position.getPosX())
					 if(pos.getPosY()==position.getPosY())
						 return cell;
			}
				return null;
			}
		};
	}

	
}
