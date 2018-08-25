package cn.localhost01.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public abstract class HttpUtil {

    /**
     * 登录网站，获取cookie
     *
     * @param url 请求地址
     * @param params <paramKey,paramValue>
     *
     * @return 登录cookie
     */
    public static String login(String url, Map<String, String> params) {
        String responseCookie = null;

        HttpURLConnection connection = null;
        BufferedReader br = null;
        OutputStream os = null;
        try {
            //1.构造请求
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);//允许连接提交信息
            connection.setRequestMethod("POST");//网页默认“GET”提交方式
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:57.0) Gecko/20100101 Firefox/57.0");

            //2.写入请求参数
            String postPrams = "&&";
            for (Map.Entry entry : params.entrySet()) {
                postPrams += ("&" + entry.getKey() + "=" + entry.getValue());
            }
            postPrams = postPrams.replace("&&&", "");
            PrintWriter writer = new PrintWriter(connection.getOutputStream());
            // 发送请求参数
            writer.write(postPrams);
            // flush输出流的缓冲
            writer.flush();

            //3.根据返回判断是否登录成功，有时返回302，需要重新登录
            if (connection.getResponseCode() == 302) {
                String location = connection.getHeaderField("Location");
                return login(location, params);
            }
            if (connection.getResponseCode() == 301) {
                String location = connection.getHeaderField("Location");
                URL realUrl2 = new URL(location);
                connection = (HttpURLConnection) realUrl2.openConnection();
                responseCookie = String.join(";", connection.getHeaderFields().get("Set-Cookie"));
                return responseCookie;
            }

            StringBuilder result = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line + "\n");
            }
        } catch (Exception e) {
            LogUtil.getLogger(HttpUtil.class).error("发送GET请求出现异常", e);
        } finally {
            try {
                if (os != null)
                    os.close();
                if (br != null)
                    br.close();
            } catch (IOException e) {
                LogUtil.getLogger(HttpUtil.class).error("发送GET请求出现异常", e);
            }
        }

        //4.获取响应Cookie
        responseCookie = String.join(";", connection.getHeaderFields().get("Set-Cookie"));

        return responseCookie;
    }

    /**
     * Description: 向指定URL发送GET请求 eq：请求参数为 ?name1=value1&name2=value2 的形式<BR>
     *
     * @param url 请求网址
     * @param cookie 登录cookie
     *
     * @return
     *
     * @author ran.chunlin
     * @date 2017年7月19日 下午5:16:06
     * @version 1.0
     */
    public static String getResultByURL(String url, String cookie) {
        //读取URL的响应
        String result = "";
        try (InputStream is = getConnectionByURL(url, cookie).getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = in.readLine()) != null)
                result += line + "\n";
        } catch (Exception e) {
            LogUtil.getLogger(HttpUtil.class).error("解析Get结果出现异常", e);
        }
        return result;
    }

    /**
     * Description: 向指定URL发送GET请求 eq：请求参数为 ?name1=value1&name2=value2 的形式<BR>
     *
     * @param url 请求网址
     * @param cookie 登录cookie
     *
     * @return
     *
     * @author ran.chunlin
     * @date 2017年7月19日 下午5:16:06
     * @version 1.0
     */
    public static HttpURLConnection getConnectionByURL(String url, String cookie) {
        HttpURLConnection connection = null;
        try {
            //1.构造连接
            URL realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();
            //2.设置cookie（常用于登录）
            connection.setRequestProperty("Cookie", cookie);
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

            //3.根据返回判断是否成功
            if (connection.getResponseCode() == 302) {
                String location = connection.getHeaderField("Location");
                return getConnectionByURL(location, cookie);
            }
        } catch (Exception e) {
            LogUtil.getLogger(HttpUtil.class).error("发送GET请求出现异常", e);
        }

        //3.返回连接
        return connection;
    }

    /**
     * Description: 向指定URL发送GET请求 eq：请求参数为 ?name1=value1&name2=value2 的形式<BR>
     *
     * @param url 请求网址
     *
     * @return
     *
     * @author ran.chunlin
     * @date 2017年7月19日 下午5:16:06
     * @version 1.0
     */
    public static String getResultByURL(String url) {
        return getResultByURL(url, "");
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
    public static String postByURL(String url, Map<String, String> params) {
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
            LogUtil.getLogger(HttpUtil.class).error("发送GET请求出现异常", e);
        } finally {
            // 4.关闭输出流、输入流
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                LogUtil.getLogger(HttpUtil.class).error("发送GET请求出现异常", ex);
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
     * 判断是否是本地网络
     *
     * @param ip
     *
     * @return
     */
    public static boolean isLocalNetwork(String ip) {
        if ("127.0.0.1".equals(ip))
            return true;
        if ("0.0.0.0".equals(ip))
            return true;
        if ("localhost".equals(ip))
            return true;
        return false;
    }
}
