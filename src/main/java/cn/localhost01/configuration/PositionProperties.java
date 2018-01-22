package cn.localhost01.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:位置配置类
 * @Author Ran.chunlin
 * @Date: Created in 17:43 2017/12/16
 */
@Configuration
@ConfigurationProperties(prefix = "position")public class PositionProperties {
    private String url;
    private String ak;
    private String coor;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getCoor() {
        return coor;
    }

    public void setCoor(String coor) {
        this.coor = coor;
    }
}
