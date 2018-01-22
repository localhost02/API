package cn.localhost01.constant;

/**
 * @Description:返回状态码
 * @Author Ran.chunlin
 * @Date: Created in 20:42 2017/12/16
 */
public interface APICode {
    int OK = 200;
    int BAD_REQUEST = 400;
    int INTERNAL_SERVER_ERROR = 500;
    int NOT_FOUND = 404;
    int METHOD_NOT_SUPPORTED = 405;
    int MEDIA_NOT_SUPPORTED = 415;
    int FORBIDDEN = 403;
    int UNAUTHORIZED = 401;
}
