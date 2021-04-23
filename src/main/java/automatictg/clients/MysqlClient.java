package automatictg.clients;
import automatictg.core.Slot;
import automatictg.core.Vehicle;
import automatictg.utility.ApplicationConfig;
import java.sql.*;
import java.util.Scanner;

public class MysqlClient extends BaseClient {
    Scanner sc=new Scanner(System.in);
    ApplicationConfig appConfig=new ApplicationConfig();
    Slot slot=new Slot(5,20);

    public void vehicleEntry() {
        // registerVehicle method will only return vehicle object when entered info are valid.
        Vehicle vehicle=registerVehicle();
        if(vehicle !=null) {
            if (isSlotAvailable()) {
                try {
                    String qry1 = "SELECT registrationNumber,colour,slot FROM PARKING_SYSTEM";
                    ResultSet rs = appConfig.getStatement().executeQuery(qry1);
                    while (rs.next()) {
                        if (rs.getString(1) == null) {
                            String query = "UPDATE PARKING_SYSTEM SET registrationNumber=?,colour=?" +
                                    "WHERE slot=?";
                            PreparedStatement statement1 = appConfig.getConnection().prepareStatement(query);
                            statement1.setString(1, vehicle.getRegNo());
                            statement1.setString(2, vehicle.getColour());
                            statement1.setInt(3, rs.getInt(3));
                            statement1.executeUpdate();
                            System.out.println();
                            System.out.println("Data Inserted to Database");
                            displayTicket(vehicle.getRegNo());
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println();
                System.out.println("Parking is full !!!");
            }
        }else {
            System.out.println("Wrong Input");
        }

    }
    public void displayTicket(String regNo){
        try {
            String qry = "SELECT * FROM PARKING_SYSTEM";
            ResultSet rs = appConfig.getStatement().executeQuery(qry);
            while (rs.next()) {
                String registrationNumber = rs.getString(1);
                if (regNo.equalsIgnoreCase(registrationNumber)) {
                    System.out.println("________Ticket________");
                    System.out.println("Registration Number:"+rs.getString(1));
                    System.out.println("Vehicle Colour :"+rs.getString(2));
                    System.out.println("Slot Number :"+ rs.getInt(3));
                    break;
                }
            }
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }

    }

    public void vehicleExit(){
        System.out.print("Enter Vehicle Registration Number :");
        String regNo=sc.nextLine();
        if(isVehicleAvailable(regNo)) {
            String qry = "UPDATE parking_system SET registrationNumber=?,colour=? WHERE registrationNumber=?";
            try {
                PreparedStatement statement = appConfig.getConnection().prepareStatement(qry);
                statement.setString(1, null);
                statement.setString(2, null);
                statement.setString(3, regNo);
                System.out.println();
                System.out.println("Slot Number is De-allocated from Database");
                statement.executeUpdate();
            } catch (SQLException throwable) {
                System.out.println(throwable.getMessage());
            }
        }
        else{
            System.out.println("Vehicle is not available ");
        }
    }
    public void searchByRegNo() {
        boolean vehicleFound = false;
        System.out.print("Enter Registration no to search vehicle Slot :");
        String regNo = sc.nextLine();
        try {
            String qry = "SELECT * FROM PARKING_SYSTEM";
            ResultSet rs = appConfig.getStatement().executeQuery(qry);
            while (rs.next()) {
                String registrationNumber = rs.getString(1);
                if (registrationNumber !=null && registrationNumber.equalsIgnoreCase(regNo)) {
                    vehicleFound = true;
                    System.out.println("Slot Number for " + regNo + " is " + rs.getInt(3));
                    break;
                }
            }
            if (!vehicleFound) {
                System.err.println("Vehicle not available");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public void regNumberByColour() {
        boolean vehicleFound = false;
        System.out.print("Enter Colour to search all vehicles Registration Number :");
        String vehicleColour = sc.nextLine();
        try {
            String qry = "SELECT * FROM PARKING_SYSTEM";
            ResultSet rs = appConfig.getStatement().executeQuery(qry);
            while (rs.next()) {
                String colour = rs.getString(2);
                if (colour !=null && colour.equalsIgnoreCase(vehicleColour)) {
                    vehicleFound = true;
                    System.out.println("Registration Number : " + rs.getString(1));
                }
            }
            if (!vehicleFound) {
                System.err.println("No vehicle is available with " + vehicleColour+" Colour");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public void slotByColour() {
        boolean vehicleFound = false;
        System.out.print("Enter Colour to find slot numbers of all Vehicle of a particular color is parked :");
        String vehicleColour = sc.nextLine();
        try {
            String qry = "SELECT * FROM parking_system";
            ResultSet rs = appConfig.getStatement().executeQuery(qry);
            while (rs.next()) {
                String colour = rs.getString(2);
                if (colour !=null && colour.equalsIgnoreCase(vehicleColour)) {
                    vehicleFound = true;
                    System.out.println("Slot Number : "+ rs.getInt(3));
                }
            }
            if (!vehicleFound) {
                System.err.println("No vehicle is available with " + vehicleColour+" Colour");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public boolean isVehicleAvailable(String regNo){
        try {
            String qry = "SELECT * FROM PARKING_SYSTEM";
            ResultSet rs = appConfig.getStatement().executeQuery(qry);
            while (rs.next()) {
                String registrationNumber = rs.getString(1);
                if (regNo.equalsIgnoreCase(registrationNumber)) {
                    return true;
                }
            }
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
        return false;
    }
    public boolean isSlotAvailable(){
        try {
            String qry = "SELECT * FROM PARKING_SYSTEM";
            ResultSet rs = appConfig.getStatement().executeQuery(qry);
            int slotNumber=-1;
            while (rs.next()) {
                if(rs.getString(1)==null && rs.getInt(3)!=0){
                    slotNumber=rs.getInt(3);
                    break;
                }
            }
            if(slotNumber!=-1 && slotNumber<=slot.getCapacity()) {
                return true;
            }
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
        return false;
    }
    public void createTable(){
        try {
            String qry1 = "CREATE TABLE IF NOT EXISTS PARKING_SYSTEM(" +
                    "registrationNumber CHAR(10) UNIQUE," +
                    "colour VARCHAR(10)," +
                    "slot int PRIMARY KEY)";

            appConfig.getStatement().execute(qry1);

            for (int i = 1; i <= slot.getCapacity(); i++) {
                String qry2 = "INSERT INTO PARKING_SYSTEM(registrationNumber,colour,slot) VALUES(?,?,?)";
                PreparedStatement statement = appConfig.getConnection().prepareStatement(qry2);
                statement.setString(1, null);
                statement.setString(2, null);
                statement.setInt(3,i);
                statement.executeUpdate();
            }
        } catch (SQLException throwable) {

        }

    }
}
