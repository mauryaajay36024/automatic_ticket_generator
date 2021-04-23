package automatictg.utility;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class ApplicationConfig {
    private Connection connection;
    private Statement statement;
    private String client;

    public ApplicationConfig() {
        try{
            Properties properties=new Properties();
            properties.load(new FileReader("src/main/resources/config.properties"));
            Class.forName(properties.getProperty("DRIVER"));
            this.setConnection(DriverManager.getConnection(properties.getProperty("URL"),properties.getProperty("USER"),properties.getProperty("PASSWORD")));
            this.setStatement(connection.createStatement());
            this.setClient(properties.getProperty("CLIENT"));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public void setClient(String client) {
        this.client = client;
    }
    public String getClient(){
        return client;
    }

}
