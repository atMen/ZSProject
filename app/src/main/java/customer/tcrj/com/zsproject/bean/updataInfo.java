package customer.tcrj.com.zsproject.bean;

/**
 * Created by leict on 2018/7/26.
 */

public class updataInfo {

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : {"cllj":"/web.files/uploadfile/2018-07-26/20180726050418864.mp4","cllx":"10402","clmc":"看见了","clms":"看见了","id":"402894b364d5d2120164d5d4ee460003","optime":{"date":26,"day":4,"hours":17,"minutes":4,"month":6,"seconds":18,"time":1532595858863,"timezoneOffset":-480,"year":118},"sort":"3","uploadDate":"2018-07-26 17:04:19","uploadUser":"gch","ywId":"402894b3636bddff01636be319030003"}
     */

    private String errorcode;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cllj : /web.files/uploadfile/2018-07-26/20180726050418864.mp4
         * cllx : 10402
         * clmc : 看见了
         * clms : 看见了
         * id : 402894b364d5d2120164d5d4ee460003
         * optime : {"date":26,"day":4,"hours":17,"minutes":4,"month":6,"seconds":18,"time":1532595858863,"timezoneOffset":-480,"year":118}
         * sort : 3
         * uploadDate : 2018-07-26 17:04:19
         * uploadUser : gch
         * ywId : 402894b3636bddff01636be319030003
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

        public static class OptimeBean {
            /**
             * date : 26
             * day : 4
             * hours : 17
             * minutes : 4
             * month : 6
             * seconds : 18
             * time : 1532595858863
             * timezoneOffset : -480
             * year : 118
             */

            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
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
