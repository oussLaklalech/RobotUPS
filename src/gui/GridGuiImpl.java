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
	JSpinner spinnerVitesse;
	JButton buttonVitesse;
	JButton buttonRobot;
	JButton buttonBox;
	JButton buttonPausePlay;

	private ArrayList<CellGui.Component> listCells = new ArrayList<CellGui.Component>();

	public void setSystemeBounds(int n) {
		requires().configureSystemeNests().setTailleGrille(n);
		requires().configureSystemeBoxs().setTailleGrille(n);
		requires().configureSystemeRobots().setTailleGrille(n);
	}

	public void initInterfaceConfig() {
		buttonPausePlay=new JButton("Pause");
		buttonBox = new JButton("Crée des Boxs");
		buttonRobot = new JButton("Crée des Robots");
		buttonVitesse = new JButton("Changer la vitesse");
		spinnerRobot = new JSpinner();
		spinnerBox = new JSpinner();
		spinnerVitesse = new JSpinner();
		buttonPausePlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(buttonPausePlay.getText().equals("Pause"))
				{
					buttonPausePlay.setText("Play");
				  requires().configureSystemeRobots().setPause();
				}else{
					buttonPausePlay.setText("Pause");
					 requires().configureSystemeRobots().setPlay();
				}
			}
		});
		
		
		spinnerVitesse.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				buttonVitesse.setText("vitesse à " + spinnerVitesse.getValue());

			}
		});
		spinnerBox.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				buttonBox.setText("Crée " + spinnerBox.getValue() + " Boxs");

			}
		});
		spinnerRobot.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				buttonRobot.setText("Crée " + spinnerRobot.getValue()
						+ " Robots");

			}
		});

		buttonBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i =(int) spinnerBox.getValue();
				if(i>0)
				requires().createBox().createBoxes(i);
				else
					JOptionPane.showMessageDialog(f,
							"Veuillez entrer nombre supérieur à 0 !", "Erreur",
							JOptionPane.WARNING_MESSAGE);

			}
		});

		buttonRobot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int i =(int)spinnerRobot.getValue();
				if(i>0)
				requires().createRobots().createRobots(i);
				else
					JOptionPane.showMessageDialog(f,
							"Veuillez entrer nombre supérieur à 0 !", "Erreur",
							JOptionPane.WARNING_MESSAGE);

				
			}
		});

		buttonVitesse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int v = (int) spinnerVitesse.getValue();
				if (v > 0) {
					requires().configureSystemeRobots().setVitesse(v);
				}else{
					JOptionPane.showMessageDialog(f,
							"Veuillez entrer nombre supérieur à 0 !", "Erreur",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		containerTop = new JPanel();
		BoxLayout b = new BoxLayout(containerTop, BoxLayout.X_AXIS);
		containerTop.setLayout(b);
		containerTop.add(spinnerRobot);
		containerTop.add(buttonRobot);
		containerTop.add(spinnerBox);
		containerTop.add(buttonBox);
		containerTop.add(spinnerVitesse);
		containerTop.add(buttonVitesse);
		containerTop.add(buttonPausePlay);
		containerTop.setMaximumSize(new Dimension(1000, 150));
		// f.getContentPane().add(containerTop);
		containerPane.add(containerTop);
	}

	@Override
	protected void start() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				containerPane.setLayout(new BoxLayout(containerPane,
						BoxLayout.Y_AXIS));
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

						String tailleString = textTailleGrille.getText();
						try {

							int taille = Integer.parseInt(tailleString);
							if(!(taille>50||taille<1)){
							initInterfaceConfig();

							panelTailleGrille.removeAll();

							panelTailleGrille.revalidate();
							panelTailleGrille.repaint();
							containerPane.remove(panelTailleGrille);
							gridpane = new JPanel(
									new GridLayout(taille, taille));
							int x = 0;
							int y = 0;

							for (int i = 0; i < taille * taille; i++) {
								y = i % taille;
								listCells.add(newCellGui(new Position(x, y)));
								if (y == taille - 1)
									x++;
							}
							gridpane.setBorder(blackline);
							containerPane.add(gridpane);
							f.pack();
							setSystemeBounds(taille);
							// f.setExtendedState(JFrame.MAXIMIZED_BOTH);
							
							
							}
							else{
								JOptionPane.showMessageDialog(f,
										"Veuillez entrer un nombre de 1 à 50 maximum !", "Erreur",
										JOptionPane.WARNING_MESSAGE);
							}
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
			String type="vide";
			Color lastColor;

			@Override
			protected void start() {
				this.position = p;
				cell = new JLabel();
				// cell.setSize(20,20);
				cell.setPreferredSize(new Dimension(30, 30));
				cell.setOpaque(true);
				cell.setBackground(Color.WHITE);
				cell.setForeground(Color.WHITE);
				cell.setBorder(blackline);
				// cell.setText(p.getPosX()+"x"+p.getPosY());
				gridpane.add(cell);

			}

			@Override
			protected IManageCell make_manageCell() {
				// TODO Auto-generated method stub
				return new IManageCell() {

					@Override
					public void RobotQuit() {
						if(!type.equals("nest"))
						{
						cell.setBackground(Color.WHITE);
						cell.setText("");
						}
					}

					@Override
					public void RobotArrive(Color color,boolean hasBox) {
						if(!type.equals("nest"))
						{
						cell.setBackground(color);
						if(hasBox){
						cell.setText("<html><b>Rb</b></html>");
						}else{
							cell.setText("<html><b>R</b></html>");
						}
						}
					}

					@Override
					public void BoxArrive(Color color) {
						if(!type.equals("nest"))
						{
							type="box";
						cell.setBackground(color);
						cell.setText("<html><b>B</b></html>");
						}

					}

					@Override
					public void BoxQuit() {
						if(!type.equals("nest"))
						{type="vide";
						cell.setBackground(Color.WHITE);
						cell.setText("");
						}
					}

					@Override
					public void NestArrive(Color color) {
						type="nest";
						cell.setBackground(color);
						cell.setText("<html><b>N</b></html>");

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
			public void RobotMoveNotification(Position lastPos,
					Position newPos, Color color, boolean hasBox) {
				CellGui.Component lastcell = getCellByPos(lastPos);
				CellGui.Component newcell = getCellByPos(newPos);

				lastcell.manageCell().RobotQuit();
				newcell.manageCell().RobotArrive(color,hasBox);
			}

			public CellGui.Component getCellByPos(Position pos) {

				for (CellGui.Component cell : listCells) {

					Position position = cell.getInfo().getPosition();

					if (pos.getPosX() == position.getPosX())
						if (pos.getPosY() == position.getPosY()) {
							return cell;
						}
				}
				return null;
			}

			@Override
			public void NestCreateNotification(Position pos, Color color) {
				// TODO Auto-generated method stub
				CellGui.Component cell = getCellByPos(pos);
			
				cell.manageCell().NestArrive(color);
			}

			@Override
			public void BoxCreateNotification(Position pos, Color color) {
				// TODO Auto-generated method stub
				CellGui.Component cell = getCellByPos(pos);
				
				cell.manageCell().BoxArrive(color);
			}

			@Override
			public void BoxPriseNotification(Position pos, Color color) {
				CellGui.Component cell = getCellByPos(pos);
				
				cell.manageCell().BoxQuit();
			}

			@Override
			public void RobotCreateNotification(Position pos, Color color) {
				CellGui.Component cell = getCellByPos(pos);
			
				cell.manageCell().RobotArrive(color,false);
				
			}
		};
	}

}
