/*
Assignment 5 : Autonomous Boat Simulation
Name: David Nallapu
NUID : 001530978
*/

package edu.neu.csye6200.absim;

class Boat {
	//Declaring variables for Boat class
	private String boatName, direction, status;
	private int posX, posY,heading, batteryCapacity, totalOil,loadCapacity;
	private double speed;
	
	Boat(String boatName, int posX,int posY,int heading, String status, double speed, int loadCapacity, int batteryCapacity , int totalOil){
		setBoatName(boatName);
		setPosX(posX);
		setPosY(posY);
		setHeading(heading);
		setStatus(status);
		setSpeed(speed);
		setLoadCapacity(loadCapacity);
		setBatteryCapacity(batteryCapacity);
		setTotalOil(totalOil);
	}
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	/**
	 * moveTo method with new Positions as parameters. Prints out points that Boat drives through to reach the end position. 
	 */
	public void moveTo(int newPosX, int newPosY){
		setPosX(newPosX);	
		setPosY(newPosY);
		}
	
	
	public String getBoatName() {
		return boatName;
	}

	public void setBoatName(String boatName) {
		this.boatName = boatName;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
		
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getHeading() {
		return heading;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(int loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	public int getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(int batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	//Method to print formatted Boat Instance Details 
	public String toFormattedString() {
		String st = String.format("%1$7s %2$5d %3$6d %4$8s° %5$10s %6$18s %7$20s ", getBoatName(),getPosX(),getPosY(),getHeading(),getSpeed(),getLoadCapacity(),getBatteryCapacity() );
		return st; 
	}
	public int getTotalOil() {
		return totalOil;
	}
	public void setTotalOil(int totalOil) {
		this.totalOil = totalOil;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	 
	
}



