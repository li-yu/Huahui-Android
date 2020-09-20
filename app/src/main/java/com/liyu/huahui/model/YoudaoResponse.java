package com.liyu.huahui.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// --Commented out by Inspection START (2020/9/20 15:16):
// --Commented out by Inspection START (2020/9/20 15:16):
/////**
//// * Created by liyu on 2017/7/12.
//// */
////
////public class YoudaoResponse {
////
////
////    /**
////     * errorCode : 0
////     * query : good
////     * translation : ["好"]
////     * basic : {"phonetic":"gʊd","uk-phonetic":"gʊd","us-phonetic":"ɡʊd","explains":["好处","好的","好"]}
////     * web : [{"key":"good","value":["良好","善","美好"]}]
////     */
////
////    private String errorCode;
////    private String query;
////    private BasicBean basic;
////    private List<String> translation;
////    private List<WebBean> web;
////
////// --Commented out by Inspection START (2020/9/20 15:16):
//////    public String getErrorCode() {
//////        return errorCode;
//////    }
////// --Commented out by Inspection START (2020/9/20 15:16):
//////// --Commented out by Inspection STOP (2020/9/20 15:16)
//////
//////    public void setErrorCode(String errorCode) {
////// --Commented out by Inspection STOP (2020/9/20 15:16)
////        this.errorCode = errorCode;
////    }
////
////    public String getQuery() {
////// --Commented out by Inspection START (2020/9/20 15:16):
//////        return query;
//////    }
//////
//////    public void setQuery(String query) {
//////        this.query = query;
////// --Commented out by Inspection START (2020/9/20 15:16):
//////// --Commented out by Inspection STOP (2020/9/20 15:16)
//////    }
//////
////// --Commented out by Inspection START (2020/9/20 15:16):
////////// --Commented out by Inspection START (2020/9/20 15:16):
//////// --Commented out by Inspection STOP (2020/9/20 15:16)
////////    public BasicBean getBasic() {
////////        return basic;
////////    }
//////// --Commented out by Inspection STOP (2020/9/20 15:16)
//////
//////    public void setBasic(BasicBean basic) {
//////        this.basic = basic;
//////    }
//////
//////    public List<String> getTranslation() {
//////        return translation;
//////    }
//////
//////    public void setTranslation(List<String> translation) {
//////        this.translation = translation;
//////    }
//////
//////    public List<WebBean> getWeb() {
//////        return web;
//////    }
//////
//////    public void setWeb(List<WebBean> web) {
//////        this.web = web;
//////    }
//////
//////    public static class BasicBean {
//////        /**
//////         * phonetic : gʊd
//////         * uk-phonetic : gʊd
//////// --Commented out by Inspection START (2020/9/20 15:16):
////////         * us-phonetic : ɡʊd
////////         * explains : ["好处","好的","好"]
////////// --Commented out by Inspection START (2020/9/20 15:16):
//////// --Commented out by Inspection START (2020/9/20 15:16):
////////// --Commented out by Inspection STOP (2020/9/20 15:16)
//// --Commented out by Inspection START (2020/9/20 15:16):
//////// --Commented out by Inspection START (2020/9/20 15:16):
//////////////         */
//////////////
//////////////        private String phonetic;
//// --Commented out by Inspection START (2020/9/20 15:16):
////// --Commented out by Inspection STOP (2020/9/20 15:16)
// --Commented out by Inspection STOP (2020/9/20 15:16)
////////// --Commented out by Inspection STOP (2020/9/20 15:16)
// --Commented out by Inspection STOP (2020/9/20 15:16)
////////        @SerializedName("uk-phonetic")
////// --Commented out by Inspection STOP (2020/9/20 15:16)
////// --Commented out by Inspection STOP (2020/9/20 15:16)
////        private String ukphonetic;
////        @SerializedName("us-phonetic")
////        private String usphonetic;
////        private List<String> explains;
////
////        public String getPhonetic() {
// --Commented out by Inspection STOP (2020/9/20 15:16)
//            return phonetic;
//        }
//
//        public void setPhonetic(String phonetic) {
// --Commented out by Inspection STOP (2020/9/20 15:16)
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
// --Commented out by Inspection START (2020/9/20 15:16):
//            return ukphonetic;
//        }
//
//        public void setUkphonetic(String ukphonetic) {
// --Commented out by Inspection STOP (2020/9/20 15:16)
// --Commented out by Inspection START (2020/9/20 15:16):
//            this.ukphonetic = ukphonetic;
//        }
//
//        public String getUsphonetic() {
// --Commented out by Inspection STOP (2020/9/20 15:16)
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

// --Commented out by Inspection START (2020/9/20 15:16):
//        public List<String> getExplains() {
//            return explains;
//        }
// --Commented out by Inspection STOP (2020/9/20 15:16)

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
