package UI.app.assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MapAsset {
    private BufferedImage image;
    private String name;
    private int imageOffsetX = 0;
    private int imageOffsetY = 0;
    private int x = 0;
    private int y = 0;

    public MapAsset(File file) {
        try{
            image = ImageIO.read(file);
            name = file.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int mapxoffset, int mapyoffset){
        g.drawImage(getImage(),getX(),getY(),null);
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getImageOffsetX() {
        return imageOffsetX;
    }

    public void setImageOffsetX(int imageOffsetX) {
        this.imageOffsetX = imageOffsetX;
    }

    public int getImageOffsetY() {
        return imageOffsetY;
    }

    public void setImageOffsetY(int imageOffsetY) {
        this.imageOffsetY = imageOffsetY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
