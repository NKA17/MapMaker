package application.io;

import application.config.Configuration;
import application.loot.structure.DropBag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class LootIO {

    public static void save(DropBag bag){
        Gson gson = new GsonBuilder().create();

        String json = gson.toJson(bag);
        try{
            PrintWriter out = new PrintWriter(Configuration.SAVE_LOOT_FOLDER+"/"+bag.getName()+Configuration.FILE_EXTENSION);
            out.print(json);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static DropBag load(String fileName){
        try{
            String str = "";
            Scanner in = new Scanner(new File(Configuration.SAVE_LOOT_FOLDER+"/"+fileName));
            while(in.hasNextLine()){
                str+=in.nextLine();
            }
            Gson gson = new GsonBuilder().create();
            DropBag bag = gson.fromJson(str,DropBag.class);
            return bag;
        }catch (Exception e){
            e.printStackTrace();
            return new DropBag();
        }
    }
}
