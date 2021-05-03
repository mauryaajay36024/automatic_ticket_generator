package automatictg.clients;

import automatictg.core.Slot;
import automatictg.core.Vehicle;
import automatictg.utility.ApplicationConfig;
import com.sun.tools.internal.ws.wsdl.document.soap.SOAPUse;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.*;


public class ElasticsearchClient extends BaseClient{
    Scanner sc=new Scanner(System.in);
    ApplicationConfig appConfig=new ApplicationConfig();
    Slot slot=new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")),Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    @Override
    public void vehicleEntry() {
        Vehicle vehicle=registerVehicle();
        boolean parkingFull=true;
        if(vehicle !=null && !isDuplicateInfo(vehicle.getRegNo())){
            try {
                SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
                sourceBuilder.query(QueryBuilders.matchQuery("RegistrationNumber","NULL"));

                SearchRequest searchRequest = new SearchRequest("parking_system");
                searchRequest.source(sourceBuilder);

                SearchResponse response=appConfig.getElasticClient().search(searchRequest,RequestOptions.DEFAULT);

                Map<String, Object> map = null;
                int id=slot.getCapacity();
                for (SearchHit hit : response.getHits()) {
                    map=hit.getSourceAsMap();
                     if((int)map.get("Slot")<id){
                         id=(int)map.get("Slot");
                     }
                    parkingFull = false;
                }
                if( !parkingFull) {
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("RegistrationNumber", vehicle.getRegNo().toUpperCase());
                    updateMap.put("Colour", vehicle.getColour().toUpperCase());
                    updateMap.put("Slot", id);

                    IndexRequest request = new IndexRequest("parking_system");
                    request.id(String.valueOf(id));
                    request.source(updateMap);
                    IndexResponse indexResponseUpdate = appConfig.getElasticClient().index(request, RequestOptions.DEFAULT);

                    //Display Ticket
                    System.out.println("____________Ticket___________");
                    System.out.println("Registration No :" + vehicle.getRegNo().toUpperCase());
                    System.out.println("Vehicle Colour  :" + vehicle.getColour().toUpperCase());
                    System.out.println("Vehicle Slot    :" + indexResponseUpdate.getId());
                }
                else {
                    System.out.println();
                    System.out.println("Parking is full");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else {
            System.out.println("Wrong Input/Duplicate info");
        }
    }

    @Override
    public void vehicleExit() {
        boolean vehicleAvailable=false;
        System.out.print("Enter Registration No :");
        String regNo= sc.nextLine();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("RegistrationNumber",regNo));

            SearchRequest searchRequest = new SearchRequest("parking_system");
            searchRequest.source(sourceBuilder);

            SearchResponse response=appConfig.getElasticClient().search(searchRequest,RequestOptions.DEFAULT);

            for (SearchHit hit : response.getHits()){
                Map<String, Object> map = hit.getSourceAsMap();
                Object registrationNumber=map.get("RegistrationNumber");
                String id=String.valueOf(map.get("Slot"));//slot no is same as _id

                if(registrationNumber.equals(regNo)){
                    Map<String, Object> updateMap = new HashMap<>();
                    updateMap.put("RegistrationNumber","NULL");
                    updateMap.put("Colour","NULL");
                    UpdateRequest request = new UpdateRequest("parking_system", id).doc(updateMap);
                    UpdateResponse updateResponse= appConfig.getElasticClient().update(request, RequestOptions.DEFAULT);
                    System.out.println("Slot Number "+ updateResponse.getId()+" is now available");
                    vehicleAvailable=true;
                    break;
                }
            }
            if(!vehicleAvailable){
                System.out.println("No vehicle available  with "+regNo+" Registration");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void searchByRegNo() {
        System.out.print("Enter Registration No :");
        String regNo= sc.nextLine().toUpperCase();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("RegistrationNumber",regNo));
            SearchRequest searchRequest = new SearchRequest("parking_system");
            searchRequest.source(sourceBuilder);

            SearchResponse response=appConfig.getElasticClient().search(searchRequest,RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits()){
                Map<String, Object> map = hit.getSourceAsMap();
                Object registrationNumber=map.get("RegistrationNumber");
                if(registrationNumber.equals(regNo)){
                    System.out.println("Vehicle parked at slot No :"+map.get("Slot"));
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void regNumberByColour() {
        System.out.print("Enter vehicle colour :");
        String colour= sc.nextLine().toUpperCase();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("Colour",colour));
            SearchRequest searchRequest = new SearchRequest("parking_system");
            searchRequest.source(sourceBuilder);

            SearchResponse response=appConfig.getElasticClient().search(searchRequest,RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits()){
                Map<String, Object> map = hit.getSourceAsMap();
                Object vehicleColour=map.get("Colour");
                if(vehicleColour.equals(colour)){
                    System.out.println("Vehicle Registration Number :"+map.get("RegistrationNumber"));
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void slotByColour() {
        System.out.print("Enter vehicle colour :");
        String colour= sc.nextLine().toUpperCase();
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("Colour",colour));
            SearchRequest searchRequest = new SearchRequest("parking_system");
            searchRequest.source(sourceBuilder);

            SearchResponse response=appConfig.getElasticClient().search(searchRequest,RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits()){
                Map<String, Object> map = hit.getSourceAsMap();
                Object vehicleColour=map.get("Colour");
                if(vehicleColour.equals(colour)){
                    System.out.println("Vehicle slot Number :"+map.get("Slot"));
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public boolean isDuplicateInfo(String regNo){
        try {
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(QueryBuilders.matchQuery("RegistrationNumber",regNo));
            SearchRequest searchRequest = new SearchRequest("parking_system");
            searchRequest.source(sourceBuilder);

            SearchResponse response=appConfig.getElasticClient().search(searchRequest,RequestOptions.DEFAULT);
            for (SearchHit hit : response.getHits()){
                Map<String, Object> map = hit.getSourceAsMap();
                Object registrationNumber=map.get("RegistrationNumber");
                if(registrationNumber.equals(regNo)){
                    return true;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
