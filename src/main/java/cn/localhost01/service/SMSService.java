package cn.localhost01.service;

import cn.localhost01.domain.SmsDO;

/**
 * @Description:短信发送接口
 * @Author Ran.chunlin
 * @Date: Created in 14:55 2017/12/17
 */
public interface SmsService {

    /**
     * Description:发送短信 <BR>
     *
     * @author ran.chunlin
     * @date 2017/12/16 18:16
     * @param  smsDO
     * @return boolean 是否发送成功
     * @throws Exception
     * @version 1.0
     */
    boolean send(SmsDO smsDO) throws  Exception;
}
