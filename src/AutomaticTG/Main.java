package AutomaticTG;

import AutomaticTG.service.SlotOperation;
import AutomaticTG.service.VehicleSearch;
import AutomaticTG.utility.Menu;

public class Main {

	public static void main(String[] args) {
		System.out.println("\t\t\t\t Automatic Parking System");
		Menu menu=new Menu();
		
		SlotOperation tgService=new SlotOperation();
		VehicleSearch search=new VehicleSearch();
		
		
		while(true) {

				try {
					switch(menu.mainMenu()) {
					
					//Ticket generation
					case 1:
						tgService.registerVehicle();
						break;
					//Slot deallocation	
					case 2:
						tgService.vehicleExit();
						break;
					
					//Searching
					case 3:
						switch(menu.searchMenu()) {
						case 1:
							//Search by colour
							switch(menu.colourMenu()) {
							case 1:
								//Registration numbers of all cars of a particular colour.
								search.regNumberByColour();
								break;
							case 2:
								//Slot numbers of all slots where a car of a particular colour is parked.
								search.slotByColour();
								break;
							
							}
							break;
							
						case 2:
							//Search  by Registration Number
							search.searchByRegNo();
							break;
						
						}
						break;
					default :
						System.out.println("Invalid Input");
						break;
						
					}
				} catch (Exception e) {
					System.err.println("Invalid Input");
				}
			}
		}
	

}
