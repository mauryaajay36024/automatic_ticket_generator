package automatictg.utility;
import com.mongodb.MongoClient;
import java.io.FileReader;
import java.sql.*;
import java.util.Properties;

public class ApplicationConfig {
    private static Connection connection;
    private static Statement statement;
    private  String client;
    private String host;
    private String mongoPort;
    Properties properties=null;
    MongoClient mongo=null;

    public ApplicationConfig() {
        try{
            properties=new Properties();
            properties.load(new FileReader("src/main/resources/config.properties"));
            this.client=properties.getProperty("CLIENT");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMongoPort() {
        return mongoPort;
    }

    public void setMongoPort(String mongoPort) {
        this.mongoPort = mongoPort;
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
    public void mysqlConnection(){
        try{
            Class.forName(properties.getProperty("DRIVER"));
            this.setConnection(DriverManager.getConnection(properties.getProperty("URL"),properties.getProperty("USER"),properties.getProperty("PASSWORD")));
            this.setStatement(connection.createStatement());
        }catch (Exception e){
            System.out.println( e.getMessage());

        }
    }
    public void mongoConnection(){
        this.setHost(properties.getProperty("HOST"));
        this.setMongoPort(properties.getProperty("mongoPort"));
        mongo = new MongoClient( "localhost" , 27017 );
    }
}
