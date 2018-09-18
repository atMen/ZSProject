package customer.tcrj.com.zsproject.bean;

/**
 * Created by leict on 2018/9/18.
 */

public class dicBean {

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : {"code":"10602","deleted":"0","id":"10602","isParent":true,"name":"一物一码","newStyle":"1","note":"","optime":{"date":22,"day":2,"hours":13,"minutes":41,"month":4,"nanos":0,"seconds":56,"time":1526967716000,"timezoneOffset":-480,"year":118},"parentId":"106","path":"#1#106#10602#","sort":2}
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
         * code : 10602
         * deleted : 0
         * id : 10602
         * isParent : true
         * name : 一物一码
         * newStyle : 1
         * note :
         * optime : {"date":22,"day":2,"hours":13,"minutes":41,"month":4,"nanos":0,"seconds":56,"time":1526967716000,"timezoneOffset":-480,"year":118}
         * parentId : 106
         * path : #1#106#10602#
         * sort : 2
         */

        private String code;
        private String deleted;
        private String id;
        private boolean isParent;
        private String name;
        private String newStyle;
        private String note;
        private OptimeBean optime;
        private String parentId;
        private String path;
        private int sort;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDeleted() {
            return deleted;
        }

        public void setDeleted(String deleted) {
            this.deleted = deleted;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsParent() {
            return isParent;
        }

        public void setIsParent(boolean isParent) {
            this.isParent = isParent;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNewStyle() {
            return newStyle;
        }

        public void setNewStyle(String newStyle) {
            this.newStyle = newStyle;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public OptimeBean getOptime() {
            return optime;
        }

        public void setOptime(OptimeBean optime) {
            this.optime = optime;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public static class OptimeBean {
            /**
             * date : 22
             * day : 2
             * hours : 13
             * minutes : 41
             * month : 4
             * nanos : 0
             * seconds : 56
             * time : 1526967716000
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
