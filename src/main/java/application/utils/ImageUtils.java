package application.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {
    public static BufferedImage resize(BufferedImage before, int newWidth, int newHieght){
        BufferedImage resized = new BufferedImage(newWidth, newHieght, before.getType());
        BufferedImage repositioned = new BufferedImage(newWidth, newHieght, before.getType());
        double wscale = (newWidth + 0.0)/(before.getWidth()+0.0);
        double hscale = (newHieght + 0.0)/(before.getHeight()+0.0);
        double scale = Math.min(wscale,hscale);

        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(before,
                0,0,
                (int)(before.getWidth()*scale),
                (int)(before.getHeight()*scale),
                0, 0, before.getWidth(),
                before.getHeight(), null);
        g.dispose();

        g = repositioned.createGraphics();
        g.drawImage(resized,
                (newWidth/2) - ((int)(before.getWidth()*scale))/2,
                (newHieght/2) - ((int)(before.getHeight()*scale))/2,
                null);

        return repositioned;
    }

    public static Rectangle drawStringRect(Graphics g, String str, int x, int y, int w, int h){
        List<String> paintStrs = new ArrayList<>();
        int th = 0;
        String paintStr = "";
        Font f = g.getFont();
        while(str.length() > 0){
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
            Rectangle2D rect = f.getStringBounds(paintStr, frc);
            int textwidth = (int)(rect.getWidth());
            int textHieght = (int)rect.getHeight();
            th = Math.max(th,textHieght);

            if(textwidth <= w){
                paintStr += str.charAt(0);
                str = str.substring(1);
                if(str.length()==0){
                    paintStrs.add(paintStr);
                }
            }else {
                int i = paintStr.lastIndexOf(' ');
                if(i > 0) {
                    str = paintStr.substring(i) + str;
                    paintStr = paintStr.substring(0, i);
                }
                paintStrs.add(paintStr);
                paintStr = "";
            }
            if((th+3)*paintStrs.size() > h && str.length() > 0){
                paintStr = paintStrs.get(paintStrs.size()-1);
                if(paintStr.length() > 4)
                    paintStr = paintStr.substring(0,paintStr.length()-3)+"...";
                else
                    paintStr+="...";
                paintStrs.remove(paintStrs.size()-1);
                paintStrs.add(paintStr);
                break;
            }
        }
        int usey = y;
        for(String s: paintStrs){
            g.drawString(s,x,usey);
            usey += th+3;
        }

        return new Rectangle(x,y,w,(th+3)*paintStrs.size());
    }

    public static void main(String[] args){
        try{

            File dir = new File("C:\\Users\\Nate\\School\\notSchool\\handDetection\\hand_png");
            for(File f : dir.listFiles()){
                if(f.getName().endsWith("png")){
                    BufferedImage bimg = ImageIO.read(f);
                    bimg = resize(bimg,32,32);
                    String name = f.getName().replaceAll("png","jpg");
                    ImageIO.write(bimg,"jpg",new File("C:\\Users\\Nate\\School\\notSchool\\handDetection\\hand_jpg\\"+name));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
