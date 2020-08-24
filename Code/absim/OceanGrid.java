/*
Assignment 5 : Autonomous Boat Simulation
Name: David Nallapu
NUID : 001530978
*/

package edu.neu.csye6200.absim;

import java.util.ArrayList;

/**
 * NOTE: I have fixed the amount of oil that spills to 10000. I'm Assuming the leaking pipe has
 * been fixed after this.
 * Also,  
 * Land Mass = -1
 * Land Mass with Oil Spilled = -2(Total Oil decreased by 20)
 * This has been done to ease programming in the canvas class.
 */
class OceanGrid implements Runnable {
    int R;
    int C;
    int oilSpread;
    public static boolean done = false;
    public static int totalOil = 10000;

    /**
	 * Default constructor 
	 */
    OceanGrid() {

    }

    
    /**
	 * Parameterized constructor 
	 */
    OceanGrid(int R, int C, int oilSpread) {
        this.R = R;
        this.C = C;
        this.oilSpread = oilSpread;
    }

    
    public static int gridWidth = 20;
    public static int gridHeight = 20;
    public static OceanGrid gridData[][] = new OceanGrid[gridHeight][gridWidth];//2D array of OceanGrid objects 
    public static ArrayList < OceanGrid > borderOil = new ArrayList < OceanGrid > ();//Keeps track of all the active objects which can spread Oil

    
    /**
	 * Method to initialise the grid with all 0 values in oilSpread variable
	 */
    public void makeGrid() {
    	
        for (int i = 0; i < gridHeight; i++) {
            OceanGrid gridRow[] = new OceanGrid[gridHeight];
            for (int j = 0; j < gridWidth; j++) {
                gridRow[j] = new OceanGrid(i, j, 0);
            }
            gridData[i] = gridRow;
        }
    }

    
    /**
	 * Method with the logic to spread the oil
	 */
    public void spreadOil() {
        if (done = false) return;

        if (totalOil <= 0) return;

        for (OceanGrid gb: borderOil) {
            if (gb.oilSpread == -1 && totalOil >= 20) {// Checks if a land mass and updates to -2 for graphics and decreases by 20
                {gb.oilSpread = -2;
                totalOil -= 20;  
                }
            } else if (gb.oilSpread < 100 && gb.oilSpread >= 0 && totalOil >= 20) {
                gb.oilSpread += 20;
                totalOil -= 20;
                if (gb.oilSpread == 100) {
                    updateGrid(gb);
                    borderOil.remove(gb);// Removes if oil Spread is 100%
                    break;
                }
            }
        }
        try {
            Thread.sleep(100);
        } catch (Exception e) {};
    }


    /**
	 * Method to update the Border Oil ArrayList and adding points for next iteration 
	 */
    public void updateGrid(OceanGrid gb) {
        //Check W and OilSpread=0 and not Land Mass
        if (gb.R > -1 && gb.C - 1 > -1 && gb.R <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1 && (gridData[gb.R][gb.C - 1].oilSpread == 0 || gridData[gb.R][gb.C - 1].oilSpread == -1))
            borderOil.add(gridData[gb.R][gb.C - 1]);

        //Check NW and OilSpread=0 and not Land Mass
        if (gb.R - 1 > -1 && gb.C - 1 > -1 && gb.R - 1 <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1 && (gridData[gb.R - 1][gb.C - 1].oilSpread == 0 || gridData[gb.R - 1][gb.C - 1].oilSpread == -1))
            borderOil.add(gridData[gb.R - 1][gb.C - 1]);

        //Check N and OilSpread=0 and not Land Mass
        if (gb.R - 1 > -1 && gb.C > -1 && gb.R - 1 <= gridHeight - 1 && gb.C <= gridWidth - 1 && (gridData[gb.R - 1][gb.C].oilSpread == 0 || gridData[gb.R - 1][gb.C].oilSpread == -1))
            borderOil.add(gridData[gb.R - 1][gb.C]);

        //Check NE  and OilSpread=0 and not Land Mass
        if (gb.R - 1 > -1 && gb.C + 1 > -1 && gb.R - 1 <= gridHeight - 1 && gb.C + 1 <= gridWidth - 1 && (gridData[gb.R - 1][gb.C + 1].oilSpread == 0 || gridData[gb.R - 1][gb.C + 1].oilSpread == -1))
            borderOil.add(gridData[gb.R - 1][gb.C + 1]);

        //Check E and OilSpread=0 and not Land Mass
        if (gb.R > -1 && gb.C + 1 > -1 && gb.R <= gridHeight - 1 && gb.C + 1 <= gridWidth - 1 && (gridData[gb.R][gb.C + 1].oilSpread == 0 || gridData[gb.R][gb.C + 1].oilSpread == -1))
            borderOil.add(gridData[gb.R][gb.C + 1]);

        //Check SE and OilSpread=0 and not Land Mass
        if (gb.R + 1 > -1 && gb.C + 1 > -1 && gb.R + 1 <= gridHeight - 1 && gb.C + 1 <= gridWidth - 1 && (gridData[gb.R + 1][gb.C + 1].oilSpread == 0 || gridData[gb.R + 1][gb.C + 1].oilSpread == -1))
            borderOil.add(gridData[gb.R + 1][gb.C + 1]);

        //Check S and OilSpread=0 and not Land Mass
        if (gb.R + 1 > -1 && gb.C > -1 && gb.R + 1 <= gridHeight - 1 && gb.C <= gridWidth - 1 && (gridData[gb.R + 1][gb.C].oilSpread == 0 || gridData[gb.R + 1][gb.C].oilSpread == -1))
            borderOil.add(gridData[gb.R + 1][gb.C]);

        //Check and OilSpread=0 SW and not Land Mass
        if (gb.R + 1 > -1 && gb.C - 1 > -1 && gb.R + 1 <= gridHeight - 1 && gb.C - 1 <= gridWidth - 1 && (gridData[gb.R + 1][gb.C - 1].oilSpread == 0 || gridData[gb.R + 1][gb.C - 1].oilSpread == -1))
            borderOil.add(gridData[gb.R + 1][gb.C - 1]);

    }

    /**
	 * Method to return amount of oil that has spilled 
	 */
    public static double getOilSpread() {
        return 100 - (totalOil * 100 / 10000);
    }

    @Override
    public void run() {
        while (!done) {
            if (MySimulation.paused)
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            else {
                spreadOil();

            }
        }
    }


}