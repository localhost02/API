package cn.localhost01.service;

import cn.localhost01.domain.MailDO;

/**
 * @Description:邮件发送接口
 * @Author Ran.chunlin
 * @Date: Created in 1:56 2017/12/16
 */
public interface MailService {

    /**
     * Description:发送邮件 <BR>
     *
     * @author ran.chunlin
     * @date 2017/12/16 18:16
     * @param  mailDO
     * @return boolean 是否发送成功
     * @throws Exception
     * @version 1.0
     */
    boolean send(MailDO mailDO) throws  Exception;
}
