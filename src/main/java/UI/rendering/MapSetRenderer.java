package UI.rendering;

import application.config.Configuration;
import application.io.AssetCache;
import application.io.LoadModel;
import model.map.structure.GraphicLayer;
import model.map.structure.MapSet;
import model.map.tiles.AssetTile;
import model.map.tiles.MapTile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class MapSetRenderer {
    private MapSet set;
    private BufferedImage floorImage;
    private BufferedImage graphicImage;
    private BufferedImage edgeImage;

    public MapSetRenderer(MapSet set) {
        this.set = set;
        floorImage = new BufferedImage(
                Configuration.TILE_WIDTH * set.getGridWidth(),
                Configuration.TILE_HEIGHT * set.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        graphicImage = new BufferedImage(
                Configuration.TILE_WIDTH * set.getGridWidth(),
                Configuration.TILE_HEIGHT * set.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        edgeImage = new BufferedImage(
                Configuration.TILE_WIDTH * set.getGridWidth(),
                Configuration.TILE_HEIGHT * set.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
    }

    public void render(){
        render(null);
    }

    public void render(LoadModel model){
        //For floor, graphic, and edge images
        if(model!=null)
            model.incrementTotalBytes(3);

        /**
         * New up layer images
         */
        floorImage = new BufferedImage(
                Configuration.TILE_WIDTH * set.getGridWidth(),
                Configuration.TILE_HEIGHT * set.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        floorImage.createGraphics();

        graphicImage = new BufferedImage(
                Configuration.TILE_WIDTH * set.getGridWidth(),
                Configuration.TILE_HEIGHT * set.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        graphicImage.createGraphics();

        edgeImage = new BufferedImage(
                Configuration.TILE_WIDTH * set.getGridWidth(),
                Configuration.TILE_HEIGHT * set.getGridHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        edgeImage.createGraphics();

        /**
         * Create Renderers section
         */
        MapLayerRenderer floorRenderer = new MapLayerRenderer(
                set.getTileLayer().getTiles(),
                set.getGridWidth(),
                set.getGridHeight());

        List<MapLayerRenderer> graphicsRenderers = new ArrayList<>();
        for(GraphicLayer layer: set.getGraphicLayers()){
            MapLayerRenderer graphicRenderer = new MapLayerRenderer(layer.getTiles(),
                    set.getGridWidth(),
                    set.getGridHeight());
            graphicsRenderers.add(graphicRenderer);
        }

        MapLayerRenderer edgeRenderer = new MapLayerRenderer(new ArrayList<>(),
                set.getGridWidth(),
                set.getGridHeight());

        /**
         * Sort edges as graphics
         */
        for(MapTile edge: set.getEdgeLayer().getTiles()){
            if(edge.getAssetResource().getName().toLowerCase().contains("fringe")){

                AssetTile tile = new AssetTile(AssetCache.get(edge.getAssetResource().getName()),
                        (1+edge.getGridx())*Configuration.TILE_WIDTH,
                        (1+edge.getGridy())*Configuration.TILE_HEIGHT);
                tile.setXoffset(-Configuration.TILE_WIDTH);
                tile.setYoffset(-Configuration.TILE_HEIGHT);
                graphicsRenderers.get(0).getTiles().add(tile);
            }else {
                edgeRenderer.getTiles().add(edge);
            }
        }

        /**
         * Render Section
         */
        floorRenderer.render(model);
        for(MapLayerRenderer renderer: graphicsRenderers){
            renderer.render(model);
        }
        edgeRenderer.render(model);

        /**
         * Super impose section
         */
        floorImage.getGraphics().drawImage(floorRenderer.getRender(),0,0,null);
        if(model!=null)
            model.incrementReadBytes(1);

        for(MapLayerRenderer renderer: graphicsRenderers){
            graphicImage.getGraphics().drawImage(renderer.getRender(),0,0,null);
        }
        if(model!=null)
            model.incrementReadBytes(1);

        edgeImage.getGraphics().drawImage(edgeRenderer.getRender(),0,0,null);
        if(model!=null)
            model.incrementReadBytes(1);
    }

    public BufferedImage getFloorImage() {
        return floorImage;
    }

    public void setFloorImage(BufferedImage floorImage) {
        this.floorImage = floorImage;
    }

    public BufferedImage getGraphicImage() {
        return graphicImage;
    }

    public void setGraphicImage(BufferedImage graphicImage) {
        this.graphicImage = graphicImage;
    }

    public BufferedImage getEdgeImage() {
        return edgeImage;
    }

    public void setEdgeImage(BufferedImage edgeImage) {
        this.edgeImage = edgeImage;
    }
}
