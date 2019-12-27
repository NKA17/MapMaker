package UI.factory;

import application.config.Configuration;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class SleekSlideUI extends BasicSliderUI {
    private BasicStroke stroke = new BasicStroke(1f, BasicStroke.CAP_ROUND,
            BasicStroke.JOIN_ROUND, 0f, new float[]{1f, 2f}, 0f);

    private int thumbWidth = 14;
    private int trackWidth = 4;

    public SleekSlideUI(JSlider b) {
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

        int y = trackRect.height / 2;
        int dh = trackWidth/2;
        g2d.setColor(Configuration.WINDOW_BG_COLOR);
        g2d.fillRect(thumbRect.x+thumbRect.width,y-dh,trackRect.width,trackWidth);
        g2d.setColor(Configuration.COMP_HOVER_BG_COLOR);
        g2d.fillRect(trackRect.x,y-dh,thumbRect.x-6,trackWidth);
//        g2d.drawRect(thumbRect.x + thumbRect.width + 2,
//                y-dh,
//                trackRect.width - (thumbRect.x + thumbRect.width + 2),
//                trackWidth);
    }

    @Override
    public void paintThumb(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setColor(Configuration.COMP_HOVER_BG_COLOR);
//        int x1 = thumbRect.x + 4;
//        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
//        shape.moveTo(x1+2, thumbRect.y);
//        shape.lineTo(x1+thumbWidth-4, thumbRect.y);
//        shape.lineTo(x1+thumbWidth-4,thumbRect.y+thumbRect.height);
//        shape.lineTo(x1+2,thumbRect.y+thumbRect.height);
//
//        shape.closePath();
//        g2d.fill(shape);
//        Stroke old = g2d.getStroke();
//        g2d.setStroke(new BasicStroke(2f));
//        //g2d.setPaint(new Color(131, 127, 211));
//        //g2d.draw(shape);
//        g2d.setStroke(old);
        Graphics2D g2d = (Graphics2D) g;
        int x1 = thumbRect.x + 2;
        int x2 = thumbRect.x + thumbRect.width - 2;
        int width = thumbRect.width - 3;
        int topY = thumbRect.y + thumbRect.height / 2 - thumbRect.width / 3 -1;
        GeneralPath shape = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        shape.moveTo(x1, topY);
        shape.lineTo(x2, topY);
        shape.lineTo(x2, topY + width);
        shape.lineTo(x1, topY + width);
        shape.closePath();
        g2d.setPaint(Configuration.COMP_HOVER_BG_COLOR);
        g2d.fill(shape);
        Stroke old = g2d.getStroke();
        //g2d.setStroke(new BasicStroke(2f));
        g2d.draw(shape);
        g2d.setStroke(old);
    }
}
