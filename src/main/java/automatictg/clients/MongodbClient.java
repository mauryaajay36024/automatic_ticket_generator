package automatictg.clients;
import automatictg.core.Slot;
import automatictg.core.Vehicle;
import automatictg.utility.ApplicationConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class MongodbClient extends BaseClient{
    Scanner sc=new Scanner(System.in);
    ApplicationConfig appConfig=new ApplicationConfig();
    Slot slot=new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")),Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));
    @Override
    public void vehicleEntry() {
        boolean flag=false;
        Vehicle vehicle=registerVehicle();
        if(vehicle!=null && !duplicateInfo(vehicle)) {
            appConfig.getUpdateFields().append("registrationNumber", vehicle.getRegNo().toUpperCase());
            appConfig.getUpdateFields().append("Colour", vehicle.getColour().toUpperCase());
            appConfig.getSetQuery().append("$set", appConfig.getUpdateFields());
            appConfig.getCollection().updateOne(Filters.eq("registrationNumber", null), appConfig.getSetQuery());
            displayTicket(vehicle.getRegNo().toUpperCase());
            flag=true;
        }

        if(!flag){
            System.out.println();
            System.out.println("Wrong Input");
        }
    }

    private void displayTicket(String regNo) {
        Document vehicleInfo = appConfig.getCollection().find(new Document("registrationNumber", regNo)).first();
        System.out.println(" _________________Ticket__________________");
        System.out.println("|Vehicle Registration Number : " + vehicleInfo.get("registrationNumber")+"|");
        System.out.println("|Vehicle Colour              : " + vehicleInfo.get("Colour"));
        System.out.println("|Vehicle Slot Number         : " + vehicleInfo.get("_id"));
        System.out.println("|________________________________________|");
    }
    public boolean duplicateInfo(Vehicle vehicle){
        Document vehicleInfo = appConfig.getCollection().find(new Document("registrationNumber", vehicle.getRegNo())).first();
        return vehicleInfo != null;
    }

    @Override
    public void vehicleExit() {
        System.out.print("Enter vehicle Registration Number :");
        String regNo=sc.nextLine().toUpperCase();
        appConfig.getUpdateFields().append("registrationNumber",null);
        appConfig.getUpdateFields().append("Colour", null);
        BasicDBObject setQuery = new BasicDBObject();
        setQuery.append("$set", appConfig.getUpdateFields());
        appConfig.getCollection().updateOne(Filters.eq("registrationNumber",regNo), setQuery);
        System.out.println("Slot is now available ");
    }

    @Override
    public void searchByRegNo() {
        boolean flag=false;
        System.out.print("Enter vehicle Registration Number :");
        String regNo=sc.nextLine().toUpperCase();
        List<Document> vehicleInfo = appConfig.getCollection().find(Filters.eq("registrationNumber", regNo)).into(new ArrayList<>());
        for (Document info : vehicleInfo) {
            System.out.println("vehicle Slot Number :"+info.get("_id"));
            flag=true;
        }
        if(!flag){
            System.out.println("No vehicle is available !!!");
        }
    }

    @Override
    public void regNumberByColour() {
        boolean flag=false;
        System.out.print("Enter vehicle Colour :");
        String colour=sc.nextLine().toUpperCase();
        List<Document> vehicleInfo = appConfig.getCollection().find(Filters.eq("Colour", colour)).into(new ArrayList<>());
        for (Document info : vehicleInfo) {
            System.out.println("vehicle Registration Number :"+info.get("registrationNumber"));
            flag=true;
        }
        if(!flag){
            System.out.println("No vehicle is available !!!");
        }
    }

    @Override
    public void slotByColour() {
        boolean flag=false;
        System.out.print("Enter vehicle Colour :");
        String colour=sc.nextLine().toUpperCase();
        List<Document> slotInfo = appConfig.getCollection().find(Filters.eq("Colour", colour)).into(new ArrayList<>());
        for (Document info : slotInfo) {
            System.out.println("Vehicle Slot Number  : " + info.get("_id"));
            flag=true;
        }
        if(!flag){
            System.out.println("No vehicle is available !!!");
        }
    }

    public void createCollection(){
        try {
            MongoCollection<Document> collection = appConfig.getDatabase().getCollection("parking_system");
            Document document = new Document();
            for (int i = 1; i <= slot.getCapacity(); i++) {
                document.append("_id", i);
                document.append("registrationNumber", null);
                document.append("Colour", null);
                collection.insertOne(document);
            }
        }catch (Exception ex){
            ex.getMessage();
        }
    }
}
