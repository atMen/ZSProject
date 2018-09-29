package customer.tcrj.com.zsproject.net;

/**
 * desc:
 * author: Will .
 * date: 2017/9/2 .
 */
public class ApiConstants {


    //http://192.168.20.51:8888/traceability/personRest/login
    //外网  221.11.18.88  traceability
    private static final String URLROOT = "http://221.11.18.88:9999/";
    public static final String ImageURLROOT = "http://221.11.18.88:9999/";//回测
    public static final String YPTURLROOT = "http://221.11.18.88:9999/web.files";//回测

    public static final String EWMURLROOT = "http://221.11.18.88:9999/front/product/showProduct.chtml?productId=";


    /**
     * 修改密码
     */
    public static final String uploadfileApi = URLROOT+"companyRest/addCompanyResource";


    /**
     * 产品销售流程查询
     */
    public static final String xslcInfoApi = URLROOT+"productRest/findAllProductChuhuo";
    /**
     * 代理商数据
     */
    public static final String dlsApi = URLROOT+"companyRest/findAllAgent";

    /**
     * 销售渠道数据
     */
    public static final String xsqdApi = URLROOT+"companyRest/findAllXsqd";

    /**
     * 添加销售渠道数据
     */
    public static final String addProductApi = URLROOT+"productRest/addProductChuhuo";


    /**
     *提交产销流程
     */
    public static final String tjcxlcApi = URLROOT+"productRest/submitProduct";

    /**
     * companyRest/findAllBase
     */
    public static final String jdinfoApi = URLROOT+"companyRest/findAllBase";

    /**
     * 修改密码
     */
    public static final String mdifypswApi = URLROOT+"personRest/updatePassword";

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
     * productRest/getByProductId
     * 产品信息详情
     */
    public static final String cpinfoApi = URLROOT+"productRest/getByProductId";
    /**
     * dictionaryRest/getDicById
     * 字典详情
     */
    public static final String getdicbyidinfoApi = URLROOT+"dictionaryRest/getDicById";

    /**
     * 产品加工过程列表
     */
    public static final String cpjglistinfoApi = URLROOT+"productRest/findAllProductProcess";

    /**
     * 添加产品加工过程
     */
    public static final String addcpjglistinfoApi = URLROOT+"productRest/addProductProcess";

    /**
     * 品加工过程模板数据
     */
    public static final String sclcmbApi = URLROOT+"productRest/findAllProductProcessBase";


    /**
     * 添加产品信息
     */
    public static final String addcplistinfoApi = URLROOT+"productRest/addProduct";

    /**
     * 修改产品信息
     *
     */
    public static final String updatecpinfoApi = URLROOT+"productRest/updateProduct";

    /**
     * 删除产品加工过程
     */
    public static final String removecpjglistinfoApi = URLROOT+"productRest/deleteProductProcess";

    /**
     * 修改产品加工过程
     *
     */
    public static final String updatecpjglistinfoApi = URLROOT+"productRest/updateProductProcess";


    /**
     * 删除产品销售过程
     */
    public static final String removecpxslistinfoApi = URLROOT+"productRest/deleteProductChuhuo";


    /**
     * 企业资源库列表
     */
    public static final String qyzylistinfoApi = URLROOT+"companyRest/getResourceByCompanyId";


    /**
     * 企业资源库删除
     */
    public static final String qyzydeleinfoApi = URLROOT+"companyRest/deletetResourceById";

}
