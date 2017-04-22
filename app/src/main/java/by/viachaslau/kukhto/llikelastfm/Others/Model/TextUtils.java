package by.viachaslau.kukhto.llikelastfm.Others.Model;

import org.jsoup.Jsoup;

/**
 * Created by kuhto on 24.03.2017.
 */

public class TextUtils {

    // Обрезает все теги
    public static String html2text(String html) {
        if (android.text.TextUtils.isEmpty(html)) return "";
        return Jsoup.parse(html).text();
    }
}
