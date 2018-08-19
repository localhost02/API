package cn.localhost01.constant;

/**
 * @Description:返回状态码
 * @Author Ran.chunlin
 * @Date: Created in 20:42 2017/12/16
 */
public interface APISubMsg {
    String SUCCESS = "执行成功";
    String FAILED = "执行失败";
    String OVER_LIMIT = "请求超出每日限制次数";
    String NOT_HOME_USER = "非三大运营商用户，禁止访问";
}
