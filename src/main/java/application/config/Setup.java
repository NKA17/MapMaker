package application.config;

public class Setup {

    public static void setup(String[] args){
        function(args);

        if(!Configuration.ENABLE_MAP && !Configuration.ENABLE_LOOT){
            System.out.println("FAILURE: You must enable at least one of the app features " +
                    "(-map, -loot)");
            System.exit(-1);
        }

        if(Configuration.ENABLE_LOOT) {
            database(args);
            //lootSaveLocation(args);
        }

        dimensions(args);
    }

    private static void function(String[] args){
        Configuration.ENABLE_MAP = checkFlag("-map",args);
        Configuration.ENABLE_LOOT = checkFlag("-loot",args);

        System.out.println("Configuring App features...");
        System.out.println(String.format("\tMap: %s",
                (Configuration.ENABLE_MAP?"ENABLED":"DISABLED")));
        System.out.println(String.format("\tLoot: %s",
                (Configuration.ENABLE_LOOT?"ENABLED":"DISABLED")));
    }

    private static void database(String[] args){
        String host = getArg("--db",args);
        if(host!=null){
            Configuration.DATABASE_HOSTNAME = "jdbc:mysql://"+host;
            System.out.println(String.format("\tUsing DB host: "+host));
        }
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

    private static boolean checkFlag(String flag, String[] args){
        for(int i = 0; i < args.length; i++){
            if(flag.equalsIgnoreCase(args[i]))
                return true;
        }

        return false;
    }
}
