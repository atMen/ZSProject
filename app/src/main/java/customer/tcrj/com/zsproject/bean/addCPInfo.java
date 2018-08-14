package customer.tcrj.com.zsproject.bean;

/**
 * Created by leict on 2018/8/14.
 */

public class addCPInfo {

    /**
     * errorcode : 9999
     * message : 操作成功。
     * data : {"id":"P18052500003"}
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
         * id : P18052500003
         */

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
