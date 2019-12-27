package UI.windows;

import UI.app.view.ApplicationWindow;
import UI.pages.start.StartPage;
import application.config.Configuration;

import javax.swing.*;
import java.awt.*;

public class BasicWindow extends ApplicationWindow {


    @Override
    public void initUI() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args){

        lootSaveLocation(args);
        dimensions(args);


        BasicWindow sp = new BasicWindow();
        sp.openPage(new StartPage());
        sp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sp.makeVisible();
//        String fonts[] = GraphicsEnvironment
//                .getLocalGraphicsEnvironment()
//                .getAvailableFontFamilyNames();
//        for(String s : fonts){
//            System.out.println(s);
//        }
    }

    private static void dimensions(String[] args){
        int w = -1;
        int h = -1;

        String dimensions = getArg("--dim", args);

        if(dimensions!=null) {
            String[] dims = dimensions.split("x");
            w = Integer.parseInt(dims[0]);
            h = Integer.parseInt(dims[1]);
        }

        String width = getArg("--width",args);
        String height = getArg("--height",args);

        if(width!=null){
            w = Integer.parseInt(width);
        }
        if(height!=null){
            h = Integer.parseInt(height);
        }

        System.out.println(String.format(
                "\tConstraints given: width=%s, height=%s",
                ((w==-1)?"none":w+""),
                ((h==-1)?"none":h+"")
        ));

        if(w!=-1){
            Configuration.WIDTH_CONSTRAINT = w;
        }
        if(h!=-1){
            Configuration.HEIGHT_CONSTRAINT = h;
        }
    }

    private static void lootSaveLocation(String[] args){
        String lootSaveLocation = getArg("--lootSave", args);

        if(lootSaveLocation != null){
            Configuration.SAVE_LOOT_FOLDER = lootSaveLocation;
        }
        System.out.println("\tLoot location: "+Configuration.SAVE_LOOT_FOLDER);
    }


    private static String getArg(String arg, String[] args){
        for(int i = 0; i < args.length-1; i++){
            if(arg.equalsIgnoreCase(args[i]))
                return args[i+1];
        }

        return null;
    }
}
