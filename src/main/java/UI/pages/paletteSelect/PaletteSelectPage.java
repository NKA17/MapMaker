package UI.pages.paletteSelect;

import UI.app.view.ApplicationPage;
import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.panels.paletteSelect.AssetSelectScrollPanel;
import application.config.Configuration;
import application.mapEditing.toolInterfaces.IPaintTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaletteSelectPage extends ApplicationPage {

    private String baseDirectory;
    private IPaintTool paintTool;

    public PaletteSelectPage(String baseDirectory, IPaintTool paintTool){
        this.baseDirectory = baseDirectory;
        this.paintTool = paintTool;
    }


    @Override
    public void loadPage() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Configuration.PANEL_BG_COLOR);

        ApplicationPanel assetSelectPanel = new AssetSelectScrollPanel(baseDirectory,paintTool);
        assetSelectPanel.loadPanel();

        JButton closeBtn = ButtonFactory.createButton("Close");
        closeBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                if(paintTool.isEmpty()){
                    File first = (new File(baseDirectory)).listFiles()[0].listFiles()[0];
                    String filePath = (first.getAbsolutePath());
                    paintTool.addAssetToPaint(filePath);
                }
                getObserver().dispose();
            }
        });

        JButton clear = ButtonFactory.createButton("Clear");
        clear.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                paintTool.resetPalette();
                removeAll();
                loadPage();
                getObserver().invalidate();
                getObserver().revalidate();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4,4,4,10);
        add(assetSelectPanel,gbc);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(4,4,4,10);
        buttonPanel.add(clear,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(4,4,4,10);
        buttonPanel.add(closeBtn,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(4,4,4,10);
        add(buttonPanel,gbc);
    }
}
