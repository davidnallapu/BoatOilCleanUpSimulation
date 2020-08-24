/*
Assignment 5 : Autonomous Boat Simulation
Name: David Nallapu
NUID : 001530978
*/

package edu.neu.csye6200.absim;

import java.util.Observable;

/**
 * NOTE: I have used ABRule as the Observable instead of MySimulation for a smoother movement and better
 * tracking on the GUI. ABRule has three Boat Rules.
 */
public class ABRule extends Observable implements Runnable {
    public static boolean done = false;
    public static Boat bt = new Boat("Cleaner", 0, 0, 90, "READY", 20, 0, 100, 0);// Boat instance to track movement
    public static int flag = 0;
    public static int boatRule = 1;// variable to decide which BoatRule method to use 

    /**
	 * Method to implement 'Rule 1' Boat Movement 
	 */
    public void boatRule1() {
        if (done) {
        	// Checks if Load Capacity or Battery Capacity is full and calls fillOil 
            if (bt.getLoadCapacity() * 100 / 8000 > 99 || bt.getBatteryCapacity() < 5) {
                fillOil();
            } else {
                int oldPosX = bt.getPosX();
                int oldPosY = bt.getPosY();
                int gridH = OceanGrid.gridHeight - bt.getPosX();
                int gridW = OceanGrid.gridWidth - bt.getPosY();
                
                //Moving East
                while (bt.getPosY() < gridH - 1 && done) {
                    if (MySimulation.paused)//Pauses the Boat immediately//Pauses the Boat immediately
                        pausedBoat();
                    else {
                        cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        bt.setPosY(bt.getPosY() + 1);
                    }
                }
                //Moving South
                while (bt.getPosX() < gridW - 1 && done) {
                    if (MySimulation.paused)//Pauses the Boat immediately
                        pausedBoat();
                    else {
                        cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        bt.setPosX(bt.getPosX() + 1);
                    }
                }
                //Moving West
                while (bt.getPosY() > oldPosX && done) {
                    if (MySimulation.paused)//Pauses the Boat immediately
                        pausedBoat();
                    else {
                        cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        bt.setPosY(bt.getPosY() - 1);
                    }
                }
                //Moving North
                while (bt.getPosX() > oldPosY && done) {
                    if (MySimulation.paused)//Pauses the Boat immediately
                        pausedBoat();
                    else {
                        cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        bt.setPosX(bt.getPosX() - 1);
                    }
                }
                bt.setBatteryCapacity(bt.getBatteryCapacity() - 10);//Decreases the battery by 10%
                bt.moveTo(bt.getPosX() + 1, bt.getPosY() + 1);//Method call to update the Boat postion for the next loop
            }
        }
    }

    //Static variable used ONLY for boatRule2 algorithm 
    public static int flagLoop = 1;
    /**
	 * Method to implement 'Rule 2' Boat Movement 
	 */
    public void boatRule2() {
        if (done) {
        	// Checks if Load Capacity or Battery Capacity is full and calls fillOil 
            if (bt.getLoadCapacity() * 100 / 8000 > 99 || bt.getBatteryCapacity() < 5) {
                fillOil();
            } else {
                if (flagLoop % 2 == 1) {
                    if (MySimulation.paused)//Pauses the Boat immediately
                        pausedBoat();
                    else {
                        bt.setPosX(bt.getPosX() + 1);
                        cleanOil(bt.getPosX(), bt.getPosY());
                        for (int i = 0; i < flagLoop; i++) {
                            if (MySimulation.paused)//Pauses the Boat immediately
                                pausedBoat();
                            else if(!done){
                            	break;
                            }
                            else {
                                bt.setPosY(bt.getPosY() + 1);
                                cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                            }
                        }
                        for (int i = 0; i < flagLoop; i++) {
                            if (MySimulation.paused)//Pauses the Boat immediately
                                pausedBoat();
                            else if(!done){
                            	break;
                            }
                            else {
                                bt.setPosX(bt.getPosX() - 1);
                                cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                            }
                        }
                        bt.setBatteryCapacity(bt.getBatteryCapacity() - 10);//Decreases the battery by 10%
                    }

                } else {
                    if (MySimulation.paused)//Pauses the Boat immediately
                        pausedBoat();
                    else {
                        bt.setPosY(bt.getPosY() + 1);
                        updateCanvas();//Updates the canvas for smooth movement of Boat. 
                        timeSpeedDelay();
                        for (int i = 0; i < flagLoop; i++) {
                            if (MySimulation.paused)//Pauses the Boat immediately
                                pausedBoat();
                            else if(!done){
                            	break;
                            }
                            bt.setPosX(bt.getPosX() + 1);
                            cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        }
                        for (int i = 0; i < flagLoop; i++) {
                            if (MySimulation.paused)//Pauses the Boat immediately
                                pausedBoat();
                            else if(!done){
                            	break;
                            }
                            bt.setPosY(bt.getPosY() - 1);
                            cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        }
                    }
                }
                if(done)
                flagLoop++;//Increases the boatRule2 algorithm variable to alternate movement in the next loop
            }
        }
    }
    
