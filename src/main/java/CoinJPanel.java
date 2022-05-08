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
        this.stop=false;
        initPanel();
    }

    public void runConversion(String rent) {
        this.conversion.setText("Conversion is: " + rent +
                " (from-" + this.foreignExchange.getChangeFrom() + " to- " + this.foreignExchange.getChangeTo()+")");
    }

    public void run() {
        new Thread(() -> {
            while (!stop) {
                runConversion(String.valueOf(foreignExchange.rateConversion()));
                System.out.println(foreignExchange.rateConversion());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void stopPanel(){
        this.stop=true;
    }

    public JLabel initLabel(int x, int y, int w, int h,int size,Color color) {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setBounds(x, y, w, h);
        label.setFont(new Font("ariel",Font.BOLD,size));
        label.setForeground(color);
        return label;
    }

    public void initPanel() {
        this.conversion = initLabel(0, 0, Constants.MAIN_WINDOW_W, 100,40,Color.cyan);
        this.add(conversion);
        run();
        this.userFiledText = new JTextField();
        userFiledText.setBounds(SwingConstants.CENTER, Constants.MAIN_WINDOW_H / 4, Constants.MAIN_WINDOW_W, Constants.BUTTON_H);
        this.add(userFiledText);
        this.result = initLabel(SwingConstants.CENTER,userFiledText.getY()+userFiledText.getHeight(),Constants.MAIN_WINDOW_W,100,20,Color.black);
        result.setText("No requests yet");
        this.add(result);
        JLabel message =initLabel(userFiledText.getX(), userFiledText.getY() - Constants.BUTTON_H, Constants.MAIN_WINDOW_W, 100,20,Color.cyan);
        message.setText("Tap the sum ov conversion. Only numbers!");
        this.add(message);
        this.backBottom();
        getTextFromUser();
    }

    public void getTextFromUser() {
        userFiledText.addActionListener(e -> {
            String text = userFiledText.getText();
            try {
                float sum = Float.parseFloat(text);
                this.result.setText(text +" USD- "+ " cast to " + foreignExchange.getChangeTo() + " is: " + foreignExchange.rateConversion() * sum);
            } catch (NumberFormatException e1) {
                result.setText("Error!Type only numbers!");
            }
            userFiledText.setText(null);

        });
    }
    public void backBottom() {
        Button button = new Button("Back to the main");
        button.setFont(Constants.FONT);
        button.setBounds(0, 400, Constants.MAIN_WINDOW_W/5, Constants.MAIN_WINDOW_W/5);
        button.setForeground(Color.cyan.darker());
        button.setBackground(Color.black);
        button.addActionListener(e -> {
            Main main = null;
            try {
                main = new Main();
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
