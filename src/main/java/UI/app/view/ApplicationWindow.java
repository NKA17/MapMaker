package UI.app.view;

import application.config.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class ApplicationWindow extends JFrame {

    private String title;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public ApplicationWindow() {
        this.title = "RPG MapMaker";
        try{
            setIconImage(ImageIO.read(new File("./src/main/resources/icons/mapIcon.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
        setBackground(Configuration.WINDOW_BG_COLOR);
        getContentPane().setBackground(Configuration.WINDOW_BG_COLOR);
        getContentPane().setLayout(new GridBagLayout());
        setLayout(gridBagLayout);
        defaultInitUI();
        initUI();

    }

    private void defaultInitUI() {

        setTitle(this.title);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public abstract void initUI();

    public void openPage(ApplicationPage appPage){
        Container contentPane = getContentPane();
        contentPane.removeAll();
        appPage.loadPage();
        appPage.setObserver(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = 1;
        contentPane.add(appPage,gbc);
        reloadWindow();

    }

    public void reloadWindow(){
        invalidate();
        revalidate();
        repaint();
    }

    public void makeVisible(){
        EventQueue.invokeLater(() -> {
            setVisible(true);
        });
    }
}
