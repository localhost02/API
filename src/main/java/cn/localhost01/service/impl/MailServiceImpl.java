package cn.localhost01.service.impl;

import cn.localhost01.domain.MailDO;
import cn.localhost01.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @Description:邮件发送实现类
 * @Author Ran.chunlin
 * @Date: Created in 18:15 2017/12/16
 */
@Service public class MailServiceImpl implements MailService {

    private static final Logger logger= LoggerFactory.getLogger(MailServiceImpl.class);

    @Override public boolean send(MailDO mailDO) throws Exception {

        //1.验证参数
        if (mailDO == null)
            return false;

        //2.构造及默认配置
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mailDO.getHost());
        props.put("mail.smtp.port", mailDO.getPort());

        //3.获取session
        Session session = Session.getDefaultInstance(props, mailDO);// 一次对话，一个session
        session.setDebug(false);

        //4.构造邮件
        MimeMessage meg = new MimeMessage(session);// 生成消息实例
        meg.setFrom(new InternetAddress(mailDO.getSender()));
        InternetAddress[] receiveAddressArray = { new InternetAddress(mailDO.getReceiver()) };
        meg.setRecipients(Message.RecipientType.TO, receiveAddressArray);// 指定收件人数组
        meg.setSubject(mailDO.getSubject());
        meg.setContent(mailDO.getContent(), "text/html;charset = gbk");
        meg.setSentDate(new Date());
        meg.saveChanges();// 保存信息

        //5.发送邮件
        try {
            Transport.send(meg);
        } catch (MessagingException e) {
            logger.warn(mailDO.getReceiver()+"的邮件发送失败",e);
            return false;
        }
        return true;
    }
}
