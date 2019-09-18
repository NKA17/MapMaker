package UI.pages.start;

import UI.app.view.ApplicationPage;
import UI.panels.start.StartNavigationPanel;

public class StartPage extends ApplicationPage {

    @Override
    public void loadPage() {
        StartNavigationPanel startNavigationPanel = new StartNavigationPanel();
        addPanel(startNavigationPanel);
        startNavigationPanel.setObserver(getObserver());
    }
}
