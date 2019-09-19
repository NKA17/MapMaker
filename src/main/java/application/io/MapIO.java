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

    public static RPGMap loadMap(String fileName){
        try{
            File file = new File(fileName);
            long totalBytes = file.length();
            long read = 0;
            String json = "";
            Scanner in = new Scanner(file);
            while(in.hasNextLine()){
                String str = in.nextLine();
                json += str;
                read += str.getBytes().length + 0.0;
                System.out.println(((read + 0.0)/(totalBytes + 0.0)) * 100.0);
            }
            in.close();
            return Objectifier.toRPGMap(new JSONObject(json));
        }catch (Exception e){
            RPGMap map = new RPGMap();
            map.init();
            return map;
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
