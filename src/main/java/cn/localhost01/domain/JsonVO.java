package cn.localhost01.domain;

import cn.localhost01.constant.APICode;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * @Description:
 * @Author Ran.chunlin
 * @Date: Created in 21:10 2017/12/16
 */
public class JsonVO extends HashMap<String, Object> {

    //默认初始大小
    public JsonVO() {
        super(2);
    }

    //手动设置初始大小
    public JsonVO(int capacity) {
        super(capacity);
    }

    public JsonVO withBase(int code, String msg) {
        this.put("code", code);
        this.put("msg", msg);
        return this;
    }

    public JsonVO withSub(int subCode, String subMsg) {
        this.put("code", APICode.OK);
        this.put("sub_code", subCode);
        this.put("sub_msg", subMsg);
        return this;
    }

    public String withBaseStr(int code, String msg) {
        return JSON.toJSONString(withBase(code, msg));
    }

    public String withSubStr(int subCode, String subMsg) {
        return JSON.toJSONString(withSub(subCode, subMsg));
    }
}
