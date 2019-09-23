package UI.rendering;

import java.util.ArrayList;
import java.util.List;

public class MapLayerGroupRenderer {
    private List<MapLayerRenderer> groups = new ArrayList<>();

    public List<MapLayerRenderer> getGroups() {
        return groups;
    }

    public void setGroups(List<MapLayerRenderer> groups) {
        this.groups = groups;
    }
}
