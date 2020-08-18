package UI.panels.playMap.mob;


import UI.app.view.ApplicationPanel;
import UI.factory.ButtonBag;
import UI.factory.ButtonFactory;
import UI.factory.TextFactory;
import UI.panels.mapView.MapViewPanel;
import application.config.Configuration;
import drawing.shapes.Shape;
import javafx.util.Pair;
import model.map.structure.RPGMap;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddMobPanel extends ApplicationPanel {
    private RPGMap map;
    private MapViewPanel viewPanel;
    private static Pair<Integer, String> size = null;
    private static ArrayList<Pair<Integer, String>> sizeOptions = new ArrayList<Pair<Integer, String>>();
    private static String lastMobName = "Mob";
    private static Color lastColor = new Color(50,255,255);
    private static ArrayList<Color> colorOptions = new ArrayList<>();
    private static HashMap<String, List<MobTile>> mobs = new HashMap<>();

    private int r = 155;
    private int g = 0;
    private int b = 0;

    static {
        sizeOptions.add(new Pair<>(25,"Small"));
        sizeOptions.add(new Pair<>(40,"Medium"));
        sizeOptions.add(new Pair<>(90,"Large"));
        sizeOptions.add(new Pair<>(140,"Huge"));
        sizeOptions.add(new Pair<>(190,"Giant"));
        size = sizeOptions.get(2);

        colorOptions.add(new Color(255,50,50));
        colorOptions.add(new Color(50,255,50));
        colorOptions.add(new Color(50,50,255));
        colorOptions.add(new Color(255,255,50));
        colorOptions.add(new Color(50,255,255));
        colorOptions.add(new Color(255,50,255));
        lastColor = colorOptions.get(0);
    }

    public AddMobPanel(RPGMap map, MapViewPanel viewPanel) {
        this.map = map;
        this.viewPanel = viewPanel;
    }

    @Override
    public void loadPanel() {
        setLayout(new GridBagLayout());

        JLabel pageTitle = TextFactory.createTitle("Create Mob");
        pageTitle.setPreferredSize(new Dimension(350,30));

        JTextArea mobName = TextFactory.createTextArea(lastMobName, 50);
        mobName.setPreferredSize(new Dimension(350,30));

        JLabel sizeLabel = TextFactory.createLabel("Size: ");

        ButtonBag buttonBag = new ButtonBag();
        ArrayList<JButton> sizebuttons = new ArrayList<JButton>();
        for(Pair<Integer, String> p: sizeOptions){
            JButton button = buttonBag.createButton(p.getValue());
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    size = p;
                }
            });
            if(p == size){
                button.setBackground(Configuration.HIGHLIGHT_COLOR);
            }
            sizebuttons.add(button);
        }

        ApplicationPanel thisRef = this;
        setPreferredSize(new Dimension(400,400));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4,4,4,10);

        JSlider colorSliderR = ButtonFactory.createSlider(0,255);
        JSlider colorSliderG = ButtonFactory.createSlider(0,255);
        JSlider colorSliderB = ButtonFactory.createSlider(0,255);
        colorSliderR.setValue(lastColor.getRed());
        colorSliderG.setValue(lastColor.getGreen());
        colorSliderB.setValue(lastColor.getBlue());
        r = lastColor.getRed();
        g = lastColor.getGreen();
        b = lastColor.getBlue();
        ApplicationPanel colorPanel = new ApplicationPanel() {
            @Override
            public void loadPanel() {
                setPreferredSize(new Dimension(32,32));
                repaint();
            }

            public void paintComponent(Graphics gr){
                gr.setColor(new Color(r,g,b));
                gr.fillRect(0,0,32,32);
            }
        };
        colorSliderR.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                r = colorSliderR.getValue();
                lastColor = new Color(r,g,b);
                repaint();
            }
        });
        colorSliderG.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                g = colorSliderG.getValue();
                lastColor = new Color(r,g,b);
                repaint();
            }
        });
        colorSliderB.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                b = colorSliderB.getValue();
                lastColor = new Color(r,g,b);
                repaint();
            }
        });

        JButton createButton = ButtonFactory.createButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastMobName = mobName.getText();
                createMob(mobName.getText(),size.getKey());
                viewPanel.repaint();
                getObserver().dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Configuration.PANEL_BG_COLOR);

        gbc.gridx = 0;
        gbc.gridy=0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pageTitle,gbc);

        gbc.gridx = 0;
        gbc.gridy=1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(mobName,gbc);

        gbc.gridx = 0;
        gbc.gridy=2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(TextFactory.createLabel("Red: "),gbc);
        gbc.gridx = 0;
        gbc.gridy=3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(TextFactory.createLabel("Green: "),gbc);
        gbc.gridx = 0;
        gbc.gridy=4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(TextFactory.createLabel("Blue: "),gbc);

        gbc.gridx = 1;
        gbc.gridy=2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorSliderR,gbc);
        gbc.gridx = 1;
        gbc.gridy=3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorSliderG,gbc);
        gbc.gridx = 1;
        gbc.gridy=4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorSliderB,gbc);

        gbc.gridx = 0;
        gbc.gridy+=1;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(colorPanel,gbc);

        int y = gbc.gridy + 1;
        int perRow = 7;
        for(int i = 0; i < sizebuttons.size(); i++){

            gbc.gridx = i % perRow;
            gbc.gridy=y+(i / perRow);
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(sizebuttons.get(i),gbc);
        }

        y += 1 + (sizebuttons.size() / perRow);
        gbc.gridx = 0;
        gbc.gridy=y;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(createButton,gbc);

        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonPanel,gbc);
    }

    private void createMob(String name, int size){
        String count = "";
        if(mobs.containsKey(name)){
            count = " "+(mobs.get(name).size() + 1);
            if(mobs.get(name).size() == 1){
                MobTile mobTile = mobs.get(name).get(0);
                mobTile.name += " 1";
            }
        }else{
            List<MobTile> list = new ArrayList<>();
            mobs.put(name,list);
        }
        MobTile mob = new MobTile(new Color(r,g,b),name+count,size,map.getXoffset()+600,map.getYoffset()+300);
        mobs.get(name).add(mob);
        map.getShapes().add(mob);
    }

    class MobTile extends Shape {
        public Color color;
        public String name;
        public int size, x, y;

        public MobTile(Color color, String name, int size, int x, int y) {
            this.color = color;
            this.name = name;
            this.size = size;
            this.x = x;
            this.y = y;
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(color);
            g.fillOval(x - (size / 2),y - size,size,size);
            g.setColor(Color.BLACK);
            g.drawOval(x - (size / 2),y - size,size,size);

            Graphics2D g2 = (Graphics2D)g;
            g2.setFont(new Font(Configuration.GAME_FONT.getFontName(),Font.BOLD,15));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.BLACK);
            g2.drawString(name,x - (size/2)-1,y-1);
            g2.drawString(name,x - (size/2)+1,y-1);
            g2.drawString(name,x - (size/2)-1,y+1);
            g2.drawString(name,x - (size/2)-1,y+1);
            g2.setColor(Color.WHITE);
            g2.drawString(name,x - (size/2),y);
        }

        @Override
        public boolean shouldDrag(int x, int y) {
            boolean xgt = x >= this.x - (size / 2);
            boolean xlt = x <= this.x + (size / 2);
            boolean ygt = y >= this.y - size;
            boolean ylt = y <= this.y;
            return xgt && xlt &&
                    ygt && ylt;
        }

        @Override
        public boolean shouldRotate(int x, int y) {
            return false;
        }

        @Override
        public void translate(int delta_x, int delta_y) {
            this.x += delta_x;
            this.y += delta_y;
        }
    }
}
