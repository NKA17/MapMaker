package UI.pages.loot.editItem;

import UI.app.view.ApplicationPage;
import UI.panels.loot.editItem.EditItemPanel;
import UI.panels.start.StartNavigationPanel;
import application.loot.structure.DropBag;
import application.loot.structure.ItemRow;

public class EditItemPage extends ApplicationPage{

    private ItemRow item;
    private DropBag dropBag;
    public EditItemPage(ItemRow item,DropBag dropBag){
        this.item = item;
        this.dropBag = dropBag;
    }
    @Override
    public void loadPage() {
        EditItemPanel startNavigationPanel = new EditItemPanel(item,dropBag);
        addPanel(startNavigationPanel);
        startNavigationPanel.setObserver(getObserver());
    }
}
