package AutomaticTG.utility;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class MysqlDb {
    public Connection createConnection(){
        Connection connection=null;
        try{
            Properties properties=new Properties();
            properties.load(new FileReader("src/main/resources/config.properties"));
            Class.forName(properties.getProperty("DRIVER"));
            connection= DriverManager.getConnection(properties.getProperty("URL"),properties.getProperty("USER"),properties.getProperty("PASSWORD"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            return connection;
        }
    }

}
