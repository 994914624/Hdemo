package cn.test.hdemo.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import cn.test.hdemo.api.API;

/**
 * Created by yzk on 2018/9/17
 */

public class HtmlParseUtil {

    private static String TAG = "yyy";



    static String html = "<html><head><title>First parse</title></head>"
            + "<body><p>Parsed HTML into a doc.</p></body></html>";

    private static String parseHtml() {

        try {

            Document doc = Jsoup.parseBodyFragment(html);
            Elements body = doc.getElementsByTag("body");
            //获取所有目标标签
            String str = body.text();
            //
            Log.d(TAG, "222:" + str);
            StringBuilder sb = new StringBuilder();

            return str+"";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getInput(String start,String count) {

        InputStreamReader reader = null;
        BufferedReader in = null;
        try {

            URL url = new URL(String.format(API.TOUTIAO_LIST,start,count));
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(10000);
            reader = new InputStreamReader(connection.getInputStream());
            in = new BufferedReader(reader);
            String line; // 每行内容
            StringBuilder content = new StringBuilder();
            while ((line = in.readLine()) != null) {
                    content.append(line);
            }
            html = content.toString();
            Log.d(TAG, "####:" + html);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    Log.d(TAG, "关闭流出现异常!!!");
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    Log.d(TAG, "关闭流出现异常!!!");
                }
            }
        }

        //解析 html
        return parseHtml();
    }
}
