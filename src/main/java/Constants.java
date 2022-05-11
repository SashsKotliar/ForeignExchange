import java.awt.*;

public class Constants {
    public static int MAIN_WINDOW_W=1000;
    public static int MAIN_WINDOW_H=650;
    public static final int BUTTONS_IN_ROW =5;
    public static final int BUTTON_X =100;
    public static final int BUTTON_Y = BUTTON_X;
    public static final int BUTTON_W=(MAIN_WINDOW_W-(BUTTON_X *2))/ BUTTONS_IN_ROW;
    public static final int BUTTON_H=(MAIN_WINDOW_H-(BUTTON_X + BUTTON_X /2))/16;
    public static final int TITLE_Y = 0;
    public static final int TITLE_H =100;
    public static final int LAST_ROW = 15;
    public static final Font FONT = new Font("Ariel", Font.BOLD, 15);
    public static final Font FONT1 = new Font("Ariel", Font.BOLD, 17);

}