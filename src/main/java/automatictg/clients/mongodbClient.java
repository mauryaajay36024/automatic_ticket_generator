package automatictg.clients;
import automatictg.core.Vehicle;
import automatictg.utility.ApplicationConfig;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class mongodbClient extends BaseClient{
    ApplicationConfig appConfig=new ApplicationConfig();

    @Override
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

    private void displayTicket(String regNo) {
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

    private boolean isSlotAvailable() {
        return true;
    }

    @Override
    public void vehicleExit() {

    }

    @Override
    public void searchByRegNo() {

    }

    @Override
    public void regNumberByColour() {

    }

    @Override
    public void slotByColour() {

    }
}
