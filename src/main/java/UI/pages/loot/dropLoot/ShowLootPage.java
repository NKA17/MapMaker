package UI.pages.loot.dropLoot;

import UI.app.view.ApplicationPage;
import UI.panels.loot.dropLoot.ShowLootPanel;
import UI.panels.loot.editItem.EditItemPanel;
import application.loot.structure.DropBag;

import java.util.List;

public class ShowLootPage extends ApplicationPage {

    private List<DropBag> dropBagList;
    private int roll;

    public ShowLootPage(List<DropBag> dropBagList, int roll) {
        this.dropBagList = dropBagList;
        this.roll = roll;
    }

    @Override
    public void loadPage() {
        ShowLootPanel lootPanel = new ShowLootPanel(dropBagList,roll);
        addPanel(lootPanel);
        lootPanel.setObserver(getObserver());
    }
}
