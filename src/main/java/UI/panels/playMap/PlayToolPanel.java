package UI.panels.playMap;

import UI.app.assets.MapAsset;
import UI.app.view.ApplicationPanel;
import UI.factory.ButtonFactory;
import UI.pages.editmap.EditMapPage;
import UI.panels.mapView.MapViewPanel;
import application.config.Configuration;
import application.io.AssetCache;
import application.mapEditing.tools.DragTool;
import application.mapEditing.tools.FogOfWarTool;
import application.mapEditing.tools.PanTool;
import application.mapEditing.tools.ShapeTool;
import drawing.shapes.Circle;
import drawing.shapes.Line;
import drawing.shapes.Square;
import drawing.shapes.Triangle;
import model.map.structure.MapSet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PlayToolPanel extends ApplicationPanel {

    private MapViewPanel viewPanel;

    public PlayToolPanel(MapViewPanel viewPanel) {
        this.viewPanel = viewPanel;
    }

    @Override
    public void loadPanel() {
        JButton dragTool = ButtonFactory.createButtonWithIcon("drag");
        dragTool.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                viewPanel.getMapEditor().setTool(new DragTool());
            }
        });

        JButton pan = ButtonFactory.createButtonWithIcon("pan");
        pan.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                viewPanel.getMapEditor().setTool(new PanTool());
            }
        });

        JButton edit = ButtonFactory.createButtonWithIcon("edit");
        edit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                for(MapSet set: viewPanel.getMap().getLayerSets()){
                    set.destroyFog();
                }
                getObserver().openPage(new EditMapPage(
                        Configuration.SAVE_FOLDER+viewPanel.getMap().getName()));
            }
        });

        JButton grid = ButtonFactory.createButtonWithIcon("grid");
        grid.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                viewPanel.getMap().getActiveLayer().toggleGrid();
                viewPanel.repaint();
            }
        });

        JButton eye = ButtonFactory.createButtonWithIcon("eye");
        eye.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                viewPanel.getMapEditor().setTool(new FogOfWarTool());
            }
        });

        JButton circle = ButtonFactory.createButtonWithIcon("circle");
        circle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                ShapeTool tool = new ShapeTool();
                tool.setShape(new Circle());
                viewPanel.getMapEditor().setTool(tool);
            }
        });


        JButton tri = ButtonFactory.createButtonWithIcon("triangle");
        tri.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                ShapeTool tool = new ShapeTool();
                tool.setShape(new Triangle());
                viewPanel.getMapEditor().setTool(tool);
            }
        });

        JButton square = ButtonFactory.createButtonWithIcon("square");
        square.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                ShapeTool tool = new ShapeTool();
                tool.setShape(new Square());
                viewPanel.getMapEditor().setTool(tool);
            }
        });

        JButton line = ButtonFactory.createButtonWithIcon("line");
        line.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                ShapeTool tool = new ShapeTool();
                tool.setShape(new Line());
                viewPanel.getMapEditor().setTool(tool);
            }
        });

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,10);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = 1;
        add(edit,gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(pan,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(grid, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(eye, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(dragTool, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(circle, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        add(tri, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(square, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(line, gbc);
    }
}
