package automatictg.clients;

import automatictg.utility.ApplicationConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchClient extends BaseClient{
        ApplicationConfig appConfig=new ApplicationConfig();

    @Override
    public void vehicleEntry() {
        IndexRequest indexRequest = new IndexRequest("posts")
                .id("1")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");
        try {
            IndexResponse indexResponse = appConfig.getElasticClient().index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Data is Inserted to elasticsearch database ");
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
