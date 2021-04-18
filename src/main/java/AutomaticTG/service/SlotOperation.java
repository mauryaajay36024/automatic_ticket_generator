package AutomaticTG.service;
import java.util.Scanner;
import AutomaticTG.core.Slot;
import AutomaticTG.core.Vehicle;

public class SlotOperation {
	Scanner sc=new Scanner(System.in);
	AutomaticTG.core.Slot slot=new Slot(2,2);
	
	public void registerVehicle() {
		boolean validInfo=true;
		System.out.print("Enter Vehicle No :");
		String regNo=sc.nextLine();
		System.out.print("Enter Vehicle Colour :");
		String colour=sc.nextLine();
		
		
		 //To check if same registration vehicle is not available
		for (int i = 0; i < slot.getParkingSlots().length; i++) {
			if(Slot.parkingSlots[i]!=null && slot.getParkingSlots()[i].getRegNo().equals(regNo)){
				validInfo=false;
				System.err.println("Duplicate Info ");
				break;
			}
		}
		if(validInfo) {
			validator(regNo, colour);
		}		
	}
	
	 //To check if entered info is valid or not
	public void validator(String regNo,String colour) {
		String colourRegex="[a-zA-Z]+";
		String regNoRegex="^[a-zA-Z]{2}[0-9]{2}[a-zA-Z]{2}[0-9]{4}";
		
		if(regNo.matches(regNoRegex) && colour.matches(colourRegex)) {
			Vehicle vehicle=new Vehicle();
			vehicle.setRegNo(regNo);
			vehicle.setColour(colour);
			ticketGenerator(vehicle);
			}
		else {
			System.err.println("Wrong Input !!!");
		}	
	}
	public void ticketGenerator(Vehicle vehicle) {
		for (int i = 0; i < slot.getParkingSlots().length; i++){
			if(slot.getParkingSlots()[i]==null) {
				slot.getParkingSlots()[i]=vehicle;
				// Now print Ticket
				System.out.println("______Ticket_____");
				System.out.println(slot.getParkingSlots()[i]);
				System.out.println("Vechicle slot number :"+ (i+1));
				break;
			}
		}
	}
	public void vehicleExit(){
		boolean vehicleFound=false;
		System.out.print("Enter vehicle Registration Number :");
		String regNo=sc.nextLine();
		for (int i = 0; i < Slot.parkingSlots.length; i++) {
			if(Slot.parkingSlots[i].getRegNo().equals(regNo)) {
				Slot.parkingSlots[i]=null;
				System.out.println("Slot Number "+(i+1)+" is now Avialable ");
				vehicleFound=true;
				break;
			}			
		}
		if(!vehicleFound) {
			System.err.println("Vehicle Not Found !!!");
		}	
	}
}
	