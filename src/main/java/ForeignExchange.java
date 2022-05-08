
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class ForeignExchange {
    public static final String COMPARISON = "USD";
    private String changeFrom;
    private String changeTo;
    private boolean reverse;
    private Element reference;
    public static Set<ForeignExchange> foreignExchanges = new HashSet<>();

    public ForeignExchange(Element element) {
        this.reference = element.getElementById("p");
        String total = element.getElementsByTag("a").get(0).text();
        int index = total.indexOf(COMPARISON);

        this.changeFrom = COMPARISON;
        this.changeTo = total.replace(COMPARISON, "");
        this.reverse = (index != 0);
    }


    public float rateConversion() {
        ForeignExchange.foreignExchanges.clear();
        try {
            ForeignExchange.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ForeignExchange a = ForeignExchange.getByCurrency(this.changeTo);
        float value = Float.parseFloat(Objects.requireNonNull(a.reference.getElementById("p")).text());
        if (this.reverse)
            value = 1 / value;
        return value;
    }

    public ForeignExchange(String changeFrom, String changeTo) {
        this.changeFrom = changeFrom;
        this.changeTo = changeTo;
    }

    public static boolean isExchangeable(String currency) {
        for (ForeignExchange foreignExchange : foreignExchanges)
            if (foreignExchange.changeTo.equals(currency) || foreignExchange.changeFrom.equals(currency))
                return true;
        return false;
    }

    private static ForeignExchange getByCurrency(String currency) {
        for (ForeignExchange foreignExchange : foreignExchanges)
            if (foreignExchange.changeTo.equals(currency) || foreignExchange.changeFrom.equals(currency))
                return foreignExchange;
        throw new IllegalArgumentException("no currency named " + currency);
    }

    public String getChangeFrom() {
        return changeFrom;
    }

    public String getChangeTo() {
        return changeTo;
    }

    public static float exchange(String c_from, String c_to, float amount) {
        ForeignExchange from = getByCurrency(c_from);
        ForeignExchange to = getByCurrency(c_to);
        float as_usd = from.exchange_to_usd(amount);
        return to.exchange_from_usd(as_usd);
    }


    public static void load() throws IOException {
        Document myWeb = Jsoup.connect("https://tradingeconomics.com/currencies").get();

        ArrayList<Element> elements = myWeb.getElementsByClass("datatable-row");
        for (int i = 0; i < elements.size(); i++) {
            foreignExchanges.add(new ForeignExchange(elements.get(i)));
        }
    }

    public float exchange_from_usd(float source_amount) {
        return source_amount * rateConversion();
    }

    public float exchange_to_usd(float source_amount) {
        return source_amount / rateConversion();
    }

    public boolean equals(ForeignExchange other) {
        return this.changeFrom.equals(other.changeFrom) && this.changeTo.equals(other.changeTo);
    }

    @Override
    public String toString() {
        return "changeFrom='" + changeFrom + '\'' +
                ", changeTo='" + changeTo + '\'' + "\n" + this.rateConversion() + "\n";
    }
}
