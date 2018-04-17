package cn.localhost01.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public abstract class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * Description: 向指定URL发送GET请求 eq：请求参数为 ?name1=value1&name2=value2 的形式<BR>
     *
     * @param url
     *
     * @return
     *
     * @author ran.chunlin
     * @date 2017年7月19日 下午5:16:06
     * @version 1.0
     */
    public static String getFromURL(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            //1.构造连接
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();

            //2.请求连接
            connection.connect();

            // 3.读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null)
                result += line;

        } catch (Exception e) {
            logger.error("发送GET请求出现异常", e);
        } finally {
            //4.关闭输入流
            try {
                if (in != null)
                    in.close();
            } catch (Exception ignore) {
            }
        }
        return result;
    }

    /**
     * Description: 向指定 URL发送POST方法的请求<BR>
     *
     * @param url
     * @param params Map形式
     *
     * @return
     *
     * @author ran.chunlin
     * @date 2017年7月19日 下午5:16:06
     * @version 1.0
     */
    public static String postToURL(String url, Map<String, String> params) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        try {
            //1.构造请求URL
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //2.进行连接，并写入请求参数
            conn.connect();
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            if (params != null) {
                StringBuilder param = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    if (param.length() > 0) {
                        param.append("&");
                    }
                    param.append(entry.getKey());
                    param.append("=");
                    param.append(entry.getValue());
                }
                out.write(param.toString());
            }
            out.flush();

            //3.读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null)
                result.append(line);
        } catch (Exception e) {
            logger.error("发送GET请求出现异常", e);
        } finally {
            // 4.关闭输出流、输入流
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                logger.error("发送GET请求出现异常", ex);
            }
        }
        return result.toString();
    }

    /**
     * Description:通过request获取真实IP eq:支持nginx等反向代理情况下的获取，同时也需在相关服务器上做相应配置<BR>
     *
     * @param request
     *
     * @return
     *
     * @author ran.chunlin
     * @date 2017年10月26日 上午11:10:28
     * @version 1.0
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1)
                return ip.substring(0, index);
             else
                return ip;
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * Description:检查一个ip是否在ipRange范围内
     *
     * @param ip
     * @param ipRange
     *
     * @return
     *
     * @author ran.chunlin
     * @date 2017年10月26日 上午11:10:28
     * @version 1.0
     */
    public static boolean inIpRange(String ip, String ipRange) {
        // 1.将输入的ip去“.”后组成一个int整型
        String[] ips = ip.split("\\.");
        if (ips.length != 4)
            return false;

        int ipAddr =
                (Integer.parseInt(ips[0]) << 24) | (Integer.parseInt(ips[1]) << 16) | (Integer.parseInt(ips[2]) << 8)
                        | Integer.parseInt(ips[3]);

        // 2.得到数字型子网掩码
        int type = Integer.parseInt(ipRange.replaceAll(".*/", ""));
        /* 将 /num格式的掩码转为xx.xx.xx.xx的整数值形式 */
        int mask = type == 0 ? 0 : (0xFFFFFFFF << (32 - type));// 解决：右移32位=右移0位

        // 3.将子网掩码前面的ip组成一个int整型
        String cidrIp = ipRange.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        if (cidrIps.length != 4)
            return false;

        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24) | (Integer.parseInt(cidrIps[1]) << 16) | (
                Integer.parseInt(cidrIps[2]) << 8) | Integer.parseInt(cidrIps[3]);

        // 4.比较
        return (ipAddr & mask) == (cidrIpAddr & mask);// ip&掩码是否相等来判断两个ip是否在同一个网段
    }
}
