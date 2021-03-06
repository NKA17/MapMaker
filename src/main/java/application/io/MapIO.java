package application.io;

import application.config.Configuration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.map.structure.RPGMap;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class MapIO {
    public static void saveMap(RPGMap map, String fileName,LoadModel loadModel){
        Gson gson = new GsonBuilder().create();


        String json = gson.toJson(Jsonifier.toJSON(map,loadModel));
        try{
            File file = new File(fileName);
            FileOutputStream fos = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }

            if(loadModel != null)
            loadModel.setTotalBytes(json.length());
            for(int i = 0; i < json.length(); i+=50){
                String buffer = json.substring(i,Math.min(json.length(),i+50));
                if(loadModel != null)
                loadModel.incrementReadBytes(buffer.length());
                fos.write(buffer.getBytes());
                fos.flush();
            }

            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(loadModel != null)
        loadModel.finish();
    }

    public static RPGMap loadMap2(String fileName,LoadModel load,RPGMap destination){
        try{
            if(!fileName.endsWith(Configuration.FILE_EXTENSION)){
                fileName += Configuration.FILE_EXTENSION;
            }
            File file = new File(fileName);
            if(!file.exists()){
                throw new FileNotFoundException();
            }
            load.setTotalBytes(file.length());
            load.incrementTotalBytes(5);
            long read = 0;
            String json = "";
            byte[] buffer = new byte[150];
            FileInputStream is = new FileInputStream(file);
            while ((read = is.read(buffer)) != -1) {
                String str = new String(buffer);
                json += str;
                load.incrementReadBytes(str.getBytes().length);
            }

            is.close();
            destination = Objectifier.toRPGMap(new JSONObject(json),destination,load);
            destination.setName(file.getName().replaceAll(Configuration.FILE_EXTENSION,""));
            load.setTotalBytes(load.getReadBytes());
            return destination;
        }catch (Exception e){
            e.printStackTrace();
            destination.init();
            load.finish();
            return destination;
        }
    }

    public static RPGMap loadMap(String fileName,LoadModel load,RPGMap destination){
        try{
            if(!fileName.endsWith(Configuration.FILE_EXTENSION)){
                fileName += Configuration.FILE_EXTENSION;
            }
            File file = new File(fileName);
            if(!file.exists()){
                throw new FileNotFoundException();
            }
            Scanner scan = new Scanner(file);
            String json = "";
            while(scan.hasNextLine()){
                json += scan.nextLine();
            }

            destination = Objectifier.toRPGMap(new JSONObject(json),destination,load);
            destination.setName(file.getName().replaceAll(Configuration.FILE_EXTENSION,""));
            load.setTotalBytes(load.getReadBytes());
            destination.resterizeMapImage();
            return destination;
        }catch (Exception e){
            e.printStackTrace();
            destination.init();
            load.finish();
            return destination;
        }
    }
}
