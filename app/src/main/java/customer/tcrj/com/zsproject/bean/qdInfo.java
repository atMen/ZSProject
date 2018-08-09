package customer.tcrj.com.zsproject.bean;

import java.util.List;

/**
 * Created by leict on 2018/8/9.
 */

public class qdInfo {

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : {"content":[{"bz":"","companyId":"402894b3636bddff01636be319030003","companyName":"","id":"402894b3636bddff01636bf5aa7600f6","isActive":"1","optime":{"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":32,"time":1526524652000,"timezoneOffset":-480,"year":118},"qdlx":"10501","qdmc":"啊实打实的","qdwddz":"气温气温"},{"bz":"","companyId":"402894b3636bddff01636be319030003","companyName":"","id":"402894b3636bddff01636bf5948100f4","isActive":"1","optime":{"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":26,"time":1526524646000,"timezoneOffset":-480,"year":118},"qdlx":"10501","qdmc":"啊实打实的","qdwddz":"啊实打实的"}],"first":true,"last":true,"number":0,"numberOfElements":2,"size":10,"sort":{},"totalElements":2,"totalPages":1}
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
         * content : [{"bz":"","companyId":"402894b3636bddff01636be319030003","companyName":"","id":"402894b3636bddff01636bf5aa7600f6","isActive":"1","optime":{"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":32,"time":1526524652000,"timezoneOffset":-480,"year":118},"qdlx":"10501","qdmc":"啊实打实的","qdwddz":"气温气温"},{"bz":"","companyId":"402894b3636bddff01636be319030003","companyName":"","id":"402894b3636bddff01636bf5948100f4","isActive":"1","optime":{"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":26,"time":1526524646000,"timezoneOffset":-480,"year":118},"qdlx":"10501","qdmc":"啊实打实的","qdwddz":"啊实打实的"}]
         * first : true
         * last : true
         * number : 0
         * numberOfElements : 2
         * size : 10
         * sort : {}
         * totalElements : 2
         * totalPages : 1
         */

        private boolean first;
        private boolean last;
        private int number;
        private int numberOfElements;
        private int size;
        private SortBean sort;
        private int totalElements;
        private int totalPages;
        private List<ContentBean> content;

        public boolean isFirst() {
            return first;
        }

        public void setFirst(boolean first) {
            this.first = first;
        }

        public boolean isLast() {
            return last;
        }

        public void setLast(boolean last) {
            this.last = last;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public SortBean getSort() {
            return sort;
        }

        public void setSort(SortBean sort) {
            this.sort = sort;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class SortBean {
        }

        public static class ContentBean {
            /**
             * bz :
             * companyId : 402894b3636bddff01636be319030003
             * companyName :
             * id : 402894b3636bddff01636bf5aa7600f6
             * isActive : 1
             * optime : {"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":32,"time":1526524652000,"timezoneOffset":-480,"year":118}
             * qdlx : 10501
             * qdmc : 啊实打实的
             * qdwddz : 气温气温
             */

            private String bz;
            private String companyId;
            private String companyName;
            private String id;
            private String isActive;
            private OptimeBean optime;
            private String qdlx;
            private String qdmc;
            private String qdwddz;

            public String getBz() {
                return bz;
            }

            public void setBz(String bz) {
                this.bz = bz;
            }

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIsActive() {
                return isActive;
            }

            public void setIsActive(String isActive) {
                this.isActive = isActive;
            }

            public OptimeBean getOptime() {
                return optime;
            }

            public void setOptime(OptimeBean optime) {
                this.optime = optime;
            }

            public String getQdlx() {
                return qdlx;
            }

            public void setQdlx(String qdlx) {
                this.qdlx = qdlx;
            }

            public String getQdmc() {
                return qdmc;
            }

            public void setQdmc(String qdmc) {
                this.qdmc = qdmc;
            }

            public String getQdwddz() {
                return qdwddz;
            }

            public void setQdwddz(String qdwddz) {
                this.qdwddz = qdwddz;
            }

            public static class OptimeBean {
                /**
                 * date : 17
                 * day : 4
                 * hours : 10
                 * minutes : 37
                 * month : 4
                 * nanos : 0
                 * seconds : 32
                 * time : 1526524652000
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
}
