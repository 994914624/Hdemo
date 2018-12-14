package cn.test.hdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yzk on 2018/12/13
 */

public class SARecommendEntity {


    /**
     * code : 200
     * msg :
     * data : [{"item_id":"6243991","item_type":"article","retrieve_id":"category_consume_topk","title":"加拿大民众致电中国驻加使馆：为加政府错误行为向中国致歉","source":"环球网","img":"http://image.huadongmedia.com/plat/cover/image/20181213/4385780dd4ed4a2c9d868ca804c3f62e.jpg"},{"item_id":"6235358","item_type":"article","retrieve_id":"category_consume_topk","title":"加拿大公民迈克尔因涉嫌危害中国国家安全被依法审查","source":"环球网","img":"http://image.huadongmedia.com/plat/cover/image/20181213/8ed2225d927d49b2b299d58a230d5889.jpg"},{"item_id":"6231706","item_type":"article","retrieve_id":"category_consume_topk","title":"上海杀妻藏尸案二审开庭被害人家属：相信会维持原判","source":"中国新闻网","img":"https://img.cp.cashtoutiao.com/plat/cover/image/20181213/8fb84292ae1220766d0606885e57efe4_0.jpg"}]
     * exp_id : base
     * log_id : 111
     * strategy_id : baseline
     */

    private int code;
    private String msg;
    private String exp_id;
    private String log_id;
    private String strategy_id;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getExp_id() {
        return exp_id;
    }

    public void setExp_id(String exp_id) {
        this.exp_id = exp_id;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getStrategy_id() {
        return strategy_id;
    }

    public void setStrategy_id(String strategy_id) {
        this.strategy_id = strategy_id;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean  implements MultiItemEntity {


        public void setType(int type) {
            Type = type;
        }

        /**
         * item_id : 6243991
         * item_type : article
         * retrieve_id : category_consume_topk
         * title : 加拿大民众致电中国驻加使馆：为加政府错误行为向中国致歉
         * source : 环球网
         * img : http://image.huadongmedia.com/plat/cover/image/20181213/4385780dd4ed4a2c9d868ca804c3f62e.jpg
         */


        public int Type =0; // 默认0
        public static final int CLICK_ITEM_VIEW = 0;
        public static final int  CLICK_ITEM_VIEW_2 =2;

        private String item_id;
        private String item_type;
        private String retrieve_id;
        private String title;
        private String source;
        private String img;

        public String getItem_id() {
            return item_id;
        }

        public void setItem_id(String item_id) {
            this.item_id = item_id;
        }

        public String getItem_type() {
            return item_type;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public String getRetrieve_id() {
            return retrieve_id;
        }

        public void setRetrieve_id(String retrieve_id) {
            this.retrieve_id = retrieve_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        @Override
        public int getItemType() {
            return Type;
        }
    }
}
