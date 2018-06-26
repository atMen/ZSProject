package customer.tcrj.com.zsproject.bean;

import java.util.List;

/**
 * Created by leict on 2018/6/21.
 */

public class zdCPLX {

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : [{"code":"10001","deleted":"0","id":"10001","isParent":true,"name":"小米","newStyle":"1","note":"","optime":{"date":11,"day":1,"hours":17,"minutes":26,"month":5,"nanos":0,"seconds":25,"time":1528709185000,"timezoneOffset":-480,"year":118},"parentId":"100","path":"#1#100#10001#","sort":1},{"code":"10002","deleted":"0","id":"10002","isParent":true,"name":"苹果","newStyle":"1","note":"","optime":{"date":11,"day":1,"hours":17,"minutes":26,"month":5,"nanos":0,"seconds":25,"time":1528709185000,"timezoneOffset":-480,"year":118},"parentId":"100","path":"#1#100#10002#","sort":2},{"code":"10003","deleted":"0","id":"10003","isParent":true,"name":"腰鼓","newStyle":"1","note":"","optime":{"date":11,"day":1,"hours":17,"minutes":26,"month":5,"nanos":0,"seconds":25,"time":1528709185000,"timezoneOffset":-480,"year":118},"parentId":"100","path":"#1#100#10003#","sort":3},{"code":"10004","deleted":"0","id":"10004","isParent":true,"name":"剪纸","newStyle":"1","note":"","optime":{"date":11,"day":1,"hours":17,"minutes":26,"month":5,"nanos":0,"seconds":25,"time":1528709185000,"timezoneOffset":-480,"year":118},"parentId":"100","path":"#1#100#10004#","sort":4},{"code":"10005","deleted":"0","id":"10005","isParent":true,"name":"农民画","newStyle":"1","note":"","optime":{"date":11,"day":1,"hours":17,"minutes":26,"month":5,"nanos":0,"seconds":25,"time":1528709185000,"timezoneOffset":-480,"year":118},"parentId":"100","path":"#1#100#10005#","sort":5}]
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

    public static class DataBean {
        /**
         * code : 10001
         * deleted : 0
         * id : 10001
         * isParent : true
         * name : 小米
         * newStyle : 1
         * note :
         * optime : {"date":11,"day":1,"hours":17,"minutes":26,"month":5,"nanos":0,"seconds":25,"time":1528709185000,"timezoneOffset":-480,"year":118}
         * parentId : 100
         * path : #1#100#10001#
         * sort : 1
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
             * date : 11
             * day : 1
             * hours : 17
             * minutes : 26
             * month : 5
             * nanos : 0
             * seconds : 25
             * time : 1528709185000
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
