import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;


public class PanelMainScreen extends BasicPanel {

    public PanelMainScreen() {
        super(0, 0, Constants.MAIN_WINDOW_W, Constants.MAIN_WINDOW_H, null);
        myTitle();
    }
    public void myTitle() {
        JLabel title = new JLabel("Exchange options: ", SwingConstants.CENTER);
        title.setFont(new Font("ariel", Font.BOLD, 35));
        title.setForeground(Color.orange);
        title.setBackground(Color.black);
        title.setOpaque(true);
        title.setBounds(0, Constants.TITLE_Y, Constants.MAIN_WINDOW_W, Constants.TITLE_H);
        this.add(title);
    }
}