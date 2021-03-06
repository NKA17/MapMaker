package UI.panels.loot.editLootBag;

import UI.app.view.ApplicationPanel;
import application.config.Configuration;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Collections;

public class DropGraphPanel extends DropRateBagGraphPanel {

    private DropBag dropBag;

    private int gw = 180;
    private int gh = 180;
    private int margin = 20;
    private int w = 200;
    private int h = 200;
    private int mousex = 0;
    private int mousey = 0;
    private boolean drawmouse = false;

    public DropGraphPanel(DropBag dropBag) {
        super(dropBag);
        this.dropBag = dropBag;
        Listener l = new Listener(this);
        addMouseListener(l);
        addMouseMotionListener(l);
    }

    @Override
    public void loadPanel() {
        w = gw+margin*2;
        h = gh+margin*2;
        setPreferredSize(new Dimension(gw+margin*2,gh+margin*2));
        repaint();
    }
    public void paintComponent(Graphics g){
        BufferedImage graphImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics graph = graphImage.getGraphics();

        g.setFont(new Font("Arial Black",0,10));
        graph.setFont(new Font("Arial Black",0,10));

        g.setColor(Configuration.WINDOW_BG_COLOR);
        g.fillRect(0,0,w,h);

        drawGraphRefs(g);
        paintCurves(graph,new Color(200,200,0,66));
        paintCurve(graph,dropBag.getHighlighted(),Configuration.HIGHLIGHT_COLOR);
        drawHighlight(g,dropBag.getHighlighted());
        drawIntersects(g);

        if(drawmouse && dropBag.getHighlighted()!=null)
            drawMouse(g);

        BufferedImage flippedGraph = createFlipped(graphImage);
        g.drawImage(flippedGraph,0,0,null);
    }

    private void drawMouse(Graphics g){
        ItemRow item = dropBag.getHighlighted();

        g.setColor(new Color(150,255,150));
        g.drawLine(mousex,0,mousex,height);

        int x = (int)Math.round((mousex+0.0-margin)/(gw/20.0));
        double val = item.solveForY(x);
        int y = height - (int)Math.round((gh+0.0)*(item.solveForY(x)+0.0));
        g.drawLine(0,y,width,y);

        if(mousex > width / 2 + 20){
            g.setColor(new Color(0,0,0,200));
            g.fillRect(mousex-70,y-15,60,14);
            g.setColor(new Color(150,255,150));
            g.drawString(String.format("(%.2f, %d)", val, x), mousex - 65, y - 5);
        }else {
            g.setColor(new Color(0,0,0,200));
            g.fillRect(mousex+3,y+3,60,14);
            g.setColor(new Color(150,255,150));
            g.drawString(String.format("(%.2f, %d)", val, x), mousex + 5, y + 14);
        }
    }

    private void paintCurves(Graphics g,Color c){
        for(ItemRow item: dropBag.getItems()){
            paintCurve(g,item,c);
        }
    }

    private void paintCurve(Graphics g, ItemRow item, Color c){
        if(item == null)return;
        g.setColor(c);
        for(int i = 0; i < 40; i++){
            int x1 = (int)Math.round(((gw)/20.0)*(i));
            int y1 = (int)Math.round((gh+0.0)*(item.solveForY(i)+0.0));
            int x2 = (int)Math.round(((gw)/20.0)*(i-1.0));
            int y2 = (int)Math.round((gh+0.0)*item.solveForY(i-1));
            g.drawLine(x1+margin,y1+margin,x2+margin,y2+margin);
        }
    }

    private void drawGraphRefs(Graphics g){
        int oney = margin;
        int onex = gw+margin;
        int halfy = margin+gh/2;
        int halfx = margin+gw/2;
        int vert = margin;
        int hor = margin+gh;

        g.setColor(new Color(90,90,90));
        g.drawLine(0,halfy,w,halfy);
        g.drawLine(0,oney,w,oney);
        g.drawLine(onex,0,onex,h);
        g.drawLine(halfx,0,halfx,h);

        g.drawString("1.0",0,oney-2);
        g.drawString("0.5",0,halfy-2);
        g.drawString("20",onex,(int)(margin*1.5)+gh);
        g.drawString("10",halfx,(int)(margin*1.5)+gh);

    }

    private void drawIntersects(Graphics g){

        g.setColor(new Color(255,255,255));
        g.drawLine(0,margin+gh,w,margin+gh);
        g.drawLine(vert,0,vert,h);
    }

    private static BufferedImage createFlipped(BufferedImage image)
    {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    private void drawHighlight(Graphics g, ItemRow item){
        if(item==null)return;
        double onex = item.solveForX(1);
        int ionex = (int)Math.round(((gw)/20.0)*(onex))+margin;
        double zerox = item.solveForX(0);
        int izerox = (int)Math.round(((gw)/20.0)*(zerox))+margin;

        Color bound = new Color(150,250,150,200);
        Color area = new Color(100,250,100,20);
        if(zerox <= 0){
            izerox = -10;
        }
        if(onex > 25){
            ionex = w+10;
        }

        g.setColor(area);
        g.fillRect(izerox,0,ionex-izerox,h);

        g.setColor(bound);
        if(onex <= 25 && onex > 0){
            g.drawLine(ionex,0,ionex,h);
            g.drawString((int)Math.round(onex)+"",ionex-16,(gh+margin-2));
        }

        if(zerox >= 0 && zerox <= 25){
            g.drawLine(izerox,0,izerox,h);
            g.drawString((int)Math.round(zerox)+"",izerox-16,(gh+margin-2));
        }
    }

    private static BufferedImage createTransformed( BufferedImage image, AffineTransform at)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    class Listener implements MouseListener, MouseMotionListener {


        private DropGraphPanel dgp;

        public Listener(DropGraphPanel dgp){
            this.dgp = dgp;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            dgp.drawmouse = true;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            dgp.drawmouse = false;
            dgp.repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            dgp.mousex = e.getX();
            dgp.mousey = e.getY();
            dgp.repaint();
        }
    }
}
