package customer.tcrj.com.zsproject.net;

/**
 * desc:
 * author: Will .
 * date: 2017/9/2 .
 */
public class ApiConstants {


//外网  221.11.18.88  traceability
    private static final String URLROOT = "http://221.11.18.88:9999/";
    public static final String ImageURLROOT = "http://221.11.18.88:9999/";






    /**
     * 获取字典信息
     */
    public static final String zdlistinfoApi = URLROOT+"dictionaryRest/findByParentId";


    /**
     * 登录
     */
    public static final String loginApi = URLROOT+"personRest/login";

    /**
     * 产品信息列表
     */
    public static final String cplistinfoApi = URLROOT+"productRest/findAllProduct";

    /**
     * 产品加工过程列表
     */
    public static final String cpjglistinfoApi = URLROOT+"productRest/findAllProductProcess";


    /**
     * traceability/productRest/addProductProcess
     */
    /**
     * 添加产品加工过程
     */
    public static final String addcpjglistinfoApi = URLROOT+"productRest/addProductProcess";

    /**
     * 删除产品加工过程
     */
    public static final String removecpjglistinfoApi = URLROOT+"productRest/deleteProductProcess";

    /**
     * productRest/updateProductProcess

     *
     */
    public static final String updatecpjglistinfoApi = URLROOT+"productRest/updateProductProcess";

}
