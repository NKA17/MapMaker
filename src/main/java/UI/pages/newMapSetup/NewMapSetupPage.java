package UI.pages.newMapSetup;

import UI.app.view.ApplicationPage;
import UI.panels.newMapSetup.NewMapSetupOptionsPanel;

public class NewMapSetupPage extends ApplicationPage {
    @Override
    public void loadPage() {
        addPanel(new NewMapSetupOptionsPanel());
    }
}
