package automatictg.clients;
import automatictg.core.Vehicle;

import java.util.Scanner;

public abstract class BaseClient {
    Scanner sc=new Scanner(System.in);

    public Vehicle registerVehicle() {
        System.out.print("Enter Vehicle No :");
        String regNo=sc.nextLine();
        System.out.print("Enter Vehicle Colour :");
        String colour=sc.nextLine();
        //Validating Input Info
        String colourRegex="[a-zA-Z]+";
        String regNoRegex="^[a-zA-Z]{2}[0-9]{2}[a-zA-Z]{2}[0-9]{4}";
        if(regNo.matches(regNoRegex) && colour.matches(colourRegex)) {
            Vehicle vehicle=new Vehicle();
            vehicle.setRegNo(regNo);
            vehicle.setColour(colour);
            return vehicle;
        }
        else {
            return null;
        }

    }
    public abstract  void vehicleEntry();
    public abstract void vehicleExit();
    public abstract void searchByRegNo();
    public abstract void regNumberByColour();
    public abstract void slotByColour();
}
