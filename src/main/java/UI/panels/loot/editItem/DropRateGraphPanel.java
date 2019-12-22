package UI.panels.loot.editItem;

import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationPanel;
import UI.windows.BasicWindow;
import application.config.Configuration;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DropRateGraphPanel extends ApplicationPanel {

    private double min;
    private double max;

    public DropRateGraphPanel(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public void loadPanel() {
        setPreferredSize(new Dimension(390,110));
        BufferedImage graph =  new BufferedImage(
                100,
                100,
                BufferedImage.TYPE_4BYTE_ABGR);

        double onex = solveForX(1,min,max);

        double halfx = solveForX(.5,min,max);

        double zerox = solveForX(0,min ,max);

        int hor = 80;
        int vert = 20;
        int right = 90;

        Graphics g = graph.getGraphics();
        g.setColor(Configuration.WINDOW_BG_COLOR);
        g.fillRect(0,0,100,100);

        g.setColor(new Color(60,60,60));
        g.drawLine(right,0,right,100);
        g.drawLine((int)(halfx/onex*right),0,(int)(halfx/onex*right),100);
        g.drawLine((int)(zerox/onex*right),0,(int)(zerox/onex*right),100);

        g.drawLine(0,10,100,10);
        g.drawLine(0,(int)((hor-10.0)/2.0)+10,100,(int)((hor-10.0)/2.0)+10);
        g.drawLine(0,hor,100,hor);

        g.setColor(Color.YELLOW);
        for(int i = -1; i < onex; i++){
            int x1 = vert+(int)( (i-1)*((right-vert+0.0)/(onex+0.0)));
            double xfory1 = solveForY((i-1),min,max);
            int y1 = hor  - 10 - (int)Math.round(((hor - 10)*xfory1));


            int x2 = vert+(int)( (i)*((right-vert+0.0)/(onex+0.0)));
            double xfory2 = solveForY((i),min,max);
            int y2 = hor  - 10 - (int)Math.round(((hor - 10)*xfory2));

            g.drawLine(x1,y1+10,x2,y2+10);
        }


        g.setColor(new Color(255,255,255));
        g.setFont(new Font("Arial Black",0,10));
        g.drawString(((int)(Math.round(onex)))+"",right-8,10);
        g.drawString(((int)(Math.round(halfx)))+"",(int)(halfx/onex*right)-8,(int)((hor-10.0)/2.0));
        g.drawString(((int)(Math.round(zerox)))+"",(int)(zerox/onex*right)-8,hor-10);

        g.drawString("1",0,10);
        g.drawString(".5",0,(int)((hor-10.0)/2.0)+10);
        g.drawString("0",0,hor);

        g.drawLine(0,hor,100,hor);
        g.drawLine(vert,0,vert,100);

        add(new JLabel(new ImageIcon(graph)));
    }

    private double solveForY(int x,double min, double max){
        if(max==min)return 20;
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

    public static void main(String[] args){
        BasicWindow bw = new BasicWindow();
        ApplicationPage page = new ApplicationPage() {
            @Override
            public void loadPage() {
                DropRateGraphPanel panel = new DropRateGraphPanel(.0,1);
                addPanel(panel);
            }
        };
        bw.openPage(page);
        bw.makeVisible();
    }
}
