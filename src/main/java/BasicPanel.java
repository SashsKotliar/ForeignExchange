import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.function.Supplier;

public class BasicPanel extends JPanel {
    private ImageIcon imageIcon;

    public BasicPanel(int x, int y, int w, int h, Color color) {
        this.setBounds(x, y, w, h);
        this.setBackground(color);
        this.imageIcon=new ImageIcon("abc.jpeg");
        init();
    }
protected void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        this.imageIcon.paintIcon(this,graphics,0,0);
}
    public void init() {
        this.setLayout(null);
        this.setDoubleBuffered(true);
    }

    public void mainBottomsOption(int x, int y, int w, int h, String titleOn, Supplier<BasicPanel> supplier) {
        Button button = new Button(titleOn);
        button.setFont(Constants.FONT);
        button.setBounds(x, y, w, h);
        button.setForeground(Color.cyan.darker());
        button.setBackground(Color.orange);
        button.addActionListener(e -> {
            BasicPanel main = supplier.get();
            main.setVisible(true);
            this.setVisible(false);
        });
        this.add(button);
    }

}
