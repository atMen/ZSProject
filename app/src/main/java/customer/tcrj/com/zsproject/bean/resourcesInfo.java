package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leict on 2018/9/28.
 */

public class resourcesInfo  implements Serializable{

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : [{"cllj":"/web.files/uploadfile/2018-07-26/20180726052754881.mp4","cllx":"10402","clmc":"轰轰","clms":"您公民","id":"402894b364d5d2120164d5ea89130009","optime":{"date":26,"day":4,"hours":17,"minutes":27,"month":6,"nanos":0,"seconds":54,"time":1532597274000,"timezoneOffset":-480,"year":118},"sort":"6","uploadDate":"2018-07-26 17:27:54","uploadUser":"gch","ywId":"402894b3636bddff01636be319030003","ywmc":"陕西天诚软件有限公司"},{"cllj":"/web.files/uploadfile/2018-07-26/20180726051004685.mp4","cllx":"10402","clmc":"你好","clms":"莫名","id":"402894b364d5d2120164d5da34a00007","optime":{"date":26,"day":4,"hours":17,"minutes":10,"month":6,"nanos":0,"seconds":4,"time":1532596204000,"timezoneOffset":-480,"year":118},"sort":"5","uploadDate":"2018-07-26 17:10:04","uploadUser":"gch","ywId":"402894b3636bddff01636be319030003","ywmc":"陕西天诚软件有限公司"},{"cllj":"/web.files/uploadfile/2018-07-26/20180726050602250.mp4","cllx":"10402","clmc":"OMG您公民的","clms":"明公民","id":"402894b364d5d2120164d5d681a00005","optime":{"date":26,"day":4,"hours":17,"minutes":6,"month":6,"nanos":0,"seconds":2,"time":1532595962000,"timezoneOffset":-480,"year":118},"sort":"4","uploadDate":"2018-07-26 17:06:02","uploadUser":"gch","ywId":"402894b3636bddff01636be319030003","ywmc":"陕西天诚软件有限公司"},{"cllj":"/web.files/uploadfile/2018-07-26/20180726050418864.mp4","cllx":"10402","clmc":"看见了","clms":"看见了","id":"402894b364d5d2120164d5d4ee460003","optime":{"date":26,"day":4,"hours":17,"minutes":4,"month":6,"nanos":0,"seconds":18,"time":1532595858000,"timezoneOffset":-480,"year":118},"sort":"3","uploadDate":"2018-07-26 17:04:19","uploadUser":"gch","ywId":"402894b3636bddff01636be319030003","ywmc":"陕西天诚软件有限公司"},{"cllj":"/web.files/uploadfile/2018-07-26/20180726050301249.jpg","cllx":"10401","clmc":"看见了","clms":"去可口可乐了","id":"402894b364d5d2120164d5d3bebd0002","optime":{"date":26,"day":4,"hours":17,"minutes":3,"month":6,"nanos":0,"seconds":0,"time":1532595780000,"timezoneOffset":-480,"year":118},"sort":"2","uploadDate":"2018-07-26 17:03:01","uploadUser":"gch","ywId":"402894b3636bddff01636be319030003","ywmc":"陕西天诚软件有限公司"}]
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
         * cllj : /web.files/uploadfile/2018-07-26/20180726052754881.mp4
         * cllx : 10402
         * clmc : 轰轰
         * clms : 您公民
         * id : 402894b364d5d2120164d5ea89130009
         * optime : {"date":26,"day":4,"hours":17,"minutes":27,"month":6,"nanos":0,"seconds":54,"time":1532597274000,"timezoneOffset":-480,"year":118}
         * sort : 6
         * uploadDate : 2018-07-26 17:27:54
         * uploadUser : gch
         * ywId : 402894b3636bddff01636be319030003
         * ywmc : 陕西天诚软件有限公司
         */

        private String cllj;
        private String cllx;
        private String clmc;
        private String clms;
        private String id;
        private OptimeBean optime;
        private String sort;
        private String uploadDate;
        private String uploadUser;
        private String ywId;
        private String ywmc;

        public String getCllj() {
            return cllj;
        }

        public void setCllj(String cllj) {
            this.cllj = cllj;
        }

        public String getCllx() {
            return cllx;
        }

        public void setCllx(String cllx) {
            this.cllx = cllx;
        }

        public String getClmc() {
            return clmc;
        }

        public void setClmc(String clmc) {
            this.clmc = clmc;
        }

        public String getClms() {
            return clms;
        }

        public void setClms(String clms) {
            this.clms = clms;
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

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getUploadDate() {
            return uploadDate;
        }

        public void setUploadDate(String uploadDate) {
            this.uploadDate = uploadDate;
        }

        public String getUploadUser() {
            return uploadUser;
        }

        public void setUploadUser(String uploadUser) {
            this.uploadUser = uploadUser;
        }

        public String getYwId() {
            return ywId;
        }

        public void setYwId(String ywId) {
            this.ywId = ywId;
        }

        public String getYwmc() {
            return ywmc;
        }

        public void setYwmc(String ywmc) {
            this.ywmc = ywmc;
        }

        public static class OptimeBean  implements Serializable{
            /**
             * date : 26
             * day : 4
             * hours : 17
             * minutes : 27
             * month : 6
             * nanos : 0
             * seconds : 54
             * time : 1532597274000
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
