package customer.tcrj.com.zsproject.bean;

import java.util.List;

/**
 * Created by leict on 2018/8/9.
 */

public class dlsInfo {

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : {"content":[{"address":"啊实打实的","bz":"阿萨德","companyId":"402894b3636bddff01636be319030003","dlqy":"啊实打实的","dlslb":"10201","dlsmc":"陕西润和有限公司","id":"402894b3636bddff01636bf5668900f2","isActive":"1","lxdh":"123123123123","lxr":"阿萨德","optime":{"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":14,"time":1526524634000,"timezoneOffset":-480,"year":118}},{"address":"啊实打实的撒多","bz":"阿萨德","companyId":"402894b3636bddff01636be319030003","dlqy":"啊实打实的","dlslb":"10205","dlsmc":"小门店","id":"402894b3636bddff01636bf3bb9c00f0","isActive":"1","lxdh":"1815255656","lxr":"比如","optime":{"date":17,"day":4,"hours":10,"minutes":35,"month":4,"nanos":0,"seconds":25,"time":1526524525000,"timezoneOffset":-480,"year":118}}],"first":true,"last":true,"number":0,"numberOfElements":2,"size":10,"sort":{},"totalElements":2,"totalPages":1}
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
         * content : [{"address":"啊实打实的","bz":"阿萨德","companyId":"402894b3636bddff01636be319030003","dlqy":"啊实打实的","dlslb":"10201","dlsmc":"陕西润和有限公司","id":"402894b3636bddff01636bf5668900f2","isActive":"1","lxdh":"123123123123","lxr":"阿萨德","optime":{"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":14,"time":1526524634000,"timezoneOffset":-480,"year":118}},{"address":"啊实打实的撒多","bz":"阿萨德","companyId":"402894b3636bddff01636be319030003","dlqy":"啊实打实的","dlslb":"10205","dlsmc":"小门店","id":"402894b3636bddff01636bf3bb9c00f0","isActive":"1","lxdh":"1815255656","lxr":"比如","optime":{"date":17,"day":4,"hours":10,"minutes":35,"month":4,"nanos":0,"seconds":25,"time":1526524525000,"timezoneOffset":-480,"year":118}}]
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
             * address : 啊实打实的
             * bz : 阿萨德
             * companyId : 402894b3636bddff01636be319030003
             * dlqy : 啊实打实的
             * dlslb : 10201
             * dlsmc : 陕西润和有限公司
             * id : 402894b3636bddff01636bf5668900f2
             * isActive : 1
             * lxdh : 123123123123
             * lxr : 阿萨德
             * optime : {"date":17,"day":4,"hours":10,"minutes":37,"month":4,"nanos":0,"seconds":14,"time":1526524634000,"timezoneOffset":-480,"year":118}
             */

            private String address;
            private String bz;
            private String companyId;
            private String dlqy;
            private String dlslb;
            private String dlsmc;
            private String id;
            private String isActive;
            private String lxdh;
            private String lxr;
            private OptimeBean optime;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

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

            public String getDlqy() {
                return dlqy;
            }

            public void setDlqy(String dlqy) {
                this.dlqy = dlqy;
            }

            public String getDlslb() {
                return dlslb;
            }

            public void setDlslb(String dlslb) {
                this.dlslb = dlslb;
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

            public String getIsActive() {
                return isActive;
            }

            public void setIsActive(String isActive) {
                this.isActive = isActive;
            }

            public String getLxdh() {
                return lxdh;
            }

            public void setLxdh(String lxdh) {
                this.lxdh = lxdh;
            }

            public String getLxr() {
                return lxr;
            }

            public void setLxr(String lxr) {
                this.lxr = lxr;
            }

            public OptimeBean getOptime() {
                return optime;
            }

            public void setOptime(OptimeBean optime) {
                this.optime = optime;
            }

            public static class OptimeBean {
                /**
                 * date : 17
                 * day : 4
                 * hours : 10
                 * minutes : 37
                 * month : 4
                 * nanos : 0
                 * seconds : 14
                 * time : 1526524634000
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
