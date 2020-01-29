package application.io;

import application.config.Configuration;
import application.config.Setup;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;
import com.sun.corba.se.impl.presentation.rmi.ExceptionHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LootIODB {

    static {
        try{

            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void saveDropBag(DropBag bag)throws SQLException{
        Connection con = getConnection();

        if(bag.getId()==0){
            saveNewBag(bag,con);
        }else {
            saveBagUpdates(bag,con);
        }

        for(ItemRow item: bag.getItems()){
            if(item.getDropBagId()!=-1)
                item.setDropBagId(bag.getId());
            saveItem(item,con);
        }
        con.close();
    }

    private static void saveNewBag(DropBag bag, Connection con) throws SQLException{

        con.createStatement().execute(
                "Insert into RPG_MAKER.DropBag " +
                        "(name) " +
                        String.format("values ('%s')",sanatize(bag.getName()))
        );
        ResultSet rs = con.createStatement().executeQuery("Select @@identity");
        rs.next();
        bag.setId(rs.getInt(1));

        for(ItemRow item: bag.getItems()){
            item.setDropBagId(bag.getId());
            item.setId(0);
        }
    }

    public static void deleteBag(DropBag bag)throws SQLException{
        Connection con = getConnection();
        deleteBag(bag,con);
        con.close();
    }
    private static void deleteBag(DropBag bag, Connection con) throws SQLException{
        con.createStatement().execute("delete from RPG_MAKER.DropBag where id="+bag.getId());
        con.createStatement().execute("delete from RPG_MAKER.ItemRow where dropbag="+bag.getId());
    }

    private static void saveBagUpdates(DropBag bag, Connection con) throws SQLException{
        con.createStatement().execute(
                String.format("update RPG_MAKER.DropBag set name='%s' " +
                        "where id=%d",
                        sanatize(bag.getName()),bag.getId())
        );
    }

    private static void saveItem(ItemRow item, Connection con) throws SQLException{
        if(item.getDropBagId()==-1) {
            deleteItem(item,con);
        }else if(item.getId()==0){
            saveNewItem(item,con);
        }else{
            saveItemUpdates(item,con);
        }
    }

    private static void saveNewItem(ItemRow item, Connection con) throws SQLException{
        con.createStatement().execute(
                "Insert into RPG_MAKER.ItemRow " +
                        "(name,description,quantity,min,max,dropbag) " +
                        String.format("values ('%s','%s','%s','%.2f','%.2f',%d)",
                                sanatize(item.getName()),
                                sanatize(item.getDescription()),
                                sanatize(item.getQuantity()),
                                item.getDropRateMin(),item.getDropRateMax(),item.getDropBagId())
        );
        ResultSet rs = con.createStatement().executeQuery("Select @@identity");
        rs.next();
        item.setId(rs.getInt(1));
    }

    private static void saveItemUpdates(ItemRow item, Connection con) throws SQLException{
        con.createStatement().execute(
                String.format("update RPG_MAKER.ItemRow set " +
                                "name='%s'," +
                                "description='%s'," +
                                "quantity='%s'," +
                                "min='%.2f'," +
                                "max='%.2f' " +
                                "where id=%d",
                        sanatize(item.getName()),
                        sanatize(item.getDescription()),
                        sanatize(item.getQuantity()),
                        item.getDropRateMin(),item.getDropRateMax(),item.getId())
        );
    }

    public static List<DropBag> getEmptyDropBags() throws SQLException{
        Connection con = getConnection();

        List<DropBag> bags = new ArrayList<>();

        ResultSet rs = con.createStatement().executeQuery("Select * from RPG_MAKER.DropBag");
        while(rs.next()){
            DropBag bag = new DropBag();
            bag.setId(rs.getLong("id"));
            bag.setName(rs.getString("name"));
            bags.add(bag);
        }

        con.close();

        return bags;
    }

    public static void fillBag(DropBag bag) throws SQLException{
        Connection con = getConnection();

        ResultSet rs = con.createStatement().executeQuery("Select * from RPG_MAKER.ItemRow " +
                "where dropbag="+bag.getId());

        while (rs.next()){
            ItemRow item = new ItemRow();

            item.setId(rs.getLong("id"));
            item.setDropBagId(rs.getLong("dropbag"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setQuantity(rs.getString("quantity"));
            item.setDropRateMin(Double.parseDouble(rs.getString("min")));
            item.setDropRateMax(Double.parseDouble(rs.getString("max")));

            bag.getItems().add(item);
        }
    }

    public static void deleteItem(ItemRow item)throws SQLException{
        Connection con = getConnection();
        deleteItem(item,con);
        con.close();
    }

    private static void deleteItem(ItemRow item,Connection con) throws SQLException{
        con.createStatement().execute("Delete from RPG_MAKER.ItemRow where id="+item.getId());
    }

    //Establishes a connection to the database
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                Configuration.DATABASE_HOSTNAME, "nate", "7.51430Nine");
    }
    public static Connection getConnection(String host, String user, String pass)throws SQLException{
        return DriverManager.getConnection(host,user,pass);
    }

    public static void main(String[] args){
        Setup.setup(args);
        System.out.println("testing...");

        listAllRemote();
    }

    private static void listAllLocal(){
        File folder = new File(Configuration.SAVE_LOOT_FOLDER);
        for(File f: folder.listFiles()){
            DropBag bag = LootIO.load(f.getName());
            System.out.println(bag.getName());
            for(ItemRow i: bag.getItems()){
                System.out.println("\t"+i.getName());
            }
        }
    }

    private static void listAllRemote(){
        try{
            List<DropBag> bags = getEmptyDropBags();
            for(DropBag bag: bags){
                fillBag(bag);
                System.out.println(bag.getName());
                for(ItemRow itemRow: bag.getItems()){
                    System.out.println("\t"+itemRow.getName());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static void pushAll(){
        try {
            File folder = new File(Configuration.SAVE_LOOT_FOLDER);
            for (File f : folder.listFiles()) {
                DropBag bag = LootIO.load(f.getName());
                saveDropBag(bag);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void deleteAll(){
        try {

            List<DropBag> bags = getEmptyDropBags();
            for(DropBag b: bags){
                fillBag(b);
                deleteBag(b);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String sanatize(String s){
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i)=='\'' && (i==0 || s.charAt(i-1)!='\\')){
                s = s.substring(0,i)+"\\"+s.substring(i);
                i--;
            }
        }
        return s;
    }
}
