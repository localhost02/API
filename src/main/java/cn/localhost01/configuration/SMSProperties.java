package cn.localhost01.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:短信配置类
 * @Author Ran.chunlin
 * @Date: Created in 17:43 2017/12/16
 */
@Configuration
@ConfigurationProperties(prefix = "sms")public class SmsProperties {
    private String url;
    private String userName;
    private String password;
    private String content;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
