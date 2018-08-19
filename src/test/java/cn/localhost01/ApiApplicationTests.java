package cn.localhost01;

import cn.localhost01.configuration.QiniuProperties;
import cn.localhost01.domain.MailDO;
import cn.localhost01.service.MailService;
import cn.localhost01.util.QiniuUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.MimeBodyPart;

@RunWith(SpringRunner.class) @SpringBootTest public class ApiApplicationTests {
    @Autowired MailService mailService;
    @Autowired QiniuProperties qiniuProperties;

    @Ignore public void testSendMailWithAttachFile() throws Exception {
        MailDO mailDO = new MailDO();
        mailDO.setReceiver("test@qq.com");
        mailDO.setContent("test~~~~");
        mailDO.setSubject("subject~~~~");
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.attachFile("E:\\PC_WorkSpace\\Google_Tmp\\Download\\新建文件夹.rar");
        mailDO.setAttachmentPart(bodyPart);
        mailService.send(mailDO);
    }

    @Test public void testUploadQiniu() throws Exception {
        QiniuUtil qiniuUtil = new QiniuUtil(qiniuProperties);
        System.out.println(qiniuUtil.upload("C:\\Robot_Download\\新建文本文档.txt"));
    }
}
