package exKid.parsePage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class Rate {
    @Setter
    private String currentTask = "";

    @Getter
    private final ArrayList<DayRate> dayRates = new ArrayList<DayRate>();

    private final Pattern date = Pattern.compile("\\d{2}\\.\\d{2}");
//    private final Pattern rate = Pattern.compile("\\d\\,\\d{2}");

    private Document getPage() throws IOException {
        String url = "";
        if(currentTask.equals("usd"))
            url = "http://www.finmarket.ru/currency/rates/?id=10123&pv=1#archive";
        else if (currentTask.equals("eur"))
            url = "http://www.finmarket.ru/currency/rates/?id=10123&pv=1&cur=52170#archive";
        else if (currentTask.equals("rub"))
            url = "http://www.finmarket.ru/currency/rates/?id=10123&pv=1&cur=52144#archive";
        return Jsoup.parse(new URL(url), 6000);
    }

    public void getData() throws IOException {
        Document page = getPage();

        Element table = page.select("table[class=karramba]").first();
        Elements rows = table.select("tr");
        List<Element> sublist = rows.subList(rows.size() - 10, rows.size());
        try {
            for(Element e : sublist) {
                String dateText = getDateFromText(e.text());
                Double rateText = Double.valueOf(getRateFromText(e.text()));
                Double changesText = Double.valueOf(getChangesFromText(e.text()));

                dayRates.add(new DayRate(dateText, rateText, changesText));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String getDateFromText(String text) throws Exception {
        Matcher matcher = date.matcher(text);
        if(matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't find date");
    }

    public String getRateFromText(String text) {
        int indexStart = text.indexOf(" ", text.indexOf(" ")+2);
        int indexEnd = text.indexOf(" ", text.indexOf(" ")+3);
        String result = text.substring(indexStart, indexEnd);

        if (result.contains(","))
            return result.replace(',', '.');
        else
            return result;

    }

    public String getChangesFromText(String text) {
        int index = text.indexOf(" ", text.indexOf(" ")+3);
        String result = text.substring(index+1);
        return result.replace(',', '.');
    }


}
