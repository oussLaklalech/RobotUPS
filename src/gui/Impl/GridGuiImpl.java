package gui.Impl;

import gridgui.GridGui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GridGuiImpl extends GridGui {
	@Override
    protected void start() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                final JFrame f = new JFrame();
                final JPanel pnl = new JPanel(new GridLayout(10,10));

                pnl.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

                for (int i =0; i<(10*10); i++){
                    final JLabel label = new JLabel("Label");
                    label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    pnl.add(label);
                }
                
                f.getContentPane().add(BorderLayout.NORTH, pnl);
                f.pack();
                f.setVisible(true);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }
}
