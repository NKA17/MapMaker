package UI.app.view;

import javax.swing.*;
import java.awt.*;

public abstract class ApplicationWindow extends JFrame {

    private String title;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public ApplicationWindow() {
        this.title = "RPG MapMaker";
        setLayout(gridBagLayout);
        defaultInitUI();
        initUI();

    }

    private void defaultInitUI() {

        setTitle(this.title);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public abstract void initUI();

    public void openPage(ApplicationPage appPage){
        Container contentPane = getContentPane();
        contentPane.removeAll();
        appPage.loadPage();
        appPage.setObserver(this);
        contentPane.add(appPage);
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
