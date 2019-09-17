package UI.windows;

import UI.app.ApplicationPage;
import UI.app.ApplicationWindow;
import UI.pages.start.StartPage;
import UI.panels.start.StartNavigationPanel;

import javax.swing.*;
import java.awt.*;

public class BasicWindow extends ApplicationWindow {


    @Override
    public void initUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        BasicWindow sp = new BasicWindow();
        sp.openPage(new StartPage());
        sp.makeVisible();
    }
}
