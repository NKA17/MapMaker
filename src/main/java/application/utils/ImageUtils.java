package application.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtils {

    public static BufferedImage resize(BufferedImage before, double scale){
        return resize(before,(int)(before.getWidth()*scale),(int)(before.getHeight()*scale));
    }
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

    public static void main(String[] args){
        try{
            BufferedImage image = ImageIO.read(new File("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\assets\\map\\assets\\Infrastructure\\Lamp 1.png"));
            image = resize(image,50,50);
            ImageIO.write(image,"png",new File("output.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
