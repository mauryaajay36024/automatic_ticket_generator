package automatictg;
import automatictg.clients.BaseClient;
import automatictg.clients.InMemoryClient;
import automatictg.clients.MysqlClient;
import automatictg.utility.Menu;
import automatictg.utility.ApplicationConfig;

public class Main {
	public static void main(String[] args) {

		System.out.println("\t\t\t\t Automatic Parking System");
		Menu menu=new Menu();
		//To initialize .Properties file
		ApplicationConfig applicationConfig=new ApplicationConfig();

		BaseClient client=new InMemoryClient();
		//Getting client type from property file
		String clientName=applicationConfig.getClient();
		if(clientName.equalsIgnoreCase("mySql")){
			client=new MysqlClient();
		}
		while(true) {
			try {
				switch(menu.mainMenu()) {
					case 1://Ticket generation
						client.vehicleEntry();
						break;
					case 2://Slot De-allocation
						client.vehicleExit();
						break;
					case 3://Searching
						switch(menu.searchMenu()) {
							case 1://Search by colour
								switch(menu.colourMenu()) {
									case 1://Registration numbers of all cars of a particular colour.
										client.regNumberByColour();
										break;
									case 2://Slot numbers of all slots where a car of a particular colour is parked.
										client.slotByColour();
										break;
									default:
										System.err.println("Invalid Choice !!!");
										break;
								}
								break;
							case 2://Search  by Registration Number
								client.searchByRegNo();
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
				System.err.println(e.getMessage());
			}
		}
	}
}
