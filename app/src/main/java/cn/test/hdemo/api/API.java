package cn.test.hdemo.api;

/**
 * Created by yzk on 2018/12/11
 */

public class API {

    public static final String AV_LIST = "https://api.douban.com/v2/movie/top250";

    //http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html  头条
    public static final String TOUTIAO_LIST = "http://c.m.163.com/nc/article/headline/T1348647853363/%s-%s.html";



    // 新闻 list
    public static final String H_ARTICLE = "http://40.125.169.204:9200/api/rec/article/feed";
    // detail
    public static final String H_ARTICLE_DETAIL = "http://40.125.169.204:9200/api/rec/detail";
    //relevent
    public static final String H_ARTICLE_RELEVENT = "http://40.125.169.204:9200/api/rec/article/relevant";


    //video
    public static final String H_VIDEO = "http://40.125.169.204:9200/api/rec/video/feed";
}
