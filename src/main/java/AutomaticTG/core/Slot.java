package AutomaticTG.core;

public class Slot{
	private Integer noOfFloor;
	private Integer spaceAtEachFloor;
	public  static Vehicle[] parkingSlots;

	public Slot(Integer noOfFloor, Integer spaceAtEachFloor) {
		this.noOfFloor = noOfFloor;
		this.spaceAtEachFloor = spaceAtEachFloor;
		Slot.parkingSlots=new Vehicle[noOfFloor*spaceAtEachFloor];
	}

	public  Vehicle[] getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Vehicle[] parkingSlots) {
		Slot.parkingSlots = parkingSlots;
	}

	public Integer getNoOfFloor() {
		return noOfFloor;
	}
	public void setNoOfFloor(Integer noOfFloor) {
		this.noOfFloor = noOfFloor;
	}
	public Integer getSpaceAtEachFloor() {
		return spaceAtEachFloor;
	}
	public void setSpaceAtEachFloor(Integer spaceAtEachFloor) {
		this.spaceAtEachFloor = spaceAtEachFloor;
	}
}
