package UI.panels.paletteSelect;

import UI.app.ApplicationPanel;
import application.mapEditing.toolInterfaces.IPaintTool;
import application.mapEditing.tools.Tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AssetSelectScrollPanel extends ApplicationPanel {

    private JPanel viewPanel;
    private String baseDirectory;
    private IPaintTool paintTool;

    public AssetSelectScrollPanel(String baseDirectory,IPaintTool paintTool) {
        this.paintTool = paintTool;
        this.baseDirectory = baseDirectory;
    }

    @Override
    public void loadPanel() {
        setLayout(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(400,200));
        viewPanel = new JPanel();
        viewPanel.setLayout(new GridBagLayout());
        scrollPane.setViewportView(viewPanel);
        scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        add(scrollPane);
        buildAssetSelecPanels(new File(baseDirectory));
        viewPanel.invalidate();
        viewPanel.revalidate();
        scrollPane.invalidate();
        scrollPane.revalidate();
    }

    public void buildAssetSelecPanels(File file){
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        for(File f: file.listFiles()){
            gbc.gridy++;
            viewPanel.add(buildAssetSelectForCategory(f),gbc);
        }
    }

    public JPanel buildAssetSelectForCategory(File f){
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel parentPanel = new JPanel(new GridBagLayout());
        JPanel tilePanel = new JPanel(new GridBagLayout());
        JLabel category = new JLabel(f.getName());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(4,4,4,10);
        parentPanel.add(category,gbc);
        gbc.gridy = 1;
        parentPanel.add(tilePanel,gbc);

        gbc.gridy = 1;
        for(File imageFile: f.listFiles()){
            if(imageFile.getName().contains("_vert"))
                continue;

            try{
                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new GridBagLayout());
                imagePanel.setPreferredSize(new Dimension(58,58));
                imagePanel.addMouseListener(new PaletteSelectButton(imagePanel,imageFile.getAbsolutePath()));

                if(paintTool.hasAssetToPaint(imageFile.getAbsolutePath()))
                    imagePanel.setBackground(new Color(150,150,200));

                BufferedImage image = ImageIO.read(imageFile);
                if(f.getParent().contains("floor")) {
                    image = image.getSubimage(0, 0, Math.min(50, image.getWidth()), Math.min(50, image.getHeight()));
                }else {
                    image = scale(image);
                }

                if(gbc.gridx > 5){
                    gbc.gridx =0;
                    gbc.gridy++;
                }

                gbc.anchor = GridBagConstraints.WEST;
                JLabel label = new JLabel(new ImageIcon(image));
                imagePanel.add(label);
                gbc.anchor = GridBagConstraints.CENTER;
                tilePanel.add(imagePanel,gbc);

                gbc.gridx ++;
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return parentPanel;
    }

    private BufferedImage scale(BufferedImage before){
        BufferedImage resized = new BufferedImage(50, 50, before.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(before, 0, 0, 50, 50, 0, 0, before.getWidth(),
                before.getHeight(), null);
        g.dispose();
        return resized;
    }

    class PaletteSelectButton implements MouseListener{

        private JPanel panel;
        private String fileName;

        public PaletteSelectButton(JPanel panel, String fileName) {
            this.panel = panel;
            this.fileName = fileName;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(paintTool.hasAssetToPaint(fileName)){
                boolean removed = paintTool.removeAssetToPaint(fileName);
                if(removed) {
                    panel.setBackground(null);
                    panel.invalidate();
                    panel.revalidate();
                }
            }else {
                paintTool.addAssetToPaint(fileName);
                panel.setBackground(new Color(150,150,200));
                panel.invalidate();
                panel.revalidate();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
