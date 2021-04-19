package AutomaticTG.mysqlDatabase;

import AutomaticTG.core.Slot;
import AutomaticTG.core.Vehicle;
import AutomaticTG.utility.MysqlDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class VehicleSearchFromDb {
    Scanner sc=new Scanner(System.in);
    MysqlDb mysqlDb=new MysqlDb();
    //Connection is created here with Mysql Database
    Connection connection=mysqlDb.createConnection();

    public void searchByRegNo() {
        boolean vehicleFound=false;
        System.out.print("Enter Registration no to search vehicle Slot :");
        String regNo=sc.nextLine();
        try {
            Statement statement =connection.createStatement();

            String qry="SELECT * FROM parking_system";
            ResultSet rs=statement.executeQuery(qry);
            while(rs.next()){
                String registrationNumber=rs.getString(1);
                if(regNo.equals(registrationNumber)){
                    System.out.println("Slot Number for "+regNo+" is "+rs.getInt(3));
                    break;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void regNumberByColour() {
        boolean vehicleFound=false;
        System.out.print("Enter Colour to find Registration numbers of all cars of a particular color :");
        String colour=sc.nextLine();
        for (int i = 0; i < Slot.parkingSlots.length; i++) {
            if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getColour().equalsIgnoreCase(colour)) {
                System.out.println("Registration Number : "+Slot.parkingSlots[i].getRegNo());
                vehicleFound=true;
            }
        }
        if(!vehicleFound) {
            System.err.println("No Vehicle Avialable with " +colour+ " Colour !!!");
        }
    }

    public void slotByColour() {
        boolean vehicleFound=false;
        System.out.print("Enter Colour to find slot numbers of all Vehicle of a particular color is parked");
        String colour=sc.nextLine();
        for (int i = 0; i < Slot.parkingSlots.length; i++) {
            if(Slot.parkingSlots[i]!=null && Slot.parkingSlots[i].getColour().equalsIgnoreCase(colour)) {
                System.out.println("Slot Number : "+(i+1));
                vehicleFound=true;
            }
        }
        if(!vehicleFound) {
            System.err.println("No Vehicle Avialable with" +colour+ "Colour !!!");
        }
    }
}
