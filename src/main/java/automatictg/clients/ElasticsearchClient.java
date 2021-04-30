package automatictg.clients;

import automatictg.core.Slot;
import automatictg.core.Vehicle;
import automatictg.utility.ApplicationConfig;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ElasticsearchClient extends BaseClient{
    Scanner sc=new Scanner(System.in);
    ApplicationConfig appConfig=new ApplicationConfig();
    Slot slot=new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")),Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    @Override
    public void vehicleEntry() {
        boolean exists = appConfig.getElasticClient().exists(getRequest, RequestOptions.DEFAULT);
    }

    @Override
    public void vehicleExit() {
        System.out.print("Enter Registration No :");
        String regNo= sc.nextLine();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("RegistrationNumber",regNo));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("parking_system");
        searchRequest.source(sourceBuilder);
        System.out.println(searchRequest);

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

    public  void createIndex(){
        for (int i = 1; i <= slot.getCapacity(); i++) {
            String id=String.valueOf(i);
            IndexRequest request = new IndexRequest("parking_system")
                    .id(id)
                    .source("RegistrationNumber", "NULL",
                            "Colour", "NULL",
                            "Slot",id);
            try {
                appConfig.getElasticClient().index(request, RequestOptions.DEFAULT);
            } catch(ElasticsearchException | IOException ex) {
                ex.printStackTrace();

            }
        }
    }
}
