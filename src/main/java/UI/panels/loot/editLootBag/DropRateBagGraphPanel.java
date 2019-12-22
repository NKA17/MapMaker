package UI.panels.loot.editLootBag;

import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationPanel;
import UI.factory.TextFactory;
import UI.windows.BasicWindow;
import application.config.Configuration;
import application.io.LootIO;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class DropRateBagGraphPanel extends ApplicationPanel {

    private DropBag dropBag;

    public DropRateBagGraphPanel(DropBag dropBag) {
        this.dropBag = dropBag;
    }

    public DropBag getDropBag() {
        return dropBag;
    }

    public void setDropBag(DropBag dropBag) {
        this.dropBag = dropBag;
    }

    int width = 200;
    int height = 200;
    int hor = height - 10;
    int vert = 20;
    int right = width - 50;
    int rightRoll = 20;
    public void loadPanel(){

        setPreferredSize(new Dimension(200,200));


        setLayout(new GridBagLayout());
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        //setPreferredSize(new Dimension(200,200));
        g.setColor(Configuration.WINDOW_BG_COLOR);
        g.fillRect(0,0,width,height);

        g.setColor(new Color(90,90,90,90));
        for(int i = -2; i < 40; i+=2) {
                g.drawLine((int)( (i-1)*((right-vert+0.0)/(rightRoll+0.0))),
                        0,
                        (int)( (i-1)*((right-vert+0.0)/(rightRoll+0.0))),
                        height);
        }

        double x1 = 0;
        double ix1 = 0;
        double x2 = 0;
        double ix2 = 0;
        if(calcForMin(dropBag.getHighlighted())){
            x1 = solveForX(0,
                    dropBag.getHighlighted().getDropRateMin(),
                    dropBag.getHighlighted().getDropRateMax());
            ix1 = vert+(int)( (x1)*((right-vert+0.0)/(rightRoll+0.0)));

        }else{
            x1 = 10;
            ix1 = (right - vert) / 2 + vert;
        }
        if(calcForMax(dropBag.getHighlighted())){
            x2 = solveForX(1,
                    dropBag.getHighlighted().getDropRateMin(),
                    dropBag.getHighlighted().getDropRateMax());
            ix2 = vert+(int)( (x2)*((right-vert+0.0)/(rightRoll+0.0)));
        }else{
            x2 = 20;
            ix2 = right;
        }

//        if(Math.round(x2)!=20) {
//            g.setColor(new Color(100,250,100,20));
//            if(calcForMin(dropBag.getHighlighted())) {
//                g.fillRect((int) Math.round(ix1), 0, (int) (Math.round(ix2) - Math.round(ix1)), height);
//            }else{
//                g.fillRect(0, 0, (int) (Math.round(ix2)), height);
//            }
//            g.setColor(new Color(150,250,150,200));
//            g.drawLine((int)Math.round(ix1), 0, (int)Math.round(ix1), height);
//            g.drawLine((int)Math.round(ix2), 0, (int)Math.round(ix2), height);
//
//            g.setColor(new Color(90,90,90));
//        }else{
//            g.setColor(new Color(90,90,90));
//            g.drawLine((int)Math.round(ix1), 0, (int)Math.round(ix1), height);
//            g.drawLine((int)Math.round(ix2), 0, (int)Math.round(ix2), height);
//        }
        int xbound1 = (int)((calcForMin(dropBag.getHighlighted()))?ix1:0);
        int xbound2 = (int)((calcForMax(dropBag.getHighlighted()))?ix2:width);
        Color lineColor1 = calcForMin(dropBag.getHighlighted())?
                new Color(150,250,150,200):new Color(90,90,90);
        Color lineColor2 = calcForMax(dropBag.getHighlighted())?
                new Color(150,250,150,200):new Color(90,90,90);

        if(calcForMax(dropBag.getHighlighted()) || calcForMin(dropBag.getHighlighted())){

            //determinable
            //g.setColor(new Color(100,100,250,20));
            g.setColor(new Color(100,250,100,20));
            g.fillRect(xbound1,0,xbound2-xbound1,height);

            //guaranteed negative
            //g.setColor(new Color(250,100,100,30));
            //g.fillRect(0,0,xbound1,height);

            //guaranteed positive
            //g.setColor(new Color(100,250,100,30));
            //g.fillRect(xbound2,0,width,height);
        }
        g.setColor(lineColor1);
        g.drawLine((int)Math.round(ix1), 0, (int)Math.round(ix1), height);
        g.setColor(lineColor2);
        g.drawLine((int)Math.round(ix2), 0, (int)Math.round(ix2), height);

        g.setColor(new Color(90,90,90));
        g.drawLine(0,10,width,10);
        g.drawLine(0,(int)((hor-10.0)/2.0)+10,width,(int)((hor-10.0)/2.0)+10);

        for(ItemRow item: dropBag.getItems()){
            drawOnGraph(g,item,new Color(200,200,0,66));
        }
        if(dropBag.getHighlighted()!=null) {
            drawOnGraph(g, dropBag.getHighlighted(), Configuration.HIGHLIGHT_COLOR);
        }

        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial Black", 0, 10));
        g.drawString(""+Math.round(x1), (int)Math.round(ix1) - 18, hor - 3);
        g.drawString("" + Math.round(x2), (int) Math.round(ix2) - 18, hor - 3);
        if(dropBag.getHighlighted()!=null) {
            int guaranteed = (int) Math.round(solveForX(1, dropBag.getHighlighted().getDropRateMin(), dropBag.getHighlighted().getDropRateMax()));
            System.out.println("g"+guaranteed);
            if (guaranteed >= 30) {
                g.drawString(Math.round(guaranteed) + "->", width - 28, hor - 3);
            }
        }

        g.drawString("1",0,10);
        g.drawString(".5",0,(int)((hor-10.0)/2.0)+10);
        g.drawString("0",0,hor);

        g.drawLine(0,hor,height,hor);
        g.drawLine(vert,0,vert,height);
    }



    public void drawOnGraph(Graphics g, ItemRow item, Color color) {
        double min = item.getDropRateMin();
        double max = item.getDropRateMax();
        g.setColor(color);

        for(int i = -1; i < 30; i++){

            int x1 = vert+(int)( (i-1)*((right-vert+0.0)/(rightRoll+0.0)));
            double xfory1 = solveForY((i-1),min,max);
            int y1 = hor  - 10 - (int)((hor - 10)*xfory1);


            int x2 = vert+(int)( (i)*((right-vert+0.0)/(rightRoll+0.0)));
            double xfory2 = solveForY((i),min,max);
            int y2 = hor  - 10 - (int)((hor - 10)*xfory2);

            if(Math.abs(min-max)< .001){
                y2 = hor  - 10 - (int)((hor - 10)*min);
                y1 = hor  - 10 - (int)((hor - 10)*min);
            }


            if(y1 <= 200 && y2 <= 200)
                g.drawLine(x1,y1+10,x2,y2+10);
        }


    }

    private double solveForY(int x,double min, double max){
        if(max-min<.01)return 1;
        double a = (x+0.0)/17.0;
        double pow = Math.pow(a,2);
        double powMinus5 = (pow-.5);
        double range = max - min;
        double rangeTimesPow = range*powMinus5;
        double z = rangeTimesPow+min;
        return z;
    }
    private double solveForX(double z,double min, double max){
        if(max==min)return z;
        try {
            return 17.0 * (Math.sqrt(((z - min) / (max - min)) + .5));
        }catch (Exception e){
            return 1;
        }
    }

    private boolean calcForMax(ItemRow item){
        if(item == null)return false;
        if(item.getDropRateMin()==item.getDropRateMax())return false;

        double one = solveForX(1,item.getDropRateMin(),item.getDropRateMax());

        return Double.NaN != one && one > 0 && one < 30;
    }

    private boolean calcForMin(ItemRow item){
        if(item == null)return false;
        if(item.getDropRateMin()==item.getDropRateMax())return false;

        double one = solveForX(0,item.getDropRateMin(),item.getDropRateMax());

        return Double.NaN != one && one > 0 && one < 30;
    }

    public static void main(String[] args){
        BasicWindow bw = new BasicWindow();
        ApplicationPage page = new ApplicationPage() {
            @Override
            public void loadPage() {
                DropBag bag = LootIO.load("Bumble.mf");
                DropRateBagGraphPanel panel = new DropRateBagGraphPanel(bag);
                addPanel(panel);
            }
        };
        bw.openPage(page);
        bw.makeVisible();
    }
}
