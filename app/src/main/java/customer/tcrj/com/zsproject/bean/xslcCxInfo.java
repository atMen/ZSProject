package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leict on 2018/8/9.
 */

public class xslcCxInfo implements Serializable{

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : [{"agentId":"402894b3636bddff01636bf3bb9c00f0","bz":"","chsj":"2018-05-29","companyId":"402894b3636bddff01636be319030003","cpmc":"山地红富士","dlsmc":"小门店","id":"402894b363b398390163b427fa4600ab","optime":{"date":6,"day":1,"hours":9,"minutes":54,"month":7,"nanos":0,"seconds":35,"time":1533520475000,"timezoneOffset":-480,"year":118},"productId":"P18053100002","qdmc":"啊实打实的","xsqdId":"402894b3636bddff01636bf5aa7600f6"}]
     */

    private String errorcode;
    private String message;
    private List<DataBean> data;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * agentId : 402894b3636bddff01636bf3bb9c00f0
         * bz :
         * chsj : 2018-05-29
         * companyId : 402894b3636bddff01636be319030003
         * cpmc : 山地红富士
         * dlsmc : 小门店
         * id : 402894b363b398390163b427fa4600ab
         * optime : {"date":6,"day":1,"hours":9,"minutes":54,"month":7,"nanos":0,"seconds":35,"time":1533520475000,"timezoneOffset":-480,"year":118}
         * productId : P18053100002
         * qdmc : 啊实打实的
         * xsqdId : 402894b3636bddff01636bf5aa7600f6
         */

        private String agentId;
        private String bz;
        private String chsj;
        private String companyId;
        private String cpmc;
        private String dlsmc;
        private String id;
        private OptimeBean optime;
        private String productId;
        private String qdmc;
        private String xsqdId;

        public String getAgentId() {
            return agentId;
        }

        public void setAgentId(String agentId) {
            this.agentId = agentId;
        }

        public String getBz() {
            return bz;
        }

        public void setBz(String bz) {
            this.bz = bz;
        }

        public String getChsj() {
            return chsj;
        }

        public void setChsj(String chsj) {
            this.chsj = chsj;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getCpmc() {
            return cpmc;
        }

        public void setCpmc(String cpmc) {
            this.cpmc = cpmc;
        }

        public String getDlsmc() {
            return dlsmc;
        }

        public void setDlsmc(String dlsmc) {
            this.dlsmc = dlsmc;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public OptimeBean getOptime() {
            return optime;
        }

        public void setOptime(OptimeBean optime) {
            this.optime = optime;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getQdmc() {
            return qdmc;
        }

        public void setQdmc(String qdmc) {
            this.qdmc = qdmc;
        }

        public String getXsqdId() {
            return xsqdId;
        }

        public void setXsqdId(String xsqdId) {
            this.xsqdId = xsqdId;
        }

        public static class OptimeBean {
            /**
             * date : 6
             * day : 1
             * hours : 9
             * minutes : 54
             * month : 7
             * nanos : 0
             * seconds : 35
             * time : 1533520475000
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
