import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

public class BasicWindow extends JFrame {

    public BasicWindow(int w, int h){
        this.setSize(w,h);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

}

