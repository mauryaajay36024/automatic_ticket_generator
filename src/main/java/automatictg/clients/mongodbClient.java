package automatictg.clients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
public class mongodbClient extends BaseClient{

    @Override
    public void vehicleEntry() {
        // Creating a Mongo client
        MongoClient mongo = new MongoClient( "localhost" , 27017 );

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential("sampleUser", "near",
                "password".toCharArray());
        System.out.println("Connected to the database successfully");

        // Accessing the database
        MongoDatabase database = mongo.getDatabase("near");
        System.out.println("Credentials ::"+ credential);
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
