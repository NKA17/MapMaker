package UI.app;

import javax.swing.*;

public abstract class ApplicationPanel extends JPanel {
    private ApplicationWindow observer;

    public void setObserver(ApplicationWindow observer){
        this.observer = observer;
    }

    public ApplicationWindow getObserver() {
        return observer;
    }

    public abstract void loadPanel();
}
