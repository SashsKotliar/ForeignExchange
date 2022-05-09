import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CoinJPanel extends BasicPanel {
    ForeignExchange foreignExchange;
    private JLabel conversion;
    private JTextField userFiledText;
    private JLabel result;
    private boolean stop;

    public CoinJPanel(ForeignExchange foreignExchange) {
        super(0, 0, Constants.MAIN_WINDOW_W, Constants.MAIN_WINDOW_H, null);
        this.foreignExchange = foreignExchange;
        this.stop = false;
        initPanel();
    }

    public void runConversion(String rent) {
        this.conversion.setText("Conversion is: " + rent +
                " (from-" + ForeignExchange.COMPARISON + " to- " + this.foreignExchange.getChangeTo() + ")");
    }

    public void run() {
        new Thread(() -> {
            while (!stop) {
                try {
                    runConversion(String.valueOf(foreignExchange.rateConversion()));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void stopPanel() {
        this.stop = true;
    }

    public JLabel initLabel(String text,int x, int y, int w, int h, int size, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setBounds(x, y, w, h);
        label.setFont(new Font("ariel", Font.BOLD, size));
        label.setForeground(color);
        label.setBackground(color.darker());
        label.setOpaque(true);

        return label;
    }

    public void initPanel() {
        this.conversion = initLabel("",0, 0, Constants.MAIN_WINDOW_W, Constants.TITLE_H, Constants.TITLE_H/3, Color.cyan);
        this.add(conversion);
        run();
        this.userFiledText = new JTextField();
        userFiledText.setBounds(SwingConstants.CENTER, Constants.MAIN_WINDOW_H / 4, Constants.MAIN_WINDOW_W, Constants.BUTTON_DISTANCE);
        this.add(userFiledText);
        this.result = initLabel("No requests yet",SwingConstants.CENTER, userFiledText.getY() + userFiledText.getHeight(), Constants.MAIN_WINDOW_W, Constants.TITLE_H, Constants.TITLE_H/4, Color.white);
        this.add(result);
        JLabel message = initLabel("Tap the sum to conversion. Only numbers!",userFiledText.getX(), userFiledText.getY() - Constants.BUTTON_DISTANCE, Constants.MAIN_WINDOW_W, Constants.BUTTON_DISTANCE, Constants.TITLE_H/4, Color.cyan.brighter());
        this.add(message);
        this.backBottom();
        getTextFromUser();
    }

    public void getTextFromUser() {
        userFiledText.addActionListener(e -> {
            String text = userFiledText.getText();
            try {
                float sum = Float.parseFloat(text);
                this.result.setText(text + " USD- " + " cast to " + foreignExchange.getChangeTo() + " is: " + foreignExchange.rateConversion() * sum);
            } catch (NumberFormatException e1) {
                result.setText("Error!Type only numbers!");
            }
            userFiledText.setText(null);

        });
    }

    public void backBottom() {
        Button button = new Button("Back to the main");
        button.setFont(Constants.FONT);
        button.setBounds(0, Constants.MAIN_WINDOW_H-(Constants.BUTTON_DISTANCE*2), Constants.BUTTON_DISTANCE*2, Constants.BUTTON_DISTANCE*2);
        button.setForeground(Color.cyan.darker());
        button.setBackground(Color.black);
        button.addActionListener(e -> {
            try {
                Main main = new Main();
                main.setVisible(true);
                (SwingUtilities.getAncestorOfClass(JFrame.class, this)).setVisible(false);
                stopPanel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        this.add(button);
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);
    }
}
