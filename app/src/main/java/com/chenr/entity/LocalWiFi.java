package com.chenr.entity;

import java.util.List;

/**
 * Created by ChenR on 2016/11/21.
 */

public class LocalWiFi {

    /**
     * retCd : 0
     * displayCount : 50
     * totalCount : 239
     * data : [{"ssid":"LionMobi","lati":30.5417036177,"securityLevel":"2","dist":53.875063230629685,"longi":104.070467218},{"ssid":"codoon_wifi","lati":30.5410999,"securityLevel":"2","dist":270.01077998338855,"longi":104.0730464},{"ssid":"EMC Guest Cafe","lati":30.5417177385,"securityLevel":"0","dist":52.35491410099681,"longi":104.070463338},{"ssid":"codoon_guest","lati":30.5416237,"securityLevel":"2","dist":234.72451990429764,"longi":104.0728875},{"ssid":"Guest","lati":30.5416237,"securityLevel":"2","dist":234.72451990429764,"longi":104.0728875},{"ssid":"Mobile","lati":30.5416237,"securityLevel":"2","dist":234.72451990429764,"longi":104.0728875},{"ssid":"ChinaNet","lati":30.5417211012,"securityLevel":"0","dist":52.120657580057326,"longi":104.070452179},{"ssid":"GYYZ","lati":30.5417393,"securityLevel":"2","dist":53.7599584138042,"longi":104.070744},{"ssid":"Eptonic","lati":30.5417175141,"securityLevel":"2","dist":53.70220509255958,"longi":104.070388299},{"ssid":"will iMac","lati":30.5416752,"securityLevel":"2","dist":56.8056116314481,"longi":104.07049644},{"ssid":"Tenda_1FBC48","lati":30.5454799,"securityLevel":"2","dist":376.1863444153524,"longi":104.0696568},{"ssid":"RetailStaging","lati":30.5408428,"securityLevel":"2","dist":178.5465383526647,"longi":104.0695076},{"ssid":"ChinaUnicom","lati":30.5416338167,"securityLevel":"2","dist":61.43318394839737,"longi":104.07049115},{"ssid":"??????","lati":30.5408428,"securityLevel":"0","dist":178.5465383526647,"longi":104.0695076},{"ssid":"scb2","lati":30.5416061,"securityLevel":"2","dist":64.57044957266503,"longi":104.0704815},{"ssid":"cdht-wifi","lati":30.5408428,"securityLevel":"0","dist":178.5465383526647,"longi":104.0695076},{"ssid":"Tencent-GuestWiFi","lati":30.5397033,"securityLevel":"2","dist":335.47650817392804,"longi":104.068542},{"ssid":"2206","lati":30.5420732,"securityLevel":"2","dist":84.34138976109254,"longi":104.0696574},{"ssid":"3001","lati":30.5415794,"securityLevel":"2","dist":67.60138425401054,"longi":104.070583},{"ssid":"Tenda_02BA18","lati":30.5407651,"securityLevel":"2","dist":176.46125653453086,"longi":104.0713463},{"ssid":"msb360","lati":30.5418033,"securityLevel":"2","dist":343.46649037965983,"longi":104.0740825},{"ssid":"JYOffice1","lati":30.5408428,"securityLevel":"2","dist":178.5465383526647,"longi":104.0695076},{"ssid":"xiaohuishi","lati":30.5408428,"securityLevel":"2","dist":178.5465383526647,"longi":104.0695076},{"ssid":"FOLK INN","lati":30.5447678,"securityLevel":"2","dist":288.78478848661223,"longi":104.0708072},{"ssid":"HFT_Phone","lati":30.5431087,"securityLevel":"2","dist":360.6675763319418,"longi":104.0669218},{"ssid":"Camera360","lati":30.5423157,"securityLevel":"2","dist":346.47993932299664,"longi":104.0669168},{"ssid":"Tenda_364708","lati":30.5417393,"securityLevel":"2","dist":53.7599584138042,"longi":104.070744},{"ssid":"woqu01","lati":30.5397631,"securityLevel":"2","dist":333.53166152258797,"longi":104.0684791},{"ssid":"U CAFE","lati":30.5417997,"securityLevel":"2","dist":46.47428014876632,"longi":104.0703403},{"ssid":"Tenda_4F32B0","lati":30.5388677,"securityLevel":"2","dist":436.99963708288124,"longi":104.0680899},{"ssid":"FAST-04FC","lati":30.54076519,"securityLevel":"2","dist":176.452283830591,"longi":104.0713463},{"ssid":"FOKL INN","lati":30.5447678,"securityLevel":"0","dist":288.78478848661223,"longi":104.0708072},{"ssid":"Tenda_2524B0","lati":30.5417393,"securityLevel":"2","dist":53.7599584138042,"longi":104.070744},{"ssid":"?????","lati":30.5436751,"securityLevel":"0","dist":168.20619657859297,"longi":104.0708171},{"ssid":"WiFi共享大师-64","lati":30.540054,"securityLevel":"2","dist":301.32067298751326,"longi":104.0685893},{"ssid":"jinyi31","lati":30.5424082,"securityLevel":"2","dist":323.4371725441338,"longi":104.0738911},{"ssid":"HiWiFi_Jack","lati":30.5418484,"securityLevel":"2","dist":37.50151684068061,"longi":104.0705486},{"ssid":"360","lati":30.5454959,"securityLevel":"2","dist":380.0594266696215,"longi":104.06956092}]
     */

    private String retCd;
    private String displayCount;
    private String totalCount;
    private List<DataBean> data;

    public String getRetCd() {
        return retCd;
    }

    public void setRetCd(String retCd) {
        this.retCd = retCd;
    }

    public String getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(String displayCount) {
        this.displayCount = displayCount;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ssid : LionMobi
         * lati : 30.5417036177
         * securityLevel : 2
         * dist : 53.875063230629685
         * longi : 104.070467218
         */

        private String ssid;
        private double lati;
        private String securityLevel;
        private double dist;
        private double longi;

        public String getSsid() {
            return ssid;
        }

        public void setSsid(String ssid) {
            this.ssid = ssid;
        }

        public double getLati() {
            return lati;
        }

        public void setLati(double lati) {
            this.lati = lati;
        }

        public String getSecurityLevel() {
            return securityLevel;
        }

        public void setSecurityLevel(String securityLevel) {
            this.securityLevel = securityLevel;
        }

        public double getDist() {
            return dist;
        }

        public void setDist(double dist) {
            this.dist = dist;
        }

        public double getLongi() {
            return longi;
        }

        public void setLongi(double longi) {
            this.longi = longi;
        }
    }
}
