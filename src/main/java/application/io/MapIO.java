package application.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.map.structure.RPGMap;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class MapIO {
    public static void saveMap(RPGMap map, String fileName){
        Gson gson = new GsonBuilder().create();

        String json = gson.toJson(Jsonifier.toJSON(map));
        try{
            PrintWriter pw = new PrintWriter(new File(fileName));
            pw.print(json);
            pw.flush();
            pw.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static RPGMap loadMap(String fileName,LoadModel load,RPGMap destination){
        try{
            File file = new File(fileName);
            if(!file.exists()){
                throw new FileNotFoundException();
            }
            load.setTotalBytes(file.length());
            load.incrementTotalBytes(5);
            long read = 0;
            String json = "";
            byte[] buffer = new byte[50];
            FileInputStream is = new FileInputStream(file);
            while ((read = is.read(buffer)) != -1) {
                String str = new String(buffer);
                json += str;
                load.incrementReadBytes(str.getBytes().length);
            }

            is.close();
            destination = Objectifier.toRPGMap(new JSONObject(json),destination,load);
            load.setTotalBytes(load.getReadBytes());
            return destination;
        }catch (Exception e){
            destination.init();
            load.finish();
            return destination;
        }
    }

//    public static IHandler serializeDataIn(){
//        String fileName= "Test.txt";
//        FileInputStream fin = new FileInputStream(fileName);
//        ObjectInputStream ois = new ObjectInputStream(fin);
//        IHandler iHandler= (IHandler) ois.readObject();
//        ois.close();
//        return iHandler;
//    }
}
