package cn.localhost01.domain;

import cn.localhost01.constant.APICode;

import java.util.HashMap;

/**
 * @Description:
 * @Author Ran.chunlin
 * @Date: Created in 21:10 2017/12/16
 */
public class JsonVO extends HashMap<String,Object>{

    //默认初始大小
    public JsonVO(){
        super(2);
    }

    //手动设置初始大小
    public JsonVO(int capacity){
        super(capacity);
    }

    public JsonVO withCodeAndMsg(int code,String msg){
        this.put("code",code);
        this.put("msg",msg);
        return this;
    }

    public JsonVO withSubCodeAndSubMsg(int subCode,String subMsg){
        this.put("code", APICode.OK);
        this.put("sub_code",subCode);
        this.put("sub_msg",subMsg);
        return this;
    }
}
