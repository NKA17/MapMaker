package UI.panels.saveMap;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.LoadPage.LoadPage;
import application.config.Configuration;
import application.io.LoadModel;
import application.io.MapIO;
import model.map.structure.RPGMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveMapPanel extends ApplicationPanel{
    private RPGMap map;

    public SaveMapPanel(RPGMap map) {
        this.map = map;
    }

    @Override
    public void loadPanel() {
        setLayout(new GridBagLayout());

        JTextField fileNameField = new JTextField("Map_Name");
        fileNameField.setPreferredSize(new Dimension(350,30));
        fileNameField.setEditable(true);

        ApplicationPanel thisRef = this;
        setPreferredSize(new Dimension(400,100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4,4,4,10);

        JButton saveButton = ButtonFactory.createButton("Save");
        saveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String fileName = fileNameField.getText().replaceAll(Configuration.FILE_EXTENSION,"").trim();

                if(fileName.equals("")){
                    gbc.gridy = 0;
                    gbc.gridx = 0;
                    gbc.anchor = GridBagConstraints.WEST;
                    JLabel warning = new JLabel("Name cannot be empty!");
                    warning.setForeground(new Color(240,60,60));
                    warning.setPreferredSize(new Dimension(350,30));
                    thisRef.add(warning,gbc);
                    thisRef.invalidate();
                    thisRef.revalidate();
                }else{
                    save(fileName);
                }
            }
        });

        JButton cancel = ButtonFactory.createButton("Cancel");
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                getObserver().dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Configuration.PANEL_BG_COLOR);

        gbc.gridx = 1;
        gbc.gridy=0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(saveButton,gbc);

        gbc.gridx = 0;
        gbc.gridy=0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        buttonPanel.add(cancel,gbc);

        gbc.gridx = 0;
        gbc.gridy=1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(fileNameField,gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel,gbc);
    }

    private void save(String fileName){
        LoadModel loadModel = new LoadModel();
        Runnable showSave = new Runnable() {
            @Override
            public void run() {
                LoadPage loadPage = new LoadPage(loadModel) {
                    @Override
                    public void onLoad() {
                        //do nothin
                    }
                };
                getObserver().openPage(loadPage);
            }
        };

        Runnable doSave = new Runnable() {
            @Override
            public void run() {
                MapIO.saveMap(map,Configuration.SAVE_FOLDER+fileName+Configuration.FILE_EXTENSION,loadModel);
                getObserver().dispose();
            }
        };

        Thread progressThread = new Thread(showSave);
        Thread saveThread = new Thread(doSave);

        progressThread.start();
        saveThread.start();
    }
}
