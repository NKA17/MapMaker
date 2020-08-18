package application.io;

import application.config.Configuration;
import application.loot.structure.DropBag;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

    public static DropBag load(DropBag bag){
        try{
            String fileName = cleanFileName(bag.getName()) + Configuration.FILE_EXTENSION;
            String str = "";
            Scanner in = new Scanner(new File(Configuration.SAVE_LOOT_FOLDER+"/"+fileName));
            while(in.hasNextLine()){
                str+=in.nextLine();
            }
            Gson gson = new GsonBuilder().create();
            bag.setItems((gson.fromJson(str,DropBag.class)).getItems());
            return bag;
        }catch (Exception e){
            e.printStackTrace();
            return new DropBag();
        }
    }

    public static List<DropBag> getEmptyDropBags(){
        List<DropBag> list = new ArrayList<>();
        File lootSaveDir = new File(Configuration.SAVE_LOOT_FOLDER);
        for(File f: lootSaveDir.listFiles()){
            DropBag db = new DropBag();
            String fileName = cleanFileName(f.getName());
            db.setName(fileName);
            list.add(db);
        }
        return list;
    }

    public static void deleteBag(String fileName){
        fileName = cleanFileName(fileName);
        File f =  new File(Configuration.SAVE_LOOT_FOLDER + "/" +fileName);
        f.delete();
    }

    private static String cleanFileName(String fileName){
        return fileName.replaceAll("\\.mf$","");
    }
}
