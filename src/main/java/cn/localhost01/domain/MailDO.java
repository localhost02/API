package cn.localhost01.domain;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @Description:邮件实体类
 * @Author Ran.chunlin
 * @Date: Created in 1:59 2017/12/16
 */
public class MailDO extends Authenticator {

    private String host;// 邮件服务器
    private int port;// 端口
    private String sender;// 发送者
    private String password;// 登录密码
    private String receiver;// 接受者
    private String subject;// 主题
    private String content;// 内容

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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
