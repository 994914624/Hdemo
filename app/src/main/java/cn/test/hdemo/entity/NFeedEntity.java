package cn.test.hdemo.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yzk on 2019/7/18
 */

public class NFeedEntity {


    /**
     * code : 200
     * msg :
     * data : [{"item_id":"25223","item_type":"shoes","retrieve_id":"hot","score":"-1.00000","name":"NIZZA HI RF 经典鞋","price":"729.0","origin_price":"729.0","brand":"adidas-originals","tags":["中性","ORIGINALS"],"img":"https://img.adidas.com.cn/resources/2019/5/10/1557467298590329_200X200.jpg"},{"item_id":"25701","item_type":"shoes","retrieve_id":"hot","score":"-1.00000","name":"PREDATOR 19.1 AG 足球鞋","price":"1599.0","origin_price":"1599.0","brand":"adidas-performance-athletics","tags":["男子","足球"],"img":"https://img.adidas.com.cn/resources/2019/6/11/15602349012008310_200X200.jpg"}]
     * exp_id :
     * log_id : sc-ded6e8b2-c2c4-41d4-9520-ef46e1619c54
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

    public static class DataBean  extends AbstractDataBean implements MultiItemEntity{
        /**
         * item_id : 25223
         * item_type : shoes
         * retrieve_id : hot
         * score : -1.00000
         * name : NIZZA HI RF 经典鞋
         * price : 729.0
         * origin_price : 729.0
         * brand : adidas-originals
         * tags : ["中性","ORIGINALS"]
         * img : https://img.adidas.com.cn/resources/2019/5/10/1557467298590329_200X200.jpg
         */

        public void setType(int type) {
            this.type = type;
        }
        public int type =3; // 默认3 item 的布局
        public static final int CLICK_ITEM_VIEW = 0;
        public static final int  CLICK_ITEM_VIEW_2 =2;
        public static final int  FEED_ITEM_1 =3;
        @Override
        public int getItemType() {
            return type;
        }

        private String item_id;
        private String item_type;
        private String retrieve_id;
        private String score;
        private String name;
        private String price;
        private String origin_price;
        private String brand;
        private String img;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getOrigin_price() {
            return origin_price;
        }

        public void setOrigin_price(String origin_price) {
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

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }


    }
}
