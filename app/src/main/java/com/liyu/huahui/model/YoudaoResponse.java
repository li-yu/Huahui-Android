package com.liyu.huahui.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by liyu on 2017/7/12.
 */

public class YoudaoResponse {


    /**
     * errorCode : 0
     * query : good
     * translation : ["好"]
     * basic : {"phonetic":"gʊd","uk-phonetic":"gʊd","us-phonetic":"ɡʊd","explains":["好处","好的","好"]}
     * web : [{"key":"good","value":["良好","善","美好"]}]
     */

    private String errorCode;
    private String query;
    private BasicBean basic;
    private List<String> translation;
    private List<WebBean> web;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean {
        /**
         * phonetic : gʊd
         * uk-phonetic : gʊd
         * us-phonetic : ɡʊd
         * explains : ["好处","好的","好"]
         */

        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        @SerializedName("us-phonetic")
        private String usphonetic;
        private List<String> explains;

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
            return ukphonetic;
        }

        public void setUkphonetic(String ukphonetic) {
            this.ukphonetic = ukphonetic;
        }

        public String getUsphonetic() {
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        /**
         * key : good
         * value : ["良好","善","美好"]
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
