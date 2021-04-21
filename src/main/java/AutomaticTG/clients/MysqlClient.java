package AutomaticTG.clients;
import AutomaticTG.core.Vehicle;
import AutomaticTG.utility.MysqlDb;

import java.sql.*;
import java.util.Scanner;

public class MysqlClient extends Client{
    Scanner sc=new Scanner(System.in);
    MysqlDb mysqlDb=new MysqlDb();
    //Connection is created here with Mysql Database
    Connection connection=mysqlDb.createConnection();
    Statement statement;
    {
        try {
            statement = connection.createStatement();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public void vehicleEntry() {
        /**
         * registerVehicle() will only return vehicle object when info entered are valid.
         */
        Vehicle vehicle=registerVehicle();
        if(vehicle instanceof Vehicle){
            try {
                String qry1 = "SELECT * FROM PARKING_SYSTEM";
                ResultSet rs = statement.executeQuery(qry1);
                boolean flag = false;
                while (rs.next()) {
                    //To allocate slot in between if there is parking slot is available.
                    if (rs.getString(1)==null) {
                        String query = "UPDATE PARKING_SYSTEM SET registrationNumber=?,colour=?" +
                                "WHERE slot=?";
                        PreparedStatement statement1 = connection.prepareStatement(query);
                        statement1.setString(1, vehicle.getRegNo());
                        statement1.setString(2, vehicle.getColour());
                        statement1.setInt(3,rs.getInt(3));
                        statement1.executeUpdate();
                        System.out.println();
                        System.out.println("Data Inserted to Database");
                        //Display Ticket
                        displayTicket(vehicle.getRegNo());
                        flag = true;
                        break;
                    }

                }//If vehicle getting slot 1st time or one after another in sequence.
                if (!flag) {
                    String qry = "INSERT INTO PARKING_SYSTEM(registrationNumber,colour) VALUES(?,?)";
                    try {
                        PreparedStatement statement2 = connection.prepareStatement(qry);
                        statement2.setString(1, vehicle.getRegNo());
                        statement2.setString(2, vehicle.getColour());
                        statement2.executeUpdate();
                        System.out.println();
                        System.out.println("Data Inserted Into Database");
                        //Display ticket
                        displayTicket(vehicle.getRegNo());
                    } catch (SQLException throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            System.out.println("Wrong Input");
        }

    }
    public void displayTicket(String regNo){
        try {
            String qry = "SELECT * FROM parking_system";
            ResultSet rs = statement.executeQuery(qry);
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
        String qry="UPDATE parking_system SET registrationNumber=?,colour=? WHERE registrationNumber=?";
        try {
            PreparedStatement statement=connection.prepareStatement(qry);
            statement.setString(1,null);
            statement.setString(2,null);
            statement.setString(3,regNo);
            System.out.println();
            System.out.println("Slot Number is De-allocated from Database");
            statement.executeUpdate();
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
    }
    public void searchByRegNo() {
        boolean vehicleFound = false;
        System.out.print("Enter Registration no to search vehicle Slot :");
        String regNo = sc.nextLine();
        try {
            String qry = "SELECT * FROM PARKING_SYSTEM";
            ResultSet rs = statement.executeQuery(qry);
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
            ResultSet rs = statement.executeQuery(qry);
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
            ResultSet rs = statement.executeQuery(qry);
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
}
