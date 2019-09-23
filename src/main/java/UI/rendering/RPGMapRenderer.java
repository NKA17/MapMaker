package UI.rendering;

import UI.pages.LoadPage.LoadPage;
import UI.windows.BasicWindow;
import application.config.Configuration;
import application.io.AssetCache;
import application.io.LoadModel;
import application.io.MapIO;
import model.map.structure.MapLayer;
import model.map.structure.MapSet;
import model.map.structure.RPGMap;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RPGMapRenderer {
    private RPGMap map;
    private List<MapSetRenderer> renderers = new ArrayList<>();
    private BufferedImage bottom = null;
    private BufferedImage middle = null;
    private BufferedImage top = null;

    public RPGMapRenderer(RPGMap map) {
        this.map = map;
    }

    private int calculateLoad(){
        int size = 0;
        for(MapSet set: map.getLayerSets()){
            size+=(set.getTileLayer().getTiles().size());
            for(MapLayer layer: set.getGraphicLayers()){
                size+=(layer.getTiles().size());
            }
            size+=(set.getEdgeLayer().getTiles().size());
        }
        return size;
    }

    public void render(){
        render(null);
    }

    public void render(LoadModel model){
        if(model != null)
            model.incrementTotalBytes(calculateLoad());

        bottom = new BufferedImage(
                Configuration.TILE_WIDTH * map.getGridWidth(),
                Configuration.TILE_HEIGHT*map.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        bottom.createGraphics();


        middle = new BufferedImage(
                Configuration.TILE_WIDTH * map.getGridWidth(),
                Configuration.TILE_HEIGHT*map.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        middle.createGraphics();


        top = new BufferedImage(
                Configuration.TILE_WIDTH * map.getGridWidth(),
                Configuration.TILE_HEIGHT*map.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        top.createGraphics();

        List<MapSetRenderer> setRenderers = new ArrayList<>();

        for(MapSet set: map.getLayerSets()){
            MapSetRenderer setRenderer = new MapSetRenderer(set);
            setRenderer.render(model);

            bottom.getGraphics().drawImage(setRenderer.getFloorImage(),0,0,null);
            middle.getGraphics().drawImage(setRenderer.getGraphicImage(),0,0,null);
            top.getGraphics().drawImage(setRenderer.getEdgeImage(),0,0,null);

            setRenderers.add(setRenderer);
        }

        this.renderers = setRenderers;
    }

    public BufferedImage draw(){
        BufferedImage render = new BufferedImage(
                Configuration.TILE_WIDTH * map.getGridWidth(),
                Configuration.TILE_HEIGHT*map.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);


        render.createGraphics();
        Graphics g = render.getGraphics();

        g.drawImage(bottom,0,0,null);
        g.drawImage(middle,0,0,null);
        g.drawImage(top,0,0,null);

        try{
            System.out.println(ImageIO.write(render,"png",new File("MapRender.png")));
        }catch (Exception e){
            e.printStackTrace();
        }

        return render;
    }

    public static void main(String[] args){
        RPGMap map = new RPGMap();

        MapIO.loadMap(Configuration.SAVE_FOLDER+"Jungle Village",new LoadModel(),map);
        RPGMapRenderer mapRenderer = new RPGMapRenderer(map);

        mapRenderer.render(new LoadModel());
        mapRenderer.draw();
    }
}
