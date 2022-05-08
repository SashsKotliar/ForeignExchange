import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MyWebMain {
    private Document myWeb;


    public MyWebMain(){
        try {
            myWeb = Jsoup.connect("https://tradingeconomics.com/currencies").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Document getMyWeb(){
        return this.myWeb;
    }

}
