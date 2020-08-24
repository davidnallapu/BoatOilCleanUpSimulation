/*
Assignment 5 : Autonomous Boat Simulation
Name: David Nallapu
NUID : 001530978
*/

package edu.neu.csye6200.absim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.java.dev.designgridlayout.DesignGridLayout;

public class MyAppUI extends ABApp{

	private Logger log = Logger.getLogger(MyAppUI.class.getName());
	private JPanel northPanel;//Panel with Buttons and Combo Box
	public static JPanel southPanel; //Panel with Statistics
	private JButton startBtn;
	private JButton stopBtn;
	private JButton pauseBtn;
	
	
	private JComboBox<String> comboBox;
	public static MyCanvas canvas;
	public static SouthPanel sp;
	private MySimulation mySim = null;
	
	/**
	 * Constructor
	 */
	public MyAppUI() {
		log.info("Autonomous Boat Simulation  started");
		
		frame.setSize(600,600);
		frame.setTitle("Autonomous Boat Simulation - David Nallapu(NUID:001530978)");
		
		menuMgr.createDefaultActions(); // Set up default menu items
		
		initSim(); // Initialize the simulation

		showUI(); // Initialize the GUI
		
	}
	
	
	/**
	 * Initialise the simulation
	 */
	private void initSim() {
		mySim = new MySimulation();
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	  new MyAppUI();
      System.out.println("MyAppIO is exiting !!!!!!!!!!!!!!");
	}

	@Override
	public JPanel getNorthPanel() {
		northPanel = new JPanel();
		//Custom Lbrary for a better view 
		DesignGridLayout pLayout = new DesignGridLayout(northPanel);
		
		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mySim.startSim();
			}
		});
		
		pauseBtn = new JButton("Pause");
		pauseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mySim.pauseSim();
			}
		});
		
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mySim.stopSim();
			}
		});
		
		comboBox = new JComboBox<String>();
		comboBox.addItem("Rule 1");
		comboBox.addItem("Rule 2");
		comboBox.addItem("Rule 3");
		
		comboBox.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(comboBox.getSelectedItem() == "Rule 1")
				ABRule.boatRule=1;
			else if(comboBox.getSelectedItem() == "Rule 2") {
				ABRule.boatRule=2;
			}
			else if(comboBox.getSelectedItem() == "Rule 3") {
				ABRule.boatRule=3;
			}
		}
		
	});
		pLayout.row().grid(new JLabel("Rule:")).add(comboBox);
		pLayout.emptyRow();
		pLayout.row().center().add(startBtn, pauseBtn, stopBtn);
		northPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
		northPanel.setBackground(new Color(76, 119, 153));
		
		return northPanel;
	}


	@Override
	public JPanel getCenterPanel() {
		canvas = new MyCanvas();
		return canvas;
	}
	
	@Override
	public JPanel getSouthPanel() {
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		sp = new SouthPanel();
		southPanel.add(sp);
		southPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
		southPanel.setBackground(new Color(76, 119, 153));
		return southPanel;// Returns the Panel with all Oil and Boat Statistics 
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	/**
	 * Inner Class for the South Panel
	 */
	public class SouthPanel extends JComponent implements Observer {

		private ABRule myABRule;

		/**
		 * Constructor forStats Graphics displayed on southPanel
		 */
		SouthPanel() {
			setPreferredSize(new Dimension(600,50));
        }

        @Override
        public void paintComponent(Graphics g) {//Gets the statistics from ABRule as an Observable and repaints 
            super.paintComponent(g);
            //Statistics include Oil Spread, Oil Cleanup, Boat status, speed, Load, battery
            g.setColor(Color.YELLOW);
            g.drawString("Oil Spread(%): "+Double.toString(OceanGrid.getOilSpread()),10, 10);
            g.drawString("Oil CleanUp(%): "+(((double)ABRule.bt.getTotalOil()*100)/10000),210, 10);
            g.setColor(Color.white);
            g.drawString("Boat Details: ",10, 27);
            g.setColor(Color.YELLOW);
            g.drawString("Status: "+ABRule.bt.getStatus(),10, 45);
            g.drawString("Speed: "+Double.toString(ABRule.bt.getSpeed()),135, 45);
            g.drawString("Load Capacity(%): "+Double.toString(ABRule.bt.getLoadCapacity()*100/8000),250, 45);
            g.drawString("Battery (%): "+Double.toString(ABRule.bt.getBatteryCapacity()),445, 45);
 
        }
        
        @Override
    	public void update(Observable o, Object arg) {
    		
    		if(arg instanceof MySimulation) {
    			myABRule = (ABRule) arg;
    		}
    		repaint(); // Tell the GUI thread that it should schedule a paint() call
    	}
	}

	
}