    /**
	 * Method to implement 'Rule 3' Boat Movement 
	 */
    public void boatRule3() {
        if (done) {
        	// Checks if Load Capacity or Battery Capacity is full and calls fillOil 
            if (bt.getLoadCapacity() * 100 / 8000 > 99 || bt.getBatteryCapacity() < 5) {
                fillOil();
            } else {
                int oldPosX = bt.getPosX();
                int gridW = OceanGrid.gridWidth;

                while (bt.getPosX() < gridW - 1 && done) {
                    if (MySimulation.paused)//Pauses the Boat immediately
                        pausedBoat();
                    else{
                        cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        if(done)
                        bt.setPosX(bt.getPosX() + 1);
                    }
                }
                cleanOil(bt.getPosX(), bt.getPosY());
                if(done) bt.setPosY(bt.getPosY() + 1);
                while (bt.getPosX() > oldPosX && done) {
                    if (MySimulation.paused)//Pauses the Boat immediately
                        pausedBoat();
                    else {
                        cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                        if(done)
                        bt.setPosX(bt.getPosX() - 1);
                    }
                }
                bt.setBatteryCapacity(bt.getBatteryCapacity() - 10);//Decreases the battery by 10%
                cleanOil(bt.getPosX(), bt.getPosY());//Method call to check and clean the oil in a particular position
                if(done) bt.moveTo(0, bt.getPosY() + 1);
            }
        }
    }

    /**
	 * Method to remove Oil from a OceanGrid box 
	 */
    public void cleanOil(int newPosX, int newPosY) {
        bt.setStatus("CLEANING");// Updates status of Boat to CLEANING. Displays on South Panel. 
        if (bt.getLoadCapacity() * 100 / 8000 < 100 ) {
            if (OceanGrid.gridData[newPosX][newPosY].oilSpread > 0 ) {
                bt.setTotalOil(bt.getTotalOil() + OceanGrid.gridData[newPosX][newPosY].oilSpread);//Updates the variable that tracks Oil cleaned by the Boat
                bt.setLoadCapacity(bt.getLoadCapacity() + OceanGrid.gridData[newPosX][newPosY].oilSpread);// Increases Load Capacity of the Boat
                OceanGrid.gridData[newPosX][newPosY].oilSpread = 0;//Cleans the area
                OceanGrid.borderOil.remove(OceanGrid.gridData[newPosX][newPosY]);
            } 
            else if (OceanGrid.gridData[newPosX][newPosY].oilSpread == -2) {//This checks for LandMass with OIl around it.
                OceanGrid.gridData[newPosX][newPosY].oilSpread = -1;//This changes it back to -1 to display on the grid as a Land Mass.
                bt.setTotalOil(bt.getTotalOil() + 20);//Updates the variable that tracks Oil cleaned by the Boat
                bt.setLoadCapacity(bt.getLoadCapacity() + 20);// Increases Load Capacity of the Boat
                OceanGrid.borderOil.remove(OceanGrid.gridData[newPosX][newPosY]);
            }
        }       
        updateCanvas();//Updates the canvas for smooth movement of Boat. 
        timeSpeedDelay();
    }

    /**
	 * Method to sleep based on Boat speed.
	 */
    public void timeSpeedDelay() {
        try {
            Thread.sleep((int)(1000 / bt.getSpeed()));
        } catch (Exception e) {};
    }

    /**
	 * Method to pause the boat and change STATUS in GUI
	 */
    public void pausedBoat() {
        bt.setStatus("PAUSED");  // Updates status of Boat to PAUSED. Displays on South Panel.  
        updateCanvas();//Updates the canvas for smooth movement of Boat. //Updates the canvas for smooth movement of Boat. 
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
	 * Method to move Boat to (0,0) to Refill Battery and unload oil 
	 */
    public void fillOil() {
        while (bt.getPosX() > 0) {
            try {
                Thread.sleep((int)(1000 / bt.getSpeed()));
            } catch (Exception e) {};
            bt.setPosX(bt.getPosX() - 1);
            updateCanvas();//Updates the canvas for smooth movement of Boat. 
        }
        while (bt.getPosY() > 0) {
            try {
                Thread.sleep((int)(1000 / bt.getSpeed()));
            } catch (Exception e) {};
            bt.setPosY(bt.getPosY() - 1);
            updateCanvas();//Updates the canvas for smooth movement of Boat. 
        }
        bt.setStatus("REFILL");
        updateCanvas();//Updates the canvas for smooth movement of Boat. 
        flag = 0;
        if (flagLoop > 1) flagLoop = 1;
        try {
            Thread.sleep(2000);
        } catch (Exception e) {};
        bt.setBatteryCapacity(100);// Recharges Boat to 100%
        bt.setLoadCapacity(0);// Unloads the Oil and returns 
    }

    /**
     * Perform an update on the simulation
     */
    private void updateCanvas(){
    	setChanged();
    	notifyObservers(this); // Sends a copy of the simulation
    }
    
    /**
	 * run method for Thread
	 */
    public void run() {
        while (done) {
            if (MySimulation.paused)//Pauses the Boat immediately
                pausedBoat();
            else {//Boat Rule decided by Combo Box
                if (boatRule == 1)
                    boatRule1();
                else if (boatRule == 2) {
                    boatRule2();
                } else if (boatRule == 3) {
                    boatRule3();
                }
            }
        }
        
    }
}