package AutomaticTG.service;
import java.util.Scanner;

import AutomaticTG.core.Slot;

public class VehicleSearch {
	Scanner sc=new Scanner(System.in);
	
	public void searchByRegNo() {
		boolean vehicleFound=false;
		System.out.print("Enter Registration no to search vehicle Slot :");
		String regNo=sc.nextLine();
		for (int i = 0; i < Slot.parkingSlots.length; i++) {
			if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getRegNo().equalsIgnoreCase(regNo)) {
				System.out.println("Slot Number is "+(i+1));
				vehicleFound=true;
				break;
			}
		}
		if(!vehicleFound) {
			System.err.println("Vehicle Not Found !!!");
		}
	}
	public void regNumberByColour() {
		boolean vehicleFound=false;
		System.out.print("Enter Colour to find Registration numbers of all cars of a particular color :");
		String colour=sc.nextLine();
		for (int i = 0; i < Slot.parkingSlots.length; i++) {
			if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getColour().equalsIgnoreCase(colour)) {
				System.out.println("Registration Number : "+Slot.parkingSlots[i].getRegNo());
				vehicleFound=true;
			}
		}
		if(!vehicleFound) {
			System.err.println("No Vehicle Avialable with " +colour+ " Colour !!!");
		}
	}

	public void slotByColour() {
		boolean vehicleFound=false;
		System.out.print("Enter Colour to find slot numbers of all Vehicle of a particular color is parked");
		String colour=sc.nextLine();
		for (int i = 0; i < Slot.parkingSlots.length; i++) {
			if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getColour().equalsIgnoreCase(colour)) {
				System.out.println("Slot Number : "+(i+1));
				vehicleFound=true;
			}
		}
		if(!vehicleFound) {
			System.err.println("No Vehicle Avialable with" +colour+ "Colour !!!");
		}	
	}	
}
