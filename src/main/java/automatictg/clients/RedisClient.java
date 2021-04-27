package automatictg.clients;


import automatictg.core.Slot;
import automatictg.core.Vehicle;
import automatictg.utility.ApplicationConfig;
import redis.clients.jedis.Jedis;

import java.util.*;

public class RedisClient extends BaseClient{
    Scanner sc=new Scanner(System.in);
    ApplicationConfig appConfig=new ApplicationConfig();
    Slot slot=new Slot(Integer.parseInt(appConfig.getProperties().getProperty("FLOOR")),Integer.parseInt(appConfig.getProperties().getProperty("CAPACITY")));

    @Override
    public void vehicleEntry() {
        Vehicle vehicle =registerVehicle();
        if(vehicle !=null){
            for (int i = 1; i <= slot.getCapacity(); i++) {
                Map<String,String> vehicleInfo=appConfig.getJedis().hgetAll(String.valueOf(i));
                Set info=vehicleInfo.entrySet();
                Iterator itr=info.iterator();
                while (itr.hasNext()){
                    Map.Entry m1= (Map.Entry) itr.next();
                    if(m1.getValue().equals("null")){
                        System.out.println(m1);
                    }
                }


            }
        }

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
    public void createDictionary() {
        Jedis jedis = appConfig.getJedis();
        Map<String,String> info= new HashMap<>();
        for (int i = 0; i <= slot.getCapacity(); i++) {
            info.put("Registration_Number","null");
            info.put("Colour","null");
            info.put("Slot",String.valueOf(i));
            jedis.hmset(String.valueOf(i),info);

        }
    }

}
