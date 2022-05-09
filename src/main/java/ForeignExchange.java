
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
    private final String changeTo;
    private boolean reverse;
    private float value;
    public static final Set<ForeignExchange> All_EXCHANGES = new HashSet<>();

    public ForeignExchange(Element element) {
        this.value = Float.parseFloat(Objects.requireNonNull(element.getElementById("p")).text());
        String total = element.getElementsByTag("a").get(0).text();
        int index = total.indexOf(COMPARISON);

        this.changeTo = total.replace(COMPARISON, "");
        this.reverse = (index != 0);
    }


    public float rateConversion() {
        refresh();
        float value = this.value;
        if (this.reverse)
            value = 1 / value;
        return value;
    }


    private static ForeignExchange getByCurrency(String currency) {
        for (ForeignExchange foreignExchange : All_EXCHANGES)
            if (foreignExchange.changeTo.equals(currency))
                return foreignExchange;
        return null;
    }

    public String getChangeTo() {
        return changeTo;
    }

    public static float exchange(String c_from, String c_to, float amount) {
        ForeignExchange from = getByCurrency(c_from);
        ForeignExchange to = getByCurrency(c_to);
        if (from == null)
            throw new IllegalArgumentException("currency " + c_from + " doesn't exist");
        if (to == null)
            throw new IllegalArgumentException("currency " + c_to + " doesn't exist");
        float as_usd = from.exchange_to_usd(amount);
        return to.exchange_from_usd(as_usd);
    }

    public static void load() throws IOException {
        Document myWeb = getDocument();

        ArrayList<Element> elements = myWeb.getElementsByClass("datatable-row");
        for (Element element : elements) {
            ForeignExchange temp = new ForeignExchange(element);
            All_EXCHANGES.add(temp);
        }
    }

    public void refresh() {
        try {
            Document myWeb = getDocument();
            String symbol;
            if (reverse) {
                symbol = changeTo + COMPARISON;
            } else {
                symbol = COMPARISON + changeTo;
            }
            symbol += ":CUR";

            ArrayList<Element> elements = myWeb.getElementsByAttributeValue("data-symbol", symbol);
            ForeignExchange foreignExchange = new ForeignExchange(elements.get(0));

            this.value = foreignExchange.value;
            this.reverse = foreignExchange.reverse;
        } catch (IOException ignore) {
        }
    }

    private static Document getDocument() throws IOException {
        return Jsoup.connect("https://tradingeconomics.com/currencies").get();
    }

    public float exchange_from_usd(float source_amount) {
        return source_amount * rateConversion();
    }

    public float exchange_to_usd(float source_amount) {
        return source_amount / rateConversion();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForeignExchange that)) return false;
        return reverse == that.reverse && changeTo.equals(that.changeTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(changeTo, reverse);
    }

    @Override
    public String toString() {
        return "changeTo='" + changeTo + '\'' + "\n" + this.rateConversion() + "\n";
    }
}
