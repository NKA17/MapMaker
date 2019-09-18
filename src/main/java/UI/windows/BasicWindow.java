package UI.windows;

import UI.app.view.ApplicationWindow;
import UI.pages.start.StartPage;

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
