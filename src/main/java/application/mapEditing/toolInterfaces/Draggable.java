package application.mapEditing.toolInterfaces;

public interface Draggable {
    void translate(int delta_x, int delta_y);
    boolean shouldDrag(int x, int y);
}
