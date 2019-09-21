package UI.panels.mapSelect;

import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.editmap.EditMapPage;
import application.config.Configuration;
import application.io.MapIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.File;

public class MapSelectPanel extends ApplicationPanel{
    @Override
    public void loadPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(4,4,4,10);
        gbc.fill = 1;

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        //scrollPane.setPreferredSize(new Dimension(400,200));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new GridBagLayout());
        scrollPane.setViewportView(viewPanel);
        scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        add(scrollPane);


        File dir = new File(Configuration.SAVE_FOLDER);
        for(File mapFile: dir.listFiles()){
            if(mapFile.getName().endsWith(Configuration.FILE_EXTENSION)){
                JButton button = createButton(mapFile);
                viewPanel.add(button,gbc);
                gbc.gridy++;
            }
        }

        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }

    private JButton createButton(File file){
        String buttonText = file.getName().replace(Configuration.FILE_EXTENSION,"");
        JButton button = ButtonFactory.createButton(buttonText);
        button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                EditMapPage editMapPage = new EditMapPage(file.getAbsolutePath());
                getObserver().openPage(editMapPage);
            }
        });

        return button;
    }
}
