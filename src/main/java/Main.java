import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;


public class Main extends BasicWindow {
    private BasicPanel mainScreen;


    public Main() throws IOException {
        super(Constants.MAIN_WINDOW_W, Constants.MAIN_WINDOW_H);
        ForeignExchange.load();
        mainScreen=new PanelMainScreen();
        this.add(mainScreen);
        myBottom();
        this.setVisible(true);
    }
    public void myBottom() {
        int y = 70;
        int h = 17;
        int x = 50;
        int w = 300;
        int counter = 0;
        for (ForeignExchange foreignExchange : ForeignExchange.foreignExchanges) {
            mainBottoms(x, y, w, h, foreignExchange.getChangeFrom() + "-" + foreignExchange.getChangeTo()
                    , () -> new CoinJPanel(foreignExchange));
            counter++;
            x = x + w;
            if (counter == 3) {
                counter = 0;
                x = 50;
                y = y + h;
            }

        }
    }
    public void mainBottoms(int x, int y, int w, int h, String titleOn, Supplier<CoinJPanel> supplier) {
        Button button = new Button(titleOn);
        button.setFont(Constants.FONT);
        button.setBounds(x, y, w, h);
        button.setForeground(Color.cyan.darker());
        button.setBackground(Color.orange);
        button.addActionListener(e -> {
            CoinJPanel coinJPanel = supplier.get();
            this.add(coinJPanel);
            coinJPanel.setVisible(true);
            mainScreen.setVisible(false);
        });
        mainScreen.add(button);
    }
    public static void main(String[] args) throws IOException {
        Main main = new Main();
    }
}