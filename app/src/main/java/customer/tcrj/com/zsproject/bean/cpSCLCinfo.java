package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leict on 2018/6/21.
 */

public class cpSCLCinfo implements Serializable{

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : {"content":[{"description":"","id":"402894b3639155a501639156eaa40001","name":"耕地","optime":{"date":24,"day":4,"hours":16,"minutes":49,"month":4,"nanos":0,"seconds":42,"time":1527151782000,"timezoneOffset":-480,"year":118},"productId":"P18051710002","sort":"1"},{"description":"","id":"402894b3639155a50163915717300003","name":"播种","optime":{"date":24,"day":4,"hours":16,"minutes":49,"month":4,"nanos":0,"seconds":53,"time":1527151793000,"timezoneOffset":-480,"year":118},"productId":"P18051710002","sort":"2"},{"description":"","id":"402894b3639155a5016391572d570005","name":"打药","optime":{"date":24,"day":4,"hours":16,"minutes":49,"month":4,"nanos":0,"seconds":59,"time":1527151799000,"timezoneOffset":-480,"year":118},"productId":"P18051710002","sort":"3"}],"first":true,"last":true,"number":0,"numberOfElements":3,"size":10,"sort":{},"totalElements":3,"totalPages":1}
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

    public static class DataBean implements Serializable{
        /**
         * content : [{"description":"","id":"402894b3639155a501639156eaa40001","name":"耕地","optime":{"date":24,"day":4,"hours":16,"minutes":49,"month":4,"nanos":0,"seconds":42,"time":1527151782000,"timezoneOffset":-480,"year":118},"productId":"P18051710002","sort":"1"},{"description":"","id":"402894b3639155a50163915717300003","name":"播种","optime":{"date":24,"day":4,"hours":16,"minutes":49,"month":4,"nanos":0,"seconds":53,"time":1527151793000,"timezoneOffset":-480,"year":118},"productId":"P18051710002","sort":"2"},{"description":"","id":"402894b3639155a5016391572d570005","name":"打药","optime":{"date":24,"day":4,"hours":16,"minutes":49,"month":4,"nanos":0,"seconds":59,"time":1527151799000,"timezoneOffset":-480,"year":118},"productId":"P18051710002","sort":"3"}]
         * first : true
         * last : true
         * number : 0
         * numberOfElements : 3
         * size : 10
         * sort : {}
         * totalElements : 3
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

        public static class SortBean implements Serializable{
        }

        public static class ContentBean implements Serializable{
            /**
             * description :
             * id : 402894b3639155a501639156eaa40001
             * name : 耕地
             * optime : {"date":24,"day":4,"hours":16,"minutes":49,"month":4,"nanos":0,"seconds":42,"time":1527151782000,"timezoneOffset":-480,"year":118}
             * productId : P18051710002
             * sort : 1
             */

            private String description;
            private String id;
            private String name;
            private OptimeBean optime;
            private String productId;
            private String sort;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public static class OptimeBean implements Serializable{
                /**
                 * date : 24
                 * day : 4
                 * hours : 16
                 * minutes : 49
                 * month : 4
                 * nanos : 0
                 * seconds : 42
                 * time : 1527151782000
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
