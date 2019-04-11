package com.sohu.auto.treasure.entry;

import java.util.List;

/**
 * Created by zhipengyang on 2019/4/11.
 */

public class TreasureListEntity {

    /**
     * data : [{"id":"5caeafd7c94306907ecd8785","created_at":"2019-04-11T11:09:11+08:00","updated_at":"2019-04-11T11:10:02+08:00","status":1,"location":[130.2828685,31.8198198],"hunter_id":"5caaf3b9c943067fa42ed9f9","hunter":"liuzy","content":"三亚五日游","question":"17-18赛季nba冠军球队","answer":"勇士","parent_id":"5caeafd7c94306907ecd8784","creator_id":"5caaf3b9c943067fa42ed9f9","image":"https://i1.hoopchina.com.cn/blogfile/201904/10/BbsImg_151410522880757_1554874795_s_606360_o_w_4608_h_3228_66069.jpg"}]
     * code : 0
     * err_msg :
     */

    private int code;
    private String err_msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5caeafd7c94306907ecd8785
         * created_at : 2019-04-11T11:09:11+08:00
         * updated_at : 2019-04-11T11:10:02+08:00
         * status : 1
         * location : [130.2828685,31.8198198]
         * hunter_id : 5caaf3b9c943067fa42ed9f9
         * hunter : liuzy
         * content : 三亚五日游
         * question : 17-18赛季nba冠军球队
         * answer : 勇士
         * parent_id : 5caeafd7c94306907ecd8784
         * creator_id : 5caaf3b9c943067fa42ed9f9
         * image : https://i1.hoopchina.com.cn/blogfile/201904/10/BbsImg_151410522880757_1554874795_s_606360_o_w_4608_h_3228_66069.jpg
         */

        private String id;
        private String created_at;
        private String updated_at;
        private int status;
        private String hunter_id;
        private String hunter;
        private String content;
        private String question;
        private String answer;
        private String parent_id;
        private String creator_id;
        private String image;
        private List<Double> location;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getHunter_id() {
            return hunter_id;
        }

        public void setHunter_id(String hunter_id) {
            this.hunter_id = hunter_id;
        }

        public String getHunter() {
            return hunter;
        }

        public void setHunter(String hunter) {
            this.hunter = hunter;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(String creator_id) {
            this.creator_id = creator_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }
    }
}
