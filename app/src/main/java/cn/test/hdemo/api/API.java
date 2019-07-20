package cn.test.hdemo.api;

/**
 * Created by yzk on 2018/12/11
 */

public class API {

    public static final String AV_LIST = "https://api.douban.com/v2/movie/top250";

    //http://c.m.163.com/nc/article/headline/T1348647853363/0-40.html  头条
    public static final String TOUTIAO_LIST = "http://c.m.163.com/nc/article/headline/T1348647853363/%s-%s.html";



    // 新闻 list
    public static final String H_ARTICLE = "http://42.159.80.211:8202/api/rec/article/feed";
    // detail
    public static final String H_ARTICLE_DETAIL = "http://42.159.80.211:8202/api/rec/detail";
    //relevent
    public static final String H_ARTICLE_RELEVENT = "http://42.159.80.211:8202/api/rec/article/relevant";


    //video
    public static final String H_VIDEO = "http://42.159.80.211:8202/api/rec/video/feed";


    //------------------------- 2019-7-18 ----------new----------------------------------------------


    public static final String N_FEED = "http://10.42.22.215:8201/api/rec/feed";
    public static final String N_RESET_USER = "http://10.42.22.215:8201/api/rec/reset_user";
    public static final String N_INFO = "http://10.42.22.215:8201/api/rec/info";
}
