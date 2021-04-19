package AutomaticTG.mysqlDatabase;
import AutomaticTG.utility.MysqlDb;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class VehicleSearchFromDb {
    Scanner sc = new Scanner(System.in);
    MysqlDb mysqlDb = new MysqlDb();
    //Connection is created here with Mysql Database
    Connection connection = mysqlDb.createConnection();

    public void searchByRegNo() {
        boolean vehicleFound = false;
        System.out.print("Enter Registration no to search vehicle Slot :");
        String regNo = sc.nextLine();
        try {
            Statement statement = connection.createStatement();

            String qry = "SELECT * FROM parking_system";
            ResultSet rs = statement.executeQuery(qry);
            while (rs.next()) {
                String registrationNumber = rs.getString(1);
                if (regNo.equalsIgnoreCase(registrationNumber)) {
                    vehicleFound = true;
                    System.out.println("Slot Number for " + regNo + " is " + rs.getInt(3));
                    break;
                }
            }
            if (!vehicleFound) {
                System.err.println("Vehicle not available");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void regNumberByColour() {
        boolean vehicleFound = false;
        System.out.print("Enter Colour to search all vehicles Registration Number :");
        String vehicleColour = sc.nextLine();
        try {
            Statement statement = connection.createStatement();

            String qry = "SELECT * FROM parking_system";
            ResultSet rs = statement.executeQuery(qry);
            while (rs.next()) {
                String colour = rs.getString(2);
                if (colour.equalsIgnoreCase(vehicleColour)) {
                    vehicleFound = true;
                    System.out.println("Registration Number : " + rs.getString(1));
                }
            }
            if (!vehicleFound) {
                System.err.println("No vehicle is available with " + vehicleColour+" Colour");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void slotByColour() {
        boolean vehicleFound = false;
        System.out.print("Enter Colour to find slot numbers of all Vehicle of a particular color is parked :");
        String vehicleColour = sc.nextLine();
        try {
            Statement statement = connection.createStatement();

            String qry = "SELECT * FROM parking_system";
            ResultSet rs = statement.executeQuery(qry);
            while (rs.next()) {
                String colour = rs.getString(2);
                if (colour.equalsIgnoreCase(vehicleColour)) {
                    vehicleFound = true;
                    System.out.println("Slot Number : "+ rs.getInt(3));
                }
            }
            if (!vehicleFound) {
                System.err.println("No vehicle is available with " + vehicleColour+" Colour");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
