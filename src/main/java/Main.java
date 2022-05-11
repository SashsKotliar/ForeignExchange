import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Supplier;


public class Main extends BasicWindow {
    private BasicPanel mainScreen;

    public Main() throws IOException {
        super(Constants.MAIN_WINDOW_W, Constants.MAIN_WINDOW_H);
        ForeignExchange.load();
        mainScreen = new PanelMainScreen();
        this.add(mainScreen);
        myBottom();
        this.setVisible(true);
    }

    public void myBottom() {
        int y = Constants.BUTTON_Y;
        int x = Constants.BUTTON_X;
        int counter = 0;
        int rows = 0;
        ArrayList<ForeignExchange> allExchanges = new ArrayList<>( ForeignExchange.All_EXCHANGES);
        allExchanges.sort(Comparator.comparing(ForeignExchange::getChangeTo));

        for (ForeignExchange foreignExchange : allExchanges) {
            mainBottoms(x, y, Constants.BUTTON_W, Constants.BUTTON_H, ForeignExchange.COMPARISON
                            + "-" + foreignExchange.getChangeTo(), () -> new CoinJPanel(foreignExchange));
            counter++;
            x += Constants.BUTTON_W;
            if (counter == Constants.BUTTONS_IN_ROW) {
                counter = 0;
                rows++;
                if (rows ==Constants.LAST_ROW){
                    x = Constants.BUTTON_X + Constants.BUTTON_W/2;
                } else {
                    x = Constants.BUTTON_X;
                }
                y += Constants.BUTTON_H;
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