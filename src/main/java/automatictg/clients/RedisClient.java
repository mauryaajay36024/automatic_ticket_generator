package automatictg.clients;
import automatictg.core.Slot;
import automatictg.core.Vehicle;
import automatictg.utility.ApplicationConfig;
import redis.clients.jedis.Jedis;
import java.util.*;

public class RedisClient extends BaseClient{
    Scanner sc=new Scanner(System.in);
    ApplicationConfig appConfig=new ApplicationConfig();
    Slot slot=new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")),Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    @Override
    public void vehicleEntry() {
        Vehicle vehicle =registerVehicle();
        boolean isSlotAvailable=false;
        if(vehicle !=null && !duplicateVehicle(vehicle)){
            for (int i = 1; i <= slot.getCapacity(); i++) {
                String index=String.valueOf(i);
                Map<String,String> vehicleInfo= new HashMap<>();
                vehicleInfo.put("registrationNumber",vehicle.getRegNo().toUpperCase());
                vehicleInfo.put("colour",vehicle.getColour().toUpperCase());
                if(!appConfig.getJedis().hexists(index,"registrationNumber")){
                    appConfig.getJedis().hmset(index,vehicleInfo);
                    System.out.println("Data is inserted to db");
                    displayTicket(index);
                    isSlotAvailable=true;
                    break;
                }
            }
            if(!isSlotAvailable){
                System.out.println();
                System.out.println("Parking is full");
                System.out.println();
            }
        }
        else{
            System.out.println("Wrong Input or Duplicate info");
        }
    }

    @Override
    public void vehicleExit() {
        System.out.print("Enter vehicle Registration Number :");
        String regNo=sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index=String.valueOf(i);
            if(appConfig.getJedis().hget(index,"registrationNumber").equalsIgnoreCase(regNo)){
                appConfig.getJedis().hdel(index,"registrationNumber");
                appConfig.getJedis().hdel(index,"colour");
                System.out.println("Slot "+appConfig.getJedis().hget(index,"Slot")+" is now available");
                break;
            }
        }
    }

    public void displayTicket(String index){
        System.out.println(" _________________Ticket__________________");
        System.out.println("|Vehicle Registration Number : " + appConfig.getJedis().hget(index,"registrationNumber")+"|");
        System.out.println("|Vehicle Colour              : " + appConfig.getJedis().hget(index,"colour"));
        System.out.println("|Vehicle Slot Number         : " + appConfig.getJedis().hget(index,"Slot"));
        System.out.println("|________________________________________|");
    }
    public boolean duplicateVehicle(Vehicle vehicle){
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index=String.valueOf(i);
            if(appConfig.getJedis().hexists(index,"registrationNumber") && appConfig.getJedis().hget(index,"registrationNumber").equalsIgnoreCase(vehicle.getRegNo())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void searchByRegNo() {
        boolean vehicleAvailable=false;
        System.out.print("Enter Registration Number :");
        String regNo= sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index=String.valueOf(i);
            if(appConfig.getJedis().hget(index,"registrationNumber").equalsIgnoreCase(regNo)){
                System.out.println("Vehicle Colour              : " + appConfig.getJedis().hget(index,"colour"));
                System.out.println("Vehicle Slot Number         : " + appConfig.getJedis().hget(index,"Slot"));
                vehicleAvailable=true;
                break;
            }
        }
        if(!vehicleAvailable){
            System.out.println("No vehicle is available with "+regNo+" Number");
        }
    }

    @Override
    public void regNumberByColour() {
        boolean vehicleAvailable=false;
        System.out.print("Enter Vehicle Colour :");
        String colour= sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index=String.valueOf(i);
            if(appConfig.getJedis().hget(index,"colour").equalsIgnoreCase(colour)){
                System.out.println("Vehicle Registration  Number  : " + appConfig.getJedis().hget(index,"registrationNumber"));
                vehicleAvailable=true;
            }
        }
        if(!vehicleAvailable){
            System.out.println("No vehicle is available with "+colour+" colour");
        }
    }

    @Override
    public void slotByColour() {
        boolean vehicleAvailable=false;
        System.out.print("Enter Vehicle Colour :");
        String colour= sc.nextLine();
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String index=String.valueOf(i);
            if(appConfig.getJedis().hget(index,"colour").equalsIgnoreCase(colour)){
                System.out.println("Vehicle Slot Number  : " + appConfig.getJedis().hget(index,"Slot"));
                vehicleAvailable=true;
            }
        }
        if(!vehicleAvailable){
            System.out.println("No vehicle is available with "+colour+" colour");
        }
    }
    public void createDictionary() {
        Jedis jedis = appConfig.getJedis();
        Map<String,String> info= new HashMap<>();
        for (int i = 0; i <= slot.getCapacity(); i++) {
            info.put("Slot",String.valueOf(i));
            jedis.hmset(String.valueOf(i),info);
        }
    }
}
