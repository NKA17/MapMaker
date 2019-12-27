package UI.factory;

import application.config.Configuration;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicSliderUI;

public class CustomSliderUI extends BasicSliderUI {

    private BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f);

    public CustomSliderUI(JSlider b) {
        super(b);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g, c);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(12, 16);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Configuration.COMP_HOVER_BG_COLOR);
        if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
            int midh = trackRect.height/2;
            int x = trackRect.x;
            int y = trackRect.y;
            int w = trackRect.width;
//            g2d.drawLine(x,y+midh-3,w+x,y+midh-3);
//            g2d.drawLine(x,y+midh+3,w+x,y+midh+3);
//            g2d.drawLine(x,y+midh-3,x,y+midh+3);
//            g2d.drawLine(x+w,y+midh-3,x+w,y+midh+3);

            g2d.drawLine(x+1,y+midh-2,w+x-1,y+midh-2);
            g2d.drawLine(x+1,y+midh+2,w+x-1,y+midh+2);
            g2d.drawLine(x+1,y+midh-2,x+1,y+midh+2);
            g2d.drawLine(x+w-1,y+midh-2,x+w-1,y+midh+2);

            g2d.fillRect(x,midh-2+y,x-3+(int)((w+0.0)*(slider.getValue()+200.0)/(slider.getMaximum()+200.0)),4);
        } else {
            g2d.drawLine(trackRect.x + trackRect.width / 2, trackRect.y,
                    trackRect.x + trackRect.width / 2, trackRect.y + trackRect.height);
        }
    }

    @Override
    public void paintThumb(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        int midh = trackRect.height/2;
//        int x1 = thumbRect.x + 2;
//        int x2 = thumbRect.x + thumbRect.width - 2;
//        int width = 8;
//        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3;
//        g2d.setColor(new Color(0,0,0));
//        g2d.fillOval(x1,midh-(width-2),width,width);
//        g2d.fillOval(x1,midh+(width/2-4),width,width);
//        g2d.fillRect(x1,midh-4,width,8);
//
//        g2d.setColor(Configuration.COMP_BG_COLOR);
//        g2d.fillOval(x1+2,2+midh-(width-2),width-4,width-4);
//        g2d.fillOval(x1+2,midh+(width/2-3),width-4,width-4);
//        g2d.fillRect(x1+2,midh-2,width-4,6);
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x + 2;
        int x2 = thumbRect.x + thumbRect.width - 2;
        int width = thumbRect.width - 4;
        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3;
        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        shape.moveTo(x1, topY);
        shape.lineTo(x2, topY);
        shape.lineTo((x1 + x2) / 2, topY + width);
        shape.closePath();
        g2d.setPaint(new Color(81, 83, 186));
        g2d.fill(shape);
        Stroke old = g2d.getStroke();
        g2d.setStroke(new BasicStroke(2f));
        //g2d.setPaint(new Color(131, 127, 211));
        g2d.draw(shape);
        g2d.setStroke(old);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JSlider slider = new JSlider();
        slider.setUI(new CustomSliderUI(slider));
        frame.add(slider);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
