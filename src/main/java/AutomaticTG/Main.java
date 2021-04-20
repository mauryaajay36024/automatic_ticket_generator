package AutomaticTG;
import AutomaticTG.mysqlDatabase.MysqlService;
import AutomaticTG.service.SlotOperation;
import AutomaticTG.service.VehicleSearch;
import AutomaticTG.utility.Menu;

public class Main {
	public static void main(String[] args) {
		System.out.println("\t\t\t\t Automatic Parking System");
		Menu menu=new Menu();
		SlotOperation tgService=new SlotOperation();
		MysqlService mysqlService=new MysqlService();
		//Vehicle Search service (In memory)
		VehicleSearch search=new VehicleSearch();

		while(true) {
			try {
				switch(menu.mainMenu()) {
					//Ticket generation
					case 1:
						tgService.registerVehicle();
						break;
					//Slot De-allocation
					case 2:
						//tgService.vehicleExit(); //Method to remove vehicle (In memory)
						mysqlService.removeFromDb();

						break;
					//Searching
					case 3:
						switch(menu.searchMenu()) {
							case 1:
								//Search by colour
								switch(menu.colourMenu()) {
									case 1:
										//Registration numbers of all cars of a particular colour.
										//search.regNumberByColour(); //uncomment this for in  memory
										mysqlService.regNumberByColour();
										break;
									case 2:
										//Slot numbers of all slots where a car of a particular colour is parked.
										//search.slotByColour();
										mysqlService.slotByColour();
										break;
									default:
										System.err.println("Invalid Choice !!!");
										break;
									}
									break;
							case 2:
								//Search  by Registration Number
//								search.searchByRegNo();
								mysqlService.searchByRegNo();

								break;
							}
						break;
					case 0:
						System.exit(0);
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
