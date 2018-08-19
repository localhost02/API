package cn.localhost01.service.impl;

import cn.localhost01.configuration.MailProperties;
import cn.localhost01.domain.MailDO;
import cn.localhost01.service.MailService;
import cn.localhost01.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

import static cn.localhost01.constant.APIConstant.CONTENT_TYPE;

/**
 * @Description:邮件发送实现类
 * @Author Ran.chunlin
 * @Date: Created in 18:15 2017/12/16
 */
@Service public class MailServiceImpl implements MailService {

    @Autowired private MailProperties mailProperties;

    @Override public boolean send(MailDO mailDO) throws Exception {

        //验证参数
        if (mailDO == null)
            return false;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", mailProperties.getHost());
        props.put("mail.smtp.port", mailProperties.getPort());

        //1.获取session
        Session session = Session.getDefaultInstance(props, mailProperties);// 一次对话，一个session
        session.setDebug(false);

        try {
            //2.构造邮件
            MimeMessage meg = new MimeMessage(session);// 生成消息实例
            meg.setFrom(new InternetAddress(mailProperties.getSender()));
            InternetAddress[] receiveAddressArray = { new InternetAddress(mailDO.getReceiver()) };
            meg.setRecipients(Message.RecipientType.TO, receiveAddressArray);// 指定收件人数组
            meg.setSubject(mailDO.getSubject());

            Object content = mailDO.getContent();
            //2.1如果存在附件
            if (mailDO.getAttachmentPart() != null) {
                Multipart multipart = new MimeMultipart();
                if (content != null && !"".equals(content)) {
                    //2.2如果存在内容
                    BodyPart bp = new MimeBodyPart();
                    bp.setContent(content, CONTENT_TYPE);
                    multipart.addBodyPart(bp);
                }
                multipart.addBodyPart(mailDO.getAttachmentPart());
                content = multipart;
            }
            meg.setContent(content, CONTENT_TYPE);
            meg.setSentDate(new Date());
            meg.saveChanges();// 保存信息

            //3.发送邮件
            Transport.send(meg);
        } catch (MessagingException e) {
            LogUtil.getLogger(MailServiceImpl.class).warn(mailDO.getReceiver() + "的邮件发送失败", e);
            return false;
        }
        return true;
    }
}
