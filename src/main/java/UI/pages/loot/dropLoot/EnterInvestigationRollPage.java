package UI.pages.loot.dropLoot;

import UI.app.view.ApplicationPage;
import UI.panels.loot.dropLoot.EnterInvestigationRollPanel;
import UI.panels.loot.editItem.EditItemPanel;
import application.loot.structure.DropBag;

import java.util.ArrayList;
import java.util.List;

public class EnterInvestigationRollPage extends ApplicationPage {

    private List<DropBag> dropBagList;

    public EnterInvestigationRollPage(List<DropBag> dropBagList) {
        this.dropBagList = dropBagList;
    }

    public EnterInvestigationRollPage(DropBag bag){
        dropBagList = new ArrayList<>();
        dropBagList.add(bag);
    }

    @Override
    public void loadPage() {
        EnterInvestigationRollPanel investigationRollPanel = new EnterInvestigationRollPanel(dropBagList);
        addPanel(investigationRollPanel);
        investigationRollPanel.setObserver(getObserver());
    }
}
