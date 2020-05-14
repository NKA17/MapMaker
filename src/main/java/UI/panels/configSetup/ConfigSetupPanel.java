package UI.panels.configSetup;

import UI.app.view.ApplicationPanel;
import UI.pages.configSetup.ConfigSetupPage;
import UI.windows.BasicWindow;
import application.config.Configuration;

import javax.swing.*;

public class ConfigSetupPanel extends ApplicationPanel {
    @Override
    public void loadPanel() {
        JFileChooser chooser = new JFileChooser();
        chooser.setBackground(Configuration.PANEL_BG_COLOR);

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getPath());
        }
    }

    public static void main(String[] args){
        BasicWindow bw = new BasicWindow();
        bw.openPage(new ConfigSetupPage());
        bw.makeVisible();
    }
}
