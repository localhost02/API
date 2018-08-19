package cn.localhost01.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @Description:邮件配置类
 * @Author Ran.chunlin
 * @Date: Created in 17:43 2017/12/16
 */
@Configuration @ConfigurationProperties(prefix = "mail") public class MailProperties extends Authenticator {
    private String host;
    private int port;
    private String sender;
    private String password;
    private String attachmentSubject;
    private String attachmentContent;

    @Override protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(sender, password);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAttachmentSubject() {
        return attachmentSubject;
    }

    public void setAttachmentSubject(String attachmentSubject) {
        this.attachmentSubject = attachmentSubject;
    }

    public String getAttachmentContent() {
        return attachmentContent;
    }

    public void setAttachmentContent(String attachmentContent) {
        this.attachmentContent = attachmentContent;
    }
}
