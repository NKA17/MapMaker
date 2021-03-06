package UI.app.view;

import application.config.AppState;
import application.config.Configuration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class ApplicationWindow extends JFrame {

    private String title;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private ApplicationPage currentPage = null;

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
        openPage(appPage,true);
    }
    public void openPage(ApplicationPage appPage, boolean makeActivePage){
        if(currentPage != null)
            currentPage.dispose();

        if(makeActivePage)
            AppState.ACTIVE_PAGE = appPage;
        Container contentPane = getContentPane();
        contentPane.removeAll();
        appPage.setObserver(this);
        appPage.loadPage();
        constrainSize(appPage);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = 1;
        contentPane.add(appPage,gbc);

        reloadWindow();

        currentPage = appPage;
//        System.out.println(String.format(
//                "Page Opened: %s\n\twidth=%d, height=%d",
//                appPage.getClass().getName(),
//                appPage.getWidth(),
//                appPage.getHeight()));
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

    public void dispose(){
        if(currentPage!=null){
            currentPage.dispose();
        }
        super.dispose();
    }

    private void constrainSize(ApplicationPage appPage){
        int w = ((Configuration.WIDTH_CONSTRAINT==-1)? appPage.getWidth():Configuration.WIDTH_CONSTRAINT);
        int h = ((Configuration.HEIGHT_CONSTRAINT==-1)? appPage.getHeight():Configuration.HEIGHT_CONSTRAINT);

        if(w!=0 && h!=0)
        appPage.setPreferredSize(new Dimension(w,h));
    }
}
