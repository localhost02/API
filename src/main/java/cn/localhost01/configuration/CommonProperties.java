package cn.localhost01.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:公共配置类
 * @Author Ran.chunlin
 * @Date: Created in 17:43 2017/12/16
 */
@Configuration @ConfigurationProperties(prefix = "common") public class CommonProperties {
    private String qqwryPath;
    private int requestLimit;

    public String getQqwryPath() {
        return qqwryPath;
    }

    public void setQqwryPath(String qqwryPath) {
        this.qqwryPath = qqwryPath;
    }

    public int getRequestLimit() {
        return requestLimit;
    }

    public void setRequestLimit(int requestLimit) {
        this.requestLimit = requestLimit;
    }
}
