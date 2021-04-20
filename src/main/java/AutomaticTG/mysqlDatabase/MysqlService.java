package AutomaticTG.mysqlDatabase;
import AutomaticTG.core.Vehicle;
import AutomaticTG.utility.MysqlDb;

import java.sql.*;
import java.util.Scanner;

public class MysqlService {
    Scanner sc=new Scanner(System.in);
    MysqlDb mysqlDb=new MysqlDb();
    //Connection is created here with Mysql Database
    Connection connection=mysqlDb.createConnection();

    public void addToDb(Vehicle vehicle) {
        try {
            Statement statement = connection.createStatement();
            String qry1 = "Select * from parking_system";
            ResultSet rs = statement.executeQuery(qry1);
            boolean flag = false;
            while (rs.next()) {
                //To allocate slot in between if there is parking slot is available.
                if ("empty".equalsIgnoreCase(rs.getString(1))) {
                    String query = "update parking_system set registrationNumber=?,colour=?" +
                            "where registrationNumber='NULL' AND colour='NULL'";
                    PreparedStatement statement1 = connection.prepareStatement(query);
                    statement1.setString(1, vehicle.getRegNo());
                    statement1.setString(2, vehicle.getColour());
                    statement1.executeUpdate();
                    System.out.println("");
                    System.out.println("Data Inserted Into Database");
                    //Display Ticket
                    displayTicket(vehicle.getRegNo());

                    flag = true;
                }
                break;
            }//If vehicle getting slot 1st time or one after another in sequence.
            if (!flag) {
                String qry = "INSERT INTO parking_system(registrationNumber,colour) values(?,?)";
                try {
                    PreparedStatement statement2 = connection.prepareStatement(qry);
                    statement2.setString(1, vehicle.getRegNo());
                    statement2.setString(2, vehicle.getColour());
                    statement2.executeUpdate();

                    System.out.println("");
                    System.out.println("Data Inserted Into Database");
                    //Display ticket
                    displayTicket(vehicle.getRegNo());

                } catch (SQLException throwable) {
                    System.out.println(throwable.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }
    }

    public void removeFromDb(){
        System.out.print("Enter Vehicle Registration Number :");
        String regNo=sc.nextLine();
//        String s="EMPTY";
        String qry="UPDATE parking_system SET registrationNumber=?,colour=? WHERE registrationNumber=?";
        try {
            PreparedStatement statement=connection.prepareStatement(qry);
            statement.setString(1,"NULL");
            statement.setString(2,"NULL");
            statement.setString(3,regNo);
            statement.executeUpdate();
            System.out.println("");
            System.out.println("Slot is De-allocated from Database");
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
    }
    public void displayTicket(String regNo){
        try {
            Statement statement = connection.createStatement();
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
}
