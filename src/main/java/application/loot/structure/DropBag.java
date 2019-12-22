package application.loot.structure;

import java.util.*;

public class DropBag {
    private String name = "NPC Loot";
    private List<ItemRow> items = new ArrayList<>();
    private ItemRow highlighted = null;

    public DropBag drop(){
        return drop(0);
    }

    public DropBag drop(int roll){
        DropBag dropBag = new DropBag();
        dropBag.setName(getName());
        for(ItemRow item: items){
            if(!item.drop(roll))continue;

            ItemRow newItem = new ItemRow();
            newItem.setName(item.getName());
            newItem.setDescription(item.getDescription());
            newItem.setDropRateMin(1);
            newItem.setQuantity(item.quantify()+"");
            dropBag.getItems().add(newItem);
        }
        return dropBag;
    }

    public String toString(){
        String str = name+"\n";
        for(ItemRow itemRow: items){
            str += "\t"+itemRow+"\n";
        }
        return str;
    }

    public static void main(String[] args){
        DropBag db = new DropBag();
        db.getItems().add(new ItemRow("Sword","","1",1.0));
        db.getItems().add(new ItemRow("Yellow Whet Stone","","1",0,.6));
        db.getItems().add(new ItemRow("Gold","","1d6 * 1d4",.2,.95));
        db.getItems().add(new ItemRow("Gold","","1d6 * 1d4",.0,.95));
        db.getItems().add(new ItemRow("Gold","","1d6 * 1d4",.0,.95));
        db.getItems().add(new ItemRow("Gold","","1d6 * 1d4",.0,.95));
        db.getItems().add(new ItemRow("Gold","","1d6 * 1d4",.0,.95));
        db.getItems().add(new ItemRow("Ring","","1",0,.6));
        db.getItems().add(new ItemRow("Ring","","1",0,.3));

        for(ItemRow ir: db.getItems()){
            System.out.println(ir);
        }

        Random r = new Random();
        for(int i = 0; i < 10; i++){
            System.out.println("-------------------------");
            int roll = r.nextInt(28)+1;
            System.out.println("Roll: "+roll);
            DropBag bag = db.drop(roll);
            bag.pack();
            for(ItemRow ir: bag.getItems()){
                System.out.println(ir);
            }
            System.out.println("-------------------------");
        }
    }

    public DropBag pack(){
        HashMap<String,ItemRow> map = new HashMap<>();
        for(ItemRow ir: items){
            if(map.containsKey(ir.getName())){
                ItemRow existing = map.get(ir.getName());
                int eVal = existing.quantify();
                eVal += ir.quantify();
                existing.setQuantity(eVal+"");
            }else {
                map.put(ir.getName(),ir);
            }
        }

        List<ItemRow> newRows = new ArrayList<>();
        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()){
            String key = iter.next();
            newRows.add(map.get(key));
        }

        items = newRows;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemRow> getItems() {
        return items;
    }

    public void setItems(List<ItemRow> items) {
        this.items = items;
    }

    public ItemRow getHighlighted() {
        return highlighted;
    }

    public void setHighlighted(ItemRow highlighted) {
        this.highlighted = highlighted;
    }
}
