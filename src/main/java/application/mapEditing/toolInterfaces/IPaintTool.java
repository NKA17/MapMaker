package application.mapEditing.toolInterfaces;

import UI.app.assets.MapAsset;

import java.io.File;

public interface IPaintTool extends ITool {
    void addAssetToPaint(String filePath);

    boolean hasAssetToPaint(String filePath);

    boolean removeAssetToPaint(String filePath);
}
