package customer.tcrj.com.zsproject.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leict on 2018/6/20.
 */

public class cpInfo implements Serializable{

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : {"content":[{"baseId":"402894b3636bddff01636bf15d7600ea","bxq":"1","bzbh":"","bzmc":"","companyId":"402894b3636bddff01636be319030003","cpbcgg":"1","cpbcggdw":"1","cplx":"10001","cpmc":"1","cpms":"<p>1<\/p>","cppp":"1","cpsx":"","cpycd":"","ewmsl":"1","fwyzm":"19746381","id":"P18061310001","jdmc":"","jylsj":"","optime":{"date":13,"day":3,"hours":9,"minutes":37,"month":5,"nanos":0,"seconds":38,"time":1528853858000,"timezoneOffset":-480,"year":118},"sjzl":"","sjzldw":"","source":"1","status":"1","timestamp":"2018-06-13 09:37:38","xcsp":"","xctp":"","ypt":"/web.files/uploadfile//2018-06-13/20180613093732060.jpg","zsfs":"10601","zsywlb":"10701"}],"first":true,"last":false,"number":0,"numberOfElements":1,"size":1,"sort":{},"totalElements":6,"totalPages":6}
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
         * content : [{"baseId":"402894b3636bddff01636bf15d7600ea","bxq":"1","bzbh":"","bzmc":"","companyId":"402894b3636bddff01636be319030003","cpbcgg":"1","cpbcggdw":"1","cplx":"10001","cpmc":"1","cpms":"<p>1<\/p>","cppp":"1","cpsx":"","cpycd":"","ewmsl":"1","fwyzm":"19746381","id":"P18061310001","jdmc":"","jylsj":"","optime":{"date":13,"day":3,"hours":9,"minutes":37,"month":5,"nanos":0,"seconds":38,"time":1528853858000,"timezoneOffset":-480,"year":118},"sjzl":"","sjzldw":"","source":"1","status":"1","timestamp":"2018-06-13 09:37:38","xcsp":"","xctp":"","ypt":"/web.files/uploadfile//2018-06-13/20180613093732060.jpg","zsfs":"10601","zsywlb":"10701"}]
         * first : true
         * last : false
         * number : 0
         * numberOfElements : 1
         * size : 1
         * sort : {}
         * totalElements : 6
         * totalPages : 6
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

        public static class ContentBean implements Serializable{
            /**
             * baseId : 402894b3636bddff01636bf15d7600ea
             * bxq : 1
             * bzbh :
             * bzmc :
             * companyId : 402894b3636bddff01636be319030003
             * cpbcgg : 1
             * cpbcggdw : 1
             * cplx : 10001
             * cpmc : 1
             * cpms : <p>1</p>
             * cppp : 1
             * cpsx :
             * cpycd :
             * ewmsl : 1
             * fwyzm : 19746381
             * id : P18061310001
             * jdmc :
             * jylsj :
             * optime : {"date":13,"day":3,"hours":9,"minutes":37,"month":5,"nanos":0,"seconds":38,"time":1528853858000,"timezoneOffset":-480,"year":118}
             * sjzl :
             * sjzldw :
             * source : 1
             * status : 1
             * timestamp : 2018-06-13 09:37:38
             * xcsp :
             * xctp :
             * ypt : /web.files/uploadfile//2018-06-13/20180613093732060.jpg
             * zsfs : 10601
             * zsywlb : 10701
             */

            private String baseId;
            private String bxq;
            private String bzbh;
            private String bzmc;
            private String companyId;
            private String cpbcgg;
            private String cpbcggdw;
            private String cplx;
            private String cpmc;
            private String cpms;
            private String cppp;
            private String cpsx;
            private String cpycd;
            private String ewmsl;
            private String fwyzm;
            private String id;
            private String jdmc;
            private String jylsj;
            private OptimeBean optime;
            private String sjzl;
            private String sjzldw;
            private String source;
            private String status;
            private String timestamp;
            private String xcsp;
            private String xctp;
            private String ypt;
            private String zsfs;
            private String zsywlb;

