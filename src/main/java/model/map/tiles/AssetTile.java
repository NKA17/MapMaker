package model.map.tiles;

import UI.app.assets.MapAsset;
import UI.app.view.ApplicationPage;
import UI.pages.editmap.EditMapPage;
import UI.pages.playMap.PlayMapPage;
import application.config.AppState;
import application.config.Configuration;
import application.mapEditing.toolInterfaces.Draggable;
import application.mapEditing.toolInterfaces.ITool;
import application.mapEditing.tools.DragTool;

import java.awt.*;
import java.io.File;

public class AssetTile extends MapTile implements Draggable{

    private int xoffset = 0;
    private int yoffset = 0;

    public AssetTile(File assetFile, int gridx, int gridy) {
        super(assetFile, gridx, gridy);
        xoffset = - (getAssetResource().getImage().getWidth() / 2);
        yoffset = - (getAssetResource().getImage().getHeight());
    }

    public AssetTile(File assetFile) {
        super(assetFile, 0, 0);
        xoffset = - (getAssetResource().getImage().getWidth() / 2);
        yoffset = - (getAssetResource().getImage().getHeight());
    }

    public AssetTile(MapAsset mapAsset, int gridx, int gridy) {
        super(mapAsset, gridx, gridy);
        xoffset = - (getAssetResource().getImage().getWidth() / 2);
        yoffset = - (getAssetResource().getImage().getHeight());
    }

    public AssetTile(MapAsset mapAsset) {
        super(mapAsset, 0, 0);
        xoffset = - (getAssetResource().getImage().getWidth() / 2);
        yoffset = - (getAssetResource().getImage().getHeight());
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(getAssetResource().getImage(),
                getGridx() + xoffset,
                getGridy() + yoffset ,
                null);

        Draggable d = AppState.ACTIVE_DRAGGABLE;
        ApplicationPage a = AppState.ACTIVE_PAGE;
        ITool i = AppState.ACTIVE_TOOL;
        if(AppState.ACTIVE_DRAGGABLE==this
                && AppState.ACTIVE_TOOL instanceof DragTool
                && (AppState.ACTIVE_PAGE instanceof EditMapPage || AppState.ACTIVE_PAGE instanceof PlayMapPage)) {
            g.setColor(Configuration.HIGHLIGHT_COLOR);
            g.drawRect(getGridx() + getXoffset() - 5,
                    getGridy() + getYoffset() - 5,
                    getAssetResource().getImage().getWidth() + 5,
                    getAssetResource().getImage().getHeight() + 5);
        }
    }

    public  boolean isOnScreen(int mapxoffset, int mapyoffset){
        int xmin = -mapxoffset - getAssetResource().getImage().getWidth();
        int ymin = -mapyoffset - getAssetResource().getImage().getHeight();
        int xmax = -mapxoffset + (getAssetResource().getImage().getWidth() + 1200);
        int ymax = -mapyoffset + (getAssetResource().getImage().getHeight() + 700);

        boolean validx = getGridx() >= xmin && getGridx() <= xmax;
        boolean validy = getGridy() >= ymin && getGridy() <= ymax;

        return validx && validy;
    }

    @Override
    public boolean shouldDrag(int x, int y) {
        int xmin = getGridx() - (getAssetResource().getImage().getWidth() / 2);
        int xmax = getGridx() + (getAssetResource().getImage().getWidth() / 2);
        int ymin = getGridy() - getAssetResource().getImage().getHeight();
        int ymax = getGridy();

        return x >= xmin
                && x <= xmax
                && y >= ymin
                && y <= ymax;
    }

    @Override
    public void translate(int delta_x, int delta_y) {
        setGridx(getGridx()+delta_x);
        setGridy(getGridy()+delta_y);
    }

    public int getXoffset() {
        return xoffset;
    }

    public void setXoffset(int xoffset) {
        this.xoffset = xoffset;
    }

    public int getYoffset() {
        return yoffset;
    }

    public void setYoffset(int yoffset) {
        this.yoffset = yoffset;
    }
}
