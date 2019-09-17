package UI.pages.start;

import UI.app.ApplicationPage;
import UI.app.ApplicationPanel;
import UI.app.ApplicationWindow;
import UI.panels.start.StartNavigationPanel;

public class StartPage extends ApplicationPage {

    @Override
    public void loadPage() {
        StartNavigationPanel startNavigationPanel = new StartNavigationPanel();
        addPanel(startNavigationPanel);
        startNavigationPanel.setObserver(getObserver());
    }
}
