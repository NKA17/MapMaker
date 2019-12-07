package UI.app.view;

import javax.swing.*;
import java.awt.*;

public abstract class ApplicationPanel extends JPanel {
    private ApplicationWindow observer;

    public ApplicationPanel(){

        setBackground(null);
    }

    public void setObserver(ApplicationWindow observer){
        this.observer = observer;
    }

    public ApplicationWindow getObserver() {
        return observer;
    }

    public abstract void loadPanel();

    public void dispose(){
    }
}