            public boolean isselect() {
                return isselect;
            }

            public void setIsselect(boolean isselect) {
                this.isselect = isselect;
            }

            private boolean isselect;

            public String getBaseId() {
                return baseId;
            }

            public void setBaseId(String baseId) {
                this.baseId = baseId;
            }

            public String getBxq() {
                return bxq;
            }

            public void setBxq(String bxq) {
                this.bxq = bxq;
            }

            public String getBzbh() {
                return bzbh;
            }

            public void setBzbh(String bzbh) {
                this.bzbh = bzbh;
            }

            public String getBzmc() {
                return bzmc;
            }

            public void setBzmc(String bzmc) {
                this.bzmc = bzmc;
            }

            public String getCompanyId() {
                return companyId;
            }

            public void setCompanyId(String companyId) {
                this.companyId = companyId;
            }

            public String getCpbcgg() {
                return cpbcgg;
            }

            public void setCpbcgg(String cpbcgg) {
                this.cpbcgg = cpbcgg;
            }

            public String getCpbcggdw() {
                return cpbcggdw;
            }

            public void setCpbcggdw(String cpbcggdw) {
                this.cpbcggdw = cpbcggdw;
            }

            public String getCplx() {
                return cplx;
            }

            public void setCplx(String cplx) {
                this.cplx = cplx;
            }

            public String getCpmc() {
                return cpmc;
            }

            public void setCpmc(String cpmc) {
                this.cpmc = cpmc;
            }

            public String getCpms() {
                return cpms;
            }

            public void setCpms(String cpms) {
                this.cpms = cpms;
            }

            public String getCppp() {
                return cppp;
            }

            public void setCppp(String cppp) {
                this.cppp = cppp;
            }

            public String getCpsx() {
                return cpsx;
            }

            public void setCpsx(String cpsx) {
                this.cpsx = cpsx;
            }

            public String getCpycd() {
                return cpycd;
            }

            public void setCpycd(String cpycd) {
                this.cpycd = cpycd;
            }

            public String getEwmsl() {
                return ewmsl;
            }

            public void setEwmsl(String ewmsl) {
                this.ewmsl = ewmsl;
            }

            public String getFwyzm() {
                return fwyzm;
            }

            public void setFwyzm(String fwyzm) {
                this.fwyzm = fwyzm;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getJdmc() {
                return jdmc;
            }

            public void setJdmc(String jdmc) {
                this.jdmc = jdmc;
            }

            public String getJylsj() {
                return jylsj;
            }

            public void setJylsj(String jylsj) {
                this.jylsj = jylsj;
            }

            public OptimeBean getOptime() {
                return optime;
            }

            public void setOptime(OptimeBean optime) {
                this.optime = optime;
            }

            public String getSjzl() {
                return sjzl;
            }

            public void setSjzl(String sjzl) {
                this.sjzl = sjzl;
            }

            public String getSjzldw() {
                return sjzldw;
            }

            public void setSjzldw(String sjzldw) {
                this.sjzldw = sjzldw;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getXcsp() {
                return xcsp;
            }

            public void setXcsp(String xcsp) {
                this.xcsp = xcsp;
            }

            public String getXctp() {
                return xctp;
            }

            public void setXctp(String xctp) {
                this.xctp = xctp;
            }

            public String getYpt() {
                return ypt;
            }

            public void setYpt(String ypt) {
                this.ypt = ypt;
            }

            public String getZsfs() {
                return zsfs;
            }

            public void setZsfs(String zsfs) {
                this.zsfs = zsfs;
            }

            public String getZsywlb() {
                return zsywlb;
            }

            public void setZsywlb(String zsywlb) {
                this.zsywlb = zsywlb;
            }

            public static class OptimeBean implements Serializable{
                /**
                 * date : 13
                 * day : 3
                 * hours : 9
                 * minutes : 37
                 * month : 5
                 * nanos : 0
                 * seconds : 38
                 * time : 1528853858000
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
