package cn.test.hdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yzk on 2018/12/11
 */

public class TouTiaoNewsEntity {

    private List<T1348647853363Bean> T1348647853363;

    public List<T1348647853363Bean> getT1348647853363() {
        return T1348647853363;
    }

    public void setT1348647853363(List<T1348647853363Bean> T1348647853363) {
        this.T1348647853363 = T1348647853363;
    }

    public static class T1348647853363Bean  implements MultiItemEntity {
        /**
         * template : normal1
         * skipID : 00AO0001|2298368
         * lmodify : 2018-12-11 17:59:21
         * postid : PHOT264G0000100A
         * source : 视觉中国
         * title : 特朗普政府鼓吹化石燃料遭抗议
         * mtime : 2018-12-11 17:59:21
         * hasImg : 1
         * topic_background : http://img2.cache.netease.com/m/newsapp/reading/cover1/C1348646712614.jpg
         * digest :
         * photosetID : 00AO0001|2298368
         * boardid : photoview_bbs
         * alias : Top News
         * hasAD : 1
         * imgsrc : http://cms-bucket.nosdn.127.net/2018/12/11/8e9753491c4c4ded88c6be7c1d72099e.jpeg
         * ptime : 2018-12-11 14:02:38
         * daynum : 17876
         * hasHead : 1
         * imgType : 1
         * order : 1
         * editor : []
         * votecount : 133
         * hasCover : false
         * docid : 9IG74V5H00963VRO_E2OI4PGSbjzhaoyapingupdateDoc
         * tname : 头条
         * priority : 666
         * ads : [{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2298352","tag":"photoset","title":"2018诺贝尔奖晚宴举行 众多名流出席","imgsrc":"bigimg","url":"00AO0001|2298352"},{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2298351","tag":"photoset","title":"马克龙发表电视讲话 承诺涨薪减税","imgsrc":"bigimg","url":"00AO0001|2298351"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2298348","tag":"photoset","title":"香港校车失事 致4人死亡11人受伤","imgsrc":"bigimg","url":"00AP0001|2298348"},{"subtitle":"","skipType":"photoset","skipID":"00AO0001|2298347","tag":"photoset","title":"加拿大树立南京大屠杀遇难者纪念碑","imgsrc":"bigimg","url":"00AO0001|2298347"},{"subtitle":"","skipType":"photoset","skipID":"00AP0001|2298344","tag":"photoset","title":"吉林松花江畔现大面积雾凇景观","imgsrc":"bigimg","url":"00AP0001|2298344"}]
         * ename : iosnews
         * replyCount : 147
         * imgsum : 3
         * hasIcon : true
         * skipType : photoset
         * cid : C1348646712614
         * url_3w : http://news.163.com/18/1208/08/E2G8CQS5000189FH.html
         * url : http://3g.163.com/news/18/1208/08/E2G8CQS5000189FH.html
         * ltitle : 万里行 万里情——习主席领航之旅纪实
         * subtitle :
         * pixel : 554*311
         */
        private String title;
        private String url;
        private String digest;
        private String imgsrc;
        private  String source;
        private int replyCount;

        public void setSource(String source) {
            this.source = source;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public String getSource() {
            return source;
        }

        public int getReplyCount() {
            return replyCount;
        }
//        private String template;
//        private String skipID;
//        private String lmodify;
//        private String postid;
//        private String source;
//
//        private String mtime;
//        private int hasImg;
//        private String topic_background;
//
//        private String photosetID;
//        private String boardid;
//        private String alias;
//        private int hasAD;
//
//        private String ptime;
//        private String daynum;
//        private int hasHead;
//        private int imgType;
//        private int order;
//        private int votecount;
//        private boolean hasCover;
//        private String docid;
//        private String tname;
//        private int priority;
//        private String ename;
//        private int replyCount;
//        private int imgsum;
//        private boolean hasIcon;
//        private String skipType;
//        private String cid;
//        private String url_3w;
//
//        private String ltitle;
//        private String subtitle;
//        private String pixel;
       // private List<?> editor;
        //private List<AdsBean> ads;



        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }



        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }


        public String getImgsrc() {
            return imgsrc;
        }

        public void setImgsrc(String imgsrc) {
            this.imgsrc = imgsrc;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }



        public static final int CLICK_ITEM_VIEW = 0;
        public int Type =0; // 默认0
        @Override
        public int getItemType() {
            return 0;
        }

    }
}
