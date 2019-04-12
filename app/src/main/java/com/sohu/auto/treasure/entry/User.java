package com.sohu.auto.treasure.entry;

/**
 * Created by zhipengyang on 2019/4/11.
 */

public class User {

    /**
     * data : {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYWRjMGEwYzk0MzA2NmNlZTNlMGI0ZCJ9.MG_nHmm7wuD9JqibO2HaMFT1pBbGQEntAFkIfjJxLRM","user_info":{"name":"sohu1","avatar":"https://cdn.v2ex.com/avatar/cd08/1ee8/15443_normal.png"}}
     * code : 0
     * err_msg :
     */

    private DataBean data;
    private int code;
    private String err_msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVjYWRjMGEwYzk0MzA2NmNlZTNlMGI0ZCJ9.MG_nHmm7wuD9JqibO2HaMFT1pBbGQEntAFkIfjJxLRM
         * user_info : {"name":"sohu1","avatar":"https://cdn.v2ex.com/avatar/cd08/1ee8/15443_normal.png"}
         */

        private String token;
        private UserInfoBean user_info;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoBean {
            /**
             * name : sohu1
             * avatar : https://cdn.v2ex.com/avatar/cd08/1ee8/15443_normal.png
             */

            private String name;
            private String avatar;
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
