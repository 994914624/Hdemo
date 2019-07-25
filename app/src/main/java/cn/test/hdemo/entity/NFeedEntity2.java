package cn.test.hdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yzk on 2019/7/22
 */

public class NFeedEntity2 extends AbstractEntity{


    /**
     * code : 200
     * msg :
     * data : [{"item_id":18887,"item_type":"shoes","retrieve_id":"latest_tag","score":"-1.00000","name":"Harden Vol. 3 场上篮球鞋","price":1399,"origin_price":1399,"brand":"adidas-performance-athletics","labels":["harden","篮球"],"tags":["男子","篮球"],"img":"https://img.adidas.com.cn/resources/2018/12/20/15452753139923062_200X200.jpg"},{"item_id":10155,"item_type":"shoes","retrieve_id":"hot","score":"-1.00000","name":"ADILETTE 拖鞋","price":369,"origin_price":369,"brand":"adidas-originals","labels":["拖鞋"],"tags":["中性","ORIGINALS"],"img":"https://img.adidas.com.cn/resources/2018/5/7/15257012604461350_200X200.jpg"}]
     * exp_id :
     * log_id : sc-008afea2-a298-4337-874a-7a85eb51064f
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

    public static class DataBean extends AbstractDataBean  {
        /**
         * item_id : 18887
         * item_type : shoes
         * retrieve_id : latest_tag
         * score : -1.00000
         * name : Harden Vol. 3 场上篮球鞋
         * price : 1399
         * origin_price : 1399
         * brand : adidas-performance-athletics
         * labels : ["harden","篮球"]
         * tags : ["男子","篮球"]
         * img : https://img.adidas.com.cn/resources/2018/12/20/15452753139923062_200X200.jpg
         */
//        public void setType(int type) {
//            this.type = type;
//        }
//        int type =1; // 默认1 item 的布局
//        public static final int  FEED_ITEM_1 =1;
//        @Override
//        public int getItemType() {
//            return type;
//        }

        private String item_id;
        private String item_type;
        private String retrieve_id;
        private String score;
        private String name;
        private int price;
        private int origin_price;
        private String brand;
        private String img;
        private List<String> labels;
        private List<String> tags;

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

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getOrigin_price() {
            return origin_price;
        }

        public void setOrigin_price(int origin_price) {
            this.origin_price = origin_price;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<String> getLabels() {
            return labels;
        }

        public void setLabels(List<String> labels) {
            this.labels = labels;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}
