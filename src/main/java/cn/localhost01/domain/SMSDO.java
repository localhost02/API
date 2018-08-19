package cn.localhost01.domain;

/**
 * @Description:短信实体类
 * @Author Ran.chunlin
 * @Date: Created in 14:57 2017/12/17
 */
public class SmsDO {

    private String url;
    private String userName;
    private String password;
    private String phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
