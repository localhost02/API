package cn.localhost01.constant;

/**
 * @Description:返回状态码
 * @Author Ran.chunlin
 * @Date: Created in 20:42 2017/12/16
 */
public interface APIMsg {
    String OK = "请求成功";
    String BAD_REQUEST = "错误的请求";
    String INTERNAL_SERVER_ERROR = "服务器发生错误";
    String NOT_FOUND = "不能找到请求的资源";
    String METHOD_NOT_SUPPORTED = "不支持的请求方法";
    String MEDIA_NOT_SUPPORTED = "不支持的媒体类型";
    String FORBIDDEN = "请求被禁止";
    String UNAUTHORIZED = "未进行认证，禁止请求";
}
