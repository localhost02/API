package cn.localhost01.domain;

import javax.mail.BodyPart;

/**
 * @Description:邮件实体类
 * @Author Ran.chunlin
 * @Date: Created in 1:59 2017/12/16
 */
public class MailDO {

    private String receiver;// 接受者
    private String subject;// 主题
    private String content;// 内容
    private BodyPart attachmentPart;// 附件

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

    public BodyPart getAttachmentPart() {
        return attachmentPart;
    }

    public void setAttachmentPart(BodyPart attachmentPart) {
        this.attachmentPart = attachmentPart;
    }
}
