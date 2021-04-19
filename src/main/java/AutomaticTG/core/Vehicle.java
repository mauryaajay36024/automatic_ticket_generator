package AutomaticTG.core;

public class Vehicle {
	
	private String regNo;
	private String colour;

	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	@Override
	public String toString() {
		return "\nVehicle Registration No:" + regNo + "\nVehicle Colour:" + colour;
	}
	
	
		
}
