package application.loot.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemRow {
    private String name = "Item";
    private String description = "item";
    private String quantity = "1";
    private double dropRateMin = 0;
    private double dropRateMax = 1;

    public ItemRow(){}

    public ItemRow(String name, String description, String quantity, double dropRateMin, double dropRateMax) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.dropRateMin = dropRateMin;
        this.dropRateMax = dropRateMax;
    }

    public ItemRow(String name, String description, String quantity, double dropRateMin) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.dropRateMin = dropRateMin;
        this.dropRateMax = dropRateMin;
    }

    public boolean drop(){
        Random r = new Random();
        double d = r.nextDouble();
        return d <= dropRateMin;
    }

    public boolean drop(int roll){
        Random r = new Random();
        double y = Math.pow(((roll+0.0)/17.0),2)-.5;
        double diff = dropRateMax - dropRateMin;
        double diffVelocity = diff*y;
        double dropRate = dropRateMin + diffVelocity;
        double d = r.nextDouble();
        System.out.println(d+" <? "+dropRate+ " "+(d<=dropRate));
        return d <= dropRate;
    }

    public String toString(){

        if(quantity.trim().equalsIgnoreCase("1"))
            return String.format("%s",name,quantity);
        else
            return String.format("%s x%s",name,quantity);
    }

    public int quantify(){
        List<String> clauses = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\*|\\+|\\\\|/|-|\\d+d\\d+|\\d+").matcher(quantity);
        while(matcher.find()){
            clauses.add(matcher.group().trim());
        }

        int total = 0;
        char operator = '+';
        for(int i = 0; i < clauses.size() ; i++){
            String clause = clauses.get(i);
            int val = 0;
            if(clause.matches("\\d+")){
                val = Integer.parseInt(clause);
            }else if(clause.matches("\\d+d\\d+")){
                String[] numsStr = clause.split("d");
                int[] nums = new int[2];
                nums[0] = Integer.parseInt(numsStr[0]);
                nums[1] = Integer.parseInt(numsStr[1]);
                Random r = new Random();
                for(int j = 0; j < nums[0]; j++){
                    val += r.nextInt(nums[1])+1;
                }
            }else if(clause.matches("\\*|\\+|\\\\|/|-")){
                operator = clause.charAt(0);
                continue;
            }

            switch (operator){
                case '+':
                    total += val;
                    break;
                case '-':
                    total -= val;
                    break;
                case '/':
                    total /= val;
                    break;
                case '\\':
                    total /= val;
                    break;
                case '*':
                    total *= val;
                    break;
            }
        }

        return total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public double getDropRateMin() {
        return dropRateMin;
    }

    public void setDropRateMin(double dropRateMin) {
        this.dropRateMin = dropRateMin;
    }

    public double getDropRateMax() {
        return dropRateMax;
    }

    public void setDropRateMax(double dropRateMax) {
        this.dropRateMax = dropRateMax;
    }

    public static void main(String[] args){
        ItemRow ir = new ItemRow();

        ir.setDropRateMin(.75);
        System.out.println(ir.drop());
        ir.setDropRateMin(1);
        System.out.println(ir.drop());
        ir.setDropRateMin(.25);
        System.out.println(ir.drop());
    }
}
