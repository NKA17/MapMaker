package model.map.tiles;

import UI.app.assets.MapAsset;
import UI.app.view.ApplicationAction;
import UI.app.view.ApplicationPage;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.pages.editmap.EditMapPage;
import UI.pages.playMap.PlayMapPage;
import UI.windows.BasicWindow;
import application.config.AppState;
import application.config.Configuration;
import application.io.AssetCache;
import application.mapEditing.tools.DragTool;
import model.map.structure.RPGMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapPointer extends AssetTile {
    public MapPointer() {
        super(AssetCache.get("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\assets\\map\\mechanics\\markers\\pointer.png"));
        assignAbbreviation();
    }

    public MapPointer(int gridx, int gridy) {
        super(AssetCache.get("C:\\Users\\Nate\\IdeaProjects\\RPGMapMaker\\src\\main\\resources\\assets\\map\\mechanics\\markers\\pointer.png"),
                gridx, gridy);
        assignAbbreviation();
    }

    private int cogXOffset = 20;
    private int cogYOffset = 20;
    private String abbreviation = "A";
    private String description = "Description";
    private static final String[] cylinder = new String[]{
            "A","B","C","D","E","F","G","H","I","J","k","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"
    };

    private void assignAbbreviation(){
        HashMap<String,Boolean> map = new HashMap<>();
        if(AppState.ACTIVE_PAGE instanceof EditMapPage
                && ((EditMapPage)AppState.ACTIVE_PAGE).getMap()!=null){

            for(MapTile mt: ((EditMapPage)AppState.ACTIVE_PAGE).getMap().getMechanicsLayer().getTiles()){
                if(mt.getAssetResource().getName().contains("pointer")) {
                    map.put(((MapPointer) mt).getAbbreviation(), true);
                }
            }
            int[] nameCylinder = new int[]{0,-1,-1};
            boolean freeName = false;
            while(!freeName){
                try {
                    if (map.containsKey(buildName(nameCylinder))) {
                        if (buildName(nameCylinder).equalsIgnoreCase("ZZZ")) {
                            abbreviation = "???";
                            return;
                        }
                        if (nameCylinder[0] >= cylinder.length - 1) {
                            nameCylinder[1]++;
                            nameCylinder[0] = -1;
                        }
                        if (nameCylinder[1] >= cylinder.length - 1) {
                            nameCylinder[2]++;
                            nameCylinder[1] = 0;
                        }
                        nameCylinder[0]++;
                    } else {
                        freeName = true;
                        abbreviation = buildName(nameCylinder);
                    }
                }catch (Exception e){
                    abbreviation = "???";
                    return;
                }
            }
        }
    }
    private String buildName(int[] nameCylinder){
        String name = cylinder[nameCylinder[0]];
        if(nameCylinder[1]>-1)name+=cylinder[nameCylinder[1]];
        if(nameCylinder[2]>-1)name+=cylinder[nameCylinder[2]];
        return name;
    }

    public static void main(String[] args){
        RPGMap map = new RPGMap();
        map.setGridHeight(20);
        map.setGridWidth(20);
        map.init();
        EditMapPage mp = new EditMapPage(map);
        AppState.ACTIVE_PAGE = mp;
        for(int i = 0; i < 26*26*28; i++){
            MapPointer p = new MapPointer();
            map.getMechanicsLayer().getTiles().add(p);
            System.out.println(p.getAbbreviation());
        }

    }
    @Override
    public void draw(Graphics g){

        g.drawImage(getAssetResource().getImage(),
                getGridx()-getAssetResource().getImage().getWidth()/2,
                getGridy()-getAssetResource().getImage().getHeight(),
                null);
        g.setColor(Color.WHITE);
        g.drawString(abbreviation,
                getGridx()-getAssetResource().getImage().getWidth()/2+10,
                getGridy()-getAssetResource().getImage().getHeight()+22);

        if(AppState.ACTIVE_DRAGGABLE==this
                && AppState.ACTIVE_TOOL instanceof DragTool
                && (AppState.ACTIVE_PAGE instanceof EditMapPage || AppState.ACTIVE_PAGE instanceof PlayMapPage)) {
            g.setColor(Configuration.HIGHLIGHT_COLOR);
            g.drawRect(getGridx() + getXoffset() - 5,
                    getGridy() + getYoffset() - 5,
                    getAssetResource().getImage().getWidth() + 5,
                    getAssetResource().getImage().getHeight() + 5);

            g.setColor(Configuration.COMP_BG_COLOR);
            g.fillRect(getGridx()-getAssetResource().getImage().getWidth()/2-cogXOffset-2,
                    getGridy()-getAssetResource().getImage().getHeight()-cogYOffset-2,
                    24,24);
            g.setColor(Color.BLACK);
            g.drawRect(getGridx()-getAssetResource().getImage().getWidth()/2-cogXOffset-2,
                    getGridy()-getAssetResource().getImage().getHeight()-cogYOffset-2,
                    24,24);
            g.drawImage(AssetCache.COG_ICON,
                    getGridx()-getAssetResource().getImage().getWidth()/2-cogXOffset,
                    getGridy()-getAssetResource().getImage().getHeight()-cogYOffset,
                    null);
        }
    }

    @Override
    public boolean shouldDrag(int x, int y) {

        int xmin = getGridx() - (getAssetResource().getImage().getWidth() / 2)-cogXOffset-2;
        int xmax = getGridx() + (getAssetResource().getImage().getWidth() / 2);
        int ymin = getGridy() - getAssetResource().getImage().getHeight()-cogYOffset-2;
        int ymax = getGridy();

        if(x >= xmin && x <= xmin+24 && y >= ymin && y <= ymin+24){
            showEditWindow();
        }

        return x >= xmin
                && x <= xmax
                && y >= ymin
                && y <= ymax;
    }

    private void showEditWindow(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                BasicWindow bw = new BasicWindow();
                EditPage page = new EditPage();
                page.setOnDisposeAction(new ApplicationAction() {
                    @Override
                    public void action() {
                        AppState.ACTIVE_PAGE.repaint();
                    }
                });
                bw.openPage(page,false);
                bw.makeVisible();
            }
        };
        Thread th = new Thread(runnable);
        th.start();
    }

    class EditPage extends ApplicationPage{

        @Override
        public void loadPage() {
            JPanel fieldPanel = new JPanel(new GridBagLayout());
            fieldPanel.setBackground(null);
            JPanel buttonPanel = new JPanel(new GridBagLayout());
            buttonPanel.setBackground(null);

            JLabel abbLabel = TextFactory.createLabel("Abbreviation:");
            JLabel descLabel = TextFactory.createLabel("Description");
            JTextField abbField = TextFactory.createTextField(abbreviation,3);
            abbField.setPreferredSize(new Dimension(60,30));
            abbField.setColumns(6);
            JTextArea descField = TextFactory.createTextArea(description,450);
            descField.setPreferredSize(new Dimension(250,180));


            JButton cancel = ButtonFactory.createButton("Cancel");
            JButton ok = ButtonFactory.createButton("Ok");

            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getObserver().dispose();
                }
            });

            ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    abbreviation = abbField.getText();
                    description = descField.getText();
                    getObserver().dispose();
                }
            });

            GridBagConstraints gbc = new GridBagConstraints();

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = Configuration.GRIDBAG_INSETS;
            gbc.anchor = GridBagConstraints.WEST;
            fieldPanel.add(abbLabel,gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            fieldPanel.add(abbField,gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            fieldPanel.add(descLabel,gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            fieldPanel.add(descField,gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.EAST;
            buttonPanel.add(ok,gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            buttonPanel.add(cancel,gbc);

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(fieldPanel,gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(buttonPanel,gbc);
        }
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
