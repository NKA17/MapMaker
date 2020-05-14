package application.mapEditing.transpose;

import UI.app.view.ApplicationPage;
import UI.windows.BasicWindow;
import application.config.Configuration;
import application.io.LoadModel;
import application.io.MapIO;
import application.utils.ImageUtils;
import model.map.structure.RPGMap;
import model.map.tiles.MapPointer;
import model.map.tiles.MapTile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DungeonMasterReferenceMap {

    private RPGMap map;
    private BufferedImage mapImage;
    private BufferedImage descImage = null;
    int w;
    int h;
    int squareSize = 3;
    int highestDrawn = 0;

    public DungeonMasterReferenceMap(RPGMap map){
        w = map.getGridWidth();
        h = map.getGridHeight();
        squareSize = 8;
        this.map = map;
        mapImage = new BufferedImage(w*squareSize,h*squareSize,BufferedImage.TYPE_4BYTE_ABGR);

    }

    public void paintBackground(){
        paintBackground(new Color(255,255,255));
    }

    public void paintBackground(Color c){
       Graphics g = mapImage.getGraphics();
       g.setColor(c);
       g.fillRect(0,0,mapImage.getWidth(),mapImage.getHeight());
    }

    public void paintDefault(){
        paintBackground(Color.BLACK);
        paintGround();
        paintWalls();
        paintFences();
        paintMarkers();
    }

    public void paintGround(){
        Graphics graphics = mapImage.getGraphics();

        for(MapTile mt: map.getActiveLayer().getTileLayer().getTiles()){

            int r = 0;
            int g = 0;
            int b = 0;

            BufferedImage bimg = mt.getAssetResource().getImage();
            for(int i = 0; i < 50; i++){
                for(int j = 0; j < 50; j++){
                    Color c = new Color(bimg.getRGB(i,j));
                    r += c.getRed();
                    g += c.getGreen();
                    b += c.getBlue();
                }
            }

            r = r / 2500;
            g = g / 2500;
            b = b / 2500;

            graphics.setColor(new Color(r,g,b));
            if(graphics.getColor().getBlue() == 0)continue;
            graphics.fillRect(mt.getGridx()*squareSize,mt.getGridy()*squareSize,squareSize,squareSize);
            highestDrawn = Math.max(highestDrawn,mt.getGridy()*squareSize+squareSize);
        }
    }

    public void paintMarkers(){
        Graphics2D g = (Graphics2D)mapImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        descImage = new BufferedImage(w*squareSize,1000,BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2 = (Graphics2D)descImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(new Font("Myriad Pro",0,10));
        g2.setColor(Configuration.WINDOW_BG_COLOR);
        g2.fillRect(0,0,descImage.getWidth(),descImage.getHeight());
        g2.setColor(Color.WHITE);
        int usedHeight = 8;

        List<MapTile> tiles = map.getMechanicsLayer().getTiles();
        Collections.sort(tiles, new Comparator<MapTile>() {
            @Override
            public int compare(MapTile o1, MapTile o2) {
                MapPointer m1 = null,m2 = null;
                if(o1 instanceof MapPointer)
                    m1 = (MapPointer)o1;
                if(o2 instanceof MapPointer)
                    m2 = (MapPointer)o2;
                if(m1 != null && m2 == null)
                    return -1;
                if(m1 == null && m2 != null)
                    return 1;
                if(m1==null&&m2==null)
                    return 0;
                return m1.getAbbreviation().compareToIgnoreCase(m2.getAbbreviation());
            }
        });
        g.setFont(new Font("Arial Black",Font.BOLD,12));
        for(MapTile mt: tiles){
            if(mt.getAssetResource().getName().contains("pointer")){
                MapPointer mp = (MapPointer)mt;
                g.setColor(Color.BLACK);
                g.drawString(mp.getAbbreviation(),
                        mp.getGridx()/Configuration.TILE_WIDTH*squareSize+3,
                        mp.getGridy()/Configuration.TILE_HEIGHT*squareSize+7
                );
                g.drawString(mp.getAbbreviation(),
                        mp.getGridx()/Configuration.TILE_WIDTH*squareSize+3,
                        mp.getGridy()/Configuration.TILE_HEIGHT*squareSize+9
                );
                g.drawString(mp.getAbbreviation(),
                        mp.getGridx()/Configuration.TILE_WIDTH*squareSize+5,
                        mp.getGridy()/Configuration.TILE_HEIGHT*squareSize+7
                );
                g.drawString(mp.getAbbreviation(),
                        mp.getGridx()/Configuration.TILE_WIDTH*squareSize+5,
                        mp.getGridy()/Configuration.TILE_HEIGHT*squareSize+9
                );
                g.setColor(Color.WHITE);
                g.drawString(mp.getAbbreviation(),
                        mp.getGridx()/Configuration.TILE_WIDTH*squareSize+4,
                        mp.getGridy()/Configuration.TILE_HEIGHT*squareSize+8
                        );

                Rectangle r = ImageUtils.drawStringRect(g2,mp.getAbbreviation()+" - "+mp.getDescription(),
                        10,usedHeight,
                        descImage.getWidth()-20,100);

                usedHeight += r.getHeight() + 6;
            }
        }

        descImage = descImage.getSubimage(0,0,mapImage.getWidth(),usedHeight);
    }

    public void paintFences(){
        Graphics g = mapImage.getGraphics();
        g.setColor(Color.BLACK);

        for(MapTile mt: map.getActiveLayer().getEdgeLayer().getTiles()){
            String name = mt.getAssetResource().getName().toLowerCase();
            name = name.substring(name.lastIndexOf('\\'));
            if(!name.contains("fence")){
                continue;
            }

            int x1 = (mt.getGridx()+1)*squareSize;
            int y1 = (mt.getGridy()+1)*squareSize;
            int y2 = 0;
            int x2 = 0;
            for(int i = 0; i < squareSize; i+=2){
                if(name.contains("vert")){
                    x2 = x1;
                    y2 = y1-1;
                }else{
                    x2 = x1-1;
                    y2 = y1;
                }
                g.drawLine(x1,y1,x2,y2);
                if(name.contains("vert")){
                    y1--;
                }else{
                    x1--;
                }
            }
            highestDrawn = Math.max(highestDrawn,y1);
        }
    }

    public void paintWalls(){
        Graphics g = mapImage.getGraphics();

        for(MapTile mt: map.getActiveLayer().getEdgeLayer().getTiles()){
            String name = mt.getAssetResource().getName().toLowerCase();
            name = name.substring(name.lastIndexOf('\\'));
            Color c = Color.BLACK;
            if(name.contains("door")||name.contains("\\0")){
                c = Color.RED;
            }
            if(name.contains("window")){
                c = new Color(150,190,255);
            }
            if(name.contains("wall")||name.contains("\\1")){
                c = Color.BLACK;
            }
            if(name.contains("boundary")||name.contains("floor")||name.contains("fence")){
                continue;
            }

            g.setColor(c);
            int x = (mt.getGridx()+1)*squareSize;
            int y = (mt.getGridy()+1)*squareSize;
            if(name.contains("vert")){
                g.drawLine(x,y,x,y-squareSize);
            }else{
                g.drawLine(x,y,x-squareSize,y);
            }
            highestDrawn = Math.max(highestDrawn,y);
        }
    }

    public void save(){
        BufferedImage tempMapImage = mapImage.getSubimage(0,0,w*squareSize,Math.min(highestDrawn+10,mapImage.getHeight()));
        BufferedImage saveImage;
        if(descImage!=null){
            saveImage = new BufferedImage(tempMapImage.getWidth(),
                    tempMapImage.getHeight()+descImage.getHeight(),
                    BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = saveImage.getGraphics();
            g.drawImage(tempMapImage,0,0,null);
            g.drawImage(descImage,0,tempMapImage.getHeight(),null);

        }else {
            saveImage = tempMapImage;
        }

        try{
            File f = new File(Configuration.REF_MAP_LOCATION+map.getName()+".png");
            ImageIO.write(saveImage,"png",f);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public BufferedImage getMapImage() {
        return mapImage;
    }

    public static void main(String[] args){
        try{
            RPGMap map = new RPGMap();
            MapIO.loadMap("Secondary Lab",new LoadModel(),map);
            DungeonMasterReferenceMap dmrm = new DungeonMasterReferenceMap(map);
            dmrm.paintGround();
            dmrm.paintWalls();

            BasicWindow bw = new BasicWindow();
            ApplicationPage page = new ApplicationPage() {
                @Override
                public void loadPage() {
                    JLabel label = new JLabel(new ImageIcon(dmrm.getMapImage()));
                    add(label);
                }
            };
            bw.openPage(page);
            bw.makeVisible();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
