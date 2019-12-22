package UI.pages.loot.lootNavigation;

import UI.app.view.ApplicationPage;
import UI.panels.loot.lootNavigation.LootNavigationPanel;

public class LootNavigationPage extends ApplicationPage {
    @Override
    public void loadPage() {
        LootNavigationPanel lootNavigationPanel = new LootNavigationPanel();
        addPanel(lootNavigationPanel);
        lootNavigationPanel.setObserver(getObserver());
    }
}
