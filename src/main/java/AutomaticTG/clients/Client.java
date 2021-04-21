package AutomaticTG.clients;
import AutomaticTG.core.Vehicle;

import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;

public abstract class Client {
    Scanner sc=new Scanner(System.in);
    public String configFile(){
        Properties properties=null;
        String client="";
        try{
            properties=new Properties();
            properties.load(new FileReader("src/main/resources/config.properties"));
            client=properties.getProperty("CLIENT");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return client;
    }

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
