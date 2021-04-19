package AutomaticTG.mysqlDatabase;

import AutomaticTG.core.Slot;
import AutomaticTG.core.Vehicle;
import AutomaticTG.utility.MysqlDb;

import java.sql.*;

public class MysqlService {
    MysqlDb mysqlDb=new MysqlDb();
    //Connection is created here with Mysql Database
    Connection connection=mysqlDb.createConnection();

    public void addToDb(String regNo,String colour,int slot){
        String qry="INSERT INTO parking_system values(?,?,?)";
        try {
            PreparedStatement statement=connection.prepareStatement(qry);
            statement.setString(1,regNo);
            statement.setString(2,colour);
            statement.setInt(3,slot);
            statement.executeUpdate();
            System.out.println("");
            System.out.println("Inserted Into Database");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeFromDb(String regNo){
        String qry="DELETE FROM parking_system where registrationNumber=?";
        try {
            PreparedStatement statement=connection.prepareStatement(qry);
            statement.setString(1,regNo);
            statement.executeUpdate();
            System.out.println("");
            System.out.println("Deleted from Database");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Before performing any operation, data must be load in RAM.
     * This method will load the data into array.
     */
    public void loadDataToArray(Slot slot){
        try {
            Statement statement =connection.createStatement();

            String qry="SELECT * FROM parking_system";
            ResultSet rs=statement.executeQuery(qry);
            while(rs.next()){
                Vehicle vehicle=new Vehicle();
                vehicle.setRegNo(rs.getString(1));
                vehicle.setColour(rs.getString(2));
                int index=rs.getInt(3);
                /**
                 * Slot no start from 1 so  index=index-1
                 */
                slot.getParkingSlots()[index-1]=vehicle;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
