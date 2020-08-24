/*
Assignment 5 : Autonomous Boat Simulation
Name: David Nallapu
NUID : 001530978
*/

package edu.neu.csye6200.absim;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyCanvas extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private OceanGrid og = new OceanGrid();
	private OceanGrid[][] grid;
	private BufferedImage img;
	private ABRule myABRule;

	/**
	 * Constructor to add Oil and Land Mass
	 */
	public MyCanvas() {
		try {
			img = ImageIO.read(getClass().getResource("boat.png"));// Loading image of the Boat
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Making the Ocean Grid
		og.makeGrid();

		// Adding the first OilSpill
		OceanGrid.gridData[OceanGrid.gridWidth / 2 - 1][OceanGrid.gridHeight / 2 - 1].oilSpread = 60;
		OceanGrid.totalOil -= 60;
		OceanGrid.borderOil.add(OceanGrid.gridData[OceanGrid.gridWidth / 2 - 1][OceanGrid.gridHeight / 2 - 1]);

		// Adding Land mass
		OceanGrid.gridData[5][8].oilSpread = -1;
		OceanGrid.gridData[8][5].oilSpread = -1;
		OceanGrid.gridData[12][3].oilSpread = -1;
		OceanGrid.gridData[11][17].oilSpread = -1;
		OceanGrid.gridData[16][11].oilSpread = -1;
		OceanGrid.gridData[3][13].oilSpread = -1;
		OceanGrid.gridData[17][12].oilSpread = -1;
		OceanGrid.gridData[13][5].oilSpread = -1;

		grid = OceanGrid.gridData;
	}

	// Swing calls when a redraw is needed
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		drawCanvas(g);
	}

	// Draw the contents of the panel
	private void drawCanvas(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		g2d.fillRect(0, 0, size.width, size.height);
		int height = size.width / 20;
		int width = size.height / 20;
		drawOceanGrid(g2d, height, width);
	}

	/**
	 * Method to draw ocean grid.
	 */
	private void drawOceanGrid(Graphics2D g2d, int height, int width) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int startx = j * height;
				if (grid[i][j].R == ABRule.bt.getPosX() && grid[i][j].C == ABRule.bt.getPosY()
						&& OceanGrid.borderOil.size() > 0) {
					if (img != null) {// In case image doesnt load due to directory change.
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.drawImage(img, startx, i * width, height - 2, width - 2, this);
					} else {
						g2d.setColor(Color.GREEN);
						g2d.fillRect(startx, i * width, height - 1, width - 1);
					}
				} else {
					if (grid[i][j].oilSpread == 100) {// If the Oil Spill is 100%
						g2d.setColor(new Color(0, 0, 0));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
					} else if (grid[i][j].oilSpread == 80) { // If the Oil Spill is 80%
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.BLACK);
						g2d.fillOval(startx, i * width, height, width);
					} else if (grid[i][j].oilSpread == 60) { // If the Oil Spill is 60%
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.BLACK);
						g2d.fillOval(startx + j / 2, i * width + i / 2, height / 2, width / 2);
					} else if (grid[i][j].oilSpread == 40) { // If the Oil Spill is 40%
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.BLACK);
						g2d.fillOval(startx + j / 3, i * width + i / 3, height / 3, width / 3);
					} else if (grid[i][j].oilSpread == 20) { // If the Oil Spill is 20%
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(Color.BLACK);
						g2d.fillOval(startx + j / 4, i * width + i / 4, height / 4, width / 4);
					} else if (grid[i][j].oilSpread == 0) { // If the Oil Spill is 0%
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
					} else if (grid[i][j].oilSpread == -2) { // If Land Mass with Oil Spill
						g2d.setColor(Color.BLACK);
						g2d.fillOval(startx, i * width, height, width);
						g2d.setColor(new Color(34, 139, 34));
						g2d.fillOval(startx, i * width, height - 1, width - 1);
					} else if (grid[i][j].oilSpread == -1) { // If Land Mass without Oil Spill
						g2d.setColor(new Color(42, 167, 172));
						g2d.fillRect(startx, i * width, height - 1, width - 1);
						g2d.setColor(new Color(34, 139, 34));
						g2d.fillOval(startx, i * width, height - 1, width - 1);
					}
				}

			}

		}

	}

	@Override
	public void update(Observable o, Object arg) {

		if (arg instanceof MySimulation) {
			myABRule = (ABRule) arg;
		}

		repaint(); // Tell the GUI thread that it should schedule a paint() call
	}

}
