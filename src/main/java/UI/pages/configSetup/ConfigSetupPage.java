package UI.pages.configSetup;

import UI.app.view.ApplicationPage;
import UI.panels.configSetup.ConfigSetupPanel;

public class ConfigSetupPage extends ApplicationPage {
    @Override
    public void loadPage() {
        ConfigSetupPanel panel = new ConfigSetupPanel();
        panel.loadPanel();
        add(panel);
    }
}
